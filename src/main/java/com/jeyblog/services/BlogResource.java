package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;
import com.jeyblog.utility.PostModel;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Blog Post resource (exposed at "/rest-api/posts" path
 * All blog post end points are ccreated within this resource.
 * Swagger Resources:
 * https://www.youtube.com/watch?v=GKGAkbHe_nw
 * https://www.youtube.com/watch?v=5lQgi-n05F4
 *
 * @author Jeanne, Yia, Estefanie
 * @version 1.0.0
 * @since 2020 -04-12
 */
@Path("/posts")
@Log4j2
@Api("BlogResource/")
//@SwaggerDefinition(tags ={  @Tag(name = "BlogPost Resource", description = "REST API CRUD operations Endpoints for blog Post")})
@SwaggerDefinition(
        tags ={ @Tag(name = "BlogResource", description = "REST API CRUD operations Endpoints for blog Post"),
        @Tag(name="Support format", description = "application/json and application/xml")}
)
public class BlogResource {
    private GenericDao blogPostDao = new GenericDao<>(Post.class);
    /**
     * The Object mapper.
     */
    ObjectMapper objectMapper;
    /**
     * The Xml mapper.
     */
    XmlMapper xmlMapper;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     * Gets all posts json.
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     * https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     * <p>
     * https://stackoverflow.com/questions/42846869/set-response-media-type-as-either-xml-or-json
     *
     * @return the all posts as application/json response
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Fetch all the posts. No login required.",
    consumes = "application/json, application/xml",
    produces = "application/json, application.xml")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Data Not found!"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response getPosts() throws JsonProcessingException {
        List<Post> postList = blogPostDao.getAll();
        objectMapper =  new ObjectMapper();
        xmlMapper = new XmlMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(SerializationFeature.INDENT_OUTPUT);

            String posts = objectMapper.writeValueAsString(postList);
            return Response.status(200).entity(posts).build();

    }

    /**
     * Gets post xml.
     *
     * @return the post xml
     * @throws JsonProcessingException the json processing exception
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/xml")
    public Response getPostXML() throws JsonProcessingException {
        xmlMapper = new XmlMapper();
        List<Post> listPost = blogPostDao.getAll();
        String xmlPosts = xmlMapper.writeValueAsString(listPost);
        return Response.status(200).entity(xmlPosts).build();
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
    @ApiOperation(value = "Create a new Post. When creating a post required fields are: " +
            "title , author, category and description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Error!")
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
           return Response.status(500).entity("Internal Error Occurred!").build();
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
    @ApiOperation(value = "Create a new Post. When creating a post required fields are: " +
            "title , author, category and description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Error!")
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
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Path("/{post}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPostByIDJSON(@PathParam("post") int id) throws JsonProcessingException {
        Post post = (Post) blogPostDao.getById(id);
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(SerializationFeature.INDENT_OUTPUT);
        String posts = objectMapper.writeValueAsString(post);
        return Response.status(200).entity(posts).build();
    }

    /**
     * Method handling HTTP PUT requests. The returned object will be sent
     * to the client as "application/xml" media type.
     * Updates the post description then returns the updated post as XML
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     * https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     *
     * @param id          the id
     * @param description the description
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
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response updatePostXML(@PathParam("id")int id, @PathParam("description")String description) {
        try {
            Post post = (Post)blogPostDao.getById(id);
            post.setDescription(description);
            blogPostDao.saveOrUpdate(post);
            return Response.status(200).entity(post).build();
        } catch (Exception ex) {
            log.error("Post: " + id, ex);
            return Response.status(500).build();
        }
    }

    /**
     * Method handling HTTP PUT requests. The returned object will be sent
     * to the client as "application/json" media type.
     * Updates the post description then returns the updates post as JSON
     * Swagger annotations:
     * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
     * https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     *
     * @param id          the id
     * @param description the description
     * @return the all posts as application/json response
     */
    @PUT
    @Path("/{id}/{description}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update an existing Posts description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "JSON Processing error"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response updatePost(@PathParam("id")int id, @PathParam("description")String description) {
        try {
            Post post = (Post)blogPostDao.getById(id);
            post.setDescription(description);
            blogPostDao.saveOrUpdate(post);

            objectMapper =  new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String update = objectMapper.writeValueAsString(post);

            return Response.status(200).entity(update).build();
        } catch (JsonProcessingException ex) {
            log.error(ex);
            return Response.status(500).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(500).build();
        }
    }

    /**
     * This method retrieve a post and return an "application/json" media type.
     *
     * @param id the id
     * @return the post as application/json response
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Path("/getID-{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPostByID(@PathParam("id") int id) throws JsonProcessingException {
        Post post = (Post) blogPostDao.getById(id);
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(SerializationFeature.INDENT_OUTPUT);
        String posts = objectMapper.writeValueAsString(post);
        return Response.status(200).entity(posts).build();
    }

    /**
     * This method delete a post from the server.
     *
     * @param id the id
     * @return the success message
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @DELETE
    @Path("/deleteID-{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deletePost(@PathParam("id") int id) throws JsonProcessingException {
        List<Post> postList = blogPostDao.getAll();
        blogPostDao.delete(blogPostDao.getById(id));
        String output = "Post ID " + id + " was successfully deleted.";
        return Response.status(200).entity(output).build();
    }
}
