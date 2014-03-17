package com.mmbang.im.provider;

import java.net.UnknownHostException;
import java.util.HashMap;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoOptions;

public class MongoConnectionManager {
	private static HashMap<String, MongoClient> mongos=new HashMap<String, MongoClient>();
	private static MongoConfig mc=null;
	
	private MongoConnectionManager(){
		
	}
	
	public static MongoClient getConnection(String host, int port)
	{
		MongoClient mongo=null;
		String key=host+":"+port;
		if (mongos.containsKey(key) && mongos.get(key)!=null){
			return mongos.get(key);
		}
		mongo=init(host, port);
		mongos.put(key, mongo);
		return mongo;
	}
	
	private static MongoClient init(String host, int port)
	{
		MongoClient mongo=null;
		if (mc==null){
			mc=new MongoConfig();
		}
		int connectionsPerHost=Integer.valueOf(mc.getRb().getString("connectionsPerHost"));
		int threadsAllowedToBlockForConnectionMultiplier=Integer.valueOf(mc.getRb().getString("threadsAllowedToBlockForConnectionMultiplier"));
		
		try {
			try{
				System.out.println("mongo uri:"+host);
				MongoClientURI mongoURI=new MongoClientURI(host);
				mongo=new MongoClient(mongoURI);
				
			}catch (IllegalArgumentException e) {
				mongo=new MongoClient(host, port);
				System.out.println("normal connect");
			}
			
			MongoOptions opt=mongo.getMongoOptions();
			opt.connectionsPerHost=connectionsPerHost;
			opt.threadsAllowedToBlockForConnectionMultiplier=threadsAllowedToBlockForConnectionMultiplier;
			
			System.out.println("connectionsPerHost:"+connectionsPerHost);
			System.out.println("threadsAllowedToBlockForConnectionMultiplier"+threadsAllowedToBlockForConnectionMultiplier);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return mongo;
	}
}
