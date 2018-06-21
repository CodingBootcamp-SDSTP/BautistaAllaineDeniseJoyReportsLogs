window.onload = function() {
	let username = "";
	let admin = false;
	let engineer;
	getSession((respText) => {
		username = respText.split(" ")[1];
		getEngineer(username, (json) => {
			engineer = json.engineer[0];
			document.getElementById("name").innerHTML = "<p>Welcome " + engineer.firstName + " " + engineer.lastName + "</p>";
			if(engineer.admin == "true") {
				admin = true;
				let div1 = document.createElement("div");
				div1.className = "menuItem";
				let a1 = document.createElement("a");
				a1.setAttribute("href", "#");
				a1.id = "addEng";
				a1.appendChild(document.createTextNode("Add Engineer"));
				div1.appendChild(a1);
				let parentDiv = document.getElementById("menu");
				let menuitems = document.getElementsByClassName("menuItem");
				parentDiv.insertBefore(div1, menuitems[menuitems.length - 1]);
				document.getElementById("addEng").onclick = function() {
					makeAddEngBody(createAddEngFunctionality);
				};
			}
		});
	});

	document.getElementById("add").onclick = function() {
		let body = document.getElementById("body");
		body.innerHTML = "<p><label>Site </label><select id=\"siteSelector\"></select></p>" +
			"<p><label>Alarm </label><select id=\"alarmSelector\"></select></p>" +
			"<p><label>Actions Taken: </label><input type=\"text\" id=\"action\" /></p>" +
			"<p><label>Remarks: </label><input type=\"text\" id=\"remarks\" /></p>" +
			"<p><label>Engineer: </label><span id=\"engineer\"></span></p>" +
			"<p><a href=\"#\" id=\"addEntry\">Add</a></p>";
		document.getElementById("engineer").innerHTML = engineer.firstName + " " + engineer.lastName;
		populateSiteSelector();
		populateAlarmSelector();
		let add = document.getElementById('addEntry');
		add.onclick = function() {
			let xhr = new XMLHttpRequest();
			xhr.open('POST', 'logs', true);
			xhr.onreadystatechange = function() {
				if(xhr.readyState == 4) {
					alert(xhr.responseText);
				}
			};
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

	document.getElementById("logout").onclick = function() {
		let xhr = new XMLHttpRequest();
		xhr.open('GET', 'logout', true);
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4) {
				let respText = xhr.responseText;
				if(respText == "") {
					alert("Successfully Logged out");
					window.location.href = "/";
				}
				else {
					alert("Failed to Log out");
				}
			}
		};
		xhr.send();
	};

	document.getElementById("view").onclick = function() {
		let body = document.getElementById("body");
		body.innerHTML = "<div id=\"filter\">" +
				"<p>Filter</p>" +
				"<div>" +
					"<label>ID: </label><input type=\"text\" id=\"filterId\" />" +
					"<label>Alarm: </label><input type=\"text\" id=\"filterAlarm\" />" +
					"<label>Site: </label><input type=\"text\" id=\"filterSite\" />" +
					"<label>Action: </label><input type=\"text\" id=\"filterAction\" />" +
					"<label>Remarks: </label><input type=\"text\" id=\"filterRemarks\" />" +
					"<label>Engineer: </label><input type=\"text\" id=\"filterEngineer\" />" +
					"<a href=\"#\" id=\"doFilter\">Filter</a>" +
				"</div>" +
			"</div>" +
			"<table>" +
				"<thead>" +
					"<th>ID</th>" +
					"<th>Alarm</th>" +
					"<th>Site</th>" +
					"<th>Action</th>" +
					"<th>Remarks</th>" +
					"<th>Engineer</th>" +
					"<th>Date</th>" +
				"</thead>" +
				"<tbody id=\"table\">" +
				"</tbody>" +
			"</table>";
		populateTable("logs");
		document.getElementById("doFilter").onclick = function() {
			let filters = ["filterId", "filterAlarm", "filterSite", "filterAction", "filterRemarks", "filterEngineer"];
			let types = ["id", "alarm", "site", "action", "remarks", "engineer"];
			let url = "logs?";
			for(let i in filters) {
				let text = document.getElementById(filters[i]).value;
				if(text != "") {
					if(url != "logs?") {
						url += "&";
					}
					url += types[i] + "=" + text;
				}
			}
			document.getElementById("table").innerHTML = "";
			populateTable(url);
		};
	};
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

function getEngineer(username, callback) {
	let xhr = new XMLHttpRequest();
	let json;
	xhr.open('GET', 'engineers/username/' + username, true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			json = JSON.parse(xhr.responseText);
			callback(json);
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

function populateTable(url) {
	let xhr = new XMLHttpRequest();
	let respText = "";
	xhr.open('GET', url, true);
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4) {
			let json = JSON.parse(xhr.responseText);
			console.log(json);
			for(let entry of json.entrieslist.entry) {
				let row = document.createElement("tr");
				let cell = document.createElement("td");
				let text = document.createTextNode(entry.id);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(entry.alarm[0].name);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(entry.site[0].name);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(entry.action);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(entry.remarks);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(entry.engineer[0].firstName + " " + entry.engineer[0].lastName);
				cell.appendChild(text);
				row.appendChild(cell);
				cell = document.createElement("td");
				text = document.createTextNode(new Date(Number(entry.time) - 28800000).toLocaleString());
				cell.appendChild(text);
				row.appendChild(cell);
				document.getElementById("table").appendChild(row);
			}
			
		}
	}
	xhr.send();
}

function makeAddEngBody(callback) {
	let body = document.getElementById("body");
	body.innerHTML = "<p><label>ID: </label><input type=\"text\" id=\"addId\"></p>" +
	"<p><label>First Name: </label><input type=\"text\" id=\"addFN\" /></p>" +
	"<p><label>Last Name: </label><input type=\"text\" id=\"addLN\" /></p>" +
	"<p><label>Age: </label><input type=\"text\" id=\"addAge\" /></p>" +
	"<p><label>Department: </label><input type=\"text\" id=\"addDepartment\" /></p>" +
	"<p><label>Username: </label><input type=\"text\" id=\"addUsername\"></p>" +
	"<p><label>Password: </label><input type=\"password\" id=\"addPassword\" /></p>" +
	"<p><label>Admin: </label>" +
		"<span id=\"radiobuttons\">" +
			"<input type=\"radio\" name=\"admin\" value=\"true\" />Yes" +
			"<input type=\"radio\" name=\"admin\" value=\"false\" checked/>No" +
		"</span>" +
	"</p>" +
	"<p><a href=\"#\" id=\"addEngineer\">Add</a></p>"
	callback();
}

function createAddEngFunctionality() {
	document.getElementById("addEngineer").onclick = function() {
		let rb = document.querySelectorAll("input[type=radio]");
			let rbvalue;
			for(let r of rb) {
				if(r.checked) {
					rbvalue = r.value;
				}
			}
		let xhr = new XMLHttpRequest();
		xhr.open('POST', 'engineers', true);
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4) {
				alert(xhr.responseText);
			}
		};
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(JSON.stringify({
			id : document.getElementById('addId').value,
			firstName : document.getElementById('addFN').value,
			lastName : document.getElementById('addLN').value,
			age : document.getElementById('addAge').value,
			department : document.getElementById('addDepartment').value,
			username : document.getElementById('addUsername').value,
			password : document.getElementById('addPassword').value,
			isadmin : rbvalue
		}));
	}
}