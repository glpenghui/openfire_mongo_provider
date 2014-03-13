package com.mmbang.im.provider;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoOptions;

public class MongoConnectionManager {
	private static MongoClient mongo=null;
	private static MongoConfig mc=null;
	
	private MongoConnectionManager(){
		
	}
	
	public static MongoClient getConnection(String host, int port)
	{
		if (mongo==null){
			init(host, port);
		}
		return mongo;
	}
	
	private static void init(String host, int port)
	{
		if (mc==null){
			mc=new MongoConfig();
		}
		int connectionsPerHost=Integer.valueOf(mc.getRb().getString("connectionsPerHost"));
		int threadsAllowedToBlockForConnectionMultiplier=Integer.valueOf(mc.getRb().getString("threadsAllowedToBlockForConnectionMultiplier"));
		
		try {
			if (mongo==null){
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
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
