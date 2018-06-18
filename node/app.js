const http = require('http');
const xml = require('xml-parse');
const StaticServer = require('node-static').Server;
const file = new StaticServer('./public');
const crypto = require("crypto");
const redis = require('redis');
const qs = require('querystring');
const client = redis.createClient();
const handlers = {};
let sessionId = "";

const options = [
	{
		hostname: '127.0.0.1',
		port: 8080,
		path: '/reportlogsapp/employees'
	},
	{
		hostname: '127.0.0.1',
		port: 8080,
		path: '/reportlogsapp/sites'
	},
	{
		hostname: '127.0.0.1',
		port: 8080,
		path: '/reportlogsapp/alarms'
	},
	{
		hostname: '127.0.0.1',
		port: 8080
	}
];

handlers["/employees"] = (req, res) => {
	if(req.method == "GET") {
		requestFromServlet(res, options[0]);
	}
};

handlers["/sites"] = (req, res) => {
	if(req.method == "GET") {
		requestFromServlet(res, options[1]);
	}
};

handlers["/alarms"] = (req, res) => {
	if(req.method == "GET") {
		requestFromServlet(res, options[2]);
	}
};

handlers["/login"] = function(req, res) {
	let data = qs.parse(req.url)
	let un = data['/login?username'];
	let pw = data['password'];
	options[3].path = "/reportlogsapp/employees?username=" + un;
	const reqServlet = http.request(options[3]);
	reqServlet.end();
	reqServlet.on('response', (resServlet) => {
		let respData = "";
		resServlet.on('data', (data) => {
			respData += data;
		});
		resServlet.on('end', () => {
			let obj = xmlToJson(respData);
			if(JSON.stringify(obj) != "{}") {
				if(pw == obj.employee[0].password) {
					startSession(un, function(id) {
						sessionId = id;
						res.writeHead(200, { "Content-Type" : "text/plain" });
						res.end(sessionId);
					});
				}
				else {
					res.writeHead(200, { "Content-Type" : "text/plain" });
					res.end("");
				}
			}
			else {
				res.writeHead(200, { "Content-Type" : "text/plain" });
				res.end("");
			}
		});
	});
}

handlers["/sessionId"] = function(req, res) {
	if(sessionId == "") {
		res.writeHead(400, { "Content-Type" : "text/plain" });
		res.end("Session ID required. Please log in.");
	}
	else {
		client.get(sessionId, function(err, result) {
			res.writeHead(200, { "Content-Type" : "text/plain" });
			res.end(sessionId + " " + result);
		});
		
	}
}

handlers["/employees/username/"] = (req, res, username) => {
	options[3].path = "/reportlogsapp/employees?username=" + username;
	requestFromServlet(res, options[3]);
};

handlers["/sites/id/"] = (req, res, id) => {
	options[3].path = "/reportlogsapp/sites?id=" + id;
	requestFromServlet(res, options[3]);
};

handlers["/alarms/id/"] = (req, res, id) => {
	options[3].path = "/reportlogsapp/alarms?id=" + id;
	requestFromServlet(res, options[3]);
};

function startSession(name, callback) {
	id = crypto.randomBytes(16).toString("hex");
	client.set(id, name, function() {
		callback(id);
	});
}

function requestFromServlet(res, option) {
	if(sessionId == "") {
		res.writeHead(400, { "Content-Type" : "text/plain" });
		res.end("Session ID required. Please log in.");
		return;
	}
	const reqServlet = http.request(option);
	reqServlet.end();
	reqServlet.on('response', (resServlet) => {
		let respData = "";
		resServlet.on('data', (data) => {
			respData += data;
		});
		resServlet.on('end', () => {
			writeResponse(res, respData);
		});
	});
}

function writeResponse(res, data) {
	res.writeHead(200, { "Content-Type" : "application/json" });
	res.write(JSON.stringify(xmlToJson(data)));
	res.end();
}

function xmlToJson(respData) {
	j = {};
	let xmlObject = xml.parse(respData);
	for(let r of xmlObject) {
		recurse(r, j);
	}
	return(j);
}

function parseChild(a, obj) {
	let o = obj[a.tagName];
	if(!o) {
		obj[a.tagName] = [];
	}
	let objArray = obj[a.tagName];
	objArray.push({});
	for(let c of a.childNodes) {
		recurse(c, objArray[objArray.length - 1]);
	}
}

function recurse(r, j) {
	if(r.childNodes.length != 0 && r.childNodes[0].type == "text") {
		j[r.tagName] = r.childNodes[0].text;
		return;
	}
	else if(r.type == "element") {
		if(r.childNodes.length > 0) {
			if(r.childNodes[0].childNodes[0].type == "text") {
				parseChild(r, j);
			}
			else {
				j[r.tagName] = {};
				for(let a of r.childNodes) {
					parseChild(a, j[r.tagName]);
				}
			}
		}
	}
}

var server = http.createServer(function(req, res) {
	let h = handlers[req.url];
	if(h) {
		h(req, res);
	}
	else {
		let url = (req.url).match(/\/\w+\/\w+\/\w+/);
		if(url) {
			let url2 = url[0].match(/^\/\w+\/\w+\//);
			let hh = handlers[url2[0]];
			if(hh) {
				let search = (url[0].match(/\w+$/))[0];
				hh(req, res, search);
			}
			else {
				res.writeHead(404, { "Content-type" : "text/html" });
				res.end("404 Not Found");
			}
		}
		else {
			let q = handlers[req.url.split("?")[0]];
			if(q) {
				q(req, res);
			}
			else {
				file.serve(req, res, function(err, result) {
					if(err && (err.status === 404)) {
						res.writeHead(404, { "Content-Type" : "text/plain" });
						res.end("Error: Not Found");
					}
				});
			}
		}
	}
});

server.listen(8030);
console.log("Server running ..");
