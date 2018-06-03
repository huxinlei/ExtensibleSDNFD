package imu.edu.cn.basecom;

import org.pcap4j.util.MacAddress;
public class Constant {
	public static byte[] UUIDTYPE = {(byte)0x80,(byte)0xff};//UUID协议类型码
	public static String TESTUUID1 = "cf330b8d-1342-4bcd-9643-626d3f388cdc";
	public static String TESTUUID2 = "545d68c0-c196-49c9-88c0-7b3b79d91223";
	public static String TESTHELLO = "Hello Future Internet!";
	public static MacAddress TESTPORTADDRESS = MacAddress.getByName("00:50:56:c0:00:08");
	public static byte VERSION = 0x01;
	public static byte FLAG_ZERO = 0x00;
	public static byte FLAG_ONE = 0x01;
	public static byte[] FRAGIDENTIFIER = {0x00,0x00};//默认编号
	
	public static int FORWARD = 1;//转发动作
	public static int DROP = 0;//丢弃动作
}
