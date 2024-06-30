#!/bin/bash

# Compile the project
mvn clean install

# Start Player1 server
java -cp target/my-maven-project-1.jar Player Player1 8080 &

# Start Player2 client
java -cp target/my-maven-project-1.jar PlayerClient Player2 localhost 8080


# java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8080 -cp target/my-maven-project-1.jar Player Player1 8080 &

# java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8081 -cp target/my-maven-project-1.jar PlayerClient Player2 localhost 8081