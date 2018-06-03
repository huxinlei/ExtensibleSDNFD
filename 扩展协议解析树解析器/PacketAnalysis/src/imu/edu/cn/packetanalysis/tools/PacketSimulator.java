package imu.edu.cn.packetanalysis.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PacketSimulator {
	/**
	 * @author huxinlei
	 * @function 模拟数据包生成工具类
	 * @date 2015-3-8
	 */
	public static void packetSimulator(){
		
		byte[] field1 = new byte[2];
		byte[] field2 = new byte[2];
		byte[] field3 = new byte[1];
		byte[] field4 = new byte[1];
		byte[] field5 = new byte[2];
		byte[] data = new byte[100];
		byte[] separator = new byte[1];
		
		separator[0] = (byte)0xFF;
		
		byte[] protocolData = new byte[0];
		field1[0] = 1;
		field1[1] = 1;
		
		field2[0] = 2;
		field2[1] = 2;
		
		field3[0] = 0;
		
		field4[0] = 0;
		
		field5[0] = 3;
		field5[1] = 3;
		
		for(int i = 0; i < 100; i++){
			data[i] = 4;
		}
		protocolData = appendBytes(protocolData,field1);
		protocolData = appendBytes(protocolData,field2);
		protocolData = appendBytes(protocolData,field3);
		protocolData = appendBytes(protocolData,field4);
		protocolData = appendBytes(protocolData,field5);
		protocolData = appendBytes(protocolData,data);
		protocolData = appendBytes(protocolData,separator);
		
		data = new byte[80];
		field1[0] = 2;
		field1[1] = 2;
		
		field2[0] = 3;
		field2[1] = 3;
		
		field3[0] = 1;
		
		field4[0] = 1;
		
		field5[0] = 4;
		field5[1] = 4;
		
		for(int i = 0; i < 80; i++){
			data[i] = 5;
		}
		
		protocolData = appendBytes(protocolData,field1);
		protocolData = appendBytes(protocolData,field2);
		protocolData = appendBytes(protocolData,field3);
		protocolData = appendBytes(protocolData,field4);
		protocolData = appendBytes(protocolData,field5);
		protocolData = appendBytes(protocolData,data);
		protocolData = appendBytes(protocolData,separator);
		
		System.out.println(protocolData.length);
		for(int i = 0; i < protocolData.length; i++){
			System.out.print(protocolData[i] + " ");
		}
		createProtocolFile("D:\\protocolfile.pro",protocolData);
		
		try{
			byte[] fileData1 = getProtocolFile2("D:\\protocolfile.pro");
			
			System.out.println("bytes available:" + fileData1.length);
			for(int i = 0; i < fileData1.length; i++){
				System.out.print(fileData1[i] + " ");
			}
			
			byte[] fileData2 = getProtocolFile2("D:\\protocolfile.pro");
			
 			
			for(int i = 0; i < fileData2.length; i++){
				System.out.print(fileData2[i] + " ");
			}
		}catch(IOException e){
			System.out.println("ERROR:" + e.toString());
		}
	}
	
	public static void packetSimulator2(){
		
		byte[] field1 = new byte[1];
		byte[] field2 = new byte[1];
		byte[] field3 = new byte[2];
		byte[] field4 = new byte[2];
		byte[] field5 = new byte[2];
		byte[] data = new byte[100];
		byte[] separator = new byte[1];
		
		separator[0] = (byte)0xFF;
		
		byte[] protocolData = new byte[0];
		field1[0] = 1;
		
		field2[0] = 0;
		
		field3[0] = 0;
		field3[1] = 1;
		
		field4[0] = 0;
		field4[1] = 1;
		
		field5[0] = 3;
		field5[1] = 3;
		
		for(int i = 0; i < 100; i++){
			data[i] = 4;
		}
		protocolData = appendBytes(protocolData,field1);
		protocolData = appendBytes(protocolData,field2);
		protocolData = appendBytes(protocolData,field3);
		protocolData = appendBytes(protocolData,field4);
		protocolData = appendBytes(protocolData,field5);
		protocolData = appendBytes(protocolData,data);
		protocolData = appendBytes(protocolData,separator);
		
		data = new byte[80];
		field4 = new byte[4];
		field1[0] = 2;
		
		field2[0] = 1;
		
		field3[0] = 1;
		field3[1] = 1;
		
		field4[0] = 0;
		field4[1] = 1;
		field4[2] = 1;
		field4[3] = 0;
		
		for(int i = 0; i < 80; i++){
			data[i] = 5;
		}
		
		protocolData = appendBytes(protocolData,field1);
		protocolData = appendBytes(protocolData,field2);
		protocolData = appendBytes(protocolData,field3);
		protocolData = appendBytes(protocolData,field4);
		protocolData = appendBytes(protocolData,data);
		protocolData = appendBytes(protocolData,separator);
		
		System.out.println(protocolData.length);
		for(int i = 0; i < protocolData.length; i++){
			System.out.print(protocolData[i] + " ");
		}
		createProtocolFile("D:\\protocolfile1.pro",protocolData);
		
		try{
			byte[] fileData1 = getProtocolFile2("D:\\protocolfile1.pro");
			
			System.out.println("Simulator bytes available:" + fileData1.length);
			for(int i = 0; i < fileData1.length; i++){
				System.out.print(fileData1[i] + " ");
			}
			System.out.println();
			byte[] fileData2 = getProtocolFile2("D:\\protocolfile1.pro");
			
 			
			for(int i = 0; i < fileData2.length; i++){
				System.out.print(fileData2[i] + " ");
			}
		}catch(IOException e){
			System.out.println("ERROR:" + e.toString());
		}		
	}
	//将byte数组写入文件
	public static boolean createProtocolFile(String path, byte[] data){
		boolean flag = false;
		try{
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			flag = true;
		}catch(IOException e){
			System.out.println("ERROR" + e.toString());
		}
		return flag;
	}
	//byte数据追加数据
	public static byte[] appendBytes(byte[] data, byte[] appendData){
		int originLength = 0;
		
		if(data != null){
			originLength = data.length;
		}
		int appendLength = 0;
		
		if(appendData != null){
			appendLength = appendData.length;
		}
			
		byte[] newData = new byte[originLength + appendLength];
		for(int i = 0; i < originLength; i++){
			newData[i] = data[i];
		}
		for(int i = 0; i < appendLength; i++){
			newData[originLength + i] = appendData[i];
		}
		
		return newData;
	}
	//获取文件内容方式2
	public static byte[] getProtocolFile2(String filePath) throws IOException{
		FileInputStream in = new FileInputStream(filePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		System.out.println("bytes available:" + in.available());
		byte[] temp = new byte[1024];
		int size = 0;
		while((size = in.read(temp)) != -1){
			out.write(temp,0,size);
		}
		in.close();
		byte[] bytes = out.toByteArray();
		System.out.println("bytes size got is:" + bytes.length);
		
		return bytes;
	}
}
