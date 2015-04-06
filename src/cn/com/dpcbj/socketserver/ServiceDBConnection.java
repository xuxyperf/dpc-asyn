package cn.com.dpcbj.socketserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.*;

public class ServiceDBConnection {

	//�������ݿ������ַ���
	private String connstr = "jdbc:oracle:thin:@22.188.134.61:1521:oraGURP2";
	//��������
	final static String jdriver = "oracle.jdbc.driver.OracleDriver";

	//Thin��ʽ����Oracle���ݿ� 
	public Connection connectDbByThin(){
		Connection conn=null;
		try{
			Class.forName(jdriver);
			conn = DriverManager.getConnection(connstr,"BOC_POC","BOC_POC");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}

	public Connection connectDbByWas()throws NamingException,SQLException{
		
		Connection conn=null;
		Context ctx=null;
		ctx=new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("jdbc/dpcpf");
		conn=ds.getConnection();
		
		return conn;
	}

}
