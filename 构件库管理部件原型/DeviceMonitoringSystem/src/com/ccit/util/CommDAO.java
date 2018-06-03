package com.ccit.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class CommDAO {
	public static String dbname = "root";
	public static String dbtype = "root";
	public static String db = "LXMSystem";

	public static Connection conn = null;

	public CommDAO() {
		conn = this.getConn();
	}

	public Connection getConn() {
		try {
			if (conn == null || conn.isClosed()) {

				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/" + db, dbname, dbtype);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String insert(File file, String tablename) {
		String sql = "insert into " + tablename + "(";

		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from " + tablename);
			ResultSetMetaData rsmd = rs.getMetaData();
			int i = rsmd.getColumnCount();

			for (int j = 1; j <= i; j++) {
				if (rsmd.getColumnName(j).equals("id"))
					continue;
				sql += rsmd.getColumnName(j) + ",";
			}
			sql = sql.substring(0, sql.length() - 1);

			sql += ") values";
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Statement st = conn.createStatement();
			Workbook wb = Workbook.getWorkbook(file);
			Sheet[] sheet = wb.getSheets();
			for (Sheet sh : sheet) {
				for (int i = 1; i < sh.getRows(); i++) {
					sql += "(";
					Cell[] cells = sh.getRow(i);
					for (Cell c : cells) {
						String value = c.getContents();
						sql += "'" + value + "',";
					}
					sql = sql.substring(0, sql.length() - 1) + "),";

				}
			}
			sql = sql.substring(0, sql.length() - 1);
			this.commOper(sql);

			st.close();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public void commOper(String sql) {
		System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new CommDAO().insert(new File("D:\\Part.xls"), "t_News");
	}
}
