package imu.edu.cn.osgilauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;

import com.googlecode.transloader.DefaultTransloader;
import com.googlecode.transloader.ObjectWrapper;
import com.googlecode.transloader.Transloader;
import com.googlecode.transloader.clone.CloningStrategy;

public class OSGiAdmin {
	private static final Log log = LogFactory.getLog(OSGiAdmin.class);
	
	private static OSGiConfig osgiConfig;
	
	private static Framework framework = null;
	
	public static void startup(OSGiConfig osgiConfig, ServletContext servletContext) throws Exception {
		if (log.isInfoEnabled()) log.info("正在启动OSGi框架...");
		
		OSGiAdmin.osgiConfig = osgiConfig;
		
		// 加载FrameworkFactory.class
		Class<FrameworkFactory> frameworkFactoryClass = loadClass(FrameworkFactory.class);
		
		if (frameworkFactoryClass == null) throw new Exception("加载class FrameworkFactory 失败");
		
		FrameworkFactory frameworkFactory = (FrameworkFactory)frameworkFactoryClass.newInstance();
		Map<String, String> cofiguration = new HashMap<String, String>();
		cofiguration.put(Constants.FRAMEWORK_BEGINNING_STARTLEVEL, String.valueOf(osgiConfig.getFrameworkStartLevel()));
		framework = frameworkFactory.newFramework(cofiguration);
		
		framework.init();
		FrameworkStartLevel frameworkStartLevel = framework.adapt(FrameworkStartLevel.class);
		frameworkStartLevel.setInitialBundleStartLevel(osgiConfig.getBundlesDefaultStartLevel());
		log.info("osgi bundles 默认启动级别：" + osgiConfig.getBundlesDefaultStartLevel());
		log.info("osgi framework 启动级别：" + osgiConfig.getFrameworkStartLevel());
		
		
		initFramework(framework, servletContext);
		
		List<BundleConfig> bundleConfigs = osgiConfig.getBundleConfigs();
		for (BundleConfig bundleConfig : bundleConfigs) {
			try {
				log.info("装载bundle " + bundleConfig.getLocationUrl() + " ...");
				Bundle bundle = installBundle(bundleConfig.getLocationUrl());
				BundleStartLevel bundleStartLevel = bundle.adapt(BundleStartLevel.class);
				int lvl = bundleConfig.getStartLevel();
				if (bundleConfig.isAutoStart()
						&& (bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR) != null || bundle.getHeaders().get("Service-Component") != null)) {
					
					if (lvl > osgiConfig.getFrameworkStartLevel()) {
						// 确保bundle的startLevel在自动启动的范围内
						lvl = osgiConfig.getFrameworkStartLevel();
					}
				}
				else {
					if (lvl <= osgiConfig.getFrameworkStartLevel()) {
						// 设置bundle的startLevel大于framework的startLevel以禁止自动启动
						lvl = osgiConfig.getFrameworkStartLevel() + 1;
					}
				}
				log.info("startLevel:" + lvl);
				bundleStartLevel.setStartLevel(lvl);
				bundle.start(Bundle.START_ACTIVATION_POLICY);
				
				log.info("装载bundle " + bundleConfig.getLocationUrl() + " 成功");
			} catch (BundleException e) {
				log.info("装载bundle " + bundleConfig.getLocationUrl() + " 失败:" + e.getMessage(), e);
			}
		}
		
		// 启动Framework
		framework.start();
		log.info("osgi framework 启动后的启动级别：" + frameworkStartLevel.getStartLevel());
		
