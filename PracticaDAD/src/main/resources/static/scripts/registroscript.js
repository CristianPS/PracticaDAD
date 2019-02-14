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
	if(document.getElementById("password").value == "" || document.getElementById("confirmpassword").value == "" ||
			document.getElementById("username").value == "" || document.getElementById("name").value == "" || 
			document.getElementById("apellidos").value == "" || document.getElementById("email").value == "" || 
			document.getElementById("fecha").value == "" || document.getElementById("city").value == "" )
	{
		alert("Debes rellenar todos los campos para poder registrarte");
	}
	else
	{
		document.getElementById("form").action = "/registroUsuario";
		document.getElementById("form").method = "post";
	}
}
function empresa() {
	document.getElementById("name").classList.add("disappear");
	document.getElementById("apellidos").classList.add("disappear");
	document.getElementById("fecha").classList.add("disappear");
	document.getElementById("fechaTitulo").classList.add("disappear");
	document.getElementById("genero").classList.add("disappear");
	document.getElementById("generoTitulo").classList.add("disappear");
	document.getElementById("fecha").classList.add("disappear");
	document.getElementById("nameEmpresa").classList.remove("disappear");
	document.getElementById("dir").classList.remove("disappear");
	document.getElementById("telefono").classList.remove("disappear");
}
function usuario() {
	document.getElementById("name").classList.remove("disappear");
	document.getElementById("apellidos").classList.remove("disappear");
	document.getElementById("fecha").classList.remove("disappear");
	document.getElementById("fechaTitulo").classList.remove("disappear");
	document.getElementById("genero").classList.remove("disappear");
	document.getElementById("generoTitulo").classList.remove("disappear");
	document.getElementById("fecha").classList.remove("disappear");
	document.getElementById("nameEmpresa").classList.add("disappear");
	document.getElementById("dir").classList.add("disappear");
	document.getElementById("telefono").classList.add("disappear");
}
function iniciarregistrar()
{

		if(document.getElementById("usuario").checked == true)
		{
			if(document.getElementById("password").value == "" || document.getElementById("confirmpassword").value == "" ||
					document.getElementById("username").value == "" || document.getElementById("name").value == "" || 
					document.getElementById("apellidos").value == "" || document.getElementById("email").value == "" || 
					document.getElementById("fecha").value == "" || document.getElementById("city").value == "" )
			{
				alert("Debes rellenar todos los campos para poder registrarte");
			}
			else
			{
				document.getElementById("form").action = "/inicioUsuario";
				document.getElementById("form").method = "post";
			}
		}
		else if(document.getElementById("empresa").checked == true)
		{
			if(document.getElementById("password").value == "" || document.getElementById("confirmpassword").value == "" ||
					document.getElementById("username").value == "" || document.getElementById("nameEmpresa").value == "" || 
					document.getElementById("dir").value == "" || document.getElementById("email").value == "" || 
					document.getElementById("telefono").value == "" || document.getElementById("city").value == "" )
			{
				alert("Debes rellenar todos los campos para poder registrarte");
			}
			else
			{
			document.getElementById("form").action = "/inicioComercio";
			document.getElementById("form").method = "post";
			}
		}
	
}