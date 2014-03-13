package com.mmbang.im.provider;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.jivesoftware.util.JiveGlobals;

import com.mongodb.MongoClientURI;

public class TestMongoURI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 ResourceBundle rb;
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("/Volumes/sdisk/workspace/openfire_mongo_provider/mongo_provider.properties"));
			rb = new PropertyResourceBundle(in);
			System.out.println(rb);
			System.out.println(rb.getString("host"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
