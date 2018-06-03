package imu.edu.cn.basecom;

import java.util.LinkedList;
import java.util.Queue;

public class ActionSet {
	private int curLength;//��ǰ�������ݰ�����
	private Queue<Action> alist;//�������ݴ洢��
	
	//������󻺴����ݰ����ȣ���ʼ���������ݴ洢��
	public ActionSet(){
		this.curLength = 0;
		this.alist = new LinkedList<Action>();
	}
	public int getCurLength() {
		return curLength;
	}
	public Queue<Action> getActionList() {
		return alist;
	}
	//���ݰ����
	public void pushAction(Action action) {
		this.alist.offer(action);
		this.curLength = this.curLength + 1;
	}
	//���ݰ�����
	public Action popAction(){
		if(curLength > 0){
			this.curLength = this.curLength - 1;
			return this.alist.poll();
		}else{
			return null;
		}
	}	
}
