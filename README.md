## Task Manager Service

### Description

A simple service that offers a json API for a task manager, with endpoint to 
- add a task `POST /api/task`
- get all tasks `GET /api/task`

### Example POST request

```
{
    "title": "Task 1",
    "description": "Task One",
    "finished": "false"
}
```

### Running the service

`$ ./mvnw spring-boot:run`