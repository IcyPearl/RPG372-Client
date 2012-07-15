package client.core.serverconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class QueryExecutor {
	private String dataBaseName = "RPG372DB";
	private String userName = "RPG372USER";
	private String password = "RPGRPG123";
	private String serverAdd = "mysql://mefu.mine.nu";
	private Map<String, ArrayList<String>> mp = null;

	public void SendQuery(String query)  {
		String url = "jdbc:"+serverAdd+"/" + dataBaseName;
		Connection conn;
		Statement stmt;
		Map<String, ArrayList<String>> map;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			map = new HashMap<String, ArrayList<String>>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				map.put(rs.getMetaData().getColumnName(i),
						new ArrayList<String>());
			}
			while (rs.next()) {
				String tmp = "";
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					tmp = rs.getMetaData().getColumnName(i);
					map.get(tmp).add(rs.getString(tmp));
				}
			}
			conn.close();
			stmt = null;
			conn = null;
			mp = map;
		} catch (Exception  e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> getRow(int index){
		ArrayList<String> rtr = new ArrayList<String>();
		for(String key:mp.keySet()){
			rtr.add(mp.get(key).get(index));
		}
		return rtr;
	}

	public int getResultCount(){
		return mp.get(mp.keySet().toArray()[0]).size();
	}

}
