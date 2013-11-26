package com.mmbang.im.provider;

import org.jivesoftware.util.JiveGlobals;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MongoConfig {
	private final String CONFIG_NAME="mongo_provider.properties";
	private ResourceBundle rb;
	public MongoConfig()
	{
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(JiveGlobals.getHomeDirectory()+"/conf/"+CONFIG_NAME));
			this.rb = new PropertyResourceBundle(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ResourceBundle getRb() {
		return this.rb;
	}
}
