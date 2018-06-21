DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS sites;
DROP TABLE IF EXISTS alarms;
DROP TABLE IF EXISTS engineers;
CREATE TABLE sites (id VARCHAR(255) PRIMARY KEY, name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL);
CREATE TABLE engineers(username VARCHAR(255) NOT NULL PRIMARY KEY,
	id VARCHAR(255), firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL, age INTEGER NOT NULL,
	department VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, isadmin BOOLEAN NOT NULL);
CREATE TABLE alarms(id VARCHAR(255) PRIMARY KEY, name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL, technology VARCHAR(255) NOT NULL);
CREATE TABLE logs(id INTEGER PRIMARY KEY AUTO_INCREMENT, alarmid VARCHAR(255) NOT NULL, siteid VARCHAR(255) NOT NULL,
	action VARCHAR(255) NOT NULL, remarks VARCHAR(255) NOT NULL, engineer VARCHAR(255) NOT NULL,
	time DATETIME NOT NULL, FOREIGN KEY(alarmid) REFERENCES alarms(id),
	FOREIGN KEY(siteid) REFERENCES sites(id),
	FOREIGN KEY(engineer) REFERENCES engineers(username));
INSERT INTO sites ( id, name, address ) VALUES ('MIN111', 'ALABEL', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN112', 'BACOLODKALAWI', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN113', 'BAGANGA', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN114', 'BAGUMBAYAN', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN115', 'BALINGASAG', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN116', 'BALOI', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN117', 'BAYUGAN', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN118', 'BISLIG', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN119', 'BONGAO', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN120', 'BUENAVISTA', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN121', 'BULUAN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN122', 'BUTUAN', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN123', 'CABADBARAN', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN124', 'CAGAYANDEORO', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN125', 'CARMEN', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN126', 'CATEEL', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN127', 'COMPOSTELA', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN128', 'COTABATO', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN129', 'DAPITAN', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN130', 'DATUODINSINSUAT', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN131', 'DAVAO', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN132', 'DIGOS', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN133', 'DIPOLOG', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN134', 'DITSAANRAMAIN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN135', 'ELSALVADOR', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN136', 'GENERALSANTOS', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN137', 'GINGOOG', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN138', 'HAGONOY', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN139', 'ILIGAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN140', 'INDANAN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN141', 'IPIL', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN142', 'ISABELA', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN143', 'ISULAN', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN144', 'JASAAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN145', 'JOLO', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN146', 'KABACAN', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN147', 'KALILANGAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN148', 'KIBLAWAN', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN149', 'KIDAPAWAN', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN150', 'KORONADAL', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN151', 'LAGUINDINGAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN152', 'LALA', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN153', 'LAMBAYONG', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN154', 'LAMITAN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN155', 'LUPON', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN156', 'MACO', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN157', 'MALAYBALAY', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN158', 'MALUSO', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN159', 'MAMBAJAO', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN160', 'MANAY', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN161', 'MANOLOFORTICH', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN162', 'MARAMAG', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN163', 'MARAWI', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN164', 'MASIU', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN165', 'MATI', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN166', 'MIDSAYAP', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN167', 'MLANG', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN168', 'MOLAVE', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN169', 'MONKAYO', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN170', 'OLDPANAMAO', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN171', 'OPOL', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN172', 'OROQUIETA', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN173', 'OZAMIS', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN174', 'PAGADIAN', 'Region IX');
INSERT INTO sites ( id, name, address ) VALUES ('MIN175', 'PANABO', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN176', 'PANGANTUCAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN177', 'PANTUKAN', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN178', 'PARANG', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN179', 'PATIKUL', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN180', 'PIKIT', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN181', 'PLARIDEL', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN182', 'POLOMOLOK', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN183', 'PRESIDENTROXAS', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN184', 'PROSPERIDAD', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN185', 'SAMAL', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN186', 'SANFRANCISCO', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN187', 'SANTACRUZ', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN188', 'SIASI', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN189', 'SITANGKAI', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN190', 'SOUTHUBIAN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN191', 'SULTANKUDARAT', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN192', 'SULTANNAGADIMAPORO', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN193', 'SURALLAH', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN194', 'SURIGAO', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN195', 'TACURONG', 'Region XII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN196', 'TAGOLOAN', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN197', 'TAGUM', 'Region XI');
INSERT INTO sites ( id, name, address ) VALUES ('MIN198', 'TAMPARAN', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN199', 'TANDAG', 'Region XIII');
INSERT INTO sites ( id, name, address ) VALUES ('MIN200', 'TANGUB', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN201', 'TUGAYA', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN202', 'VALENCIA', 'Region X');
INSERT INTO sites ( id, name, address ) VALUES ('MIN203', 'WAO', 'Region ARMM');
INSERT INTO sites ( id, name, address ) VALUES ('MIN204', 'ZAMBOANGA', 'Region IX');
INSERT INTO engineers (id, firstName, lastName, age, department, username, password, isadmin)
	VALUES ('2018-01-001', 'Allaine', 'Bautista', 25, 'WIRELINE', 'allaine', 'allainepw', TRUE);
INSERT INTO engineers (id, firstName, lastName, age, department, username, password, isadmin)
	VALUES ('2018-01-002', 'Jecelyn', 'Gamay', 28, 'WIRELESS', 'jecelyn', 'jecelynpw', FALSE);
INSERT INTO engineers (id, firstName, lastName, age, department, username, password, isadmin)
	VALUES ('2018-01-003', 'Louievic', 'Sancon', 24, 'TRANSMISSION', 'louievic', 'louievicpw', FALSE);
INSERT INTO alarms (id, name, description, technology) VALUES
	('001', 'LTE Cell Down', 'One sector is down', '4G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('002', 'No power', 'No power on site', 'MSAN');
INSERT INTO alarms (id, name, description, technology) VALUES
	('003', '3G Down', 'Whole 3G is down', '3G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('004', 'LTE Cell Degraded ', 'Sector is degraded', '4G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('005', 'Antenna Port Overdrive ', 'High VSWR', '4G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('006', 'Fan Stalled', 'Hardware problem', '2G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('007', 'No Contact to Board', 'No contact to board', '4G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('008', 'Fuse Blown Alarm', 'Fuse Blown', 'MSAN');
INSERT INTO alarms (id, name, description, technology) VALUES
	('009', 'Board High Temperature', 'Board has high temperature', '3G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('010', 'VSWR Threshold Crossed', 'High VSWR', '3G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('011', 'Cell Out of Service', 'One sector is down', '2G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('012', 'Low DC Voltage', 'Low DC Voltage', 'MSAN');
INSERT INTO logs ( id, alarmid, siteid, action, remarks, engineer, time)
	VALUES ('1', '002', 'MIN148', ' none', ' No scheduled power interruption ', 'allaine', ' 2018-06-21 14:27:19');
INSERT INTO logs ( id, alarmid, siteid, action, remarks, engineer, time)
	VALUES ('2', '001', 'MIN155', ' Conducted soft reset', ' Alarm cleared', 'louievic', ' 2018-06-21 14:27:55');
INSERT INTO logs ( id, alarmid, siteid, action, remarks, engineer, time)
	VALUES ('3', '001', 'MIN194', ' Conducted soft reset', ' To no avail. For FT Issuance', 'jecelyn', ' 2018-06-21 14:28:14');
INSERT INTO logs ( id, alarmid, siteid, action, remarks, engineer, time)
	VALUES ('4', '003', 'MIN177', ' Performed shut/unshut. ', ' Site restored', 'louievic', ' 2018-06-21 14:29:09');
INSERT INTO logs ( id, alarmid, siteid, action, remarks, engineer, time)
	VALUES ('5', '001', 'MIN131', ' Power reset', ' Alarm cleared', 'allaine', ' 2018-06-21 14:30:01');
