package com.jeyblog.perisistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.services.BlogResource;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Blog post resource test.
 * How to set up Gizzy to use application uri:
 * https://mincong.io/2018/12/18/testing-jax-rs-resources/
 */
@Log4j2
public class BlogPostResourceTest extends JerseyTest {

    private WebTarget posts;
    @Before
    public void setUp() {
        posts = ClientBuilder.newClient().target("http://localhost:8080/jeyblog/rest-api/posts");
    }
    @Override
    protected Application configure() {
        return new ResourceConfig(BlogResource.class);
    }

    /**
     * Tes fetch all.
     * https://www.javaguides.net/2018/06/how-to-test-jersey-rest-api-with-junit.html
     */
    @Test
    public void testFetchAllPosts() {
        Response response = posts.request().get();
        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return post list", response.getEntity().toString());
        System.out.println( response);
        System.out.println(response.readEntity(String.class));
    }


    /**
     * Test fetch all with 404 error.
     */
    @Test
    public void testFetchAllWith404Error() {
        BlogResource resource =  new BlogResource();
        Response response = posts.request().get();
        assertEquals("should return status 404", 404, response.getStatus());
        System.out.println( response);
        System.out.println(response.readEntity(String.class));
    }


    /**
     * Test create post.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testCreatePost() throws JsonProcessingException {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Post newPost = new Post();
        newPost.setTitle("Jersey Unit Test Framework");
        newPost.setAuthor("Anne Marie");
        newPost.setCategory("Programming");
        newPost.setDescription("This is working!!!!!!!!");
            Entity<Post> postEntity = Entity.entity(newPost, MediaType.APPLICATION_JSON);
        String post = objectMapper.writeValueAsString(postEntity);
            Response response = posts.request().post(Entity.json(post)); //Here we send POST request
        log.error(response);
        assertNotNull("Should return post list", response.getEntity().toString());
        assertEquals("Should return status 400", 404, response.getStatus());
        System.out.println(response.getEntity());
    }
}
