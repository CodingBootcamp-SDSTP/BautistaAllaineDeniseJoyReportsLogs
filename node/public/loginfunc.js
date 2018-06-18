window.onload = function() {
	let login = document.getElementById('login');
	login.onclick = function() {
		var xhr = new XMLHttpRequest();
		let un = document.getElementById('username').value;
		let pw = document.getElementById('password').value;
		let url = "login?username=" + un + "&password=" + pw;
		xhr.open('GET', url, true);
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4) {
				let resText = xhr.responseText;
				if(resText != "") {
					window.location.href = "dashboard.html";
				}
				else {
					alert("Log in failed");
				}
			}
		}
		xhr.send();
	}
};