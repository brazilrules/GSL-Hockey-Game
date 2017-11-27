function login() {
	var email = $('#email').val();
	var password = $('#password').val();
	
	$.get(`http://localhost:8080/login/${email}/${password}`, function(data) {
		if(data.indexOf("error") != -1) {
			alert(data);
			return;
		}
		if(JSON.parse(data)) {
			$('#login').hide();
			initWebsocket(email);
			$('#hockeyGame').show();
		} else {
			alert("The Email or Password is invalid.");
		}
	});
}

function showRegister() {
	$('#login').hide();
	$("#register").show();
}

function register() {
	var email = $('#registerEmail').val();
	var password = $('#registerPassword').val();
	
	$.get(`http://localhost:8080/register/${email}/${password}`, function(data) {
		if(data.indexOf("error") != -1) {
			alert(data);
			return;
		}
		if(JSON.parse(data)) {
			$('#login').hide();
			$('#hockeyGame').show();
		} else {
			alert("The Email or Password is invalid.");
		}
	});
	
	$("#register").hide();
	$("#login").show();
}