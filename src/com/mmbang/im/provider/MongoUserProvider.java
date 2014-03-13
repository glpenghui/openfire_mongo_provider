package com.mmbang.im.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.bson.types.ObjectId;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.openfire.user.UserProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoUserProvider implements UserProvider {
	private String DB_NAME;
	private String COLLECTION_NAME;
	private String USERNAME_KEY;
	private String EMAIL_KEY;
	private String NICKNAME_KEY;
	private String REGISTER_TIME_KEY;
	private MongoConfig mc;
	private Mongo mongo;
	public MongoUserProvider()
	{
		mc=new MongoConfig();
		mongo=MongoConnectionManager.getConnection(mc.getRb().getString("host"), new Integer(mc.getRb().getString("port")));
		this.DB_NAME=mc.getRb().getString("db_name");
		this.COLLECTION_NAME=mc.getRb().getString("collection_name");
		this.USERNAME_KEY=mc.getRb().getString("username_key");
		this.EMAIL_KEY=mc.getRb().getString("email_key");
		this.NICKNAME_KEY=mc.getRb().getString("nickname_key");
		this.REGISTER_TIME_KEY="register_time";
	}
	
	public User createUser(String username, String password, String name,
			String email) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	public Collection<User> findUsers(Set<String> fields, String query)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		System.out.println("findUsers1");
		return null;
	}

	public Collection<User> findUsers(Set<String> fields, String query,
			int startIndex, int numResults)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		System.out.println("findUsers2");
		System.out.println(fields);
		return null;
	}

	public Set<String> getSearchFields() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		System.out.println("getSearchFields");
		return null;
	}

	public int getUserCount() {
		// TODO Auto-generated method stub
		DBCollection users=mongo.getDB(DB_NAME).getCollection(COLLECTION_NAME);
		int total= (int)users.count();
		return total;
	}

	public Collection<String> getUsernames() {
		// TODO Auto-generated method stub
		System.out.println("getUsernames");
		return null;
	}

	public Collection<User> getUsers() {
		// TODO Auto-generated method stub
		System.out.println("getUsers1");
		return null;
	}

	public Collection<User> getUsers(int startIndex, int numResults) {
		System.out.println("getUsers2.3");
		// TODO Auto-generated method stub
		Collection<User> usersColl=new ArrayList<User>();
		
		DBCollection users=mongo.getDB(DB_NAME).getCollection(COLLECTION_NAME);
		DBCursor cursor=users.find().skip(startIndex).limit(numResults);
		Iterator<DBObject> iter=cursor.iterator();
		while(iter.hasNext()){
			BasicDBObject userObject=(BasicDBObject) iter.next();
			String username=userObject.get(USERNAME_KEY).toString();
	        String name = (String) userObject.get(NICKNAME_KEY);
	        String email = (String)userObject.get(EMAIL_KEY);
	        Double creationTime= (Double) userObject.get(REGISTER_TIME_KEY);
	        
	        if(creationTime==null){
	        	creationTime=0.0;
	        }
	        
	        Date creationDate = new Date(creationTime.longValue());
	        Date modificationDate = new Date();
	        User userObj=new User(username, name, email, creationDate, modificationDate);
	        
	        usersColl.add(userObj);
		}
		return usersColl;
	}

	public boolean isEmailRequired() {
		// TODO Auto-generated method stub
		System.out.println("isEmailRequired");
		return false;
	}

	public boolean isNameRequired() {
		// TODO Auto-generated method stub
		System.out.println("isNameRequired:ok");
		return false;
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		System.out.println("isReadOnly");
		return false;
	}

	public User loadUser(String username) throws UserNotFoundException {
		// TODO Auto-generated method stub
		DBCollection users=mongo.getDB(DB_NAME).getCollection(COLLECTION_NAME);
		BasicDBObject searchQuery = new BasicDBObject();
		
		String usernameType=mc.getRb().getString("username_type");
		if ("int".equals(usernameType)){
			searchQuery.put(USERNAME_KEY, new Integer(username));
		} else if("mongoId".equals(usernameType)){
			searchQuery.put(USERNAME_KEY, new ObjectId(username));
		} else{
			searchQuery.put(USERNAME_KEY, new String(username));
		}
		
		DBObject user=users.findOne(searchQuery);
		
		if (user==null){
			throw new UserNotFoundException();
		}
		
        String name = username;
        String email = (String)user.get(EMAIL_KEY);
        Date creationDate = new Date(Long.parseLong("001384422636067"));
        Date modificationDate = new Date(Long.parseLong("001384422636067"));
		return new User(username, name, email, creationDate, modificationDate);
	}

	public void setCreationDate(String username, Date creationDate)
			throws UserNotFoundException {
		// TODO Auto-generated method stub

	}

	public void setEmail(String username, String email)
			throws UserNotFoundException {
		// TODO Auto-generated method stub

	}

	public void setModificationDate(String username, Date modificationDate)
			throws UserNotFoundException {
		// TODO Auto-generated method stub

	}

	public void setName(String username, String name)
			throws UserNotFoundException {
		// TODO Auto-generated method stub

	}

}
