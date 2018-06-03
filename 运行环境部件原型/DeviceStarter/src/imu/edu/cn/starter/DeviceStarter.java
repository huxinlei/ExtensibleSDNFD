package imu.edu.cn.starter;

//import java.awt.Desktop;
import java.io.File;

public class DeviceStarter {
	public static void main(String[] args) throws Exception{
		String filePath = System.getProperty("user.dir");//获得user.dir指定了当前的路径
		System.out.println(filePath);
		filePath = filePath+"\\startup.bat";
		//filePath = "D:\\SQL\\apache-tomcat-6.0.32\\bin\\startup.bat";
		//filePath = "D:\\SQL\\plugins\\run.bat";
		System.out.println(filePath);
		File file = new File(filePath);
		System.out.println(file.getPath());
		System.out.println(file.getName());
		System.out.println(file.isFile());
		System.out.println(file.exists());
		if(file.isFile()&&file.exists()){
			//Runtime.getRuntime().exec(filePath);
			//Runtime.getRuntime().exec(filePath);
			//Desktop.getDesktop().open(file);
			String cmdStr = "cmd.exe   /C   start  " + filePath;
			System.out.println(cmdStr);
			Runtime.getRuntime().exec(cmdStr);
		}else{
			System.out.println("start device failed, the file run.bat is missing!");
		}
		//Desktop.getDesktop().open(file);
		
	}
}
