package imu.edu.cn.basecom;

import java.util.LinkedList;
import java.util.Queue;

public class ActionSet {
	private int curLength;//当前缓存数据包长度
	private Queue<Action> alist;//缓存数据存储区
	
	//设置最大缓存数据包长度，初始化缓存数据存储区
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
	//数据包入队
	public void pushAction(Action action) {
		this.alist.offer(action);
		this.curLength = this.curLength + 1;
	}
	//数据包出队
	public Action popAction(){
		if(curLength > 0){
			this.curLength = this.curLength - 1;
			return this.alist.poll();
		}else{
			return null;
		}
	}	
}
