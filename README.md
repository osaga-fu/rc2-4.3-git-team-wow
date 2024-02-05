# 4.3. Git Ways of working

Each team must write the code in the `ToyController` and `UserController` classes to make the tests pass. 

You can run the tests by doing:

```
./mvnw test
```

These are the API methods that you must implement

| Method | Resource              | Details                               |
| ------ | --------------------- | ------------------------------------- |
| GET    | /api/v1/toys          | Returns a list of ToyResponse objects |
| POST   | /api/v1/toys          | Create a Toy                          |
| PUT    | /api/v1/toys/:id      | Edit the toy with id `:id`            |
| DELETE | /api/v1/toys          | Delete the toy with id `:id`          |
| GET    | /api/v1/customers     | Returns a list of ToyResponse objects |
| POST   | /api/v1/customers     | Create a Toy                          |
| PUT    | /api/v1/customers/:id | Edit the toy with id `:id`            |
| DELETE | /api/v1/customers     | Delete the toy with id `:id`          |
