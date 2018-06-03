package com.ccit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.ccit.manager.ObjectManager;
import com.ccit.manager.ObjectManagerImpl;
import com.ccit.model.User;

public class ExportDB {

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		sdf = new SimpleDateFormat("HH:mm");
		String time = sdf.format(new Date());
		System.out.println(date);
		System.out.println(time);
	}
}

