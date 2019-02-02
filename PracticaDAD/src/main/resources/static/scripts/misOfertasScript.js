/**
 * 
 */

var statistics = document.getElementById("statistics");
var btn = document.getElementById("button");
var span = document.getElementsByClassName("close")[0];

function showStatistics(){
	var statistics = document.getElementById("statistics");
	statistics.style.display = "block";
}

function closeStatistics(){
	var statistics = document.getElementById("statistics");
	statistics.style.display ="none";
}
/*btn.onclick = function() {
	statistics.style.display = "block";
}

span.onclick = function() {
	statistics.style.display = "none";
}

window.onclick = funcion(event) {
	if (event.target == statistics) {
		statistics.style.display = "none";
	}
}*/