package imu.edu.cn.packetanalysis.input;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import imu.edu.cn.packetanalysis.tools.Constant;

public class PacketFileService implements PacketsInputService{
	/**
	 * @author huxinlei
	 * @function 从文件中抓取模拟数据包操作类
	 * @date 2015-3-8
	 */
	private List<byte[]> packetData;
	
	public List<byte[]> getPacketData(){
		
		if(this.packetData == null){
			List<byte[]> packetData = new ArrayList<byte[]>();
		}
		return this.packetData;
	}
	
	//初始化协议数据byte包
	public void initPacketData(String filePath) throws IOException{
		byte[] buffer = getProtocolFile(filePath);
		long bufferSize = buffer.length;
		this.packetData = new ArrayList<byte[]>();
		
		if(bufferSize > Integer.MAX_VALUE){
			System.out.println("超出处理范围,PacketFileService类数据初始化失败......");
			return ;
		}
		
		int beginIndex = 0;
		for(int i = 0; i < bufferSize; i++){
			
			if(buffer[i] == Constant.BFULL){
				int tempLength = i - beginIndex;
				byte[] temp = new byte[tempLength];
				
				for(int j = 0; j < tempLength; j++){
					temp[j] = buffer[beginIndex + j];
				}
				this.packetData.add(temp);
				beginIndex = i + 1;
			} 
		}
		
	}
	
	//获取模拟协议数据
	public byte[] getProtocolFile(String filePath) throws IOException{
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
