window.onload = function() {
	let username = "";
	getSession((respText) => {
		username = respText.split(" ")[1];
	});
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
}

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

