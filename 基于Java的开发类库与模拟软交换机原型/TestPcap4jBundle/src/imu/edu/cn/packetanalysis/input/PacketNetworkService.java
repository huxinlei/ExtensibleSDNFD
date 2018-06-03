package imu.edu.cn.packetanalysis.input;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

//import org.jnetpcap.Pcap;
//import org.jnetpcap.PcapIf;
//import org.jnetpcap.packet.PcapPacket;
//import org.jnetpcap.packet.PcapPacketHandler;
//import org.jnetpcap.protocol.network.Ip4;
//import org.jnetpcap.protocol.tcpip.Tcp;
//import org.jnetpcap.protocol.lan.Ethernet;

import imu.edu.cn.packetanalysis.tools.TypeConvertor;

public class PacketNetworkService implements PacketsInputService{
	/**
	 * @author huxinlei
	 * @function 从网络中抓取数据包操作类
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
	public void initPacketData() throws Exception{
		/*List<PcapIf> alldevs = new ArrayList<PcapIf>();
		this.packetData  = new ArrayList<byte[]>();
		StringBuilder errbuf = new StringBuilder();
		
		int r = Pcap.findAllDevs(alldevs,errbuf);
		if(r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices,error is %s", errbuf.toString());
			return;
		}
		System.out.println("Network devices found:");
		int i = 0;
		for(PcapIf device : alldevs) {
			String description = 
				(device.getDescription() != null) ? device.getDescription() 
					: "No description available";
			System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
		}
		PcapIf device = alldevs.get(1);
		System.out.printf("\nChoosing '%s' on your behalf:\n",
				(device.getDescription() != null) ? device.getDescription() 
						: device.getName());
		int snaplen = 64 * 1024;
		int flags = Pcap.MODE_PROMISCUOUS;
		int timeout = 10 * 1000;
		Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		
		if (pcap == null) {
			System.err.printf("Error while opening device for capture: "
					+ errbuf.toString());
			return;
		}
		
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
			public void nextPacket(PcapPacket packet, String user) {
				//Tcp tcp = new Tcp();
				Ip4 ip4 = new Ip4();
				Ethernet ethernet = new Ethernet();
				Tcp tcp = new Tcp();
				
				System.out.printf("Received packet at %s caplen = %-4d len=%-4d %s\n",
						new Date(packet.getCaptureHeader().timestampInMillis()),
						packet.getCaptureHeader().caplen(),
						packet.getCaptureHeader().wirelen(),
						user
						);
				if(packet.hasHeader(ethernet)){
					//byte[] temp = ethernet.getPayload();
					//if(packet.hasHeader(tcp)){
						byte[] temp = ethernet.getPayload();
						String str = TypeConvertor.bytesToHexString(temp);
						System.out.println(str);
						packetData.add(temp);
					//}
					//System.out.print("通过截串获取数据为：");
					//System.out.println(ethernet.getPayload().length);
					//byte[] temp1 = new byte[4];
				    //int version = temp[0] >> 4;
					//byte ttl = temp[8];
					//int len = temp[0]& 0x0F;
					//int tos = temp[1];
					//temp1[0] = temp[3];
					//temp1[1] = 0;
					//temp1[2] = 0;
					//temp1[3] = 0;
					//int paylength = TypeConvertor.bytesToInt(temp1, 0);
					
					//temp1[1] = temp[1];
					//temp1[2] = 0;
					//temp1[3] = 0;
					//int version = TypeConvertor.bytesToInt(temp1, 0);
					//System.out.println(temp[0]);
					//System.out.println(temp[1]);
					//System.out.println(version);
					//System.out.println(ttl);
					//System.out.println(len);
					//System.out.println(tos);
					//String str = TypeConvertor.bytesToHexString(temp);
					//System.out.println(str);
					
					//System.out.println(temp[2]);
					//System.out.println(temp[3]);
					//System.out.println(paylength);
					//System.out.println();
					//if(packet.hasHeader(ip4)){
						//System.out.print("通过Ipv4包格式获取数据为：");
						//System.out.println(ip4.getPayload().length);
						//System.out.println(ip4.version());
						//System.out.println(ip4.ttl());
						//System.out.println(ip4.hlen());//为5的原因？
						//System.out.println(ip4.tos());
						//System.out.println(ip4.getPayloadLength());
						//System.out.println();
					//}
					//packetData.add(temp);
				}
				/*if (packet.hasHeader(ip4)) {
					
					/*if (packet.hasHeader(tcp)) {
						System.out.println(packet);
					}/
				}*/
		/*}
		};
	
		pcap.loop(10, jpacketHandler, "jNetPcap rocks!");
		pcap.close();	*/	
	}
}
