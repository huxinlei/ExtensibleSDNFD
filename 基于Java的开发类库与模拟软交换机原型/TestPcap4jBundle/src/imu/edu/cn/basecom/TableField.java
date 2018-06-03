package imu.edu.cn.basecom;

public class TableField {
	private String field_des;//×Ö¶ÎÃèÊö
	private int length;//×Ö¶Î³¤¶È
	private byte[] field;//×Ö¶ÎÄÚÈİ
	
	public String getField_des() {
		return field_des;
	}
	public void setField_des(String field_des) {
		this.field_des = field_des;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getField() {
		return field;
	}
	public void setField(byte[] field) {
		this.field = field;
	}
}
