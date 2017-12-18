var webSocket;
var teams = ["Toronto Maple Leafs", "Vancouver Canucks"];
var players = {11: "Tyler Bozak", 12: "Andreas Borgman", 13: "Connor Brown", 14: "Josh Leivo", 15: "Frederik Andersen",
			   21: "Michael Del Zotto", 22: "Sven Baertschi", 23: "Brock Boeser", 24: "Alexander Burmistrov", 25: "Jacob Markstrom"}; 

$(document).ready(function() {
    webSocket = new WebSocket("ws://localhost:8080/HockeyGame/hockeyGame");

    webSocket.onopen = function() {
            webSocket.send(`teams;${JSON.stringify(teams)}`);
            webSocket.send(`playerList;${JSON.stringify(players)}`);
    };

    webSocket.onmessage = function(e) {
            //Would implement handshake here
    };

    webSocket.onclose = function(e) {
            $('#message').html("The connection was lost. Please reload the page and login again to continue using.");
    };

    $(window).bind(
            "beforeunload", 
            function() { 
                    webSocket.close();
            }
    );
});

function sendTeamScore(team) {
	webSocket.send(`scoreTeam${team}`);
}

function sendPlayer() {
	var player = $('#playerOfMatch select').val();
	webSocket.send(`finalPlayer:${player}`);
}