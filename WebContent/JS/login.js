/**
 * 
 */
window.onload=function(){
	var btn = document.getElementById("butt");
	btn.addEventListener('click', myFunc);
	
	function myFunc() {
		var username = document.getElementById("userName").value;
		var password = document.getElementById("password").value;
		var formObject =  {
            "uName": username,
            "uPass": password
        }
		$.ajax({
			type : "POST",
			dataType: 'json',		
			contentType: "application/json; charset=UTF-8",
			url : "/Login",
			data : JSON.stringify(jsObject)		
		}).done(function(data) {
				alert(data["username"]);
		   });
	}
}