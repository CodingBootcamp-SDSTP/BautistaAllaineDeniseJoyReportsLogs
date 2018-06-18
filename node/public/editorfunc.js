window.onload = function() {
	checkSessionId();
	populateSiteSelector();
	populateAlarmSelector();

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
			else {
				alert(xhr.responseText);
			}
		}
	}
	xhr.send();
}

function populateSiteSelector() {
	let site = document.getElementById("siteSelector");
	let xhr = new XMLHttpRequest();
	let json;
	xhr.open('GET', 'sites', true);
	xhr.onreadystatechange = function() {
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
	xhr.onreadystatechange = function() {
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