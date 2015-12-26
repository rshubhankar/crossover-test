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

