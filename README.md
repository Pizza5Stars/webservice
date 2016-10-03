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
| Endpoints              | Attributes         | Methods | Description                                             | Authorization |
|------------------------|--------------------|---------|---------------------------------------------------------|---------------|
| /customer              |                    | POST    | create user                                             |               |
| /auth/login            |                    | POST    | login                                                   |               |
| /auth/authenticate     |                    | GET     | authenticate via Json-Web-Token                         |               |
| /ingredients           |                    | GET     | get ingredients                                         |               |