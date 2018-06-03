package cn.edu.imu.client;


import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author huxinlei
 * @date 2015-06-20
 * @version 1.0
 */

public class DiskClient extends Socket{
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 2013;
	
	private Socket client;
	private FileInputStream fis;
	private DataOutputStream dos;
    private DataInputStream dis;
    private FileOutputStream fos;
	//写文件
	public boolean WriteFile(String filePath){
		boolean flag = false;
        try {
            try {
            	System.out.println(new Date());//开始时间
                client =new Socket(SERVER_IP, SERVER_PORT);
                //向服务端传送文件
                File file =new File(filePath);
                fis =new FileInputStream(file);
                dos =new DataOutputStream(client.getOutputStream());
                  
                //文件名和长度
                dos.writeUTF("WRITE");
                dos.flush();
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                  
                //传输文件
                byte[] sendBytes =new byte[1024];
                int length =0;
                while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
                    dos.write(sendBytes,0, length);
                    dos.flush();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(fis !=null)
                    fis.close();
                if(dos !=null)
                    dos.close();
                client.close();
                flag = true;
                System.out.println(new Date());//结束时间
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
		return flag;
	}
	//读文件
	public boolean ReadFile(String filePath){
		boolean flag = false;
        try {
            try { 
            	System.out.println(new Date());//开始时间
                client =new Socket(SERVER_IP, SERVER_PORT);
                //向服务端传送文件
                File file =new File(filePath);
                fis =new FileInputStream(file);
                dos =new DataOutputStream(client.getOutputStream());
                //文件名和长度
                dos.writeUTF("READ");
                dos.flush();
                dos.writeUTF(filePath);
                dos.flush();
                
                dis =new DataInputStream(client.getInputStream());
                //文件名和长度
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                fos =new FileOutputStream(new File("e:/" + fileName));
                      
                byte[] sendBytes =new byte[1024];
                int transLen =0;
                System.out.println("----开始接收文件<" + fileName +">,文件大小为<" + fileLength +">----");
                while(true){
                    int read =0;
                    read = dis.read(sendBytes);
                    if(read == -1)
                       break;
                    transLen += read;
                    System.out.println("接收文件进度" +100 * transLen/fileLength +"%...");
                    fos.write(sendBytes,0, read);
                    fos.flush();
                }
                System.out.println("----接收文件<" + fileName +">成功-------");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(dis !=null)
                    dis.close();
                if(fos !=null)
                    fos.close();
                client.close();
                flag = true;
                System.out.println(new Date());//结束时间
            }
        }catch (Exception e) {
            e.printStackTrace();
        }		
		return flag;
	}
	

	public static void main(String[] args) {
		DiskClient diskClient = new DiskClient();
		diskClient.WriteFile("c:/Other Linux 2.2.x kernel (2).zip");
	}

}
