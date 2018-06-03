package imu.edu.cn.packetanalysis.tools;

public class Constant {
	/**
	 * @author huxinlei
	 * @function 常量信息类
	 * @date 2015-3-8
	 */
	//bit操作常量
	public static int ONE = 0xFE;
	public static int TWO = 0xFD;
	public static int THREEE = 0xFB;
	public static int FOUR = 0xF7;
	public static int FIVE = 0xEF;
	public static int SIX = 0xDF;
	public static int SEVEN = 0xBF;
	public static int EIGHT = 0x7F;
	
	public static int ONE_Z = 0x01;
	public static int TWO_Z = 0x02;
	public static int THREE_Z = 0x04;
	public static int FOUR_Z = 0x08;
	public static int FIVE_Z = 0x10;
	public static int SIX_Z = 0x20;
	public static int SEVEN_Z = 0x40;
	public static int EIGHT_Z = 0x80;
	
	public static int FULL = 0x00FF;
	public static int FULL_Z = 0x0000;
	
	public static byte BFULL = (byte)0xFF;
	public static byte BFULL_Z = (byte)0x00;
	//配置文件常量
	public static String PROTOCOLDATA = "protocoldata";
	
	public static String PROTOCOLFILE = "protocolfile";
	public static String PROTOCOLFILE_NAME = "name";
	public static String PROTOCOLFILE_HL = "headerlength";
	public static String PROTOCOLFILE_INSPIRE = "inspire";
	public static String PROTOCOLFILE_CLASSNAME = "classname";
	
	public static String LOGICDEV = "logicdev";
	public static String LOGICDEV_NAME = "name";
	public static String LOGICDEV_LENGTH = "length";
	public static String LOGICDEV_VALUE = "value";
	
	public static String RULEDEV = "ruledev";
	public static String RULEDEV_NAME = "name";
	public static String RULEDEV_DATALENGTH = "datalength";
	
	public static String PROTOCOLINSPIRE = "protocolinspire";
	public static String PROTOCOLINSPIRE_NAME = "name";
	
	//输出数据类型常量
	public static String OUTPUT_TYPE_MAP = "map";
	public static String OUTPUT_TYPE_CLASS = "class";
	
}
