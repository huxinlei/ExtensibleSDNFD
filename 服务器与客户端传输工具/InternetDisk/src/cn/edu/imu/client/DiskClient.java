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
	//д�ļ�
	public boolean WriteFile(String filePath){
		boolean flag = false;
        try {
            try {
            	System.out.println(new Date());//��ʼʱ��
                client =new Socket(SERVER_IP, SERVER_PORT);
                //�����˴����ļ�
                File file =new File(filePath);
                fis =new FileInputStream(file);
                dos =new DataOutputStream(client.getOutputStream());
                  
                //�ļ����ͳ���
                dos.writeUTF("WRITE");
                dos.flush();
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                  
                //�����ļ�
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
                System.out.println(new Date());//����ʱ��
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
		return flag;
	}
	//���ļ�
	public boolean ReadFile(String filePath){
		boolean flag = false;
        try {
            try { 
            	System.out.println(new Date());//��ʼʱ��
                client =new Socket(SERVER_IP, SERVER_PORT);
                //�����˴����ļ�
                File file =new File(filePath);
                fis =new FileInputStream(file);
                dos =new DataOutputStream(client.getOutputStream());
                //�ļ����ͳ���
                dos.writeUTF("READ");
                dos.flush();
                dos.writeUTF(filePath);
                dos.flush();
                
                dis =new DataInputStream(client.getInputStream());
                //�ļ����ͳ���
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                fos =new FileOutputStream(new File("e:/" + fileName));
                      
                byte[] sendBytes =new byte[1024];
                int transLen =0;
                System.out.println("----��ʼ�����ļ�<" + fileName +">,�ļ���СΪ<" + fileLength +">----");
                while(true){
                    int read =0;
                    read = dis.read(sendBytes);
                    if(read == -1)
                       break;
                    transLen += read;
                    System.out.println("�����ļ�����" +100 * transLen/fileLength +"%...");
                    fos.write(sendBytes,0, read);
                    fos.flush();
                }
                System.out.println("----�����ļ�<" + fileName +">�ɹ�-------");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(dis !=null)
                    dis.close();
                if(fos !=null)
                    fos.close();
                client.close();
                flag = true;
                System.out.println(new Date());//����ʱ��
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
