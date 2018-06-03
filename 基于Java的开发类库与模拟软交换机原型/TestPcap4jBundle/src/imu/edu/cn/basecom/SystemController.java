package imu.edu.cn.basecom;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.util.ByteArrays;

public class SystemController {
	public static List<Port> ports;
	public static List<Table> tables;
	public static Port port;
	public static int index;
	public static void initPorts(int inBufferSize, int outBufferSize) throws PcapNativeException,InterruptedException {
		List<PcapNetworkInterface> interList = Pcaps.findAllDevs();
		ports = new ArrayList<Port>();
		for(int i = 0; i < interList.size(); i++){
			System.out.println("===================================begin=====================================");
			System.out.println(interList.get(i).getName() + "(" + interList.get(i).getDescription() + ")");
			System.out.println(interList.get(i).getAddresses() + "(" + interList.get(i).getLinkLayerAddresses()+ ")");
			System.out.println(" ======================================end==================================");
			PacketBuffer inBuffer = null;
			if(inBufferSize > 0){
				inBuffer = new PacketBuffer(inBufferSize);
			}else{
				inBuffer = new PacketBuffer(10);
			}
			PacketBuffer outBuffer = null;
			if(outBufferSize > 0){
				outBuffer = new PacketBuffer(outBufferSize);
			}else{
				outBuffer = new PacketBuffer(10);
			}
			Port port = new Port(interList.get(i),inBuffer,outBuffer);
			ports.add(port);
		}
		//for(int i = 0; i < interList.size(); i++){
			//index = i;
		Thread t = new Thread(new Runnable(){
			public void run(){
				try{
					//int i = index;
					ports.get(0).initPacketSender();
					ports.get(0).initReceiveListener();
				}catch(NotOpenException e){
					e.printStackTrace();
				}catch(PcapNativeException e){
					e.printStackTrace();
				}
			}
		});
		t.start();
		/*Thread t1 = new Thread(new Runnable(){
			public void run(){
				try{
					ports.get(1).initPacketSender();
					ports.get(1).initReceiveListener();
				}catch(NotOpenException e){
					e.printStackTrace();
				}catch(PcapNativeException e){
					e.printStackTrace();
				}
			}
		});
		t1.start();*/
		//}

	}
	public static void initTables(){
		tables = new ArrayList<Table>();
		Table table = new Table();
		////����ƥ���id
		table.setTable_id(0);//����Table Id
		
		////����ƥ����
		TableField matchField = new TableField();//����ƥ����1
		matchField.setField_des(UUIDPacket.destuuid_des);//����ƥ��������
		TableField matchField2 = new TableField();//����ƥ����2
		matchField2.setField_des(UUIDPacket.sourceuuid_des);
		List<TableField> matchFields = new ArrayList<TableField>();
		matchFields.add(matchField);
		matchFields.add(matchField2);
		table.setMatchFileds(matchFields);
		
		////��������Ŀ
		TableItem item = new TableItem();
		  ////��������ʼid
		int item_id = 0;
		item.setItem_id(item_id);
		
		  ////�������ֶ�
		Map<String,TableField> fieldList = new HashMap<String, TableField>();
		TableField itemField = new TableField();//��Ŀ����1
		itemField.setField_des(UUIDPacket.destuuid_des);
		itemField.setField(Constant.TESTUUID2.getBytes());
		itemField.setLength(Constant.TESTUUID2.getBytes().length);
		fieldList.put(UUIDPacket.destuuid_des, itemField);
		
		TableField itemField1 = new TableField();//��Ŀ����2
		itemField1.setField_des(UUIDPacket.sourceuuid_des);
		itemField1.setField(Constant.TESTUUID1.getBytes());
		itemField1.setLength(Constant.TESTUUID1.getBytes().length);
		fieldList.put(UUIDPacket.sourceuuid_des, itemField1);
		
		item.setFieldList(fieldList);
		
		  ////���ö�����
		List<Action> actionList = new ArrayList<Action>();
		Action action = new Action();
		action.setType(Constant.FORWARD);
		action.setPortAddress(Constant.TESTPORTADDRESS);
		actionList.add(action);
		item.setActionList(actionList);
		
		////���������
		List<TableItem> items = new ArrayList<TableItem>();
		items.add(item);
		table.setItems(items);
		
		//�������
		tables.add(table);
	}
	public static void testPortAndBuffer() throws  PcapNativeException,InterruptedException {
		List<PcapNetworkInterface> interList = Pcaps.findAllDevs();
		for(int i = 0; i < interList.size(); i++){
			System.out.println("===================================begin=====================================");
			System.out.println(interList.get(i).getName() + "(" + interList.get(i).getDescription() + ")");
			System.out.println(interList.get(i).getAddresses() + "(" + interList.get(i).getLinkLayerAddresses()+ ")");
			System.out.println(" ======================================end==================================");
		}
		PacketBuffer inBuffer = new PacketBuffer(10);
		PacketBuffer outBuffer = new PacketBuffer(10);
		
		final byte[] sendata = {  
		        (byte)0x00, (byte)0x0c, (byte)0x29, (byte)0x7f, (byte)0x4c, (byte)0x3b,//dst mac  
		        (byte)0x00, (byte)0x50, (byte)0x56, (byte)0xc0, (byte)0x00, (byte)0x08,//src mac  
		        (byte)0x88, (byte)0x8e, //Type: 802.1x authentication  
		        (byte)0x01, //Version:v1  
		        (byte)0x01, //Type:  Start (1)  
		        (byte)0x00, (byte)0x00//Length 0  
		};
		outBuffer.pushPacket(sendata);
		port = new Port(interList.get(0),inBuffer,outBuffer);
		Thread t = new Thread(new Runnable(){
			public void run(){
			    try{
		    		port.initPacketSender();
		    		port.initReceiveListener();
			    }catch(NotOpenException e){
			    	e.printStackTrace();
			    }catch(PcapNativeException e){
			    	e.printStackTrace();
			    }
			}
		});
		t.start();
		while(true){
			System.out.println("===========================pop-begin====================");
			//port.getOutBuffer().pushPacket(sendata);
			byte[] data = port.getInBuffer().popPacket();
			System.out.println("Buffer��ǰ�������ݰ�������" + port.getInBuffer().getCurLength());
			if(data != null)
				System.out.println(ByteArrays.toHexString(data, " "));
			System.out.println(" ===========================pop-end====================");
			Thread.sleep(900);
		}					
	}
	public static void switchWork() throws InterruptedException{
		while(true){
			System.out.println("===========================pop-begin====================");
			//port.getOutBuffer().pushPacket(sendata);
			byte[] data = ports.get(0).getInBuffer().popPacket();
			System.out.println("Buffer��ǰ�������ݰ�������" + ports.get(0).getInBuffer().getCurLength());
			if(data != null)
				System.out.println(ByteArrays.toHexString(data, " "));
			System.out.println(" ===========================pop-end====================");
			////��ʼ���ݰ�����
			SimpleParser parser = new SimpleParser();
			BasePacket packet = parser.parse(data);
			packet = parser.parse(packet);
			
			////ƥ��������ȷ��������
			TableMatcher matcher = new TableMatcher();
			packet = matcher.match(tables.get(0), packet);
			
			////��������ִ����
			ActionExecutor executor = new ActionExecutor();
			executor.executeActions(packet, ports);//ת�����ݰ�
			Thread.sleep(100);
		}	
	}
	public static void main(String[] args)  throws PcapNativeException,InterruptedException {
		initPorts(100,100);//��ʼ���������˿�
		initTables();//��ʼ������
		switchWork();
		//testPortAndBuffer();
	}

}
