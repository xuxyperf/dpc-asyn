package cn.com.dpcbj.service;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceAsynchronyServlet extends HttpServlet{

	private ServiceInterface si = new ServiceInterface();

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String sysTime = si.getStringDateShort();
		String docType = 
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0"+
			"Transitional//EN\">\n";
		out.println(docType+
				"<HTML>\n"+
				"<HEAD><TITLE>HellowGET</TITLE></HEAD>\n"+
				"<BODY BGCOLOR =\"#FDF5E6\">\n"+
				"<SysTime>"+ sysTime +"</SysTime>\n"+
		"</BODY></HTML>");
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		String resDataStr= "";
		String  sqlStr = request.getParameter("SQLSTR");
		if(sqlStr != null && sqlStr.contains("GMT"))
		{
			sqlStr = sqlStr.replace("GMT ", "GMT+");			
		}
		if(sqlStr != null && sqlStr.compareToIgnoreCase("") != 0)
		{
			resDataStr = si.getDatabaseContent(sqlStr);
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType = 
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0"+
			"Transitional//EN\">\n";
		out.println(docType+
				"<HTML>\n"+
				"<HEAD><TITLE>HellowPOST</TITLE></HEAD>\n"+
				"<BODY BGCOLOR =\"#FDF5E6\">\n"+
				"<DataStr>"+ resDataStr +"</DataStr>\n"+
		"</BODY></HTML>");
	}
}