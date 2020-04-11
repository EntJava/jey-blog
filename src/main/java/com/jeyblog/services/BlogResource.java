package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.utility.PostModel;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Blog Post resource (exposed at "/" path
 * Swagger Resources
 * https://www.youtube.com/watch?v=GKGAkbHe_nw
 * https://www.youtube.com/watch?v=5lQgi-n05F4
 * @author Jeanne
 * @version 1.0.0
 * @since 2020-04-06
 */
@Path("/posts")
@Log4j2
@Api("BlogResource/")
//@SwaggerDefinition(tags ={  @Tag(name = "BlogPost Resource", description = "REST API CRUD operations Endpoints for blog Post")})
@SwaggerDefinition(
        tags ={  @Tag(name = "BlogResource", description = "REST API CRUD operations Endpoints for blog Post")},
        consumes = {MediaType.APPLICATION_JSON}
)
public class BlogResource {
    private GenericDao blogPostDao = new GenericDao<>(Post.class);
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
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,
            MediaType.APPLICATION_FORM_URLENCODED,MediaType.MULTIPART_FORM_DATA})
    @ApiOperation(value = "Fetch all the posts. No login required.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Data Not found!"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response getPosts() throws JsonProcessingException {
        List<Post> postList = blogPostDao.getAll();
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(SerializationFeature.INDENT_OUTPUT);
        String posts = objectMapper.writeValueAsString(postList);
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
    @ApiOperation(value = "Create a new Post. When creating a post required fields are: " +
            "title , author, category and description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400,message = "Bad Request, Bad data format!"),
            @ApiResponse(code = 500, message = "Internal Error!")
    })
    public Response createPost(
            @ApiParam(required = true) Post post) throws JsonProcessingException {
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
//        Post newPost = new Post();
//        newPost.setTitle(post.getTitle());
//        newPost.setAuthor(post.getAuthor());
//        newPost.setCategory(post.getCategory());
//        newPost.setDescription(post.getDescription());
        String postObj = objectMapper.writeValueAsString(blogPostDao.create(post));
        log.error("Post: " + postObj);
        return Response.status(200).entity(postObj).build();
    }

}
