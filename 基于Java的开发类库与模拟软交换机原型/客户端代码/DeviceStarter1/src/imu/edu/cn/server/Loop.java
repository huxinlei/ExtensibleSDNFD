package imu.edu.cn.server;
import java.io.IOException;
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
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
import com.sun.jna.Platform;

@SuppressWarnings("javadoc")
public class Loop {

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

  private Loop() {}

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
    final PcapHandle sendHandle
    = nif.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

    if (filter.length() != 0) {
      handle.setFilter(
        filter,
        BpfCompileMode.OPTIMIZE
      );
    }
    final byte[] sendata = {  
        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,//dst mac  
        (byte)0x00, (byte)0x23, (byte)0x54, (byte)0x0e, (byte)0xe5, (byte)0xd8,//src mac  
        (byte)0x88, (byte)0x8e, //Type: 802.1x authentication  
        (byte)0x01, //Version:v1  
        (byte)0x01, //Type:  Start (1)  
        (byte)0x00, (byte)0x00//Length 0  
    };
    PacketListener listener
      = new PacketListener() {
          @Override
          public void gotPacket(Packet packet) {
            //System.out.println(handle.getTimestamp());
        	if(packet.get(EthernetPacket.class).getHeader().getSrcAddr().toString().equals("00:50:56:c0:00:08")){
                System.out.println("=============================begin==========================");
                //System.out.println(packet.get(EthernetPacket.class).getHeader().getSrcAddr());
                //System.out.println(packet.get(EthernetPacket.class).getHeader().getDstAddr());
                System.out.println(packet);
                //System.out.println(packet.get(EthernetPacket.class));
                System.out.println(ByteArrays.toHexString(packet.getRawData()," "));
                System.out.println("==============================end=========================");
        	}
            //System.out.println(packet);

            /*System.out.println(packet.get(EthernetPacket.class).getHeader().getDstAddr().toString().equals("00:50:56:c0:00:08"));
            if(packet.get(EthernetPacket.class).getHeader().getDstAddr().toString().equals("00:50:56:c0:00:08")){
                try {
        			EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        			etherBuilder.dstAddr(packet.get(EthernetPacket.class).getHeader().getDstAddr())
        			            .srcAddr(packet.get(EthernetPacket.class).getHeader().getSrcAddr())
        			            .pad(sendata);
                	sendHandle.sendPacket(etherBuilder.build());
                	System.out.println("data send complete!");
                	System.out.println(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
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
