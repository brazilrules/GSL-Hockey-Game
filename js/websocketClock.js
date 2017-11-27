var websocket;
var teams = ["Toronto Maple Leafs", "Vancouver Canucks"];
var players = {11: "Tyler Bozak", 12: "Andreas Borgman", 13: "Connor Brown", 14: "Josh Leivo", 15: "Frederik Andersen",
			   21: "Michael Del Zotto", 22: "Sven Baertschi", 23: "Brock Boeser", 24: "Alexander Burmistrov", 25: "Jacob Markstrom"}; 

$document.ready(function() {
	webSocket = new WebSocket("ws://localhost:8080/hockeyGame");
	
	websocket.onOpen = function() {
		webSocket.send(`teams;${teams}`);
		webSocket.send(`playerList;${players}`);
	};
	
	websocket.onmessage = function(e) {
		//Would implement handshake here
	};
	
	websocket.onclose = function(e) {
		$('#message').html("The connection was lost. Please reload the page and login again to continue using.");
	};
});

function sendTeamScore(team) {
	websocket.send(`scoreTeam${team}`);
}

function sendPlayer() {
	var player = $('#playerOfMatch select').val();
	websocket.send(`finalPlayer:${player}`);
}

$(document).ready(function() {
	$(window).bind(
		"beforeunload", 
		function() { 
			websocket.close();
		}
	);
});