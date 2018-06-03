package imu.edu.cn.basecom;

import java.util.List;
import java.util.Map;
public class TableItem {
	private int item_id;
	private Map<String,TableField> fieldList;
	private List<Action> actionList;

	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public Map<String, TableField> getFieldList() {
		return fieldList;
	}
	public void setFieldList(Map<String, TableField> fieldList) {
		this.fieldList = fieldList;
	}
	public List<Action> getActionList() {
		return actionList;
	}
	public void setActionList(List<Action> actionList) {
		this.actionList = actionList;
	}
}
