package com.apitest.check.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.apitest.core.BaseTest;





public class DbHelper {
	static Logger log = LogManager.getLogger(DbHelper.class.getName());
	
	public java.sql.Connection conn = null; //connection object
	public ResultSet rs = null; //resultset object
	public Statement stmt = null; //statement object
	public PreparedStatement pstmt = null; //preparedstatement object
	private String drivers = null; //connection parameter:drivers
	private String url = null; //connection parameter:url
	private String user = null; //connection parameter:user
	private String password = null; //connection parameter:password
	private String configFile;

	public DbHelper() {

	}

	public DbHelper(String configFile) {
		this.configFile = configFile;
		init();
	}

	public static DbHelper getInstance() {
		DbHelper instance = new DbHelper();
		instance.init();
		return instance;
	}

	private void init() {
		drivers = BaseTest.getCfgProperty("persistence.datasource.driverClassName");
		url = BaseTest.getCfgProperty("persistence.datasource.url");
		user = BaseTest.getCfgProperty("persistence.datasource.username");
		password = BaseTest.getCfgProperty("persistence.datasource.password");
		try {
			log.info(drivers+"---"+url);
			Class.forName(drivers);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List query(String sql) throws Exception{
		List list = null;
		rs = stmt.executeQuery(sql);
		if (null != rs) {
			list = ResultSetHandler.toMapList(rs);
		}
		return list;
	}
	
	public boolean execute(String sql) throws Exception{
		boolean flag=false;
		flag = stmt.execute(sql);
		return flag;
	}

	public Map queryToMap(String sql) throws Exception{
		Map map = null;
		rs = stmt.executeQuery(sql);
		if (null != rs) {
			map = ResultSetHandler.toMap(rs);
		}
		return map;
	}

	public List queryToList(String sql) throws Exception {
		List list = null;
		rs = stmt.executeQuery(sql);
		if (null != rs) {
			list = ResultSetHandler.toStringArrayList(rs);
		}
		return list;
	}

	public String queryToString(String sql) throws Exception{ 
		String str = null;
		rs = stmt.executeQuery(sql);
		if (null != rs) {
			str = ResultSetHandler.toString(rs);
		}
		return str;
	}

	public void close() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException ex) {
				rs = null;
			}
		}
		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				stmt = null;
			}
		}
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException ex) {
				pstmt = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				conn = null;
			}

		}
	}

	public static void main(String args[]) {
		DbHelper db = DbHelper.getInstance();
		try {
			List list = db.query("select * from fun ");
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
				log.info(map.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
