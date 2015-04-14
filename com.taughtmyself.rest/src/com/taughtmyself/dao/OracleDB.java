package com.taughtmyself.dao;
import java.sql.Connection;
import javax.naming.*;
import javax.sql.*;

public class OracleDB {
	//initialize connection vars
	private static DataSource OracleDs = null;
	private static Context context = null;
	
	/**
	 * We simply try to connect to the DB server via the JDBC data source
	 * @return
	 * @throws Exception
	 */
	public static DataSource OracleDbConn() throws Exception{
		
		try{
			if(context==null)
				context=new InitialContext();
			OracleDs=(DataSource) context.lookup("oracleds");
		}catch(Exception e){
			e.printStackTrace();
		}
		return OracleDs;
	}
	
	/**
	 * This is a bit redundant as it is connecting to the database via calling the above method, 
	 * then calling getConnection() method; we could have called the getConnection() method right onto the method above.
	 * @return
	 */
	protected static Connection OracleDBConn(){
		Connection conn=null;
		
		try{
			conn = OracleDbConn().getConnection();
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}
