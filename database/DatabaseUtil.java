package com.floow.challenge.wordcount.database;

import java.net.UnknownHostException;

import com.floow.challenge.wordcount.constants.IWordCount;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * The Class DatabaseUtil.
 * 
 * @author Gaurav Singhal
 * 
 * This class provides the database related information.
 *
 */
public class DatabaseUtil {
	
	private static MongoClient mongo = null;

	private static DBCollection getCollections() throws UnknownHostException {
		return getDatabase().getCollection(IWordCount.WORDCOUNT_COLLECTION);
	}

	private static DB getDatabase() throws UnknownHostException {
		return getMongoClient().getDB(IWordCount.WORDCOUNT_DB);
	}

	private static MongoClient getMongoClient() throws UnknownHostException {
		if(mongo==null){
			mongo = new MongoClient(IWordCount.LOCAL_HOST , IWordCount.PORT_NUMBER);
		}
		return mongo;
	}
	
	public static void updateDatabaseForWordCount(String word, int numberOfoccurences){
		BasicDBObject document = new BasicDBObject();
		document.put(word, numberOfoccurences);
		try {
			getCollections().insert(document);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
