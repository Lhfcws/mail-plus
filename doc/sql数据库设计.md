## SQL数据库设计

> DB_TYPE = SQLite  
> DB_NAME = mailplus  
> DB_PATH = ./.sqlite/mailplus.db

### User
		CREATE TABLE 'user' (
			id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
			nickname varchar(32) DEFAULT '',
			email varchar(64) NOT NULL UINQUE KEY COMMENT 'whole email address',
			password varchar(32) NOT NULL COMMENT 'base64 encrypted'
		) ENGINE=innodb DEFAULT CHARSET=utf-8;
		
### Email

		CREATE TABLE 'email' (
			id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
			from varchar(32) NOT NULL COMMENT 'sender',
			to text COMMENT 'receivers, json of List<String>',
			cc text COMMENT 'cc, json of List<String>',
			subject varchar(512),
			content text,
			attachment text,
			timestamp TIMESTAMP(8)
		) ENGINE=innodb DEFAULT CHARSET=utf-8;
		
### EmailStatus

		CREATE TABLE 'email_status' (
			id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
			email_id int(11) NOT NULL UNIQUE KEY,
			status int(11) NOT NULL DEFAULT 0 
				COMMENT '0:unread, 1:readed, 2:writing, 
					3:draft, 4:sending, 5:sended'
		) ENGINE=innodb DEFAULT CHARSET=utf-8;
	
### RemoteHosts

		CREATE TABLE 'remote_hosts' (
			id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
			domain varchar(32) NOT NULL UNIQUE KEY COMMENT 'like 163.com, qq.com'
			smtp varchar(32) NOT NULL UNIQUE KEY COMMENT 'smtp host',
			pop3 varchar(32) DEFAULT NULL COMMENT 'pop3 host',
			imap varchar(32) DEFAULT NULL COMMENT 'imap host'
		) ENGINE=innodb DEFAULT CHARSET=utf-8;
	