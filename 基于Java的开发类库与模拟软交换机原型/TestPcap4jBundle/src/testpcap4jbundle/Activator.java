package testpcap4jbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.testpcap4j.Loop;
import com.testpcap4j.SendArpRequest;
import com.testpcap4j.SendFragmentedEcho;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		String[] str = new String[0];
		//Loop.main(str);
		SendArpRequest.main(str);
		//SendFragmentedEcho.main(str);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
