var team1Score = 0;
var team2Score = 0;

$(document).ready(function() {
	$('#team1').val(teams[0] + " +");
	$('#team2').val(teams[1] + " +");
	
	for (let player in players) {
            $('#playerOfMatch select').append(`<option value="${player}">${players[player]}</option>`);
	}
});

function scoreTeam1() {
	team1Score++;
	updateScore();
	sendTeamScore(1);
}

function scoreTeam2() {
	team2Score++;
	updateScore();
	sendTeamScore(2);
}

function updateScore() {
	$('#score').html(`${team1Score}x${team2Score}`);
}