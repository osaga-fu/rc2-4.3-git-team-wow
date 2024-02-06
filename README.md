# 4.3. Git Ways of working

Each team must write the code in the `ToyController` and `CustomerController` classes to make the tests pass. 

You can run the tests by doing:

```
./mvnw test
```

These are the API methods that you must implement

| DONE | Method | Resource              | Details                                            |
| ---- | ------ | --------------------- | -------------------------------------------------- |
| No   | GET    | /api/v1/toys          | Returns a list of ToyResponse objects              |
| No   | GET    | /api/v1/toys/:id      | Returns a ToyResponse for the object with id `:id` |
| Olaya| POST   | /api/v1/toys          | Create a Toy                                       |
| No   | PUT    | /api/v1/toys/:id      | Edit the toy with id `:id`                         |
| No   | DELETE | /api/v1/toys          | Delete the toy with id `:id`                       |
| No   | GET    | /api/v1/customers     | Returns a list of ToyResponse objects              |
| No   | POST   | /api/v1/customers     | Create a Toy                                       |
| No   | PUT    | /api/v1/customers/:id | Edit the toy with id `:id`                         |
| No   | DELETE | /api/v1/customers     | Delete the toy with id `:id`                       |
