package imu.edu.cn.server;

import org.pcap4j.util.MacAddress;
public class Constant {
	public static byte[] UUIDTYPE = {(byte)0x80,(byte)0xff};//UUID type code
	public static String TESTUUID1 = "cf330b8d-1342-4bcd-9643-626d3f388cdc";
	public static String TESTUUID2 = "545d68c0-c196-49c9-88c0-7b3b79d91223";
	public static String TESTHELLO = "Hello Future Internet!";
	public static MacAddress TESTPORTADDRESS = MacAddress.getByName("00:0c:29:7f:4c:3b");
	public static byte VERSION = 0x01;
	public static byte FLAG_ZERO = 0x00;
	public static byte FLAG_ONE = 0x01;
	public static byte[] FRAGIDENTIFIER = {0x00,0x00};//identifier number
	public static byte[] LENGTH = {0x00,0x16};//Hello Future Internet! byte size
}
