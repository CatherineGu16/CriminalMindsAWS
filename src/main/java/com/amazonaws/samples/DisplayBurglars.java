package com.amazonaws.samples;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import java.util.ArrayList;
import java.util.Random;

import java.util.LinkedList;

public class DisplayBurglars {

	public static void main(String args[])
	{
		ArrayList<Item> resultquery; // = connect();
		
		System.out.print("Querying the database");
		
		// Build a UI to let user type in name
		String ID = "B";
		
		resultquery = queryCriminal(ID);
		// 1 - query by name
		// 2 - query by prisoner number
		// 3 - query by gender
		// 4 - query by birthday
		// 5 - query by age
		
		
		for (Item thing:resultquery)
		{
			System.out.println("Burglars:");
			
			System.out.println(thing.get("Prisoner#"));
			System.out.println(thing.get("1 Name"));
			System.out.println(thing.get("2 Gender"));
			System.out.println(thing.get("3 Birthday"));
			System.out.println(thing.get("4 Age"));
			System.out.println(thing.get("5 ID"));
			System.out.println(thing.get("Crime for Trial"));
			System.out.println(thing.get("B DollarValue"));
			System.out.println(thing.get("BSK State"));
			System.out.println(thing.get("BSK Date"));
			System.out.println(thing.get("BSK Penalty"));	
		}

	}
	
	public static ArrayList<Item> queryCriminal(String fieldname) 
	{
		ArrayList<Item>itemList = new ArrayList<Item>();
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/johnmortensen/.aws/credentials), and is in valid format.",
                    e);
        }
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        	.withCredentials(credentialsProvider)
            .withRegion("us-west-1")
            .build();

		DynamoDB dynamoDB = new DynamoDB(client);
	    Table table = dynamoDB.getTable("Criminals");
	    ScanRequest scanRequest = new ScanRequest()
	    	    .withTableName("Criminals");


	    ScanResult result = client.scan(scanRequest);
	    Item item;

	    	// Query by ID
		    for(int x = 1; x <= result.getCount(); x++)
		    {
		        item = table.getItem("5 ID", fieldname);
	
		    	itemList.add(item);
	
		    }

			return itemList;
		}
		
}
