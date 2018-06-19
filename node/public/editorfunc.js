window.onload = function() {
	let username = "";
	getSession((respText) => {
		username = respText.split(" ")[1];
		console.log(username);
		setEmployeeSpan(username);
	});
	populateSiteSelector();
	populateAlarmSelector();
	let add = document.getElementById('add');
	add.onclick = function() {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'logs', true);
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4) {
				alert(xhr.responseText);
			}
		};
		xhr.open('POST', 'logs', true);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(JSON.stringify({
			alarm : document.getElementById('alarmSelector').value,
			site : document.getElementById('siteSelector').value,
			action : document.getElementById('action').value,
			remarks : document.getElementById('remarks').value,
			engineer : username,
			date : Date.now() + 28800000
		}));
	}

};

function getSession(callback) {
	let xhr = new XMLHttpRequest();
	let respText = "";
	xhr.open('GET', 'sessionId', true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			respText = xhr.responseText;
			if(xhr.status == 400) {
				alert(respText);
				window.location.href = "/";
			}
			callback(respText);
		}
	}
	xhr.send();
}

function populateSiteSelector() {
	let site = document.getElementById("siteSelector");
	let xhr = new XMLHttpRequest();
	let json;
	xhr.open('GET', 'sites', true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			json = JSON.parse(xhr.responseText);
			console.log(json);
			let index = 0;
			for(let j of json.siteslist.site) {
				let option = document.createElement("option");
				option.text = j.name + " - " + j.id;
				option.value = j.id;
				site.options.add(option, index++);
			}
		}
	}
	xhr.send();
}

function populateAlarmSelector() {
	let alarm = document.getElementById("alarmSelector");
	let xhr = new XMLHttpRequest();
	let json;
	xhr.open('GET', 'alarms', true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			json = JSON.parse(xhr.responseText);
			console.log(json);
			let index = 0;
			for(let j of json.alarmslist.alarm) {
				let option = document.createElement("option");
				option.text = j.technology + " - " + j.name;
				option.value = j.id;
				alarm.options.add(option, index++);
			}
		}
	}
	xhr.send();
}

function setEmployeeSpan(username) {
	let employee = document.getElementById("employee");
	let xhr = new XMLHttpRequest();
	let json;
	xhr.open('GET', 'employees/username/' + username, true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			json = JSON.parse(xhr.responseText);
			console.log(json);
			employee.innerHTML = json.employee[0].firstName + " " + json.employee[0].lastName;
		}
	}
	xhr.send();
}
