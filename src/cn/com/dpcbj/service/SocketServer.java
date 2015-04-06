package cn.com.dpcbj.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SocketServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketServer socketServer = new SocketServer();  
		socketServer.doListen(); 
	}

	public void doListen() {  
		ServerSocket server;  
		try {  
			server = new ServerSocket(1080);  
			while (true) {  
				Socket client = server.accept();  
				new Thread(new ServiceSocket(client)).start();  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	} 

	class ServiceSocket implements Runnable {  
		Socket client;  
		public ServiceSocket(Socket client) {  
			this.client = client;  
		}  
		public void run() {  
			DataInputStream input = null;  
			DataOutputStream output = null;  
			try {  
				byte[] buf =new byte[4096]; 
				input = new DataInputStream(client.getInputStream());  
				output = new DataOutputStream(client.getOutputStream());  
				int len = input.read(buf);
                String revMessage = new String(buf, 0, len);
				if(revMessage.compareToIgnoreCase("systemtime") == 0)
				{
					String systemTime = getStringDateShort();
					byte[] timeBytes = systemTime.getBytes();
					output.write(timeBytes);
					output.flush();
					output.close();
				}
				buf = new byte[4096]; 
			} catch (IOException e) {
				e.printStackTrace();  
			}
			finally
			{
				try
				{
					if(client != null)
					{
						client.close();
					}
					if(input != null)
					{
						input.close();
					}
					if(output != null)
					{
						output.close();
					}
				}
				catch(IOException e){
					e.printStackTrace();  
				}
			}
		}  
	} 

	public static String getStringDateShort() {
		Date currentTime = new Date();  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String dateString = formatter.format(currentTime);  
		return dateString; 
	}
}
