D## SQL数据库设计

> DB_TYPE = SQLite  
> DB_NAME = mailplus  
> DB_PATH = ./.sqlite/mailplus.db

### User
		CREATE TABLE 'user' (
			'id' INTEGER PRIMARY KEY AUTOINCREMENT,
			nickname varchar(32) DEFAULT '',
			email varchar(64) NOT NULL, /* 'whole email address'*/
			password varchar(32) NOT NULL /* 'base64 encrypted'*/
		);
		
### Email

		CREATE TABLE 'email' (
			'id' INTEGER PRIMARY KEY AUTOINCREMENT,
			'mail_id' int DEFAULT -1,
			'from' varchar(32) NOT NULL, /*sender*/
			'to' text, /*receivers, json of List<String>*/
			'cc' text, /*cc, json of List<String>*/
			'subject' varchar(512),
			'content' text,
			'attachment' text,
			'status' int NOT NULL DEFAULT 0,
				/*'0:unread, 1:readed, 2:writing, 
					3:draft, 4:sending, 5:sended'*/
			'signature' varchar(64),
			'timestamp' TIMESTAMP(8)
		);		
	
### RemoteHosts

		CREATE TABLE 'remote_hosts' (
			'id' INTEGER PRIMARY KEY AUTOINCREMENT,
			domain varchar(32) NOT NULL, /*'like 163.com, qq.com'*/
			smtp varchar(32) NOT NULL, /* 'smtp host'*/
			pop3 varchar(32) DEFAULT NULL, /*'pop3 host'*/
			imap varchar(32) DEFAULT NULL /*'imap host'*/
		);
	