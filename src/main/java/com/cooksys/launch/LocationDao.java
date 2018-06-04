package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocationDao {
	private static void update(Location location) {
		try (Connection connection = ConnectionHandler.getConnection();) {
			if(location.getId() == null || location.getId() == 0) {
				throw new IllegalArgumentException("Id is null or 0 while updating (Don't do that m8)");
			} else {
				connection.createStatement().executeUpdate(
				"	UPDATE person " +
				"	SET city='" + location.getCity() + "'," +
						"state='" + location.getState() + "'," +
						"country=" + location.getCountry() + " " +
				"	WHERE id=" + location.getId()
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void save(Location location) {
		try(Connection connection = ConnectionHandler.getConnection();){
			if(location.getId() == null || location.getId() == 0) {
				connection.createStatement().executeUpdate(
				"	INSERT INTO location(city, state, country) " +
				"	VALUES('" + 
						location.getCity() + "', '" +
						location.getState() + "', '" + 
						location.getCountry() + "'" +
				"	)"
				);
			} else {
				update(location);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Location getById(long id) {
		Location location = null;
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT * " +
			"	FROM location " +
			"	WHERE location.id = " + id
			);
		){
			if(!result.next())
				throw new IllegalArgumentException("Id called does not exist"); 

			location = new Location(
				result.getString(2),	//city
				result.getString(3),	//state
				result.getString(4)		//country
			);
			//Id is special so it isn't in constructor
			location.setId(result.getLong(1));
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Query failed!");
			e.printStackTrace();
		}
		return location;
	}
	
	//getById throws an exception, (as per defined in assignment specification)
	//so this one is used for checking Id existance
	public static boolean doesIdExist(long id) {
		boolean answer = true;
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT * " +
			"	FROM location " +
			"	WHERE location.id = " + id
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
