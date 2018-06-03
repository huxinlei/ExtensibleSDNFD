package imu.edu.cn.basecom.test;

import imu.edu.cn.basecom.PacketBuffer;
import imu.edu.cn.basecom.Port;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.util.ByteArrays;


import java.util.List;
//import java.lang.InterruptedException;

public class TestPortAndBuffer {
	public static Port port;
	public static void main(String[] args) throws PcapNativeException,InterruptedException{
		List<PcapNetworkInterface> interList = Pcaps.findAllDevs();
		for(int i = 0; i < interList.size(); i++){
			System.out.println("===================================begin=====================================");
			System.out.println(interList.get(i).getName() + "(" + interList.get(i).getDescription() + ")");
			System.out.println(interList.get(i).getAddresses() + "(" + interList.get(i).getLinkLayerAddresses()+ ")");
			System.out.println(" ======================================end==================================");
		}
		PacketBuffer inBuffer = new PacketBuffer(10);
		PacketBuffer outBuffer = new PacketBuffer(10);
		
		final byte[] sendata = {  
		        (byte)0x00, (byte)0x0c, (byte)0x29, (byte)0x7f, (byte)0x4c, (byte)0x3b,//dst mac  
		        (byte)0x00, (byte)0x50, (byte)0x56, (byte)0xc0, (byte)0x00, (byte)0x08,//src mac  
		        (byte)0x88, (byte)0x8e, //Type: 802.1x authentication  
		        (byte)0x01, //Version:v1  
		        (byte)0x01, //Type:  Start (1)  
		        (byte)0x00, (byte)0x00//Length 0  
		};
		outBuffer.pushPacket(sendata);
		port = new Port(interList.get(1),inBuffer,outBuffer);
		Thread t = new Thread(new Runnable(){
			public void run(){
			    try{
		    		port.initPacketSender();
		    		port.initReceiveListener();
			    }catch(NotOpenException e){
			    	e.printStackTrace();
			    }catch(PcapNativeException e){
			    	e.printStackTrace();
			    }
			}
		});
		t.start();
		while(true){
			System.out.println("===========================pop-begin====================");
			//port.getOutBuffer().pushPacket(sendata);
			byte[] data = port.getInBuffer().popPacket();
			System.out.println("Buffer当前缓存数据包数量：" + port.getInBuffer().getCurLength());
			if(data != null)
				System.out.println(ByteArrays.toHexString(data, " "));
			System.out.println(" ===========================pop-end====================");
			Thread.sleep(900);
		}	
	}
}
