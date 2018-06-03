package imu.edu.cn.basecom;

import java.util.List;
public class Table {
	private int table_id;
	private List<TableItem> items;
	private List<TableField> matchFields;
	
	public int getTable_id() {
		return table_id;
	}
	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}
	public List<TableItem> getItems() {
		return items;
	}
	public void setItems(List<TableItem> items) {
		this.items = items;
	}
	public List<TableField> getMatchFields() {
		return matchFields;
	}
	public void setMatchFileds(List<TableField> matchFields) {
		this.matchFields = matchFields;
	}
}
