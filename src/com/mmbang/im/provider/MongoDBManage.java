package com.mmbang.im.provider;
import java.net.UnknownHostException;

import com.mongodb.Mongo;
public class MongoDBManage {
	private static Mongo mongo=null;
	private static MongoConfig mc;
	
	public static Mongo getConnection()
	{
		mc=new MongoConfig();
		try {
			mongo= new Mongo(mc.getRb().getString("host"), new Integer(mc.getRb().getString("port")));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return mongo;
	}
	
	public static void closeConnection()
	{
		if(mongo!=null){
			mongo.close();
		}
	}
}
