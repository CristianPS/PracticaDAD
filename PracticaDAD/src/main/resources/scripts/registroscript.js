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