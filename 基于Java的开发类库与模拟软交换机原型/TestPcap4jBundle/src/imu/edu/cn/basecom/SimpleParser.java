package imu.edu.cn.basecom;

import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

import java.util.Arrays;
import java.util.HashMap;

public class SimpleParser {
	public BasePacket parse(byte[] data){
		BasePacket packet = new BasePacket();
		packet.setOrignalDatas(data);
		if(data == null){
			return packet;
		}
		//����destination address�ֶ�
		int offset = 0;
		int length = 6;
		if(offset + length < packet.getOrignalDatas().length){
			byte[] temp = new byte[length];
			int index = 0;
			for(int i = offset; i < offset+length; i++){
				temp[index] = packet.getOrignalDatas()[i];
				index = index + 1;
			}
			System.out.println(ByteArrays.toHexString(temp, " "));
			packet.setDstAddress(MacAddress.getByAddress(temp));
			System.out.println(packet.getDstAddress().toString());
		}
		//����source address�ֶ�
		offset = offset + length;
		length = 6;
		if(offset + length < packet.getOrignalDatas().length){
			byte[] temp = new byte[length];
			int index = 0;
			for(int i = offset; i < offset+length; i++){
				temp[index] = packet.getOrignalDatas()[i];
				index = index + 1;
			}
			System.out.println(ByteArrays.toHexString(temp, " "));
			packet.setSrcAddress(MacAddress.getByAddress(temp));
			System.out.println(packet.getSrcAddress().toString());
		}
		//����type�ֶ�
		offset = offset + length;
		length = 2;
		if(offset + length < packet.getOrignalDatas().length){
			byte[] temp = new byte[length];
			int index = 0;
			for(int i = offset; i < offset+length; i++){
				temp[index] = packet.getOrignalDatas()[i];
				index = index + 1;
			}
			packet.setType(temp);
			System.out.println(ByteArrays.toHexString(temp, " "));
		}		
		return packet;
	}
	public BasePacket parse(BasePacket packet){
		if(packet.getOrignalDatas()!=null && Arrays.equals(packet.getType(), Constant.UUIDTYPE)){
			packet.setDatas(new HashMap<String,byte[]>());
			//����version�ֶ�
			int offset = 16;
			int length = UUIDPacket.version;
			if(offset + length < packet.getOrignalDatas().length){
				byte[] data = new byte[length];
				int index = 0;
				for(int i = offset; i < offset+length; i++){
					data[index] = packet.getOrignalDatas()[i];
					index = index + 1;
				}
				packet.getDatas().put(UUIDPacket.ver_des, data);
			}
			//����flag�ֶ�
			offset = offset + length;
			length = UUIDPacket.flag;
			if(offset + length < packet.getOrignalDatas().length){
				byte[] data = new byte[length];
				int index = 0;
				for(int i = offset; i < offset+length; i++){
					data[index] = packet.getOrignalDatas()[i];
					index = index + 1;
				}
				packet.getDatas().put(UUIDPacket.flag_des, data);
			}
			//��������ݰ�����ֶ�
			offset = offset + length;
			length = UUIDPacket.fragidentifier;
			if(offset + length < packet.getOrignalDatas().length){
				byte[] data = new byte[length];
				int index = 0;
				for(int i = offset; i < offset+length; i++){
					data[index] = packet.getOrignalDatas()[i];
					index = index + 1;
				}
				packet.getDatas().put(UUIDPacket.fragidentifier_des, data);
			}
			//����ԴUUID
			offset = offset + length;
			length = UUIDPacket.sourceuuid;
			if(offset + length < packet.getOrignalDatas().length){
				byte[] data = new byte[length];
				int index = 0;
				for(int i = offset; i < offset+length; i++){
					data[index] = packet.getOrignalDatas()[i];
					index = index + 1;
				}
				packet.getDatas().put(UUIDPacket.sourceuuid_des, data);
			}
			//����Ŀ��UUID
			offset = offset + length;
			length = UUIDPacket.destuuid;
			if(offset + length < packet.getOrignalDatas().length){
				byte[] data = new byte[length];
				int index = 0;
				for(int i = offset; i < offset+length; i++){
					data[index] = packet.getOrignalDatas()[i];
					index = index + 1;
				}
				packet.getDatas().put(UUIDPacket.destuuid_des, data);
			}	
		}
		return packet;
	}
}
