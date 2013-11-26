package com.mmbang.im.provider;

import org.bson.types.ObjectId;
import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import com.mmbang.im.provider.MongoDBManage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoAuthProvider implements AuthProvider {
	private String DB_NAME;
	private String COLLECTION_NAME;
	private String USERNAME_KEY;
	private String PASSWORD_KEY;
	private MongoConfig mc;
	
	public MongoAuthProvider(){
		mc=new MongoConfig();
		this.DB_NAME=mc.getRb().getString("db_name");
		this.COLLECTION_NAME=mc.getRb().getString("collection_name");
		this.USERNAME_KEY=mc.getRb().getString("username_key");
		this.PASSWORD_KEY=mc.getRb().getString("password_key");
	}
	
	public void authenticate(String username, String password)
			throws UnauthorizedException, ConnectionException,
			InternalUnauthenticatedException {
        try {
            if (!password.equals(getPassword(username))) {
                throw new UnauthorizedException();
            }
        }
        catch (UserNotFoundException unfe) {
            throw new UnauthorizedException();
        }
	}

	public void authenticate(String username, String token, String digest)
			throws UnauthorizedException, ConnectionException,
			InternalUnauthenticatedException {
		// TODO Auto-generated method stub
		throw new UnauthorizedException();

	}

	public String getPassword(String username) throws UserNotFoundException,
			UnsupportedOperationException {
		// TODO Auto-generated method stub
		Mongo mongo=MongoDBManage.getConnection();
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
			mongo.close();
			throw new UserNotFoundException();
		}
		String password=(String)user.get(PASSWORD_KEY);
		//√‹¬Îº”√‹À„∑®
		String encryptPassword=password;
		if ("sha1".equals(mc.getRb().getString("password_encrypt"))){
			encryptPassword=SHA1.getMD5ofStr(password);
		}
		
		if(password==null){
			mongo.close();
			throw new UserNotFoundException();
		}
		mongo.close();
		return encryptPassword;
	}

	public boolean isDigestSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPlainSupported() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setPassword(String username, String password)
			throws UserNotFoundException, UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	public boolean supportsPasswordRetrieval() {
		// TODO Auto-generated method stub
		return false;
	}

}
