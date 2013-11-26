package com.mmbang.im.provider;
import java.io.ByteArrayInputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.bson.types.ObjectId;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jivesoftware.openfire.vcard.VCardProvider;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.util.AlreadyExistsException;
import org.jivesoftware.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoVCardProvider implements VCardProvider {
	private String DB_NAME;
	private String COLLECTION_NAME;
	private String NICKNAME_KEY;
	private String USERNAME_KEY;
	private String AVATAR_KEY;
	private MongoConfig mc;
	private Mongo mongo;

	private static final int POOL_SIZE = 10;
	private BlockingQueue<SAXReader> xmlReaders = new LinkedBlockingQueue<SAXReader>(POOL_SIZE);
	
	private static final Logger Log = LoggerFactory.getLogger(MongoVCardProvider.class);
	
    public MongoVCardProvider() {
        super();
        
		mc=new MongoConfig();
		mongo=MongoDBManage.getConnection();
		
		this.DB_NAME=mc.getRb().getString("db_name");
		this.COLLECTION_NAME=mc.getRb().getString("collection_name");
		this.USERNAME_KEY=mc.getRb().getString("username_key");
		this.NICKNAME_KEY=mc.getRb().getString("nickname_key");
		this.AVATAR_KEY=mc.getRb().getString("avatar_key");
        
        // Initialize the pool of sax readers
        for (int i=0; i<POOL_SIZE; i++) {
            SAXReader xmlReader = new SAXReader();
            xmlReader.setEncoding("UTF-8");
            xmlReaders.add(xmlReader);
        }
    }	
	
	public Element createVCard(String username, Element cardElement)
			throws AlreadyExistsException {
		// TODO Auto-generated method stub
		System.out.println("createVCard");
		return null;
	}

	public void deleteVCard(String username) {
		// TODO Auto-generated method stub
		System.out.println("deleteVCard");
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		System.out.println("isReadOnly");
		return false;
	}

	public Element loadVCard(String username) {
		// TODO Auto-generated method stub
		Element vCardElement = null;
		SAXReader xmlReader = null;
		
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
		
		DBObject user=null;
		user=users.findOne(searchQuery);
		String nickname=(String)user.get(NICKNAME_KEY);
		String avatarUrl=(String)user.get(AVATAR_KEY);
		
		VCard vcard=null;
		vcard = new VCard();
		vcard.setNickName(nickname);
		vcard.setField("username", username);
		vcard.setField("avatarURL", avatarUrl!=null ? avatarUrl: mc.getRb().getString("default_avatar_url"));
		
		String vcardXml=vcard.toString();
		try {
			xmlReader = xmlReaders.take();
			vCardElement=xmlReader.read(new ByteArrayInputStream(vcardXml.getBytes("UTF-8"))).getRootElement();;
			xmlReaders.add(xmlReader);
		} catch (Exception e) {
			Log.error("Error loading vCard of username: " + username, e);
		}
        return vCardElement;
	}

	public Element updateVCard(String username, Element cardElement)
			throws NotFoundException {
		// TODO Auto-generated method stub
		System.out.println("updateVCard");
		return null;
	}

}
