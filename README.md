# Java-Chat
A LAN based chat application built with Java Sockets with MySQL database for file transfer capabilities.

Usage Instructions:

1. This application uses MySQL for the file transfer module. Therefore, ensure that MySQL is installed on your system. Run the following queries:

create database connect;
use connect;
create table files(name varchar(30), file longblob, path varchar(200));

and you are good to go.

2. Run the server application first. The server will then wait for clients to connect. Then, run the clients.

3. The rest is pretty straightforward.Use attach to send a file across the network and download to retrieve a file from the MySQL database.
