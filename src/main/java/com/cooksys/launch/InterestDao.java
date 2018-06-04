package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterestDao {
	private static void update(Interest interest) {
		try (Connection connection = ConnectionHandler.getConnection();) {
			if(interest.getId() == null || interest.getId() == 0) {
				throw new IllegalArgumentException("Id is null or 0 while updating (Don't do that m8)");
			} else {
				connection.createStatement().executeUpdate(
				"	UPDATE interest " +
				"	SET first_name='" + interest.getTitle() +
				"	WHERE id=" + interest.getId()
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void save(Interest interest) {
		try(Connection connection = ConnectionHandler.getConnection();){
			if(interest.getId() == null || interest.getId() == 0) {
				connection.createStatement().executeUpdate(
				"	INSERT INTO interest(title) " +
				"	VALUES('" + 
						interest.getTitle() + "'" +
				"	)"
				);
			} else {
				update(interest);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Interest getById(long id) {
		Interest interest = null;
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT * " +
			"	FROM interest " +
			"	WHERE interest.id = " + id
			);
		){
			if(!result.next())
				throw new IllegalArgumentException("Id called does not exist"); 

			interest = new Interest(
				result.getString(2)	//title
			);
			//Id is special so it isn't in constructor
			interest.setId(result.getLong(1));

			connection.close();
		} catch (Exception e) {
			System.out.println("Query failed!");
			e.printStackTrace();
		}
		return interest;
	}
	
	//getById throws an exception, (as per defined in assignment specification)
	//so this one is used for checking Id existance
	public static boolean doesIdExist(long id) {
		boolean answer = true;
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT * " +
			"	FROM interest " +
			"	WHERE interest.id = " + id
			);
		){
			if(!result.next())
				answer = false;
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Query failed!");
			e.printStackTrace();
		}
		
		return answer;
	}
}
