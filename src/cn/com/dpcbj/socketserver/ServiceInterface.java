package cn.com.dpcbj.socketserver;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;

public class ServiceInterface {

	public String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String dateString = formatter.format(currentTime);  
		return dateString; 
	}

	public String DateTimeDiff(String dateTimeStartStr, String dateTimeEndStr)
	{
		String dateDiff = "";
		long timeDiff = 0;
		Date dateStart = null;
		Date dateEnd = null;
		try
		{
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dateStart = inputFormat.parse(dateTimeStartStr);
			dateEnd = inputFormat.parse(dateTimeEndStr);
			long startTime = dateStart.getTime();
			long endTime = dateEnd.getTime();
			timeDiff = endTime - startTime;   
			long day = timeDiff / (24 * 60 * 60 * 1000);
			long hour = (timeDiff / (60 * 60 * 1000) - day * 24);
			long min = ((timeDiff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (timeDiff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			long ms = (timeDiff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
			dateDiff = day * 24 * 3600 + hour * 3600 + min * 60 + s + "." + String.format("%1$03d", ms).replace("-", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return dateDiff;
	}

	public String DateTimeDiff(String dateTimeStartStr, String dateTimeEndStr, String wasteTime)
	{
		String dateDiff = "";
		double totalTime = 0.000,tempTime = 0.000;
		long timeDiff = 0;
		Date dateStart = null;
		Date dateEnd = null;
		try
		{
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dateStart = inputFormat.parse(dateTimeStartStr);
			dateEnd = inputFormat.parse(dateTimeEndStr);
			long startTime = dateStart.getTime();
			long endTime = dateEnd.getTime();
			timeDiff = endTime - startTime;   
			long day = timeDiff / (24 * 60 * 60 * 1000);
			long hour = (timeDiff / (60 * 60 * 1000) - day * 24);
			long min = ((timeDiff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (timeDiff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			long ms = (timeDiff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
			dateDiff = day * 24 * 3600 + hour * 3600 + min * 60 + s + "." + String.format("%1$03d", ms).replace("-", "");
			tempTime = Double.parseDouble(wasteTime)/1000000;
			totalTime = Double.parseDouble(dateDiff) - tempTime;
			dateDiff = String.valueOf(totalTime);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return dateDiff;
	}	

	public String getStringDbStampTime(String instrID,String mID,String dID){
		Connection conn = null;
		Statement st = null;
		String resStampTime = "",resMsgSts = "",intra = "";
		try
		{
			if((instrID != null && instrID.compareTo("") != 0) || (mID != null && mID.compareTo("") != 0) || (dID != null && dID.compareTo("") != 0))
			{
				ServiceDBConnection dbconn1 = new ServiceDBConnection();
				conn = dbconn1.connectDbByWas();
				st = conn.createStatement();
				if(instrID != null && instrID.compareToIgnoreCase("") != 0)
				{
					intra = "Select a.p_Time_Stamp,a.p_Msg_Sts from minf a where a.p_instr_id ='"+ instrID +"'";
				}
				if(mID != null && mID.compareToIgnoreCase("") != 0)
				{
					intra = "Select a.p_Time_Stamp,a.p_Msg_Sts from minf a where a.p_Mid ='"+ mID +"'";
				}
				if(dID != null && dID.compareToIgnoreCase("") != 0)
				{
					intra = "Select a.p_Time_Stamp,a.p_Msg_Sts from minf a where a.p_duplicate_index ='"+ dID +"'";
				}
				ResultSet rs1 = st.executeQuery(intra);
				while(rs1.next()){
					resStampTime = rs1.getString(1);
					resMsgSts = rs1.getString(2);
				}
				rs1.close();
				st.close();
				conn.close();
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(NamingException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(st != null)
				{
					st.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();			
			}
		}

		return resStampTime + "|" + resMsgSts;
	}

	public String getStringDbStampTimeByMid(String mID){
		Connection conn = null;
		Statement st = null;
		String resStampTime = "",resMsgSts = "",intra = "";
		try
		{
			if(mID != null && mID.compareTo("") != 0)
			{
				ServiceDBConnection dbconn1 = new ServiceDBConnection();
				conn = dbconn1.connectDbByWas();
				st = conn.createStatement();
				if(mID != null && mID.compareToIgnoreCase("") != 0)
				{
					intra = "Select a.p_Time_Stamp,a.p_Msg_Sts from minf a where a.p_Mid ='"+ mID +"'";
				}
				ResultSet rs1 = st.executeQuery(intra);
				while(rs1.next()){
					resStampTime = rs1.getString(1);
					resMsgSts = rs1.getString(2);
				}
				rs1.close();
				st.close();
				conn.close();
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(NamingException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(st != null)
				{
					st.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();			
			}
		}

		return resStampTime + "|" + resMsgSts;
	}

	public String getResourceID(String IndexStart,String IndexEnd,String TransStatus){
		Connection conn = null;
		Statement st = null;
		String resMid = "",resInstrId = "", intraSQL = "";
		try
		{
			if((IndexStart != null && IndexStart.compareTo("") != 0) && (IndexEnd != null && IndexEnd.compareTo("") != 0) && (TransStatus != null && TransStatus.compareTo("") != 0))
			{
				ServiceDBConnection dbconn1 = new ServiceDBConnection();
				conn = dbconn1.connectDbByWas();
				st = conn.createStatement();
				intraSQL = "select a.p_mid,a.p_instr_id from minf a where rownum = 1 and a.p_duplicate_index >= '~'||'"+ IndexStart +"' and a.p_duplicate_index <= '~'||'"+ IndexEnd +"' and a.p_msg_sts = '"+ TransStatus +"'";
				ResultSet rs1 = st.executeQuery(intraSQL);
				while(rs1.next()){
					resMid = rs1.getString(1);
					resInstrId = rs1.getString(2);
				}
				rs1.close();
				st.close();
				conn.close();
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(NamingException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(st != null)
				{
					st.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();			
			}
		}

		return resMid + "|" + resInstrId;
	}

	public String getDatabaseContent(String sqlStr){
		Connection conn = null;
		Statement st = null;
		String columnData = "",columnName = "",resStr = "";
		try
		{
			if(sqlStr != null && sqlStr.compareTo("") != 0)
			{
				ServiceDBConnection dbconn = new ServiceDBConnection();
				conn = dbconn.connectDbByWas();
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sqlStr);
				while(rs.next()){
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnSize = rsmd.getColumnCount();
					for(int i=1; i <= columnSize; i++)
					{
						columnName = rsmd.getColumnName(i);
						columnData = rs.getString(i);
						resStr += "<" + columnName + ">" + columnData + "</"+ columnName +">";
					}
				}
				rs.close();
				st.close();
				conn.close();
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(NamingException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(st != null)
				{
					st.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();			
			}
		}

		return resStr;
	}
}
