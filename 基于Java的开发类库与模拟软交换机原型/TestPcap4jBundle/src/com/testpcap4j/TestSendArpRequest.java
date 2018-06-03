package com.testpcap4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
@SuppressWarnings("javadoc")
public class TestSendArpRequest {
	private static final String COUNT_KEY
	  = TestSendArpRequest.class.getName() + ".count";
	private static final int COUNT 
	  = Integer.getInteger(COUNT_KEY, 1);
	private static final String READ_TIMEOUT_KEY 
	  = TestSendArpRequest.class.getName() + ".readTimeout";
	private static final int READ_TIMEOUT 
	  = Integer.getInteger(READ_TIMEOUT_KEY, 10);
	private static final String SNAPLEN_KEY
	  = TestSendArpRequest.class.getName() + ".snaplen";
	private static final int SNAPLEN 
	  = Integer.getInteger(SNAPLEN_KEY, 65536);
	private static final MacAddress SRC_MAC_ADDR
	  = MacAddress.getByName("00:0c:29:cc:42:3f");
	private static MacAddress resolvedAddr;
	
	private TestSendArpRequest(){}
	
	public static void main(String[] args) throws PcapNativeException,NotOpenException{
		String strSrcIpAddress = "192.168.28.137";//for InetAddress.getByName()
		String strDstIpAddress = "192.168.28.1";//for InetAddress.getByName()
		
		System.out.println(COUNT_KEY + ": " + COUNT);
		System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
		System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
		System.out.println("\n");
		
		PcapNetworkInterface nif;
		try{
			nif = new NifSelector().selectNetworkInterface();
		}catch(IOException e){
			e.printStackTrace();
			return;
		}
		if(nif == null){
			return;
		}
		System.out.println(nif.getName() + "(" + nif.getDescription() + ")");
		
		PcapHandle handle 
		  = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
		PcapHandle sendHandle
		  = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
		ExecutorService pool = Executors.newSingleThreadExecutor();
		
		try{
			handle.setFilter(
					"arp and src host " + strDstIpAddress
					  + " and dst host " + strSrcIpAddress
					   + " and ether dst " + Pcaps.toBpfString(SRC_MAC_ADDR),
					   BpfCompileMode.OPTIMIZE
					   );
			
			PacketListener listener
			 = new PacketListener(){
				@Override
				public void gotPacket(Packet packet){
					if(packet.contains(ArpPacket.class)){
						ArpPacket arp = packet.get(ArpPacket.class);
						if(arp.getHeader().getOperation().equals(ArpOperation.REPLY)){
							TestSendArpRequest.resolvedAddr = arp.getHeader().getSrcHardwareAddr();
						}
					}
					System.out.println(packet);
				}
			};
			
			Task t = new Task(handle, listener);
			pool.execute(t);
			ArpPacket.Builder arpBuilder = new ArpPacket.Builder();
			try{
				arpBuilder
				  .hardwareType(ArpHardwareType.ETHERNET)
				  .protocolType(EtherType.IPV4)
				  .hardwareAddrLength((byte)MacAddress.SIZE_IN_BYTES)
				  .protocolAddrLength((byte)ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES)
				  .operation(ArpOperation.REQUEST)
				  .srcHardwareAddr(SRC_MAC_ADDR)
				  .srcProtocolAddr(InetAddress.getByName(strSrcIpAddress))
				  .dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
				  .dstProtocolAddr(InetAddress.getByName(strDstIpAddress));
			}catch(UnknownHostException e){
				throw new IllegalArgumentException(e);
			}
			
			EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
			etherBuilder.dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
			            .srcAddr(SRC_MAC_ADDR)
			            .type(EtherType.ARP)
			            .payloadBuilder(arpBuilder)
			            .paddingAtBuild(true);
			for(int i = 0; i < COUNT; i++){
				Packet p = etherBuilder.build();
				System.out.println(p);
				sendHandle.sendPacket(p);
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					break;
				}
			}
		}finally{
			if(handle != null && handle.isOpen()){
				handle.close();
			}
			if(sendHandle != null && sendHandle.isOpen()){
				sendHandle.close();
			}
			if(pool != null && !pool.isShutdown()){
				pool.shutdown();
			}
			System.out.println(strDstIpAddress + "was resolved to " + resolvedAddr);
		}
	}
	
	private static class Task implements Runnable {
		private PcapHandle handle;
		private PacketListener listener;
		
		public Task(PcapHandle handle, PacketListener listener){
			this.handle = handle;
			this.listener = listener;
		}
		@Override 
		public void run(){
			try{
				handle.loop(COUNT, listener);
			}catch(PcapNativeException e){
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(NotOpenException e){
				e.printStackTrace();
			}
		}
	}
}
