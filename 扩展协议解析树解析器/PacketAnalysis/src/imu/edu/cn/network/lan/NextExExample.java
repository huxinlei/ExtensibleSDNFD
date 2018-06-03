package imu.edu.cn.network.lan;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JRegistry;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;

public class NextExExample {

	public static void main(String[] args) {
		final String FILE_NAME = "C:\\test-12tp.pcap";
		StringBuilder errbuf = new StringBuilder();
		//first we open up the selected device
		Pcap pcap = Pcap.openOffline(FILE_NAME, errbuf);
		if(pcap == null){
			System.err.printf("Error while opening file for capture: " 
					+ errbuf.toString());
			return;
		}
		
		Ip4 ip = new Ip4();
		Ethernet eth = new Ethernet();
		PcapHeader hdr = new PcapHeader(JMemory.POINTER);
		JBuffer buf = new JBuffer(JMemory.POINTER);
		
		//third we must map pcap's data-link-type to jNetPcap's protocols IDs.
		//this is needed by the scanner so that it knows what the first header in
		//the packet is.
		int id = JRegistry.mapDLTToId(pcap.datalink());
		//fourth we peer header and buffer (not copy, think of C pointers)
		while(pcap.nextEx(hdr, buf) == Pcap.NEXT_EX_OK){
			//fifth we copy header and buffer data to new packet object
			PcapPacket packet = new PcapPacket(hdr, buf);
			//six we scan the new packet to discover what headers it contains
			packet.scan(id);
			//we use formatUtils (found in org.jnetpcap.packet.format package),
			//to convert our raw addresses to a human readable string.
			if(packet.hasHeader(eth)){
				String str = FormatUtils.mac(eth.source());
				System.out.printf("#%d: eth.src=%s\n", packet.getFrameNumber(), str);
			}
			if(packet.hasHeader(ip)){
				String str = FormatUtils.ip(ip.source());
			    System.out.printf("#%d: ip.src=%s\n", packet.getFrameNumber(),str);
			}
		}
		//last thing to do is close the pcap handle
		pcap.close();
	}

}
