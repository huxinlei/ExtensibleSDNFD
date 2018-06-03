package imu.edu.cn.osgilauncher;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContainerListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(OSGiAdmin.class);
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			String osgiConfigPath = event.getServletContext().getInitParameter("osgiConfig");
			if (osgiConfigPath == null) osgiConfigPath = "/osgi.properties";
			Properties prop = new Properties();
			InputStream is = ContainerListener.class.getResourceAsStream(osgiConfigPath);
			prop.load(is);
			is.close();
			OSGiConfig osgiConfig = new OSGiConfig();
			osgiConfig.load(prop, event.getServletContext());
			
			
			OSGiAdmin.startup(osgiConfig, event.getServletContext());
		} catch (Exception e) {
			log.error("Æô¶¯OSGi¿ò¼ÜÊ§°Ü£º" + e.getMessage(), e);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		try {
			OSGiAdmin.shutdown();
		} catch (Exception e) {
			log.error("Ð¶ÔØOSGi¿ò¼ÜÊ§°Ü£º" + e.getMessage(), e);
		}
	}

}