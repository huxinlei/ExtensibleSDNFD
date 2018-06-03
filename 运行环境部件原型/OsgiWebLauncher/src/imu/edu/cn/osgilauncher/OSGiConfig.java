package imu.edu.cn.osgilauncher;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OSGiConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6985436975365029797L;
	
	private static final Log log = LogFactory.getLog(OSGiConfig.class);
	private static final String DEFAULT_PATH = "osgi.bundles.defaultPath";
	private static final String FRAMEWORK_START_LEVEL = "osgi.startLevel";
	private static final String BUNDLES_DEFAULT_START_LEVEL = "osgi.bundles.defaultStartLevel";
	private static final String BUNDLES = "osgi.bundles";
	private static final String BUNDLES_SEPARATOR = ",";
	private static final String BUNDLES_NAME_SEPARATOR = "@";
	private static final String BUNDLES_STARTLEVEL_SEPARATOR = ":";
	private static final String BUNDLES_START = "start";
	
	private String defaultPath;
	private int frameworkStartLevel = 6;
	private int bundlesDefaultStartLevel = 4;
	private List<BundleConfig> bundleConfigs = new ArrayList<BundleConfig>();
	
	public String getDefaultPath() {
		return defaultPath;
	}
	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}
	public int getFrameworkStartLevel() {
		return frameworkStartLevel;
	}
	public void setFrameworkStartLevel(int frameworkStartLevel) {
		this.frameworkStartLevel = frameworkStartLevel;
	}
	public int getBundlesDefaultStartLevel() {
		return bundlesDefaultStartLevel;
	}
	public void setBundlesDefaultStartLevel(int bundlesDefaultStartLevel) {
		this.bundlesDefaultStartLevel = bundlesDefaultStartLevel;
	}
	public List<BundleConfig> getBundleConfigs() {
		return bundleConfigs;
	}
	public void setBundleConfigs(List<BundleConfig> bundleConfigs) {
		this.bundleConfigs = bundleConfigs;
	}
	
	public void load(Properties prop, ServletContext servletContext) {
		if (prop == null) return;
		setDefaultPath(servletContext.getRealPath("/") + File.separator + prop.getProperty(DEFAULT_PATH));
		
		String fsl = prop.getProperty(FRAMEWORK_START_LEVEL);
		if (fsl != null && !"".equals(fsl)) {
			try {
			    int fslInt = Integer.parseInt(fsl);
			    setFrameworkStartLevel(fslInt);
			} catch (NumberFormatException e) {
				log.warn("解析" + FRAMEWORK_START_LEVEL + "异常：" + e.getMessage(), e);
			}
		}
		
		String dsl = prop.getProperty(BUNDLES_DEFAULT_START_LEVEL);
		if (dsl != null && !"".equals(dsl)) {
			try {
			    int dslInt = Integer.parseInt(dsl);
			    setBundlesDefaultStartLevel(dslInt);
			} catch (NumberFormatException e) {
				log.warn("解析" + BUNDLES_DEFAULT_START_LEVEL + "异常：" + e.getMessage(), e);
			}
		}
		String bundlesStr = prop.getProperty(BUNDLES);
		if (bundlesStr != null && !"".equals(bundlesStr)) {
			String[] bundlesStrArr = bundlesStr.split(BUNDLES_SEPARATOR);
			for (String str : bundlesStrArr) {
				BundleConfig bundleConfig = parseBundleConfig(str);
				if (bundleConfig != null) bundleConfigs.add(bundleConfig);
			}
		}
		sortBundleConfigs();
	}
	
	private BundleConfig parseBundleConfig(String str) {
		if (str == null) return null;
		BundleConfig result = new BundleConfig();
		int idx = str.indexOf(BUNDLES_NAME_SEPARATOR);
		if (idx > 0) {
			String locationStr = str.substring(0, idx).trim();
			result.setLocationUrl(getLocationUrl(locationStr));
			str = str.substring(idx + 1);
			idx = str.indexOf(BUNDLES_STARTLEVEL_SEPARATOR);
			if (idx >= 0) {
				String startLevelStr = str.substring(0, idx);
				try {
				    int startLevel = Integer.parseInt(startLevelStr);
				    result.setStartLevel(startLevel);
				} catch (NumberFormatException e) {
					log.warn("解析数字异常：" + e.getMessage(), e);
				}
				
				str = str.substring(idx + 1);
				if (BUNDLES_START.equals(str.trim())) result.setAutoStart(true);
			}
			else {
				if (BUNDLES_START.equals(str.trim())) result.setAutoStart(true);
				result.setStartLevel(getBundlesDefaultStartLevel());
			}
		}
		else {
			result.setLocationUrl(getLocationUrl(str));
			result.setStartLevel(getBundlesDefaultStartLevel());
		}
		return result;
	}
	
	private String getLocationUrl(String str) {
		try {
			new URL(str);
		} catch (MalformedURLException e) {
			str = getDefaultPath() + "/" + str;
		}
		File file = new File(str);
		return file.toURI().toString();
	}
	
	private void sortBundleConfigs() {
		Collections.sort(bundleConfigs, new Comparator<BundleConfig>() {
			public int compare(BundleConfig o1, BundleConfig o2) {
				int l1 = o1.getStartLevel();
				int l2 = o2.getStartLevel();
				if (l1 == l2) return 0;
				else if (l1 < l2) return -1;
				else return 1;
			}
			
		});
	}
}