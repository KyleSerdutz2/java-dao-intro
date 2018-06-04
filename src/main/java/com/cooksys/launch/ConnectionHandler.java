package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHandler {
	public static Connection getConnection() {
		try {
			//Necessary to ensure working
			//Just copy and paste
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(
				//49872 	= port
				//dayone 	= database name
				"jdbc:postgresql://localhost:5432/dayone",
				//postgres 	= user
				//bondstone = pass
				"postgres",
				"bondstone"
			);
			return connection;
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		return null;
	}
}
