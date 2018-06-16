DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS sites;
DROP TABLE IF EXISTS engineers;
DROP TABLE IF EXISTS alarms;
CREATE TABLE sites (id VARCHAR(255) PRIMARY KEY, name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL);
CREATE TABLE engineers(id VARCHAR(255) PRIMARY KEY, firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL, age INTEGER NOT NULL,
	department VARCHAR(255) NOT NULL, username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL);
CREATE TABLE alarms(id VARCHAR(255) PRIMARY KEY, name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL, technology VARCHAR(255) NOT NULL);
CREATE TABLE logs(alarmid VARCHAR(255) NOT NULL, siteid VARCHAR(255) NOT NULL,
	action VARCHAR(255) NOT NULL, employeeid VARCHAR(255) NOT NULL,
	time DATETIME NOT NULL, FOREIGN KEY(alarmid) REFERENCES alarms(id),
	FOREIGN KEY(siteid) REFERENCES sites(id),
	FOREIGN KEY(employeeid) REFERENCES engineers(id));
INSERT INTO sites (id, name, address) VALUES ('MIN111', 'BAJADA', 'bajada');
INSERT INTO sites (id, name, address) VALUES ('MIN112', 'BUHANGIN', 'buhangin');
INSERT INTO engineers (id, firstName, lastName, age, department, username, password)
	VALUES ('2018-01-001', 'Allaine', 'Bautista', 25, 'WIRELINE', 'allaine', 'allainepw');
INSERT INTO engineers (id, firstName, lastName, age, department, username, password)
	VALUES ('2018-01-002', 'Jecelyn', 'Gamay', 28, 'WIRELESS', 'jecelyn', 'jecelynpw');
INSERT INTO engineers (id, firstName, lastName, age, department, username, password)
	VALUES ('2018-01-003', 'Louievic', 'Sancon', 24, 'TRANSMISSION', 'louievic', 'louievicpw');
INSERT INTO alarms (id, name, description, technology) VALUES
	('001', 'LTE Cell Down', 'One sector is down', '4G');
INSERT INTO alarms (id, name, description, technology) VALUES
	('002', 'No power', 'No power on site', 'MSAN');
INSERT INTO alarms (id, name, description, technology) VALUES
	('003', '2G Down', 'Whole 2G is down', '2G');
