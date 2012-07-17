package client.core.serverconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class QueryExecutor {
	private String dataBaseName = "RPG372DB";
	private String userName = "RPG372USER";
	private String password = "RPGRPG123";
	private String serverAdd = "mysql://mefu.mine.nu";
	public ArrayList<String> cols = null;
	public ArrayList<ArrayList<String>> vals = null;

	public void SendQuery(String query)  {
		String url = "jdbc:"+serverAdd+"/" + dataBaseName;
		Connection conn;
		Statement stmt;
		Map<String, ArrayList<String>> map;
		vals = new ArrayList<ArrayList<String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			cols = new ArrayList<String>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				cols.add(rs.getMetaData().getColumnName(i));
			}
			ArrayList<String> temp = null;
			while (rs.next()) {
				temp = new ArrayList<String>();
				for (int i = 1; i <= cols.size(); i++) {
					temp.add(rs.getString(i));
				}
				vals.add(temp);
			}
			conn.close();
			stmt = null;
			conn = null;
		} catch (Exception  e) {
			e.printStackTrace();
		}

	}
	public ArrayList<String> getFirst(){
		return vals.get(0);
	}
	public int resultSize(){
		return vals.size();
	}


}
