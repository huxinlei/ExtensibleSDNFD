package cn.edu.imu.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.FileInputStream;

/**
 * @author huxinlei
 * @date 2015-06-20
 * @version 1.0
 *
 */
public class DiskServer extends ServerSocket{

    private static final int PORT =2013;
    
    private ServerSocket server;
    private Socket client;
    private DataInputStream dis;
	private DataOutputStream dos;
    private FileOutputStream fos;
	private FileInputStream fis;
    
    public DiskServer()throws Exception{
        try {
            try {
                server =new ServerSocket(PORT);
                System.out.println("server running...");
                while(true){
                    client = server.accept();
                      
                    dis =new DataInputStream(client.getInputStream());
                    
                    //文件名和长度
                    String command = dis.readUTF();
                    if(command.equals("WRITE")){
                        String fileName = dis.readUTF();
                        long fileLength = dis.readLong();
                        fos =new FileOutputStream(new File("d:/" + fileName));
                          
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
                        client.close();
                    }else if(command.equals("READ")){
                    	String filePath = "d:/" + dis.readUTF();
                        File file =new File(filePath);
                        fis =new FileInputStream(file);
                    	dos =new DataOutputStream(client.getOutputStream());
                    	
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
                        client.close();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(dis !=null)
                    dis.close();
                if(fos !=null)
                    fos.close();
                if(fis !=null)
                    fis.close();
                if(dos !=null)
                    dos.close();
                
                server.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) throws Exception {
		new DiskServer();
	}

}
