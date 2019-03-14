/**
 *
 */
/*$(document).ready(function() {
    $('#list').click(function(event){event.preventDefault();$('#products').addClass('list');$('#products .item').removeClass('grid');});
    $('#grid').click(function(event){event.preventDefault();$('#products').removeClass('list');$('#products .item').addClass('grid');});
});*/

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function connect() {
	var ws = new WebSocket('ws://127.0.0.1:7777');
	window.alert("Tratando de mandar la oferta a tu cuenta de correo");

	ws.onopen = function(){
		window.alert("Correo mandado con exito");
		var offerId = document.getElementById("offerId").value;
		var username = document.getElementById("username").value;
		ws.send(offerId+"-"+username);
		//ws.send(username);
		document.getElementById("formOffer").action="/obtenerOferta";
		document.getElementById("formOffer").method="post";
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
