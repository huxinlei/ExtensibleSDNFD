package imu.edu.cn.basecom;
import java.io.IOException;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.RawPacketListener;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapStat;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
import org.pcap4j.util.ByteArrays;

import com.sun.jna.Platform;
import com.testpcap4j.LoopRaw;
public class Port {
	//常量值设置
	private static final String COUNT_KEY
	    = LoopRaw.class.getName() + ".count";
	private static final int COUNT
	    = Integer.getInteger(COUNT_KEY, 0);

	private static final String READ_TIMEOUT_KEY
	    = LoopRaw.class.getName() + ".readTimeout";
	private static final int READ_TIMEOUT
	    = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

	private static final String SNAPLEN_KEY
	    = LoopRaw.class.getName() + ".snaplen";
	private static final int SNAPLEN
	    = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]
	  
	private PcapNetworkInterface pInterface;//网卡信息
	private PcapHandle recHandle;//数据包接收处理器
	private PcapHandle sendHandle;//数据包发送处理器
	private PacketBuffer inBuffer;//接收数据包缓存队列
	private PacketBuffer outBuffer;//发送数据包缓存队列
	
	public Port(PcapNetworkInterface pInterface, PacketBuffer inBuffer, PacketBuffer outBuffer){
		this.pInterface = pInterface;
		this.inBuffer = inBuffer;
		this.outBuffer = outBuffer;
	}
	public PcapNetworkInterface getPInterface() {
		return pInterface;
	}
	public void setPInterface(PcapNetworkInterface interface1) {
		pInterface = interface1;
	}
	public PcapHandle getRecHandle() {
		return recHandle;
	}
	public void setRecHandle(PcapHandle recHandle) {
		this.recHandle = recHandle;
	}
	public PcapHandle getSendHandle() {
		return sendHandle;
	}
	public void setSendHandle(PcapHandle sendHandle) {
		this.sendHandle = sendHandle;
	}
	public PacketBuffer getInBuffer() {
		return inBuffer;
	}
	public void setInBuffer(PacketBuffer inBuffer) {
		this.inBuffer = inBuffer;
	}
	public PacketBuffer getOutBuffer() {
		return outBuffer;
	}
	public void setOutBuffer(PacketBuffer outBuffer) {
		this.outBuffer = outBuffer;
	}
	//监听功能初始化
	public void initReceiveListener() throws PcapNativeException,NotOpenException{
	    if (pInterface == null) {
	       return;
	    }
        System.out.println(pInterface.getName() + "(" + pInterface.getDescription() + ")");
	    this.recHandle = pInterface.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
	    
	    RawPacketListener listener
	         = new RawPacketListener() {
	          @Override
	          public void gotPacket(byte[] packet) {
	        	  System.out.println(pInterface.getName() + "(" + pInterface.getDescription() + ")");
	             System.out.println(recHandle.getTimestamp());
	             System.out.println(ByteArrays.toHexString(packet, " "));
	             inBuffer.pushPacket(packet);
	          }
	     };
	     try {
	       recHandle.loop(0, listener);
	     } catch (InterruptedException e) {
	       e.printStackTrace();
	     }
	     PcapStat ps = recHandle.getStats();
	     System.out.println("ps_recv: " + ps.getNumPacketsReceived());
	     System.out.println("ps_drop: " + ps.getNumPacketsDropped());
	     System.out.println("ps_ifdrop: " + ps.getNumPacketsDroppedByIf());
	     recHandle.close();
	}
	//数据包发送功能初始化
	public void initPacketSender() throws PcapNativeException,NotOpenException{
	    if (pInterface == null) {
		    return;
		}
	    System.out.println(pInterface.getName() + "(" + pInterface.getDescription() + ")");	
	    this.sendHandle = pInterface.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
	    Thread t = new Thread(new Runnable(){
	    	public void run(){
	    	    try{
		    		boolean flag = true;
		    		while(true){
		    			byte[] packet = outBuffer.popPacket();
		    			if(packet != null){
			    			sendHandle.sendPacket(packet);
			    			System.out.println("packet send complete !");
		    			}
		    		}
	    	    }catch(NotOpenException e){
	    	    	e.printStackTrace();
	    	    }catch(PcapNativeException e){
	    	    	e.printStackTrace();
	    	    }
	    	}
	    });
	    t.start();
	}
	
}
