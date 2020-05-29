package com.amazonaws.samples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
*/


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;


public class CriminalAdd {

    public static void main(String[] args) throws Exception {
    	//connect();
    	//displayAll();
    	int criminalID = 100;
    	String criminalName = "Jack the Ripper";
    	String criminalGender = "M";
    	String criminalBirthday = "January 1, 1900";
    	String criminalAge = "40 (dead)";
    	
    	create(criminalID, criminalName, criminalGender, criminalBirthday, criminalAge);
    }
    public static Connection connect() {
		String url = "jdbc:sqlite:amazon2.db"; //url tells the driver manager where to locate the Database.db file
		Connection conn = null;
		
		//Attempts to connect to the database file
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn; //returns a connection object
	}
	
	//Displays all of the information that is stored in the table 
	public static void displayAll() throws InterruptedException{
		//Selecting the columns you'd like to get information from
        String sql = "Dog ID, Name, Breed, Color, Personality, Registration, Favorite Food, Number of Toys";
        
        //Connecting to the database then preparing the objects to receive the info
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set, outputting the information row by row
            while (rs.next()) {
                System.out.println(rs.getInt("Prisoner#") +  "\t" + 
                                   rs.getString("1 Name") + "\t" +
                                   rs.getString("2 Gender") + "\t" +
                                   rs.getString("3 Birthday") + "\t" +
                                   rs.getString("4 Age"));
                
               // create(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
	
	public static void create(int criminalID, String criminalName, String criminalGender, 
			String criminalBirthday, String criminalAge) throws InterruptedException, SQLException
	{
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-1").build();
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Criminals");
		
		// Build the item
		Item item = new Item()
		    .withPrimaryKey("Prisoner#", criminalID)
		    .withString("1 Name", criminalName)
		    .withString("2 Gender", criminalGender)
		    .withString("3 Birthday", criminalBirthday)
		    .withString("4 Age", criminalAge);
		
		// Write the item to the table
		PutItemOutcome outcome = table.putItem(item);
		
		//Display
		System.out.println("Added Criminal");
	}
}
