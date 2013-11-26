package com.mmbang.im.provider;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.admin.AdminProvider;
import org.xmpp.packet.JID;

public class MongoAdminProvider implements AdminProvider {
	private MongoConfig mc;
	public List<JID> getAdmins() {
		// TODO Auto-generated method stub
		List<JID> adminList = new ArrayList<JID>();
		
		mc=new MongoConfig();
		String admins=mc.getRb().getString("admins");
		String admins_username[]= admins.split(",");
		for(String username : admins_username){
			adminList.add(new JID(username, XMPPServer.getInstance().getServerInfo().getXMPPDomain(), null, true));
		}
		
		return adminList;
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setAdmins(List<JID> admins) {
		// TODO Auto-generated method stub

	}

}
