package cn.com.dpcbj.socketserver;

import java.util.logging.Level;
import java.util.logging.Logger;

import cn.com.dpcbj.socketserver.ServiceInterface;


/**
 * 
 */
public class SocketServerProcessHandler {

	private Logger logger;

	private ServiceInterface si = new ServiceInterface();

	public SocketServerProcessHandler(Logger logger) throws Exception {
		this.logger = logger;
	}

	public String response(String message) {
		String resStr = "0";
		try
		{
			if (message.contains("#") == true) {
				resStr = process(message.split("#"));
				/*			title = message.substring(0,message.indexOf("\\#"));
			sqlStr = message.substring(message.indexOf("\\#")+1);
			if(title != null && title.compareToIgnoreCase("sqlstr") == 0){
				if(sqlStr.contains("GMT"))
				{
					sqlStr = sqlStr.replace("GMT ", "GMT+");
				}
				resStr = si.getDatabaseContent(sqlStr);
			}
			if (title!= null && title.compareToIgnoreCase("systime") == 0) {
				resStr = si.getStringDateShort();
			}*/
			} else {
				resStr = "1";
			}
		}catch(Exception e)
		{
			e.printStackTrace();			
		}

		return resStr;
	}

	private String process(String[] records) {
		
		//logger.log(Level.INFO,"[records0]: " + records[0]);
		//logger.log(Level.INFO,"[records1]: " + records[1]);

		String resResult = "",sqlStr = "";
		try {
			if (records[0]!= null && records[0].compareToIgnoreCase("systime") == 0) {
				resResult = si.getStringDateShort();
			}
			if(records[0] != null && records[0].compareToIgnoreCase("sqlstr") == 0)
			{
				sqlStr = records[1];
				if(sqlStr.contains("GMT"))
				{
					sqlStr = sqlStr.replace("GMT ", "GMT+");
				}
				resResult = si.getDatabaseContent(sqlStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resResult;
	}
}
