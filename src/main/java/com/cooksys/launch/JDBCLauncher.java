package com.cooksys.launch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
---

[x]Implement
findInterestGroup(Interest, Location)

[x]It executes the Stored Procedure
defined in the Schema Design Assignment

[x]Transforms the ResultSet obtained from
executing the Stored Procedure into a Set<Person>

[x]Returns the Set<Person>

---

[x]Add a "location" field to the Person class
Data type : Location

[x]Add a "interests" field to the Person class
Data type : Set<Interest>

[Did with Joins]
[x]Modify the get(id) method in PersonDao
interact with the LocationDao and InterestDao
in order to ensure that all Person objects retrieved
have accurate data in their interests and location fields.

[_]Modify the save(Person) method in PersonDao
to also update
	[x]the Location Table,
	[x]the Interest Table,
	[_]and any related Join Tables or Join Columns
so that the contents of the saved Person object
are stored accurately

---
*/

public class JDBCLauncher {
	private static HashSet<Person> findInterestGroup(String sInterest, String sCity){
		HashSet<Person> personSet = new HashSet<Person>();
		try (Connection connection = ConnectionHandler.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
			"    SELECT * \r\n" + 
			"    FROM Public.person\r\n" + 
			
			"    /*Join Person to PersonToInterest*/\r\n" + 
			"    JOIN Public.person_to_interest\r\n" + 
			"    ON Public.person_to_interest.person_id = Public.person.id\r\n" + 
			
			"    /*Join Interest to PersonToInterest*/\r\n" + 
			"    JOIN Public.interest\r\n" + 
			"    ON Public.person_to_interest.interest_id = Public.interest.id\r\n" + 
			
			"    /*Join Location last*/\r\n" + 
			"    JOIN Public.location\r\n" + 
			"    ON Public.person.location_id=Public.location.id\r\n" + 
			"    WHERE Public.interest.title='"+sInterest+"' AND Public.location.city='"+sCity+"';"
			);
		){
			//Need to figure out what exactly they want?
			int i = 0;
			while(result.next()) {
				//We could just get the id(of person) and nothing else
				System.out.println(result.getString(1)); //id
				
				personSet.add(PersonDao.getById(result.getLong(1)));
				/*
				personSet.add( new Person(
					result.getLong(1),		//id
					result.getString(2),	//first_name
					result.getString(3),	//last_name
					result.getInt(4)		//age
				));
				*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new HashSet<Person>();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("\tBegin JDBCLauncher");
		
		Person person = PersonDao.getById(2);
		
		System.out.println(person.getLocation().getCity());
		System.out.println("---");
		Iterator<Interest> it = person.getInterests().iterator();
	    while(it.hasNext()){
	    	System.out.println(it.next().getTitle());
	    }
		
		System.out.println("\tEnd JDBCLauncher");
	}
}
