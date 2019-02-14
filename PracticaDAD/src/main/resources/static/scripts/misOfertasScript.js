/**
 * 
 */

var statistics = document.getElementById("statistics");
var btn = document.getElementById("button");
var span = document.getElementsByClassName("close")[0];

function showStatistics(){
	var statistics = document.getElementById("statistics");
	statistics.style.display = "block";
	document.getElementById("form").action="";
	document.getElementById("form").method="";
}

function closeStatistics(){
	var statistics = document.getElementById("statistics");
	statistics.style.display ="none";
}

/*function acceder(){
	document.getElementById("form").action="/mostrarAnuncio?title={{title}}";
	document.getElementById("form").method="post";
}*/
