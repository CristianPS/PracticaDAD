/**
 * 
 */

function showPassword(){
	if(document.getElementById("password").type == "password")
	{
		document.getElementById("password").type = "text";
	}
	else
	{
		document.getElementById("password").type = "password";
	}
}

function iniciar()
{
	if(document.getElementById("name").value == "" || document.getElementById("password").value == "")
	{
		alert("Debes rellenar todos los campos para poder iniciar sesion");
	}
	else
	{
		document.getElementById("form").action = "/inicioUsuario";
		document.getElementById("form").method = "post";
	}
}

function registro()
{
	document.getElementById("form").action = "registro.html"
	document.getElementById("form").method = "";
}

function editarA() {
	document.getElementById("pass").classList.remove("invisible");
	document.getElementById("newPass").classList.remove("invisible");
	document.getElementById('nombre').disabled = false;
	document.getElementById('apellidos').disabled = false;
	document.getElementById('gender').disabled = false;
	document.getElementById('ciudad').disabled = false;
	document.getElementById('fecha').disabled = false;
	document.getElementById('correo').disabled = false;

}