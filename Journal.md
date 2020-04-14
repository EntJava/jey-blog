# Journal
## Week 9
### Mar 23 , 2020
#### Completed tasks and Time:
Jeanne:  

| Tasks completed | Hours | Notes |
|------|------|-------|
|Meeting to brainstorming  a team project| 1 ||
|Researched possible REST API project | 2 | There are many REST API but some of them are hard to use due to poor or counter intuitive documentation|

Yia:

| Tasks completed | Hours | Notes |
|------|------|-------|
|Created Project|||
||||
||||

Estefanie: 

| Tasks completed | Hours | Notes |
|------|------|-------|
|Created Repository and push to Github | 1 hour||
|Research REST APIS| 2 hours||
||||
## Week 10
### Mar 30 , 2020
### Completed tasks and Time:
Jeanne: 

| Tasks completed | Hours | Notes |
|------|------|-------|
|Created Project structure  and hibernate configuration files, db, hibernate, log and test dependencies| 2.5| Directories created as package were not working at first due to presence of - in package name|
|Added Problem statement, Project plan and .gitignore| 2.75 ||
|Created database(db)s both test and development, table and add user with access to db. Configured IntelliJ to connect to db and created Post entity and mapped it to db | 3 ||
|Added SessionFactory Provider and created Post Dao and  with get all post and add new post functions |||
|Created Application class that adds resources, add Blg Resource with Get pots| 1 ||
|Added functionality of Get all post and Create post methods |4 ||
|Researched how to integrate swager in Java project and try  to integrate  | 7.5 | It was not easy since there are not many resources that do not use Spring|

Estefanie
| Tasks completed | Hours | Notes |
|------|------|-------|
|Implement PUT request| 4 hour| Required some research on how PUT requests works and downloaded Insomnia to aid with the testing|
||||
## Week 11
### Apr 6, 2020
### Completed tasks:
### Completed tasks and Time:

Jeanne: 

| Tasks completed | Hours | Notes |
|------|------|-------|
|Implemented Swagger jaxrs and Swagger UI to added documentation and endpoints for REST API| 9 ||
| Researched and added Jersey Test Framework with get All posts unit Tests| 5 ||
|Added Endpoints and Post attributes to the readme| .5||
|Meeting and competed log| 2.25 ||

## Week 12
#### Apr 13, 2020
#### Completed tasks:

Estefanie
| Tasks completed | Hours | Notes |
|------|------|-------|
|Modify PUT request add swagger documentation that Jeanne implemented| 1 hour| |
| Add an xml endpoint for a put request | 2 hours| I kept having configuration issues  in my pom.xml file that were giving me a hard time|
| Add getByColumnName and unit test to GenericDao | 1 hours| |
| Added xml endpoints for all other requests except POST | 5 hours| I had a hard time getting the lists of entities to return in the Response object. I ended up wrapping the list in an generic entity|
| Deploy to AWS | 3 hours| I was able to deploy easily but had some configuration issues. I had to reconfigure swagger to work on AWS and was getting exceptions when trying to access the REST endpoints. After a bunch of running around all I ended up needing to do was restart tomcat|
| Add some error handling to the endpoints | 1 hours| |