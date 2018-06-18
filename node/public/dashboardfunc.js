window.onload = function() {
	checkSessionId();
};

function checkSessionId() {
	let xhr = new XMLHttpRequest();
	xhr.open('GET', 'sessionId', true);
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4) {
			if(xhr.status == 400) {
				alert(xhr.responseText);
				window.location.href = "/";
			}
		}
	}
	xhr.send();
}