package imu.edu.cn.basecom;
import java.util.Queue;
import java.util.LinkedList;

public class PacketBuffer {
	private int maxLength;//最大缓存数据包长度
	private int curLength;//当前缓存数据包长度
	private Queue<byte[]> datas;//缓存数据存储区
	
	//设置最大缓存数据包长度，初始化缓存数据存储区
	public PacketBuffer(int maxLength){
		this.maxLength = maxLength;
		this.datas = new LinkedList<byte[]>();
	}
	public int getMaxLength() {
		return maxLength;
	}
	public int getCurLength() {
		return curLength;
	}
	public Queue<byte[]> getDatas() {
		return datas;
	}
	//数据包入队
	public void pushPacket(byte[] data) {
		if(curLength < maxLength){
			this.datas.offer(data);
			this.curLength = this.curLength + 1;
		}
	}
	//数据包出队
	public byte[] popPacket(){
		if(curLength > 0){
			this.curLength = this.curLength - 1;
			return this.datas.poll();
		}else{
			return null;
		}
	}
}
