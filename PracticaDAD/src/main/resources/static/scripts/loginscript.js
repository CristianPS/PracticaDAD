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

function registro()
{
	document.getElementById("form").action = "registro.html"
}