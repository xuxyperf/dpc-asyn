package cn.com.dpcbj.socketserver;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import cn.com.dpcbj.socketserver.SocketServerThread;

public class SocketListener implements ServletContextListener{

	final public static Logger logger = Logger.getLogger("PerfServiceSocketServer");
	private Map<String, Object> config = new HashMap<String, Object>();
	private SocketServerThread thread = null;

	public SocketListener() {
		super();
	}

	public void contextInitialized(ServletContextEvent event) {
		initConfig(event);

		event.getServletContext().log("++++++++++++++++++++++++++++++++++++++++++");
		event.getServletContext().log(">>> Starting PerfService Socket Server");
		event.getServletContext().log(">>> Socket Port: " + config.get("port"));
		thread = new SocketServerThread(logger, config);
		thread.start();
		event.getServletContext().log(">>> Started PerfService Socket Server");
		event.getServletContext().log("++++++++++++++++++++++++++++++++++++++++++");
	}

	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().log("++++++++++++++++++++++++++++++++++++++++++");
		event.getServletContext().log(">>> Stoping PerfService Socket Server");
		thread.shutdown();
		event.getServletContext().log(">>> Stoped PerfService Socket Server");
		event.getServletContext().log("++++++++++++++++++++++++++++++++++++++++++");
	}

	private void initConfig(ServletContextEvent event) {
		config.put("port", 1080);
		config.put("pool", 5);
		config.put("timeout", 0);
		config.put("clientip", null);

		try {
			int port = Integer.parseInt(event.getServletContext().getInitParameter("SocketServerPort"));
			int maxConnections = Integer.parseInt(event.getServletContext().getInitParameter("SocketServerMaxConnections"));
			int connectTimeout = Integer.parseInt(event.getServletContext().getInitParameter("SocketServerConnectTimeout"));
			String limitedClientIPs = event.getServletContext().getInitParameter("SocketServerLimitedClientIPs");

			if (port > 1 && port < 65535) config.put("port", port);
			if (maxConnections > 0 && maxConnections < 50) config.put("pool", maxConnections);
			if (connectTimeout > 10000) config.put("timeout", connectTimeout);
			if (limitedClientIPs.trim().length() > 0) config.put("clientip", limitedClientIPs.split(","));

	} catch (Exception e) {
		logger.log(Level.WARNING, "Configuration file does not exist or configuration is not complete, will use the default configuration.");
	}
}
}
