# Notification Management Service
## Project set-up:
The project can be launched both separately and together.

* To run together: you need to run the docker-compose file. it will set up postgreSQL, back-end service and ui-service containers itself.\
    ```docker compose up``` 
* To run separately: you need to  set up PostgreSQL and run  both services.\
    ```mvn spring-boot:run```

If you use Docker to run these are default settings for ports:
* PostgreSQL - :54321
* backend - :8080
* ui service - :8000

## Architecture
Main stack: Java 17, SpringBoot3, PostgreSQL, Thymeleaf

![Alt text](./ProjectDiagram.png)




