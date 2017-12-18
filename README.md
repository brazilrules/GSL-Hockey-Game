# GSL-Hockey-Game
Vanhackaton 4.0 challenge for GSL Group

This is just a proof of concept to show what can be done, but more development would be needed to make a server to serve the current game informations to the Websocket server, and one to register the users on a central database no matter where the user is. Also there is a need to add functionality so that a "Man of the Match" can only be chosen before the last quarter and would automatically be decided by the outcome of the game.

Instructions:

	For running under localhost:
	1. Deploy the file in dist folder to an Java web container (e.g. Apache Tomcat) on the 8080 port
	2. Open http://localhost:8080/HockeyGame/clock.html in a browser window to initialize the representation of the Score Clock and send team and player informations to the server.
	3. Open http://localhost:8080/HockeyGame in another browser window to  serve as the representation of what a spectator would open on their cellphone.
	4. Put both windows in view at the same time to see the interaction between the clock and the spectator game.
	5 Make guesses in the spectator game window and then, on the clock window, click on the buttons to change the score, or choose a name from dropdown list to send notifications of points gained to the players.
	Optional: multiple spectator game windows can be opened at the same time.
	
	For running on multiple devices or if another port is used:
	* After deploying, change the diplay.js, websocketClock.js and websocketHockey.js files, located in HockeyGame\src\main\webapp\js, service URLs to the IP/domain used to access the server.