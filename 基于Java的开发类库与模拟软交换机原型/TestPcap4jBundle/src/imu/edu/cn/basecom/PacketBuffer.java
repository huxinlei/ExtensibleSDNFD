package imu.edu.cn.basecom;
import java.util.Queue;
import java.util.LinkedList;

public class PacketBuffer {
	private int maxLength;//��󻺴����ݰ�����
	private int curLength;//��ǰ�������ݰ�����
	private Queue<byte[]> datas;//�������ݴ洢��
	
	//������󻺴����ݰ����ȣ���ʼ���������ݴ洢��
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
	//���ݰ����
	public void pushPacket(byte[] data) {
		if(curLength < maxLength){
			this.datas.offer(data);
			this.curLength = this.curLength + 1;
		}
	}
	//���ݰ�����
	public byte[] popPacket(){
		if(curLength > 0){
			this.curLength = this.curLength - 1;
			return this.datas.poll();
		}else{
			return null;
		}
	}
}
