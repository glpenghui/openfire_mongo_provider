package com.mmbang.im.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.openfire.group.AbstractGroupProvider;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.group.GroupNotFoundException;
import org.xmpp.packet.JID;

public class MongoGroupProvider extends AbstractGroupProvider {

	public Group getGroup(String arg0) throws GroupNotFoundException {
		System.out.println("getGroup");
		// TODO Auto-generated method stub
		Group group=new Group();
		return group;
	}

	public int getGroupCount() {
		System.out.println("getGroupCount");
		// TODO Auto-generated method stub
		return 0;
	}

	public Collection<String> getGroupNames() {
		System.out.println("getGroupNames1");
		// TODO Auto-generated method stub
		Collection<String> groupNames=new ArrayList<String>();
		groupNames.add("test");
		return groupNames;
	}

	public Collection<String> getGroupNames(JID arg0) {
		System.out.println("getGroupNames2");
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	public Collection<String> getGroupNames(int arg0, int arg1) {
		System.out.println("getGroupNames3");
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

}
