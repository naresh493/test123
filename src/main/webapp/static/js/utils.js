function noSpaceAndSpecialChars(value){
		//var iChars = "~!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
	//	var iChars ="/^([0-9]|[a-z])+([0-9a-z]+)$/i"
			var iChars ="^[a-zA-Z0-9]*$";
			var pName = value;
		if(pName.trim()=='')
			{
			alert("Spaces are Not Allowed");
			document.getElementById('projectName').value="";
			return false;
			}
		/* for (var i = 0; i < document.getElementById('projectName').value.length; i++) {
		    if (iChars.indexOf(document.getElementById('projectName').value.charAt(i)) != -1) {
		    alert ("Special characters  are not allowed.\n");
		    document.getElementById('projectName').value="";
		    return false;
		        }
		    } */
		if((pName.match("^[a-zA-Z0-9]*$")) == null || (pName.match("^[a-zA-Z0-9]*$")) == "null" || (pName.match("^[a-zA-Z0-9]*$")) == "NULL"){
			alert("Spaces are Not Allowed");
			 document.getElementById('projectName').value="";
			return false;
			}
	}