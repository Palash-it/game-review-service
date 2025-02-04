### Project Requirements
## Game Love Service
In this assignment, we look forward to test your skills in software development. Please, be pragmatic and avoid over engineering your solution.

## Problem Statement:
We want you to implement a game love service that keeps track of the games that the player loves.

### Design a REST API with the following:
## It should be possible to create a new entry by feeding it with the following: [Here is the endpoint /api/v1/player-game-interaction/love-game]
- The player that loved the game.
- The game it loved.
## It should be possible to unlove games. [Here is the endpoint /api/v1/player-game-interaction/unlove-game]
## It should be possible to fetch all games a player have loved. [Here is the endpoint /api/v1/player-game-interaction/{playerId}/loved-games]
## It should be possible to fetch the most loved games. [Here is the endpoint /api/v1/player-game-interaction/most-loved-games?limit=10]
- The list should contain the x top loved games, where x should be possible to define in every request.
- Each item in this list should contain:
- The game identifier.
- Number of loves the game has.
## It should be possible to fetch all games. [Here is the endpoint /api/v1/games?pageNo=0&pageSize=50]
Deliverables:
Your submission should contain a Java service based Maven project solution. Avoid sending large attachments in your submission so do a maven clean before submission.

In this case, we recommend you to make use of spring boot as a starting point for your application.

In addition, in order for us to test your solution; make use of an embedded database like H2 or Sqlite, using a local file database scheme with your working solution.

We look forward to your solution and appreciate your time applying with us!

### Used Tech Stack
- Java21
- Spring boot 3.3.1
- H2 In memory database
- Junit 5
- Maven 3.5.0
- Swagger for API documentation

### How to Run Project
This application will be running on port 8080 and is using H2 in Memory database which can be accessed using bellow link 
- http://localhost:8080/h2-console
- username: sa
- password: 

## In project classpath there is schema.sql and data.sql database file. 

### Maven Commands

# To start the application here are some maven commands
- [install dependency] mvn clean install
- [start] mvn spring-boot:run
- [test] mvn clean test

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### API Documentations
- http://localhost:8080/swagger-ui/index.html


### Run using docker
## Build the docker image from dockerfile and run the container
- docker build -t game-review-service .
- docker run -d -p 8080:8080 game-review-service

