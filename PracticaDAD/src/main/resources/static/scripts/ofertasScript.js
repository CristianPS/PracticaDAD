/**
 *
 */
/*$(document).ready(function() {
    $('#list').click(function(event){event.preventDefault();$('#products').addClass('list');$('#products .item').removeClass('grid');});
    $('#grid').click(function(event){event.preventDefault();$('#products').removeClass('list');$('#products .item').addClass('grid');});
});*/

function connect() {
	var ws = new WebSocket('ws://127.0.0.1:7777');
	window.alert("En breves instantes recibirás un pdf con la oferta seleccionada en tu cuenta de correo")
	ws.onopen = function(){
		//window.alert("El correo se ha mandado correctamente");
		var offerId = document.getElementById("offerId").value;
		var username = document.getElementById("username").value;
		ws.send("0_"+offerId+"-"+username);
		//ws.send(username);
		//document.getElementById("formOffer").action="/obtenerOferta";
		//document.getElementById("formOffer").method="post";
	};

	ws.onerror = function() {
		window.alert("Ha ocurrido un error");
	};


}

function connectPass() {
	var ws = new WebSocket('ws://127.0.0.1:7777');	
	window.alert("En breves instantes recibirás tu nueva contraseña en tu cuenta de correo");
	ws.onopen = function(){
		var username = document.getElementById("username").value;
		ws.send("1_"+username);
		//ws.send(username);
		//document.getElementById("formOffer").action="/cambiarPass";
		//document.getElementById("formOffer").method="post";
	};

	ws.onerror = function() {
		window.alert("Ha ocurrido un error");
	};
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
