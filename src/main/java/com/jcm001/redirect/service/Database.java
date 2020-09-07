package com.jcm001.redirect.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private Connection conn;
	private String host;
	private String database;
	private String port;
	private String user;
	private String password;
	
	public Database() {
		host = "localhost";
		database = "JCM001";
		port = "3306";
		user = "root";
		password = "";
	}
	
	public ResultSet execQuery(String query) throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		
		try {
			if(BeginConnection()) {
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = st.executeQuery(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	private boolean BeginConnection() throws ClassNotFoundException, SQLException {
		boolean response = false;
		this.conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
			conn = DriverManager.getConnection(url, user, password);
			response = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public boolean EndConnection() throws ClassNotFoundException, SQLException {
		try {
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
