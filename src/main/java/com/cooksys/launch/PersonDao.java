package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

public class PersonDao {
	private static void updatePersonToInterest(Person person) {
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT person_to_interest.person_id, person_to_interest.interest_id " + 
			"	FROM person_to_interest " + 
			"	WHERE person_to_interest.person_id="+person.getId()+" "+
			"	ORDER BY person_to_interest.interest_id;"
			);
		){
			Iterator<Interest> it = person.getInterests().iterator();
		    while(it.hasNext()){
		    	Interest currentPersonInterest = it.next();
		    	
		    	System.out.println(it.next().getTitle());
		    	
		    	//If(Interest/Person pair exists)
		    	//	Nothing
		    	//If(Interest exists in Person but not Interest/Person)
		    	//	Insert
		    	//If(Person exists in p
		    }
		    
			/*
			long row=1;
			Interest temp;
			while(result.next()) {
				temp = InterestDao.getById(result.getLong(1));
				if(temp.getId() != )
			}
//			if(row == 1) //Didn't even iterate once
//				System.out.println("No Interests related to person);
			//iojoij
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void update(Person person) {
		try (Connection connection = ConnectionHandler.getConnection();) {
			if(person.getId() == null || person.getId() == 0) {
				throw new IllegalArgumentException("Id is null or 0 while updating");
			} else {
				connection.createStatement().executeUpdate(
				"	UPDATE person " +
				"	SET first_name='" + person.getFirstName() + "'," +
						"last_name='" + person.getLastName() + "'," +
						"age=" + person.getAge() + " " +
				"	WHERE id=" + person.getId()
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void save(Person person) {
		try(Connection connection = ConnectionHandler.getConnection();){
			if(person.getId() == null || person.getId() == 0) {
				connection.createStatement().executeUpdate(
				"	INSERT INTO person(first_name, last_name, age) " +
				"	VALUES('" + 
						person.getFirstName() + "', '" +
						person.getLastName() + "', '" + 
						person.getAge() + "'" +
				"	)"
				);
				//Add new Location (if new)
				if(!InterestDao.doesIdExist(person.getLocation().getId())) {
		    		//Insert new location
		    		LocationDao.save(person.getLocation());
		    	}
				//Add new Interests (if any)
				Iterator<Interest> it = person.getInterests().iterator();
			    while(it.hasNext()){
			    	Interest temp = it.next();
			    	if(!InterestDao.doesIdExist(temp.getId())) {
			    		//Insert new interest
			    		InterestDao.save(temp);
			    	}
			    }
			    //
			} else {
				update(person);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Person getById(long id) {
		Person person = null;
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"	SELECT person.id,person.first_name, person.last_name, person.age, interest.id, location.id "+
			"	FROM person "+
			"/*Join Person to PersonToInterest*/"+
			"	JOIN person_to_interest "+
			"	ON person_to_interest.person_id = person.id "+
			"/*Join Interest to PersonToInterest*/"+
			"	JOIN interest "+
			"	ON person_to_interest.interest_id = interest.id "+
			"/*Join Location last*/"+
			"	JOIN location "+
			"	ON person.location_id=location.id "+

			"	WHERE person.id="+id+";"
			);
		){
			long row = 1;
			while(result.next()) {
				if(row == 1) {
					person = new Person(
						result.getString(2),	//first_name
						result.getString(3),	//last_name
						result.getInt(4)		//age
					);
					//Id is special so it isn't in constructor
					person.setId(result.getLong(1));		//id
					person.setLocation(result.getInt(6)); 	//location_id
					
					HashSet<Interest> interests = new HashSet<Interest>();
					interests.add(InterestDao.getById(result.getInt(5)));	//interest_id
					person.setInterests(interests);
				}else {
					HashSet<Interest> interests = person.getInterests();
					interests.add(InterestDao.getById(result.getInt(5)));	//interest_id
					person.setInterests(interests);	
				}
				
				row++;
			}
			if(row == 1) //Didn't even iterate once
				throw new IllegalArgumentException("Id called does not exist"); 
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Query failed!");
			e.printStackTrace();
		}
		return person;
	}
}
