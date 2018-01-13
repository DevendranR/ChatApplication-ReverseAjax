	// calling the doLogin method of Login class -->
	function login()
	{
		var userName=document.getElementById('userName').value;
		Login.doLogin(userName,loginResult);
	}

	// redirecting to Chat.html in success condition else Error.html page-->
	function loginResult(newPage)
	{
		window.location.href=newPage;
	}



	function init() {
		// enabling reverse ajax 
		dwr.engine.setActiveReverseAjax(true);
		
		//invoking checkUser method of Chat class -->
		Chat.checkUser(result);
		
	}
	function result(newPage)
	{
		//page to be redirected if no user has logged in -->
	  window.location.href=newPage;
	}
	function logout() {
		//invoking doLogout method of Chat class -->
		Login.doLogout(showLoginScreen);
	}
	
	function usersName(){
		var user = dwr.util.byId('userName');
		
		Login.getUserName({
			callback : function(userName) {
				user.innerHTML =userName;
			}
		});
		
		
		
	}
	
	function showLoginScreen() {
		window.location.href = 'Index.html';
	}
	function showOnlineUsers(users) {

		//getting the logged in user through reverse ajax from Chat class-->
		
		var cellFuncs = [ function(user) {

			return '<i>' + user + '</i>';
		} ];
		Login.getUsersOnline({
			callback : function(users) {
				dwr.util.removeAllRows('usersOnline');
				dwr.util.addRows("usersOnline", users, cellFuncs, {
					escapeHtml : false
				});
			}
		});
	}
	function sendMessage() {
		//invoking addMessage method of Chat class-->
		var text = dwr.util.getValue("text");
		Chat.addMessage(text);
		dwr.util.setValue("text", "");
	}

	function receiveMessages(messages) {
		//getting the messages through reverse ajax from Chat class and combining it with messages already present in chat log-->
		var chatArea = dwr.util.byId('chatlog');
		var oldMessages = chatArea.innerHTML;
		var html="";
		
			html += messages;
			
		chatArea.innerHTML = oldMessages + html;
		var chatAreaHeight = chatArea.scrollHeight;
		chatArea.scrollTop = chatAreaHeight;
		
	}

	function getPreviousMessages() {

		//getting the messages already present in chat log-->

		Chat.getChatContent({
			callback : function(messages) {
				var chatArea = dwr.util.byId('chatlog');
				
				var html = "";
				for (index in messages) {
					var msg = messages[index];
					
					html += msg;
				
				}
				chatArea.innerHTML = html;
				var chatAreaHeight = chatArea.scrollHeight;
				chatArea.scrollTop = chatAreaHeight;
			}
		});
	}

	//sending the message just be clicking on enter through AJAX call -->
	function sendMessageIfEnter(event) {
		if (event.keyCode == 13) {
			sendMessage();
		}
	}