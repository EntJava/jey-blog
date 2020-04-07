package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Blog Post resource (exposed at "/" path
 */
@Path("/posts")
@Log4j2
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
     *https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-configuration
     * @return the all posts as application/json response
     * @throws JsonProcessingException the json processing exception https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/post-example.html
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,
            MediaType.APPLICATION_FORM_URLENCODED,MediaType.MULTIPART_FORM_DATA})
    @Operation(summary = "Finds all posts",
            tags = {"posts"},
            description = "The posts are in json format",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Post.class))),
                    @ApiResponse(
                            responseCode = "400", description = "Invalid status value"
                    )}
    )
    public Response getAllPostsJSON() throws JsonProcessingException {
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
     * @param newPost the new post
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Add a new post.",
            tags = {"posts"},
            responses = {
                    @ApiResponse(responseCode = "405", description = "Invalid input")
            })
    public Response createPost(
            @Parameter(description = "Post object to create", required = true) Post newPost) throws JsonProcessingException {
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String post = objectMapper.writeValueAsString(blogPostDao.create(newPost));
        log.error("Post: " + post);
        return Response.status(200).entity(post).build();
    }

}
