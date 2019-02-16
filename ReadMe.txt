====== README INFO ======
P3 Project: Online package management

By: ds303e18 @ Aalborg University 
=========================


===== STRUCTURE =====
~/: Here is our report and this file
BACKEND: Backend system in Spring Boot.
FRONTEND: Graphic interface in REACT (Javascript, HTML & CSS)
GRADLE & LIB: Libraries for build tools
=========================


===== HOW TO RUN PROJECT =====

Frontend, backend and database (MongoDB) need to be running simultaniously. 
Currently the project is set up to only run on localhost.

/////////////////////////////////////

[ Frontend: ]
Go to ~/Frontend in a terminal
Run “npm install”
Run “npm start” and our web page should appear in a browser

/////////////////////////////////////

[ Backend (using IntelliJ): ]
Go to File -> Project Structure -> Libaries 
Press “+”
Select both .jar files in  ~/Dependencies
Add these to all
Open “MockDataCreator” in backend project (~/(our package)/SystemTest/MockDataCreator)
Run mongod in terminal and execute “makeData” on line 93.

Run “P3WarehouseApplication” in the root of our package in ~/src

/////////////////////////////////////

[ Getting the mail service to work: ]
	Insert “mail.txt” file on the running computer Desktop. Provide a Gmail with password in the following format: 			
	example@gmail.com;password 
	
/////////////////////////////////////

Merry christmas and happy new years!,
Group ds303e18
