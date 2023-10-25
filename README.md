# Choicemaker

| Implemented By  |  Sachith Senarathne |
| :------------ | :------------ |
|  version | v1.0.0  |
|  Date | 25.10.2023  |

### Introduction

Choicemaker is here to simplify the process and make dining decisions an effortless and enjoyable experience. Choicemaker is an application that  combines React-based frontend and a Spring Boot backend, equipped with both RESTful APIs and WebSocket communication. With Choicemaker, it will facilitate a more efficient and collaborative approach to choosing the perfect restaurant for your day.

#### Key Features:

1. Session Creation: Any user can initiate a dining session, taking the lead in selecting where to eat. The creator has the privilege to add other users to the session, establishing a central hub for the decision-making process.
2. User Collaboration: Choicemaker has the ability to bring people together. Users can join an existing session (undergoing the fact that the user should be invited by the session creator), contributing their opinions and preferences to the decision-making process.
3. Real-time Updates: Choicemaker employs WebSockets to keep all users in the session instantly informed. When a participant submits their restaurant choice, the rest of the group receives immediate notifications, ensuring everyone is on the same page throughout the process.
4. Session Conclusion: The session creator holds the key to finalizing the choice. When they decide the time is right, the creator can end the session, and this action will trigger the final selection process.
5. Randomized Selection: To decision-making, Choicemaker randomly selects the restaurant from the submitted options. Selected restaurant will be notified by all the users in the session.

#### Dev Environment and Local Setup

##### PreRequisite

- Java 17
- Maven 3
- MySql
- Node and npm
- Git

#### Backend API
Note: Please configure the db connection properly by adding below properties as per the running system db before starting the application

`spring.datasource.url=jdbc:mysql://localhost:3306/choicemaker`<br>
`spring.datasource.username=username`<br>
`spring.datasource.password=password`<br>

**Step 01:**<br>
Clone the project<br>
`git clone https://github.com/sachithsen/choicemaker`

**Step 02:**<br>
Go to the directory<br>
`cd choicemaker`

**Step 03:**<br>
Install maven dependencies<br>
`mvn clean install`

**Step 04:**<br>
Run the project<br>
`mvn spring-boot:run`

You can verify the application status using actuator health endpoints.<br>
[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health "http://localhost:8080/actuator/health")<br>
Which will give `{"status":"UP"}` as a result.

#### Froned App

Frontend app is inside the same repo and version controlled as a mono repo

**Step 01:**<br>
Go to the directory<br>
`cd choicemaker/frontend`

**Step 02:**<br>
Install node modules<br>
`npm install`

**Step 03:**<br>
Run the project<br>
`npm start`

You will be able to access the frontend application using the URL below.<br>
[http://localhost:3000](http://localhost:3000 "http://localhost:3000")<br>

#### API Documentation

You will be able access Open API documentation once the backend application is up and running and try the endpoints as well.<br>
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html "http://localhost:8080/swagger-ui/index.html")<br>

#### AOP Logging for Debugging

Log levels have been set to debug the application and logging has been introduced using Spring AOP to improve the traceability.

`logging.level.org.springframework.web=INFO` <br>
`logging.level.org.springframework.security=INFO` <br>
`logging.level.org.hibernate=ERROR` <br>
`logging.level.org.sachith.choicemaker=DEBUG`

Further details and user journeys can be found in ChoicemakerGuide.pdf of this repo or https://docs.google.com/document/d/10ZujEasn8HIemh6x3yBXibKCxUR-LRqhztYKth9m40I/edit?usp=sharing


