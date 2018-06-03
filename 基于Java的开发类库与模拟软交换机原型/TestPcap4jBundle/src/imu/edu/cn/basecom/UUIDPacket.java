package imu.edu.cn.basecom;
import java.util.UUID;
import java.util.Arrays;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

public class UUIDPacket {
	public static int version = 1;//bit个数
	public static int flag = 1;//0为单个数据包，1为多个数据包
	public static int fragidentifier = 2;//当flag为1时，多个数据包的编号，当flag为0时，数据包的编号为1。
	public static int sourceuuid = 36;//目的UUID
	public static int destuuid = 36;//源UUID
	public static String ver_des = "version";
	public static String flag_des = "flag";
	public static String fragidentifier_des = "fragidentifier";
	public static String sourceuuid_des = "sourceuuid";
	public static String destuuid_des = "destuuid";
	
	//数据存储区
	private byte version_data;
	private byte flag_data;
	private byte[] fragidentifier_data;
	private byte[] sourceuuid_data;
	private byte[] destuuid_data;

	public byte getVersion_data() {
		return version_data;
	}


	public void setVersion_data(byte version_data) {
		this.version_data = version_data;
	}


	public byte getFlag_data() {
		return flag_data;
	}


	public void setFlag_data(byte flag_data) {
		this.flag_data = flag_data;
	}


	public byte[] getFragidentifier_data() {
		return fragidentifier_data;
	}


	public void setFragidentifier_data(byte[] fragidentifier_data) {
		this.fragidentifier_data = fragidentifier_data;
	}


	public byte[] getSourceuuid_data() {
		return sourceuuid_data;
	}


	public void setSourceuuid_data(byte[] sourceuuid_data) {
		this.sourceuuid_data = sourceuuid_data;
	}


	public byte[] getDestuuid_data() {
		return destuuid_data;
	}


	public void setDestuuid_data(byte[] destuuid_data) {
		this.destuuid_data = destuuid_data;
	}
	//获取UUID实验值1：cf330b8d-1342-4bcd-9643-626d3f388cdc 二进制表示：63 66 33 33 30 62 38 64 2d 31 33 34 32 2d 34 62 63 64 2d 39 36 34 33 2d 36 32 36 64 33 66 33 38 38 63 64 63
	//获取UUID实验值2：545d68c0-c196-49c9-88c0-7b3b79d91223 二进制表示：63 66 33 33 30 62 38 64 2d 31 33 34 32 2d 34 62 63 64 2d 39 36 34 33 2d 36 32 36 64 33 66 33 38 38 63 64 63
	//获取字符串"Hello Future Internet!" 二进制表示：48 65 6c 6c 6f 20 46 75 74 75 72 65 20 49 6e 74 65 72 6e 65 74 21
	//UUID测试区
	public static void main(String args[]){
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		byte[] uuid_bytes = uuid.toString().getBytes();
		System.out.println(ByteArrays.toHexString(uuid_bytes, " "));
		System.out.println(uuid_bytes.length);
		
		byte[] uuid_bytes2 = uuid.toString().getBytes();
		System.out.println(ByteArrays.toHexString(uuid_bytes2, " "));
		System.out.println(uuid_bytes2.length);
		System.out.println("内容值相同：" + Arrays.equals(uuid_bytes, uuid_bytes2));
		
		UUID uuid1 = UUID.randomUUID();
		System.out.println(uuid1.toString());
		byte[] uuid_bytes1 = uuid1.toString().getBytes();
		System.out.println(ByteArrays.toHexString(uuid_bytes1, " "));
		System.out.println(uuid_bytes1.length);
		System.out.println("内容值不相同：" + Arrays.equals(uuid_bytes, uuid_bytes1));
		//Hello Future Internet!
		String str = "Hello Future Internet!";
		byte[] strBytes = str.getBytes();
		System.out.println(strBytes.length);
 		System.out.println(ByteArrays.toHexString(strBytes, " "));
 		String str1 = new String(strBytes);
 		System.out.println(str1);
 		
 		MacAddress mac = MacAddress.getByName("00:50:56:c0:00:08");
 		MacAddress mac1 = MacAddress.getByName("00:0c:29:7f:4c:3b");
 		MacAddress mac2 = MacAddress.getByName("00:0c:29:7f:4c:3b");
 		
 		System.out.println(mac.equals(mac1));
 		System.out.println(mac1.equals(mac2));
	}
}
