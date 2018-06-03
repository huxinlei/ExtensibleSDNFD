package cn.edu.imu.test;

import cn.edu.imu.config.ProReader;
/**
 * @author huxinlei
 * @date 2015-06-19
 * @version 1.0
 */
public class InternetDisk_Test {
	public static void main(String[] args) {
		String test = ProReader.getProReader().getProperty("DRIVER_CLASS");
		System.out.println(test);
		test = ProReader.getProReader().getProperty("DRIVER_URL");
		System.out.println(test);
		test = ProReader.getProReader().getProperty("DATABASE_USER");
		System.out.println(test);
		test = ProReader.getProReader().getProperty("DATABASE_PASSWORD");
		System.out.println(test);
	}

}
