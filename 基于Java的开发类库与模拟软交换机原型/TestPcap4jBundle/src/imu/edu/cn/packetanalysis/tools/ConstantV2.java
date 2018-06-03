package imu.edu.cn.packetanalysis.tools;

public class ConstantV2 {
	/**
	 * @author huxinlei
	 * @function 常量信息类
	 * @date 2015-11-16
	 */
	//bit操作常量
	public static byte ONE = (byte)0x80;
	public static byte TWO = (byte)0xC0;
	public static byte THREE = (byte)0xE0;
	public static byte FOUR = (byte)0xF0;
	public static byte FIVE = (byte)0xF8;
	public static byte SIX = (byte)0xFC;
	public static byte SEVEN = (byte)0xFE;
	public static byte EIGHT = (byte)0xFF;
	
	public static byte ONE_Z = (byte)0x7F;
	public static byte TWO_Z = (byte)0x3F;
	public static byte THREE_Z = (byte)0x1F;
	public static byte FOUR_Z = (byte)0x0F;
	public static byte FIVE_Z = (byte)0x07;
	public static byte SIX_Z = (byte)0x03;
	public static byte SEVEN_Z = (byte)0x01;
	public static byte EIGHT_Z = (byte)0x00;
	
	public static int FULL = 0x00FF;
	public static int FULL_Z = 0x0000;
	
	public static byte BFULL = (byte)0xFF;
	public static byte BFULL_Z = (byte)0x00;
	//配置文件常量
	
	public static String PROTOCOLFILE = "protocolfile";
	public static String PROTOCOLFILE_SEQUENCE = "sequence";
	public static String PROTOCOLFILE_NAME = "name";
	public static String PROTOCOLFILE_HEADERLENGTH = "headerlength";
	public static String PROTOCOLFILE_PRELENGTH = "prelength";
	
	public static String LOGICDEV = "logicdev";
	public static String LOGICDEV_SEQUENCE = "sequence";
	public static String LOGICDEV_NAME = "name";
	public static String LOGICDEV_PRELENGTH = "prelength";
	public static String LOGICDEV_VALUE = "value";
	
	public static String RULEDEV = "ruledev";
	public static String RULEDEV_SEQUENCE = "sequence";
	public static String RULEDEV_NAME = "name";
	public static String RULEDEV_PRELENGTH = "prelength";
	
	public static String PROTOCOLINSPIRE = "protocolinspire";
	public static String PROTOCOLINSPIRE_SEQUENCE = "sequence";
	public static String PROTOCOLINSPIRE_NAME = "name";
	public static String PROTOCOLINSPIRE_CLASSNAME = "classname";
	
	//输出数据类型常量
	public static String OUTPUT_TYPE_MAP = "map";
	public static String OUTPUT_TYPE_CLASS = "class";
}
