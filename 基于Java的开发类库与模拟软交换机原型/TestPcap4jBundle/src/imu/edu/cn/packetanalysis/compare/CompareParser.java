package imu.edu.cn.packetanalysis.compare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.tools.Constant;
import imu.edu.cn.packetanalysis.tools.ConstantV2;
import imu.edu.cn.packetanalysis.tools.TypeConvertor;

public class CompareParser {
	private PacketsInputService packetInput;
	private PacketOutputService packetOutput;
	
	public CompareParser(PacketsInputService packetInput){
		//数据输入赋值
		this.packetInput = packetInput;
	}
	public PacketOutputService analysisRun(){
		this.packetOutput = this.packetsParser();
		return this.packetOutput;
	}
	public PacketOutputService packetsParser(){
		Date begin = new Date();
		System.out.println("传统处理数据包方式开始时间：" + begin.toLocaleString());
		PacketMapService packets = new PacketMapService();
		List<Map> listMap = new ArrayList<Map>();
		List<byte[]> datalist = this.packetInput.getPacketData();
		if(datalist.size() > 0){
			//循环处理每一个节点
			for(int i = 0; i < datalist.size(); i++){
				Map packet = onePacketParse(datalist.get(i));
				listMap.add(packet);
			}
		}
		packets.setPacketData(listMap);
		Date end = new Date();
		System.out.println("传统处理数据包方式结束时间：" + end.toLocaleString());
        FileWriter writer;
        try {
            writer = new FileWriter("C:/compare.txt");
            writer.write("传统处理数据包方式开始时间：" + begin.toLocaleString());
            writer.write("传统处理数据包方式结束时间：" + end.toLocaleString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return packets;
	}
	public Map onePacketParse(byte[] onedata){
		Map packet = new HashMap();
		byte[] data = onedata;
		if(data != null){
			ipParser(packet, data);
		}
		return packet;
	}
	public void ipParser(Map packet, byte[] onedata){
		int prelength = 0;
		byte[] version = contentParse(Ipv4.version, prelength, onedata);
		System.out.println("( 1.1 version:" +TypeConvertor.bytesToHexString(version)+ ")");
		packet.put("1.1", version);
		prelength = prelength + Ipv4.version;
		byte[] hl = contentParse(Ipv4.headerlength, prelength, onedata);
		System.out.println("( 1.2 headerlength:" +TypeConvertor.bytesToHexString(hl)+ ")");
		packet.put("1.2", hl);
		
		prelength = prelength + Ipv4.headerlength;
		byte[] tos = contentParse(Ipv4.tos, prelength, onedata);
		System.out.println("( 1.3 tos:" +TypeConvertor.bytesToHexString(tos)+ ")");
		packet.put("1.3", tos);
		
		prelength = prelength + Ipv4.tos;
		byte[] al = contentParse(Ipv4.alength, prelength, onedata);
		System.out.println("( 1.4 alength:" +TypeConvertor.bytesToHexString(al)+ ")");
		packet.put("1.4", al);
		prelength = prelength + Ipv4.alength;
		
		byte[] fragi = contentParse(Ipv4.fragidentifier, prelength, onedata);
		System.out.println("( 1.5 fragidentifier:" +TypeConvertor.bytesToHexString(fragi)+ ")");
		packet.put("1.5", fragi);
		prelength = prelength + Ipv4.fragidentifier;
		
		byte[] fragmf = contentParse(Ipv4.fragmentflags, prelength, onedata);
		System.out.println("( 1.6 fragmentflags:" +TypeConvertor.bytesToHexString(fragmf)+ ")");
		packet.put("1.6", fragmf);
		prelength = prelength + Ipv4.fragmentflags;
		
		byte[] fragmo = contentParse(Ipv4.fragmentoffset, prelength, onedata);
		System.out.println("( 1.7 fragmentoffset:" +TypeConvertor.bytesToHexString(fragmo)+ ")");
		packet.put("1.7", fragmo);
		prelength = prelength + Ipv4.fragmentoffset;
		
		byte[] ttl = contentParse(Ipv4.ttl, prelength, onedata);
		System.out.println("( 1.8 ttl:" +TypeConvertor.bytesToHexString(ttl)+ ")");
		packet.put("1.8", ttl);
		prelength = prelength + Ipv4.ttl;
		
		byte[] type = contentParse(Ipv4.type, prelength, onedata);
		System.out.println("( 1.9 type:" +TypeConvertor.bytesToHexString(type)+ ")");
		packet.put("1.9", type);
		prelength = prelength + Ipv4.type;
		
		byte[] checksum = contentParse(Ipv4.checksum, prelength, onedata);
		System.out.println("( 1.10 checksum:" +TypeConvertor.bytesToHexString(checksum)+ ")");
		packet.put("1.10", checksum);
		prelength = prelength + Ipv4.checksum;
		
		byte[] sourceip = contentParse(Ipv4.sourceip, prelength, onedata);
		System.out.println("( 1.11 sourceip:" +TypeConvertor.bytesToHexString(sourceip)+ ")");
		packet.put("1.11", sourceip);
		prelength = prelength + Ipv4.sourceip;
		
		byte[] destip = contentParse(Ipv4.destip, prelength, onedata);
		System.out.println("( 1.12 destip:" +TypeConvertor.bytesToHexString(destip)+ ")");
		packet.put("1.12", destip);
		prelength = prelength + Ipv4.destip;
		
		String protype = TypeConvertor.bytesToHexString(type).trim();
		if(protype.equals("06")){
			tcpParser(packet, onedata, prelength);
		}else if(protype.equals("11")){
			udpParser(packet, onedata, prelength);
		}
	}
	public void tcpParser(Map packet, byte[] onedata, int prel){
		int prelength = prel;
		
		byte[] sourceport = contentParse(Tcp.sourceport, prelength, onedata);
		System.out.println("( 2.1 sourceport:" +TypeConvertor.bytesToHexString(sourceport)+ ")");
		packet.put("2.1", sourceport);
		prelength = prelength + Tcp.sourceport;
		
		byte[] destport = contentParse(Tcp.destport, prelength, onedata);
		System.out.println("( 2.2 destport:" +TypeConvertor.bytesToHexString(destport)+ ")");
		packet.put("2.2", destport);
		prelength = prelength + Tcp.destport;
		
		byte[] snum = contentParse(Ipv4.tos, prelength, onedata);
		System.out.println("( 2.3 sequencenum:" +TypeConvertor.bytesToHexString(snum)+ ")");
		packet.put("2.3", snum);
		prelength = prelength + Tcp.sequencenum;
		
		byte[] ackment = contentParse(Tcp.acknowledgement, prelength, onedata);
		System.out.println("( 2.4 acknowledgement:" +TypeConvertor.bytesToHexString(ackment)+ ")");
		packet.put("2.4", ackment);
		prelength = prelength + Tcp.acknowledgement;
		
		byte[] hl = contentParse(Tcp.headerlength, prelength, onedata);
		System.out.println("( 2.5 headerlength:" +TypeConvertor.bytesToHexString(hl)+ ")");
		packet.put("2.5", hl);
		prelength = prelength + Tcp.headerlength;
		
		byte[] retain = contentParse(Tcp.retain, prelength, onedata);
		System.out.println("( 2.6 retain:" +TypeConvertor.bytesToHexString(retain)+ ")");
		packet.put("2.6", retain);
		prelength = prelength + Tcp.retain;
		
		byte[] flags = contentParse(Tcp.flags, prelength, onedata);
		System.out.println("( 2.7 flags:" +TypeConvertor.bytesToHexString(flags)+ ")");
		packet.put("2.7", flags);
		prelength = prelength + Tcp.flags;
		
		byte[] window = contentParse(Tcp.window, prelength, onedata);
		System.out.println("( 2.8 window:" +TypeConvertor.bytesToHexString(window)+ ")");
		packet.put("2.8", window);
		prelength = prelength + Tcp.window;
		
		byte[] checksum = contentParse(Tcp.checksum, prelength, onedata);
		System.out.println("( 2.9 checksum:" +TypeConvertor.bytesToHexString(checksum)+ ")");
		packet.put("2.9", checksum);
		prelength = prelength + Tcp.checksum;
		
		byte[] urgentp = contentParse(Tcp.urgentpointer, prelength, onedata);
		System.out.println("( 2.10 urgentpointer:" +TypeConvertor.bytesToHexString(urgentp)+ ")");
		packet.put("2.10", urgentp);
		prelength = prelength + Tcp.urgentpointer;
		
		int length = onedata.length * 8 - prelength;
		byte[] data = contentParse(length, prelength, onedata);
		System.out.println("( 2.11 data:" +TypeConvertor.bytesToHexString(data)+ ")");
		packet.put("2.11", data);
	}
	public void udpParser(Map packet, byte[] onedata, int prel){
		int prelength = prel;
		byte[] sourceport = contentParse(Udp.sourceport, prelength, onedata);
		System.out.println("( 3.1 sourceport:" +TypeConvertor.bytesToHexString(sourceport)+ ")");
		packet.put("3.1", sourceport);
		prelength = prelength + Udp.sourceport;
		
		byte[] destport = contentParse(Udp.destport, prelength, onedata);
		System.out.println("( 3.2 destport:" +TypeConvertor.bytesToHexString(destport)+ ")");
		packet.put("3.2", destport);
		prelength = prelength + Udp.destport;
		
		byte[] lg = contentParse(Udp.length, prelength, onedata);
		System.out.println("( 3.3 length:" +TypeConvertor.bytesToHexString(lg)+ ")");
		packet.put("3.3", lg);
		prelength = prelength + Udp.length;
		
		byte[] checksum = contentParse(Udp.checksum, prelength, onedata);
		System.out.println("( 3.4 checksum:" +TypeConvertor.bytesToHexString(checksum)+ ")");
		packet.put("3.4", checksum);
		prelength = prelength + Udp.checksum;
		
		int length = onedata.length * 8 - prelength;
		byte[] data = contentParse(length, prelength, onedata);
		System.out.println("( 3.5 data:" +TypeConvertor.bytesToHexString(data)+ ")");
		packet.put("3.5", data);
	}
	/**
	 * 从数据中解析协议解析
	 * @param length
	 * @param prelength
	 * @param data
	 */
	public byte[] contentParse(int length, int prelength, byte[] data){
		int bcount = 0;
		int remainder = 0;
		int precount = 0;
		int preremainder = 0;
		byte[] field = null;
		//判断字段长度是否是8的倍数
		if(length % 8 == 0){
			bcount = length / 8;
			remainder = 0;
		}else{
			bcount = length / 8;
			remainder = length % 8;
		}
		//判断字段之前数据字节数是否是8的倍数
		if(prelength % 8 == 0){
			precount = prelength / 8;
			preremainder = 0;
		}else{
			precount = prelength / 8;
			preremainder = prelength % 8;
		}
		//字段之前数据为字节的整数倍
		if(preremainder == 0){
			if(remainder == 0){
				field = new byte[bcount];
				for(int i = precount,count = 0; i < data.length && count < bcount; i++,count++){
					field[count] = data[i];
				}
			}else{
				field = new byte[bcount + 1];
				for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
					field[count] = data[i];
				}
				//处理最后一个非整字节数据
				afterRemainder(field, bcount, remainder);
			}
		}else{
			if(remainder == 0){
				field = new byte[bcount + 1];
				for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
					field[count] = data[i];
				}
				//处理之前一个非整字节数据
				beforeRemainder(field, precount - 1, preremainder);
				//处理最后一个非整数字节
				afterRemainder(field, bcount, 8 - preremainder);
			}else{
				//获取处理后缀
				int after = length - (8 - preremainder) - (bcount * 8);
				if(after <= 0){
					field = new byte[bcount + 1];
					for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//处理之前一个非整字节数据
					beforeRemainder(field, 0, preremainder);
					//处理最后一个非整数字节
					afterRemainder(field, bcount, -after);
				} else {
					field = new byte[bcount + 2];
					for(int i = precount - 1,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//处理之前一个非整字节数据
					beforeRemainder(field, 0, preremainder);
					//处理最后一个非整数字节
					afterRemainder(field, bcount + 1, after);
				}
			}
		}
		return field;
	}
	/**
	 * 处理后面数据为非整数字节
	 * @param data
	 * @param count
	 * @param remainder
	 */
	public void afterRemainder(byte[] data, int count, int remainder){
		switch(remainder){
			case 1:
				data[count] = (byte)(data[count] & ConstantV2.ONE);
				break;
			case 2:
				data[count] = (byte)(data[count] & ConstantV2.TWO);
				break;
			case 3:
				data[count] = (byte)(data[count] & ConstantV2.THREE);
				break;
			case 4:
				data[count] = (byte)(data[count] & ConstantV2.FOUR);
				break;
			case 5:
				data[count] = (byte)(data[count] & ConstantV2.FIVE);
				break;
			case 6:
				data[count] = (byte)(data[count] & ConstantV2.SIX);
				break;
			case 7:
				data[count] = (byte)(data[count] & ConstantV2.SEVEN);
				break;
		}
	}
	/**
	 * 处理后面数据为非整数字节
	 * @param data
	 * @param count
	 * @param remainder
	 */
	public void beforeRemainder(byte[] data, int count, int remainder){
		switch(remainder){
			case 1:
				data[count] = (byte)(data[count] & ConstantV2.ONE_Z);
				break;
			case 2:
				data[count] = (byte)(data[count] & ConstantV2.TWO_Z);
				break;
			case 3:
				data[count] = (byte)(data[count] & ConstantV2.THREE_Z);
				break;
			case 4:
				data[count] = (byte)(data[count] & ConstantV2.FOUR_Z);
				break;
			case 5:
				data[count] = (byte)(data[count] & ConstantV2.FIVE_Z);
				break;
			case 6:
				data[count] = (byte)(data[count] & ConstantV2.SIX_Z);
				break;
			case 7:
				data[count] = (byte)(data[count] & ConstantV2.SEVEN_Z);
				break;
		}
	}
}
