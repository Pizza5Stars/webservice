# pizza5starts webservice

##Prerequisites:
- install Maven and Java
- make sure Maven and Java System environment variables are set
- run db_pizza5stars.sql on database server
- create user for db and grant privileges
- configure user, password and database-url in config.yaml

##Build and run (via Terminal):
- mvn package
- java -jar target/webservice-1.0-SNAPSHOT.jar server config.yaml

## Endpoints
| Endpoints              | Attributes         | Methods | Description                                                 | Authorization |
|------------------------|--------------------|---------|-------------------------------------------------------------|---------------|
| /customer/address      |                    | POST    | add address to logged in customer                           |       X       |
| /customer/addresses    |                    | GET     | get addresses of  logged in customer                        |       X       |
| /customer/order        |                    | POST    | create order for logged in customer                         |       X       |
| /customer/receipts     |                    | GET     | get receipts of logged in customer                          |       X       |
| /customer/pizza        |                    | POST    | add new pizza to logged in customer                         |       X       |
| /customer/pizza        |                    | PUT     | update pizza of  logged in customer                         |       X       |
| /customer/pizzas       |                    | GET     | get pizzas of loggedin customer                             |       X       |
| /customer/pizzas/:id   |                    | DELETE  | delete pizza of logged in customer with id                  |       X       |
| /customer              |                    | POST    | create customer                                             |               |
| /auth/login            |                    | POST    | login                                                       |               |
| /auth/authenticate     |                    | GET     | authenticate via Json-Web-Token                             |               |
| /ingredients           |                    | GET     | get ingredients                                             |               |
| /order                 |                    | POST    | create order without registered customer                    |               |
| /pizza/suggestions     |                    | GET     | get suggested pizzas                                        |               |
| /pizza/sizes           |                    | GET     | get possible sizes of pizza                                 |               |
| /pizza                 |                    | POST    | create pizza without registered customer                    |               |
| /pizza                 | ?ids=1&ids=2&ids=3 | GET     | get multiple pizzas by ids (to get pizzas without customer) |               |
| /address               |                    | POST    | create address without registered customer                  |               |