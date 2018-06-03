package imu.edu.cn.uuid;

import java.io.IOException;
import java.util.Arrays;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapStat;
import org.pcap4j.core.RawPacketListener;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
import imu.edu.cn.basecom.Constant;
import com.sun.jna.Platform;
import com.testpcap4j.LoopRaw;

@SuppressWarnings("javadoc")
public class UUIDPacketReceiver {
	private static final String COUNT_KEY
	    = LoopRaw.class.getName() + ".count";
	private static final int COUNT
	    = Integer.getInteger(COUNT_KEY, 5);

	private static final String READ_TIMEOUT_KEY
	    = LoopRaw.class.getName() + ".readTimeout";
	private static final int READ_TIMEOUT
	    = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

	private static final String SNAPLEN_KEY
	    = LoopRaw.class.getName() + ".snaplen";
	private static final int SNAPLEN
	    = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

	private UUIDPacketReceiver() {}
	public static void main(String[] args) throws PcapNativeException, NotOpenException {
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

	    if (filter.length() != 0) {
	      handle.setFilter(
	        filter,
	        BpfCompileMode.OPTIMIZE
	      );
	    }

	    RawPacketListener listener
	      = new RawPacketListener() {
	          @Override
	          public void gotPacket(byte[] packet) {
	        	 byte[] dst = null;
	        	 byte[] src = null;
	        	 byte[] type1 = null;
	      		//处理destination address字段
	      		int offset = 0;
	      		int length = 6;
	      		if(offset + length < packet.length){
	      			byte[] temp = new byte[length];
	      			int index = 0;
	      			for(int i = offset; i < offset+length; i++){
	      				temp[index] = packet[i];
	      				index = index + 1;
	      			}
	      			dst = temp;
	      			System.out.println(ByteArrays.toHexString(temp, " "));
	      		}
	      		//处理source address字段
	      		offset = offset + length;
	      		length = 6;
	      		if(offset + length < packet.length){
	      			byte[] temp = new byte[length];
	      			int index = 0;
	      			for(int i = offset; i < offset+length; i++){
	      				temp[index] = packet[i];
	      				index = index + 1;
	      			}
	      			src = temp;
	      			System.out.println(ByteArrays.toHexString(temp, " "));
	      		}
	      		//处理type字段
	      		offset = offset + length;
	      		length = 2;
	      		if(offset + length < packet.length){
	      			byte[] temp = new byte[length];
	      			int index = 0;
	      			for(int i = offset; i < offset+length; i++){
	      				temp[index] = packet[i];
	      				index = index + 1;
	      			}
	      			type1 = temp;
	      			System.out.println(ByteArrays.toHexString(temp, " "));
	      		}	
	      		if(src == null || !Arrays.equals(src, Constant.TESTPORTADDRESS.getAddress())){
	      			return;
	      		}
	      		////////////////////////////////获取UUID数据包////////////////////////////////
	            System.out.println(handle.getTimestamp());
	            System.out.println(ByteArrays.toHexString(packet, " "));
	            byte[] type = new byte[2];
	            type[0] = packet[12];
	            type[1] = packet[13];
	            System.out.print("TYPE:");
	            System.out.println(ByteArrays.toHexString(type, " "));
	            byte[] uuidsrc = new byte[36];
	            byte[] uuiddst = new byte[36];
	            int index = 0;
	            for(int i = 20; i < 56; i++){
	            	uuidsrc[index] = packet[i];
	            	index = index + 1;
	            }
	            index = 0;
	            for(int i = 56; i < 92; i++){
	            	uuiddst[index] = packet[i];
	            	index = index + 1;
	            }
	            System.out.print("Source UUID Packet:");
	            System.out.println(ByteArrays.toHexString(uuidsrc, " "));
	            System.out.print("Destination UUID Packet:");
	            System.out.println(ByteArrays.toHexString(uuiddst, " "));
	            byte[] data = new byte[packet.length - 92];
	            index = 0;
	            for(int i = 92; i < packet.length; i++){
	            	data[index] = packet[i];
	            	index = index + 1;
	            }
	            System.out.print("Packet Content Data:");
	            System.out.println(ByteArrays.toHexString(data, " "));
	            ////
	     		String str = new String(data);
	     		System.out.println(str);
	          }
	        };

	    try {
	      handle.loop(0, listener);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }

	    PcapStat ps = handle.getStats();
	    System.out.println("ps_recv: " + ps.getNumPacketsReceived());
	    System.out.println("ps_drop: " + ps.getNumPacketsDropped());
	    System.out.println("ps_ifdrop: " + ps.getNumPacketsDroppedByIf());
	    if (Platform.isWindows()) {
	      System.out.println("bs_capt: " + ps.getNumPacketsCaptured());
	    }

	    handle.close();		
	}
}
