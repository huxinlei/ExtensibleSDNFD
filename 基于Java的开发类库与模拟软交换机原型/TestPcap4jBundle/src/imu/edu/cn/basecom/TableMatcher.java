package imu.edu.cn.basecom;

import java.util.Arrays;
public class TableMatcher {
	public BasePacket match(Table table, BasePacket packet){
		if(table != null && table.getMatchFields() != null
				&& table.getMatchFields().size() > 0 
				 && table.getItems() != null 
				  && table.getItems().size() > 0){
			boolean flag = false;
			TableItem item = null;
			for(int i = 0; i < table.getItems().size(); i++){
				int count = 0;
				for(int j = 0; j < table.getMatchFields().size(); j++){
					byte[] packetValue = packet.getDatas() != null?packet.getDatas().get(table.getMatchFields().get(j).getField_des()):null;
					byte[] fieldValue = table.getItems().get(i).getFieldList().get(table.getMatchFields().get(j).getField_des()).getField();
					if(packetValue != null && fieldValue != null && Arrays.equals(packetValue, fieldValue)){
						count = count + 1;
					}
				}
				if(count == table.getMatchFields().size()){
					item = table.getItems().get(i);
					flag = true;
				}
			}
			if(flag && item != null){
				for(int i = 0; i < 6; i++){
					packet.getOrignalDatas()[i] = (byte)0xff;
				}
				byte[] source = item.getActionList().get(0).getPortAddress().getAddress();
				int index = 0;
				for(int i = 6; i < 12; i++){
					packet.getOrignalDatas()[i] = source[index];
					index = index + 1;
				}
				ActionSet alist = new ActionSet();
				alist.pushAction(item.getActionList().get(0));
				packet.setAlist(alist);
			}
		}
		return packet;
	}
}
