#!/bin/bash

# 1. Clean and build the Spring Boot application
echo "Starting Clean Install..."
./mvnw clean install

# 2. Print 10 empty lines
printf '\n%.0s' {1..10}

# 3. Print 10 dotted lines
for i in {1..10}
do
    echo ".................................................................."
done

# 4. Run the application
echo "Starting Spring Boot App..."
./mvnw spring-boot:run
