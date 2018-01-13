package com.dev.bean;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;

import com.dev.util.Util;

public class Chat {
	private static List<String> chatContent = new Vector<String>();
	public void addMessage(String text) {
		String newMessage = "";
		
		if (text != null && text.trim().length() > 0) {
			Calendar calendar = Calendar.getInstance();
			StringBuffer sb = new StringBuffer();
			String user = (new Util()).getCurrentUserName();
					
			sb.append(" <b><i>");
			sb.append(user);
			sb.append("</i></b> ");
			
			sb.append(" <br/> " + text+"&nbsp;&nbsp;&nbsp;");
			sb.append("<span style='font-size:9px;float:right;'>");
			sb.append(calendar.get(Calendar.HOUR) + ":"+ calendar.get(Calendar.MINUTE));
			sb.append("</span> <br/>");
			
			newMessage = sb.toString();
			// messages.addFirst(new Message(newMessage));
			
			
			storeMessage(newMessage);

		}

	}
	public void storeMessage(String message){
		
		chatContent.add(message);
		postNewMessage(message);
	}
	
	public String checkUser(){
		
		String user = (new Util()).getCurrentUserName();
		if(user==null){
			return "Error.html";
		}
		return "#";
	}
	
	public List<String> getChatContent() {
		List<String> newStr=new LinkedList<String>();
		newStr.addAll(chatContent);
		return newStr;
	}

	public void postNewMessage(final String newMessage) {
		Browser.withCurrentPage(new Runnable() {
			public void run() {
				ScriptSessions.addFunctionCall("receiveMessages", newMessage);
			}
		});
	}
	// static LinkedList<Message> messages = new LinkedList<Message>();

}
