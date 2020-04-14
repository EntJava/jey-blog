package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Blog Post resource (exposed at "/rest-api/posts" path
 * All blog post end points are ccreated within this resource.
 * Swagger Resources:
 * https://www.youtube.com/watch?v=GKGAkbHe_nw
 * https://www.youtube.com/watch?v=5lQgi-n05F4
 * @author Jeanne, Yia, Estefanie
 * @version 1.0.0
 * @since 2020-04-12
 */
@Path("/posts")
@Log4j2
@Api("BlogResource/")
//@SwaggerDefinition(tags ={  @Tag(name = "BlogPost Resource", description = "REST API CRUD operations Endpoints for blog Post")})
@SwaggerDefinition(

//        Comment the following  3 lines in BlogResources
//        schemes = {SwaggerDefinition.Scheme.HTTP,SwaggerDefinition.Scheme.HTTPS},
//        host = "localhost:8080",
//        basePath = "/jey-blog/rest-api",
        consumes = {"application/json, application/xml"},
        produces = {"application/json, application/xml"},
        info = @Info(title = "Blog Post Rest Api",
        version = "1.0.0",
        description = "Simple API that handles CRUD operations of Post for a Blog." +
                " No authentication  required to consume this API yet."),
        tags ={@Tag(name="Support format", description = "application/json and application/xml"),
                @Tag(name = "BlogResource", description = "REST API CRUD operations Endpoints for blog Post")
        }
)
public class BlogResource {
    private GenericDao blogPostDao = new GenericDao<>(Post.class);
    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Object mapper.
     */
    ObjectMapper objectMapper;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     * Gets all posts json.
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     *https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     * @return the all posts as application/json response
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Fetch all posts in JSON format.",
    notes = "All the posts available are display under this endpoint in JSON format.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Data Not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response getPosts() {
        try {
            List<Post> postList = blogPostDao.getAll();
            objectMapper =  new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .enable(SerializationFeature.INDENT_OUTPUT);
            String posts = objectMapper.writeValueAsString(postList);
            return Response.status(200).entity(posts).build();
        }
        catch (JsonProcessingException ex)
        {
            logger.error(ex);
            return Response.status(500).build();
        }
        catch (Exception ex)
        {
            logger.error(ex);
            return Response.status(500).build();
        }
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/xml" media type.
     * Gets all posts xml.
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     *https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     * @return the all posts as application/xml response
     */
    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @ApiOperation(value = "Fetch all posts in XML format.",
            notes = "All the posts available are displayed under this endpoint in XML format.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Data Not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response getPostXML() throws JsonProcessingException {
        List<Post> listPost = blogPostDao.getAll();
        GenericEntity<List<Post>> posts =  new GenericEntity<List<Post>>(listPost) {};
        log.error(" XML get ALL: " + Response.status(200).entity(listPost).build());
        return Response.status(200).entity(posts).build();
    }

    /**
     * Create post response.
     *
     * @param post the new post
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Add a new Post in JSON format.",
            notes = "The following are required fields to successfully created a new post. " +
            "title , author, category and description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response createPost(
            @ApiParam(required = true) Post post) throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
        .enable(SerializationFeature.INDENT_OUTPUT);
        String postObj = objectMapper.writeValueAsString(blogPostDao.create(post));
        int id = blogPostDao.create(postObj);
        log.error("Post: " + postObj);
        if (Response.status(200).equals(200)) {
            return Response.status(200).entity("Post Added Successfully with ID: " + id).build();
        } else if (Response.serverError().equals(500)) {
           return Response.status(500).entity("Internal Server Error Occurred!").build();
        } else {
          return  Response.noContent().entity(Response.serverError()).build();
        }
    }

    /**
     * Create post xml response.
     *
     * @param post the post
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @ApiOperation(value = "Add a new Post in XML format.",
            notes = "The following are required fields to successfully created a new post. " +
                    "title , author, category and description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Path("/new-post/xml")
    public Response createPostXML(@ApiParam(required = true) Post post) {
        int postID =  blogPostDao.create(post);
        return Response.status(200).entity("Success fully added a new post with ID : " + postID).build();
    }


    /**
     * This method retrieve a post and return an "application/json" media type.
     *
     * @param id the id
     * @return the all posts as application/json response
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get a new Post by ID in JSON format.",
            notes = "ID is a require field to retrieve a Post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response getPostByIDJSON(@ApiParam(value = "Id of the Post.", required = true) @PathParam("id") int id) {
        try {
            Post post = (Post) blogPostDao.getById(id);
            objectMapper =  new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .enable(SerializationFeature.INDENT_OUTPUT);
            String posts = objectMapper.writeValueAsString(post);
            return Response.status(200).entity(posts).build();

        } catch (JsonProcessingException ex) {
            logger.error("Post: " + id, ex);
            return Response.status(500).entity("Internal Server Error Occurred!").build();
        } catch (Exception ex) {
            logger.error("Post: " + id, ex);
            return Response.status(500).entity("Internal Server Error Occurred!").build();
        }

    }

    /**
     * This method retrieve a post and return a XML media type.
     *
     * @param id the id
     * @return the all posts as a XML response
     */
    @GET
    @Path("/xml/{id}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @ApiOperation(value = "Get a post by ID in XML format.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Data Not found!"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response getPostByIDXML(@ApiParam(value = "Id of the Post.", required = true) @PathParam("id") int id) {
        try {
            Post post = (Post)blogPostDao.getById(id);
            return Response.status(200).entity(post).build();
        } catch (Exception ex) {
            logger.error("Post: " + id, ex);
            return Response.status(500).build();
        }
    }

    /**
     * Method handling HTTP PUT requests. The returned object will be sent
     * to the client as "application/json" media type.
     * Updates the post description then returns the updated post as JSON
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     *https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     * @return the all posts as application/json response
     */
    @PUT
    @Path("/{id}/{description}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update an existing Posts description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Data formatting error"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response updatePost(@ApiParam(value = "Post Id of the object to update.", required = true)
                                   @PathParam("id")int id,
                               @ApiParam(value = "Description updated attribute.", required = true)
                               @PathParam("description")String description) {
        try {
            Post post = (Post)blogPostDao.getById(id);
            post.setDescription(description);
            blogPostDao.saveOrUpdate(post);

            objectMapper =  new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String update = objectMapper.writeValueAsString(post);

            return Response.status(200).entity(update).build();
        } catch (JsonProcessingException ex) {
            logger.error("Post: " + id, ex);
            return Response.status(500).build();
        } catch (Exception ex) {
            log.error("Post: " + id, ex);
            return Response.status(500).build();
        }
    }

        /**
         * Method handling HTTP PUT requests. The returned object will be sent
         * to the client as "application/xml" media type.
         * Updates the post description then returns the updated post as XML
         * Swagger annotations:
         * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
         *https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
         * @return the all posts as application/json response
         */
        @PUT
        @Path("xml/{id}/{description}")
        @Produces({MediaType.APPLICATION_XML})
        @Consumes({MediaType.APPLICATION_XML})
        @ApiOperation(value = "Update an existing Posts description")
        @ApiResponses({
                @ApiResponse(code = 200, message = "Success"),
                @ApiResponse(code = 400,message = "Data formatting error"),
                @ApiResponse(code = 500, message = "Internal Server Error!")
        })
        public Response updatePostXML(@ApiParam(value = "Post Id of the object to update.", required = true) @PathParam("id") int id, @PathParam("description") String description) {
            try {
                Post post = (Post)blogPostDao.getById(id);
                post.setDescription(description);
                blogPostDao.saveOrUpdate(post);
                return Response.status(200).entity(post).build();
            } catch (Exception ex) {
                logger.error("Post: " + id, ex);
                return Response.status(500).build();
            }
        }

    /**
     * This method retrieve a list of posts with an "application/json" media type.
     *
     * @param category
     * @return the post as application/json response
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Path("/category/{category}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get all posts of a specific category")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Data formatting error"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response getPostByCategory(@PathParam("category") String category) {
        try {
            List<Post> postList =  blogPostDao.getByColumnName("category", category);
            objectMapper =  new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .enable(SerializationFeature.INDENT_OUTPUT);
            String posts = objectMapper.writeValueAsString(postList);
            return Response.status(200).entity(posts).build();
        } catch (JsonProcessingException ex) {
            logger.error("Post Category: " + category, ex);
            return Response.status(500).build();
        } catch (Exception ex) {
            logger.error("Post Category: " + category, ex);
            return Response.status(500).build();
        }
    }

    /**
     * This method retrieve a list of posts of a specified category with an "application/xml" media type.
     *
     * @param category
     * @return the post as application/json response
     */
    @GET
    @Path("/xml/category/{category}")
    @Produces({MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get all posts of a specific category")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Data formatting error"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response getPostByCategoryXML(@PathParam("category") String category) {
        try {
            List<Post> postList =  blogPostDao.getByColumnName("category", category);
            return Response.status(200).entity(new GenericEntity<List<Post>>(postList) {}).build();
        } catch (Exception ex) {
            logger.error("Post Category: " + category, ex);
            return Response.status(500).build();
        }
    }

    /**
     * This method delete a post from the server.
     *
     * @param id the id
     * @return the success message
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Deletes an existing post by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Data formatting error"),
            @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    public Response deletePost(@ApiParam(value = "Post Id of the object to delete.", required = true) @PathParam("id") int id) {
        try
        {
            blogPostDao.delete(blogPostDao.getById(id));
            String output = "Post ID " + id + " was successfully deleted.";
            return Response.status(200).entity(output).build();
        } catch (Exception ex) {
            logger.error("Post ID: " + id, ex);
            return Response.status(500).build();
        }
    }
}
