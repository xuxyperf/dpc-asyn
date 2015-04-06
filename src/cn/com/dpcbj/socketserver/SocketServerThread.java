package cn.com.dpcbj.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;

public class SocketServerThread extends Thread {
	private Logger logger = null;
	private Map<String, Object> config = new HashMap<String, Object>();
	private ServerSocket serverSocket = null;
	private Thread[] serverSocketPool = null;
	
	public SocketServerThread(Logger logger, Map<String, Object> config) {
		this.logger = logger;
		this.config = config;
	}

	public void run() {
		try {
			ServerSocketFactory factory = ServerSocketFactory.getDefault();
			serverSocket = factory.createServerSocket((Integer) config.get("port"));
			Socket request = null;
			
			serverSocketPool = new Thread[(Integer) config.get("pool")];
			for (int i = 0; i < (Integer) config.get("pool"); i++) {
				serverSocketPool[i] = new Thread(new SocketServerThreadPool(logger, config));
				serverSocketPool[i].start();
			}
			
			while (true) {
				request = serverSocket.accept();
				//logger.log(Level.INFO, "++++++ Request is Accepted ++++++");
				SocketServerThreadPool.processRequest(request);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (serverSocketPool != null) {
				for (int i = 0; i < serverSocketPool.length; i++) {
					serverSocketPool[i].interrupt();
				}
			}
			this.interrupt();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> config = new HashMap<String, Object>();
		Logger logger = Logger.getLogger("PerfServiceSocketServer");
		
		config.put("port", 5555);
		config.put("pool", 5);
		config.put("timeout", 0);
		config.put("clientip", null);

		new SocketServerThread(logger, config).start();
	}
}
