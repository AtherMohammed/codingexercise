# codingexercise

Implementation requirements:
----------------------------

Create a drop-wizard service with the following:


A User rest Resource that allows clients to create, read, update, delete a user or list all users. 

You can use JDBI3 with a database of your choice but it's also fine to just use a Map<String, User> in memory to keep track of users by their ids. 

Make use of logging and metrics in the resource. 

Write unit tests for the service. 

Use the jacoco-maven-plugin for unit test coverage. Or whatever, you can also use gradle if you prefer that to maven.

See: https://www.dropwizard.io/1.3.5/docs/getting-started.html

The user JSON can just be id, first name, last name, zip code, and email address. Extra points if the User class is immutable.




Implementation guide :
----------------------

All efforts have been made to implement everything as mentioned in the above guidelines.

clone the repository from : 

https://github.com/AtherMohammed/codingexercise.git


The code is written using java 8.

Install maven on the local machine to execute this project.

after check out go the project root directory and execute <mvn clean package> 

The above maven command will do a clean compile and run the test cases generates the jacoco reports and build the executable jars

run the below command from the target directory that is created by the above command (codingexercise/target)
java -jar codingexercise-1.0.jar server config.yml (jetty is baked in which runs the service)


The database used is HSQL and used as in memory.

Once the server is started , Run the below command to perform the create operation

curl -H "Content-Type:application/json" -X POST "http://localhost:8080/profiles/users/" -d '{"id": "", "firstName": "AtherHussain", "lastName": "mohammed", "zipcode": "97078", "emailaddress": "hussainmoha@gmail.com"}' -i

Idempotency is handled to avoid issues with duplicate request submission.

Retrieve the stored user using the get api:

curl -H "Content-Type:application/json" -X GET "http://localhost:8080/profiles/users/<userId>" -i

Update operation can be performed using below command
curl -H "Content-Type:application/json" -X PUT "http://localhost:8080/profiles/users/id" -d '{"id": "1", "firstName": "AtherHussain", "lastName": "mohammedTest", "zipcode": "97078", "emailaddress": "hussainmoha@gmail.com"}' -i


Delete can be performed by below rest call :
curl -H "Content-Type:application/json" -X DELETE "http://localhost:8080/profiles/users/<userid>" -i


Metrics
--------

There are annotations used on the GET endpoint on UserResource.java to record the metrics that can be used to send the report to the 
graphite server if available. Modify the config.yml file  to point the graphite server port and address.
Currently you may see errors sending the report to graphite server as the service is expecting the graphite server hosted at localhost:2003 as mentioned in config.yml.



Artifacts created :
------------------

UserDao for DB operations
User object used as db entity.
UserResource.java is the service that has all the CRUD data end points.
User.json sample json document used for testing.