		if (log.isInfoEnabled()) log.info("启动OSGi框架成功");
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(Class<T> clazz) throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String resource = "META-INF/services/" + clazz.getName();
		InputStream in  =  classLoader.getResourceAsStream(resource);
		if (in == null) return null ;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String serviceClassName  =  reader.readLine();
			return (Class<T>)classLoader.loadClass(serviceClassName);
		} finally {
			in.close();
		}
	}
	
	public static OSGiConfig getOSGiConfig() {
		return OSGiAdmin.osgiConfig;
	}
	
	
	private static void registerContext(BundleContext bundleContext, ServletContext servletContext) {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("ServerInfo", servletContext.getServerInfo());
		properties.put("ServletContextName", servletContext.getServletContextName());
		properties.put("MajorVersion", String.valueOf(servletContext.getMajorVersion()));
		properties.put("MinorVersion", String.valueOf(servletContext.getMinorVersion()));
		bundleContext.registerService(ServletContext.class.getName(), servletContext, properties);
	}
	
	private static void initFramework(Framework framework, ServletContext servletContext) throws IOException {
		BundleContext bundleContext  =  framework.getBundleContext();
	    // 将ServletContext注册为服务
		registerContext(bundleContext, servletContext);
	}
	
	
	public static void startup() throws Exception {
		framework.start();
	}
	
	public static void shutdown() throws Exception {
		if (framework == null) return;
		if (log.isInfoEnabled()) log.info("正在停止OSGi框架...");
		
		if (framework.getState() == Framework.ACTIVE) framework.stop();
		framework.waitForStop(0);
		//framework = null;
		log.info("OSGi框架已停止");
	}
	
	private static BundleContext getBundleContext() {
		return framework.getBundleContext();
	}
	
	public static int getFrameworkState() {
		return framework.getState();
	}
	
	
	
	public static List<BundleInfo> getBundles() {
		List<BundleInfo> result = new ArrayList<BundleInfo>();
		try {
	    	Bundle[] bundles = framework.getBundleContext().getBundles();
	    	for (Bundle bundle : bundles) {
	    		BundleInfo info = new BundleInfo();
	    		info.setId(bundle.getBundleId());
	    		info.setState(bundle.getState());
	    		info.setSymbolicName(bundle.getSymbolicName());
	    		info.setLocation(bundle.getLocation());
	    		Version version = bundle.getVersion();
	    		info.setVersionMajor(version.getMajor());
	    		info.setVersionMinor(version.getMinor());
	    		info.setVersionMicro(version.getMicro());
	    		info.setVersionQualifier(version.getQualifier());
	    		
	    		result.add(info);
	    	}
		}
		catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
    	return result;
    }
	
	public static String getBundleStateStr(int state) {
		System.out.println(Bundle.INSTALLED + "INSTALLED");
		System.out.println(Bundle.RESOLVED + "RESOLVED");
		System.out.println(Bundle.STARTING + "STARTING");
		System.out.println(Bundle.ACTIVE + "ACTIVE");
		System.out.println(Bundle.STOPPING + "STOPPING");
		System.out.println(Bundle.UNINSTALLED + "UNINSTALLED");
		switch (state) {
		    case Bundle.INSTALLED : return "INSTALLED";
		    case Bundle.RESOLVED : return "RESOLVED";
		    case Bundle.STARTING : return "STARTING";
		    case Bundle.ACTIVE : return "ACTIVE";
		    case Bundle.STOPPING : return "STOPPING";
		    case Bundle.UNINSTALLED : return "UNINSTALLED";
		}
		return "UNKOWN";
	}
	
	private static Bundle getBundle(long id) {
		BundleContext bundleContext = getBundleContext();
		Bundle bundle = bundleContext.getBundle(id);
		return bundle;
	}
	
	public static void startBundle(long id) throws BundleException {
		Bundle bundle = getBundle(id);
		if (bundle == null) return;
		if (bundle.getState() != Bundle.UNINSTALLED && bundle.getState() != Bundle.ACTIVE) {
			bundle.start();
		}
	}
	
    public static void stopBundle(long id) throws BundleException {
    	Bundle bundle = getBundle(id);
		if (bundle == null) return;
		if (bundle.getState() == Bundle.ACTIVE) {
			bundle.stop();
		}
	}
    
    public static Bundle installBundle(String location) throws BundleException {
		BundleContext bundleContext = getBundleContext();
		Bundle bundle = bundleContext.installBundle(location);
		return bundle;
	}
    
    public static void uninstallBundle(long id) throws BundleException {
    	Bundle bundle = getBundle(id);
		if (bundle == null) return;
		if (bundle.getState() == Bundle.UNINSTALLED) return;
		if (bundle.getState() == Bundle.ACTIVE) {
			bundle.stop();
		}
		bundle.uninstall();
	}
    
    @SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> clazz) {
    	T result = null;
    	BundleContext bundleContext = getBundleContext();
    	ServiceReference<?> serviceRef = bundleContext.getServiceReference(clazz.getName());

    	if (null != serviceRef) {
    		Object resultObj = bundleContext.getService(serviceRef);
    		if (resultObj != null) {
    			Transloader transloader = getTransloader();
    			ObjectWrapper resultWrapped = transloader.wrap(resultObj);
                if (resultWrapped.isInstanceOf(clazz.getName())) {
                	result = (T)resultWrapped.makeCastableTo(clazz);
                }
    		}
    	}
    	
    	if (serviceRef != null) bundleContext.ungetService(serviceRef);
    	
    	return result;
    }
    
    private static Transloader getTransloader() {
    	Transloader transloader = new DefaultTransloader(CloningStrategy.MINIMAL);
    	return transloader;
    }
}