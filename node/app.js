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
		path: '/reportlogsapp/engineers'
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
		port: 8080,
		path: '/reportlogsapp/logs'
	},
	{
		hostname: '127.0.0.1',
		port: 8080
	},
	{
		hostname: '127.0.0.1',
		method: "POST",
		port: 8080,
		path: '/reportlogsapp/addentry',
		header: {'Content-Type': 'application/x-www-form-urlencoded'}
	}
];

handlers["/engineers"] = (req, res) => {
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
	options[4].path = "/reportlogsapp/engineers?username=" + un;
	const reqServlet = http.request(options[4]);
	reqServlet.end();
	reqServlet.on('response', (resServlet) => {
		let respData = "";
		resServlet.on('data', (data) => {
			respData += data;
		});
		resServlet.on('end', () => {
			let obj = xmlToJson(respData);
			if(JSON.stringify(obj) != "{}") {
				if(pw == obj.engineer[0].password) {
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

handlers["/logs"] = (req, res) => {
	if(req.method == "POST") {
		let data = "";
		req.on('data', function(d) {
			data += d;
		});
		req.on('end', function() {
			let json = JSON.parse(data);
			let content = "alarm=" + json.alarm + "&site=" + json.site + 
				"&action=" + json.action + "&remarks=" + json.remarks + 
				"&engineer=" + json.engineer + "&date=" + json.date;
			const reqServlet = http.request(options[5]);
			reqServlet.write(content);
			reqServlet.end();
			res.writeHead(200, { "Content-Type" : "text/plain"});
			res.end("Received");
		});
	}
	if(req.method == "GET") {
		let split = req.url.split("?");
		if(split.length > 1) {
			let query = qs.parse(split[1]);
			let path = "/reportlogsapp/logs?";
			for(let k in query) {
				if(path != "/reportlogsapp/logs?") {
					path += "&"
				}
				path += k + "=" + query[k];
				
			}
			options[4].path = path;
			requestFromServlet(res, options[4]);
		}
		else {
			requestFromServlet(res, options[3]);
		}
	}
};

handlers["/sessionId"] = function(req, res) {
	doesSessionExist(sessionId, function(reply) {
		if(!reply) {
			res.writeHead(400, { "Content-Type" : "text/plain" });
			res.end("Session ID required. Please log in.");
		}
		else {
			client.get(sessionId, function(err, result) {
				res.writeHead(200, { "Content-Type" : "text/plain" });
				res.end(sessionId + " " + result);
			});
		}
	});
}

handlers["/engineers/username/"] = (req, res, username) => {
	options[4].path = "/reportlogsapp/engineers?username=" + username;
	requestFromServlet(res, options[4]);
};

handlers["/sites/id/"] = (req, res, id) => {
	options[4].path = "/reportlogsapp/sites?id=" + id;
	requestFromServlet(res, options[4]);
};

handlers["/alarms/id/"] = (req, res, id) => {
	options[4].path = "/reportlogsapp/alarms?id=" + id;
	requestFromServlet(res, options[4]);
};

function startSession(name, callback) {
	id = crypto.randomBytes(16).toString("hex");
	client.set(id, name, function() {
		callback(id);
	});
}

function doesSessionExist(sessionid, callback) {
	client.exists(sessionid, function(err, reply) {
		callback(reply);
	});
}

function requestFromServlet(res, option) {
	doesSessionExist(sessionId, function(reply) {
		if(!reply) {
			res.writeHead(400, { "Content-Type" : "text/plain" });
			res.end("Session ID required. Please log in.");
		}
		else {
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
	// console.log(respData);
	// return(xmlObject);
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
	if(!r.innerXML.match(".*<.*") && !r.innerXML.match(".*>.*")) {
		j[r.tagName] = r.innerXML;
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
	let requrl = req.url;
	let h = handlers[requrl.split("?")[0]];
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
