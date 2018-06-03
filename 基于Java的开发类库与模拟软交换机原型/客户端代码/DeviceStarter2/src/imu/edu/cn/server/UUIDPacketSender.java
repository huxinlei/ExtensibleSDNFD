package imu.edu.cn.server;

import java.io.IOException;
import java.io.InterruptedIOException;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapStat;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
import org.pcap4j.util.ByteArrays;
@SuppressWarnings("javadoc")
public class UUIDPacketSender {
	private static final String COUNT_KEY
	    = Loop.class.getName() + ".count";
	private static final int COUNT
	    = Integer.getInteger(COUNT_KEY, 5);

	private static final String READ_TIMEOUT_KEY
	    = Loop.class.getName() + ".readTimeout";
	private static final int READ_TIMEOUT
	    = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

	private static final String SNAPLEN_KEY
	    = Loop.class.getName() + ".snaplen";
	private static final int SNAPLEN
	    = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

	private UUIDPacketSender() {}
	public static void main(String[] args)  throws PcapNativeException, NotOpenException {
	    String filter = args.length != 0 ? args[0] : "";

	    System.out.println(COUNT_KEY + ": " + COUNT);
	    System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
	    System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
	    System.out.println("\n");

	    PcapNetworkInterface nif;
	    try {
	      nif = new NifSelector().selectNetworkInterface();
	    } catch (IOException e) {
	      e.printStackTrace();
	      return;
	    }

	    if (nif == null) {
	      return;
	    }

	    System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

	    final PcapHandle handle
	      = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
	    final PcapHandle sendHandle
	    = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

	    if (filter.length() != 0) {
	      handle.setFilter(
	        filter,
	        BpfCompileMode.OPTIMIZE
	      );
	    }
	    int length = 6 + Constant.TESTPORTADDRESS.getAddress().length;
	    length = length + Constant.UUIDTYPE.length;
	    length = length + Constant.LENGTH.length;
	    length = length + 1;
	    length = length + 1;
	    length = length + Constant.FRAGIDENTIFIER.length;
	    length = length + Constant.TESTUUID1.getBytes().length;
	    length = length + Constant.TESTUUID2.getBytes().length;
	    length = length + Constant.TESTHELLO.getBytes().length;
	    final byte[] sendata = new byte[length];
	    int offset = 0;
	    for(int i = 0; i < 6; i++){
	    	sendata[i] = (byte)0xff;
	    	offset = offset + 1;
	    }
	    int index = 0;
	    for(int i = offset; i < 12; i++){
	    	sendata[i] = Constant.TESTPORTADDRESS.getAddress()[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 14; i++){
	    	sendata[i] = Constant.UUIDTYPE[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 16; i++){
	    	sendata[i] = Constant.LENGTH[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 17; i++){
	    	sendata[i] = Constant.VERSION;
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 18; i++){
	    	sendata[i] = Constant.FLAG_ZERO;
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 20; i++){
	    	sendata[i] = Constant.FRAGIDENTIFIER[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 56; i++){
	    	sendata[i] = Constant.TESTUUID1.getBytes()[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 92; i++){
	    	sendata[i] = Constant.TESTUUID2.getBytes()[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    index = 0;
	    for(int i = offset; i < 114; i++){
	    	sendata[i] = Constant.TESTHELLO.getBytes()[index];
	    	index = index + 1;
	    	offset = offset + 1;
	    }
	    System.out.println(ByteArrays.toHexString(Constant.TESTUUID1.getBytes()," "));
	    System.out.println(ByteArrays.toHexString(Constant.TESTUUID2.getBytes()," "));
	    System.out.println(ByteArrays.toHexString(sendata, " "));
	    int count = 0;
	    while(true){
	    	try{
		    	sendHandle.sendPacket(sendata);
		    	System.out.println("===================TEST=======================");
		    	count = count + 1;
		    	System.out.println(count);
		    	System.out.println("===================TEST=======================");
		    	Thread.sleep(100);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}

	    }
	}

}
