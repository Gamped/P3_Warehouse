====== README INFO ======
P3 Project: Online package management

By: ds303e18 @ AAU 
=========================


===== STRUCTURE OF FOLDER =====
~/: Here is our report and this file
P3_WAREHOUSE: The code
BACKEND: Our backend Java code using Spring.
DEPENDENCIES: The .jar files needed for mail functionality
FRONTEND: The webpage GUI using REACT (Javascript, HTML & CSS)
GRADLE & LIB: Libraries needed for backend
=========================


===== HOW TO RUN PROJECT =====

Frontend, backend and database need to all be running at the same time. 
Currently the project is setup to run locally on localhost.

/////////////////////////////////////

[ Frontend: ]
Install Node.js
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
Run “makeData” on line 93 WHILE BACKEND IS RUNNING
You now have mock-data for the backend.
Run “P3WarehouseApplication” in the root of our package in ~/src
This will run our backend

/////////////////////////////////////

[ Getting the mail service to work: ]
	In interest of not sharing login-credentials to a gmail account, we have made the code look for a file called “mail.txt” on the desktop page of the computer. This file has to have the following format: example@gmail.com;password ,where the gmail account has to have allowed access for “less secure apps”. We will also showcase this during our exam.

/////////////////////////////////////

[ Database: ]
Install MongoDB https://www.mongodb.com/
Once installed run the service (potentially Google how to set up, as it differs a lot depending on your operating system)

=========================

If you run into problems with getting our project to run, then please send us an email, and we’ll try to help with getting the project up and running :)

Merry christmas and happy new years!,
Group ds303e18
