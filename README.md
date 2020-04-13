# Estefanie, Jeanne, Yia's Team Project Jey-Blog

### Problem Statement

Majority of websites exists there have Blog in addition to the main features of a web application.
Some of the reasons of having blog are to increase the traffic on a given website, 
getting users feedback about what they like and issues etc... 

It is not easy to find a free and simple blog post REST API for developer to consume and add this feature on web applications.
 The jey-blog application will provide simple CRUD blog-post REST API endpoints to developers to consume while adding blog. 
### Supported format for blog post REST API
* application/json

### Supported Blog post Actions and REST API endpoints 
    * GET: 
            Get All posts : /rest-api/posts
            Get by postId:  /rest-api/posts/{postId}
            get by title: /rest-api/posts/{title} 
    * POST:
            Create a new blog post: /rest-api/posts
    * PUT:
            Update blog post: /rest-api/posts/{postId} 
    * DELETE:
            Remove a blog post: /rest-api/posts/{postId}
    
### The Attributes of Blog Post Resource

    postId
    title
    author
    category 
    pubDate
    description
    createdAt
    updatedAt
### Project Technologies/Techniques 
* JAXRS
* Jackson
* Jersey
* Swagger jaxrs and Swagger UI
* Jersey Test Framework
##  Database
* MySQL database
* Stores posts
## Object Relational Mapping (ORM)  Framework
* Hibernate 5
## Dependency Management
* Maven
## Hosting
AWS
# Independent Research Topic/s
* Host a group project on AWS
* Lombok project
* Swagger jaxrs and Swagger UI
* Jersey Test Framework
## Topics covered in class
* IDE: IntelliJ IDEA

### Design
### [Project Plan](ProjectPlan.md)
### [To Do List](TODO.md)