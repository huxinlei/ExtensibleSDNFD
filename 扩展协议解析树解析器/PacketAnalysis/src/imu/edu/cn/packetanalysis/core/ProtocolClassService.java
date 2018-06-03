package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.tools.Constant;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ProtocolClassService {
	/**
	 * @author huxinlei
	 * @function 生成实体类
	 * @date 2015-3-9 
	 * @最终修改日期：2015-3-12
	 */
	public void initClasses(String className, 
			PacketMapService packets, PacketClassService classes){
		System.out.println("initClassses工作正常进行中...");
		System.out.println("要调用的类:" + className);
		List<Object> packetData = new ArrayList<Object>();
		try{
			for(int i = 0; i < packets.getPacketData().size(); i++){
				Map map = packets.getPacketData().get(i);
				Iterator it = map.keySet().iterator();
			
				Class<?> classType = Class.forName(className);
				Constructor con = classType.getDeclaredConstructor(new Class[]{});
				Object object = con.newInstance(new Object[]{});
		
				String key;
				byte[] value;
				int intValue;
				while(it.hasNext()){
			
					key = it.next().toString();
					
					System.out.println(key);
					
					if(key.equals(Constant.PROTOCOLFILE_HL)){
						continue;
					}
					Field field = classType.getDeclaredField(key);
					field.setAccessible(true);
					Object str = field.get(object);
					//System.out.println("修改之前" + key + "的值：" + (String)str);
			
					try{
						value = (byte[])map.get(key);
						System.out.println("key:" + key);
						System.out.print("value:");
						for(int j = 0; j < value.length; j++){
							System.out.print(value[j] + " ");
						}
						System.out.println();
						field.set(object, value);
					}catch(Exception e){
						intValue = Integer.parseInt(map.get(key).toString());
						System.out.println("key:" + key);
						System.out.print("value:" + intValue);
						System.out.println();
						field.set(object, intValue);
					}
				}
				
				packetData.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		classes.setPacketData(packetData);
	}
}
