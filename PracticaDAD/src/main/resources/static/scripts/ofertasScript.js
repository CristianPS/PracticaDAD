/**
 * 
 */
$(document).ready(function() {
    $('#list').click(function(event){event.preventDefault();$('#products').addClass('list');$('#products .item').removeClass('grid');});
    $('#grid').click(function(event){event.preventDefault();$('#products').removeClass('list');$('#products .item').addClass('grid');});
});


function connect() {
	window.alert("mis muelas");
	console.log('entramos en javascript');
	ws = new WebSocket('ws://localhost://8081/name');
	setConnected(true);
	ws.send('4');
	document.getElementById("formOffer").action="/obtenerOferta?title={{title}}";
	document.getElementById("formOffer").method="post";
}


function grid() {
	document.getElementById("item").classList.add("itemGrid");
	document.getElementById("item").classList.remove("itemList");
	document.getElementById("img").classList.remove("list-group-image-list");
	document.getElementById("img").classList.add("list-group-image");
}
function list() {
	document.getElementById("item").classList.add("itemList");
	document.getElementById("item").classList.remove("itemGrid");
	document.getElementById("img").classList.remove("list-group-image");
	document.getElementById("img").classList.add("list-group-image-list");
}
