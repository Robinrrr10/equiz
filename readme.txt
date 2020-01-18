
Technology used:
----------------
REST Application using spring boot


Repository:
----------
https://github.com/Robinrrr10/equiz.git


Pre-requirement:
---------------
Java 8 or more
Maven
Please import dbInstall.sql in your database. You can find dbInstall.sql in root of the project


Below is the property file used:
application.properties - This file is in src/main/resources
----------------------
server.port=8080                                      - Give port number here. spring boot will run on this port number
server.servlet.context-path=/equiz
application.equiz.mysql.class=com.mysql.jdbc.Driver
application.equiz.mysql.url=jdbc:mysql://localhost:3306/equiz          - Give mysql host name here. Here hostname is localhost and database name is equiz
application.equiz.mysql.user=root                                      - mysql username
application.equiz.mysql.password=root                                  - mysql user password



To run the jar:
--------------
java -jar equiz-0.0.1-SNAPSHOT.jar


If the project repository is in your local we can also use below maven command to run the application
-----------------------------------------------------------------------------------------------------
mvn spring-boot:run





Below are the API which can be used for backend
-----------------------------------------------


1. Health Check API:

url: http://localhost:8080/equiz/api/questions
method: GET



2. To add questions:

url: http://localhost:8080/equiz/api/questions/add
method: POST
payload:
 [
        {
            "question": "What is java?",
            "level": "HARD",
            "tag": "tag6"
        },
         {
            "question": "Mysql user name and passwoed means?",
            "level": "HARD",
            "tag": "tag6"
        }
    ]




3. To add single questions:

url: http://localhost:8080/equiz/api/questions/addQuestion?questionEntry=What is java?|MEDIUM|tag5
method: POST



4. To add list of questions by uploading files:

url: http://localhost:8080/equiz/api/questions/uploadQuestions
method: POST
multipart:
name: file      
sampleInputFile.txt



5. To get all questions:

url: http://localhost:8080/equiz/api/questions/all
method: GET



6. To get available questions(questions which are not used):

url: http://localhost:8080/equiz/api/questions/available
method: GET



7. To get quiz questions based on all given criteria:

url: http://localhost:8080/equiz/api/questions/quizQuestions
method: PUT



8. To get quiz questions(only 10 questions):

url: http://localhost:8080/equiz/api/questions/quizQuestionsTen
method: PUT



9. To get quiz questions with each tag one question:

url: http://localhost:8080/equiz/api/questions/quizQuestionsBasedOnTag
method: PUT



10. To get quiz questions with each difficulty level two questions:

url: http://localhost:8080/equiz/api/questions/quizQuestionsBasedOnLevel
method: PUT




