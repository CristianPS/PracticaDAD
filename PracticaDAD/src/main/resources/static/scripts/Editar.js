/**
 * 
 */

function editar() {
	document.getElementById("pass").classList.remove("invisible");
	document.getElementById("newPass").classList.remove("invisible");
	document.getElementById("confPass").classList.remove("invisible");
	document.getElementById("texto").classList.remove("invisible");
	document.getElementById("editar").classList.add("disappear");
	document.getElementById("guardar").classList.remove("disappear");
	document.getElementById('nombre').disabled = false;
	document.getElementById('apellidos').disabled = false;
	document.getElementById('gender').disabled = false;
	document.getElementById('ciudad').disabled = false;
	document.getElementById('fecha').disabled = false;
	document.getElementById('correo').disabled = false;
}
function editarEmpresa() {
	document.getElementById("pass").classList.remove("disappear");
	document.getElementById("newPass").classList.remove("disappear");
	document.getElementById("confPass").classList.remove("disappear");
	document.getElementById("texto").classList.remove("disappear");
	document.getElementById("guardar").classList.remove("disappear");
	document.getElementById("editar").classList.add("disappear");
	document.getElementById("nombre").disabled = false;
	document.getElementById("direccion").disabled = false;
	document.getElementById("telephone").disabled = false;
	document.getElementById("ciudad").disabled = false;
	document.getElementById("correo").disabled = false;
}
function editarOferta() {
	document.getElementById("title").classList.remove("disappear");
	document.getElementById("date").classList.remove("disappear");
	document.getElementById("description").classList.remove("disappear");
	document.getElementById("guardar").classList.remove("disappear");
	document.getElementById("nombreoferta").classList.add("disappear");
	document.getElementById("expDate").classList.add("disappear");
	document.getElementById("offerDescription").classList.add("disappear");
	document.getElementById("editar").classList.add("disappear");
}