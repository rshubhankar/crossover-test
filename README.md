# crossover-test
This is a crossover assignment. It is a "Online Test Exam Maker" web application.

This is a simple application with below mentioned features:
Test exam pages: You should create three pages as described below (do not waste time in web design for those pages):-

* Description page: this is the home page which describe what the test exam is about (description should be read from database). Candidate should insert his/her credentials as username and password.

* Question page: this page shows a single question and its multiple choices. Candidate can switch to another question by selecting question number from a combo box and clicking on go button. user can submit the answer and go to next question. Candidate can end the exam and go to third page to confirm finishing.

* End exam page: final submission of exam. you should notify the candidate if he/r did not answer any question with an appropriate message.

Automatic candidate grading after final exam submission. Save candidate grade result into database.

You can populate the database with information related to tests, users and questions but you have to provide all needed scripts in your assignment delivery (including insertions of test data) .

View a front-end timer in the question page that count down by second.

Create back-end timer callback service that check if candidate passed the exam duration, this service should be invoked by front end every 2 seconds. in front end; exam should be end with an appropriate message if test duration passed.

Please read the following steps to run the scraper

# Pre-requisite installations on Windows #

1. Maven ( Maven should be added to path)
2. Java 8 (Java should be added to the path)
3. MySQL (5.5 or higher version should be installed)
4. Apache Tomcat 8

In this extracted zip file, you will find the maven project.

## Connect to MySQL server ##

Connect to MySQL server using any MySQL client or Workbench and create a database schema by running the sql script: 
```
create_ddl.sql
```

Also, run the sql script:
```
create_data.sql
```
to populate data in the tables.

Set the following properties in Exam-web.properties with appropriate values.

```
jdbcUrl=jdbc:mysql://localhost:3306/co-test
jdbcUser=root
jdbcPassword=root
jdbcDriver=com.mysql.jdbc.Driver
```

## Compile the code and make it executable ##


Execute the following command 

```
<path to repository>/crossover-test> mvn clean install
```

This will download the required dependencies and may take a while, compile the code, run the tests and build the application
Note if the build fails fails, it may be for various reasons as follows,

1 : Network issue > Maven needs proxy settings to download the dependency jars if behind a proxy.

2 : Maven repository not set > we need to set the maven home and maven repository home where the maven downloads and saves the dependency jars

3 : Java 8 not installed or not in the Path variable > pom.xml has specification to run the application on Java 8, hence it should installed.

Once mvn clean install executes successfully, go in the target directory, copy the Exam.war file to the location:
```
<Tomcat directory>/webapps/
```

Also, copy the Exam-web.properties file to the location:
```
<Tomcat directory>/bin/
```

Start Tomcat by executing the startUp.bat file in bin folder of <Tomcat Directory>.

## Verify the execution ##
Assuming tomcat is running on default port, open the below link:
```
http://localhost:8080/Exam
```

This should open the login page. Enter any username and password. It will log in. Click on the Start button to start the test.

##### NOTE: For some reason, if you are seeing an Error instead of login page, then check if there exists a record in the Exam table.#####

After completion of the test your result will be displayed.
