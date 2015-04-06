package cn.com.dpcbj.socketserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SocketServerThreadPool implements Runnable {
	private Logger logger = null;
	@SuppressWarnings("unused")
	private Map<String, Object> config = new HashMap<String, Object>();

	//protected Socket connection;
	Socket client;  

	public SocketServerThreadPool(Socket client) {  
		this.client = client;  
	}  
	protected static List<Socket> pool = new LinkedList<Socket>();

	public SocketServerThreadPool(Logger logger, Map<String, Object> config) {
		this.logger = logger;
		this.config = config;
	}

	public void handleConnection() {

		//System.out.println("Info:" + client.getInetAddress() + " ClientIPAddress " + Calendar.getInstance().getTime().toString() + " TimeStamp");

		DataInputStream in = null;
		PrintStream out = null;
		try {
			in = new DataInputStream(client.getInputStream());
			out = new PrintStream(client.getOutputStream());
			byte[] buffer = new byte[1024];
			String msg = null;
			SocketServerProcessHandler handler = new SocketServerProcessHandler(logger);
			while (in.read(buffer) != -1 && client != null) {
				msg = new String(buffer, "GBK").trim();
				//System.out.println("[Client]: " + msg);
				byte[] responseBuffer = handler.response(msg).getBytes("UTF8");
				if (new String(responseBuffer).equals("-1") == true) break;
				//System.out.println("[Server]: " + new String(responseBuffer));
				out.write(responseBuffer, 0, responseBuffer.length);
				out.flush();
				buffer = new byte[1024];
			}
			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
				if(in != null)
				{
					in.close();
				}
				if(in != null)
				{
					in.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();  
			}
		}
	}

	public static void processRequest(Socket request) {
		synchronized (pool) {
			pool.add(pool.size(), request);
			pool.notifyAll();
		}
	}

	public void run() {
		while (true) {
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				client = (Socket) pool.remove(0);
			}
			handleConnection();
		}
	}

}