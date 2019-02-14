/**
 * 
 */

function showPassword()
{
	if(document.getElementById("password").type == "password")
	{
		document.getElementById("password").type = "text";
	}
	else
	{
		document.getElementById("password").type = "password";
	}
}

function showNewPassword()
{
	if(document.getElementById("newpassword").type == "password")
	{
		document.getElementById("newpassword").type = "text";
	}
	else
	{
		document.getElementById("newpassword").type = "password";
	}
}

function showConfirmedPassword()
{
	if(document.getElementById("confirmpassword").type == "password")
	{
		document.getElementById("confirmpassword").type = "text";
	}
	else
	{
		document.getElementById("confirmpassword").type = "password";
	}
}

function terminar()
{
	if(document.getElementById("password").value == "" || document.getElementById("confirmpassword").value == "" || document.getElementById("username").value == "" || document.getElementById("name").value == "" || document.getElementById("apellidos").value == "" || document.getElementById("email").value == "" || document.getElementById("fecha").value == "" || document.getElementById("city").value == "" )
	{
		alert("Debes rellenar todos los campos para poder registrarte");
	}
	else
	{
		document.getElementById("form").action = "/registroUsuario";
		document.getElementById("form").method = "post";
	}
}
