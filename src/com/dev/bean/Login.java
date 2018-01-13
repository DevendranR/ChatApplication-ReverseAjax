package com.dev.bean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.dev.dao.UserDatabase;
import com.dev.util.Util;


public class Login {
	List<String> list=new ArrayList<String>();
	
	public String doLogin(String userName) {
	      UserDatabase userDb=UserDatabase.getInstance();
	      if(!userDb.isUserLogged(userName)) {
	         userDb.login(userName);
	         WebContext webContext= WebContextFactory.get();
	         HttpServletRequest request = webContext.getHttpServletRequest();
	         HttpSession session=request.getSession();
	         session.setAttribute("username", userName);
	         String scriptId = webContext.getScriptSession().getId();
	         session.setAttribute("scriptSessionId", scriptId);
	         for (String string : list) {
				if(!(new Util()).getCurrentUserName().equals(string)){
					list.add((new Util()).getCurrentUserName());
				}
			}
	         return "Chat.html";
	      }
	      else {
	         return "Error.html";
	      }
	   }
		
	public String getUserName(){
		WebContext webContext= WebContextFactory.get();
		HttpServletRequest request = webContext.getHttpServletRequest();
        HttpSession session=request.getSession();
		return session.getAttribute("username").toString();
	}
	

	   public void doLogout() {
		      try {
		         WebContext ctx = WebContextFactory.get();
		         HttpServletRequest request = ctx.getHttpServletRequest();
		         HttpSession session = request.getSession();
		         Util util = new Util();
		         String userName = util.getCurrentUserName(session);
		         UserDatabase.getInstance().logout(userName);
		         session.removeAttribute("username");
		         session.removeAttribute("scriptSessionId");
		         session.invalidate();
		      } catch (Exception e) {
		         System.out.println(e.toString());
		      }
		      list.remove((new Util()).getCurrentUserName());
		      updateUser(list);
		   }
	   
	   public void getUsersOnline2() {
		   	updateUser(list);
		   }
	   
	   public List<String> getUsersOnline() {
		      UserDatabase userDb=UserDatabase.getInstance();
		      return userDb.getLoggedInUsers();
		   }
	   
	   public void updateUser(final List<String> list) {
			Browser.withCurrentPage(new Runnable() {
				public void run() {
					ScriptSessions.addFunctionCall("showOnlineUsers", list);
				}
			});
		}
}
