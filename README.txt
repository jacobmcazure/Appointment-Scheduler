WGU C195 Final Project for SOFTWARE II

Author: Jacob McEwen
Contact Information: jmcewe8@wgu.edu
Application version: 1.01
Date: 06/22/2023

Purpose
This application serves as a scheduling desktop application for a global consulting organization 
that is capable of managing customers, their appointments, and contacts related to the organization

Dependencies
IntelliJ Community 2022.2.5
Java SE 19
JavaFX-SDK-17.0.2
mysql-connector-java-8.1.33

Directions for how to run the program
The application first presents a login screen where the user will have to enter a valid username 
and password according to the database if they want to access the rest of the program. From there,
the user is taken to a main menu screen, containing buttons to navigate to a screen containing 
either a list of customers, a list of appointments, or a collection of reports.
The appointments screen has a table of all appointments that can be sorted by all, montly, and weekly
using radio buttons. You can also add, update or delete appointments here.
The customers screen has a table of all customers. You can also add, update, or delete customers here.
The reports screen contains a total of 3 reports. The first report allows the user to see a count of
how many appointments of a certain critera(by month and type). The second report allows the user to
select a contact and view all of that contact's appointments. The third report is described below, but
it essentially allows the user to see how many customers have been deleted in the current session.

Additional Report
The additional report tracks the total number of customers that have been deleted 
in the current session. It is done by incrementing a value everytime a customer is deleted from 
the database.