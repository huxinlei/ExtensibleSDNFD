package cn.edu.imu.config;

import java.util.Properties;
import java.io.InputStream;

public class ProReader extends Properties{
	
	private static final long serialVersionUID = 1L;
	
	private static ProReader proReader = null;//����proReader��ʵ��
	
	/***
	 * ����ȡ��ʵ���ķ���
	 * return proReader
	 */
	public static ProReader getProReader()
	{
		if(proReader == null)
		{
			makeProReader();
		}
		
		return proReader;//����proReader����
	}
	
	/**
	 * ͬ������˽�й��췽�����ΨһProReaderʵ��
	 */
	private static synchronized void makeProReader()
	{
		if(proReader == null)
		{
			try{
				proReader = new ProReader();//ͨ�������˽�й��췽�����proReader��ʵ��
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ˽�й��췽��,ȷ��ʵ����Ψһ��
	 */
	private ProReader() throws Exception
	{
		InputStream is = getClass().getResourceAsStream("db.properties");//��������ļ���������
		
		try
		{
			load(is);//����������
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
