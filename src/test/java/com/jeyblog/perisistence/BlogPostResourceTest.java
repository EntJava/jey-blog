package com.jeyblog.perisistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.services.ApplicationServices;
import com.jeyblog.services.BlogResource;
import com.jeyblog.utility.PostModel;
import lombok.extern.log4j.Log4j2;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * The type Blog post resource test.
 * How to set up Gizzy to use application uri:
 * https://mincong.io/2018/12/18/testing-jax-rs-resources/
 */
@Log4j2
public class BlogPostResourceTest extends JerseyTest {

    private WebTarget posts;
    private WebTarget posts404;
    private WebTarget postXML;
    /**
     * The Client.
     */
    Client client = ClientBuilder.newClient();
    @Before
    public void setUp() {
        posts = client.target( "http://localhost:8080/jey-blog/rest-api/posts");
        posts404 = client.target("http://localhost:8080/jey-blog/rest-api");
        postXML =  client.target("http://localhost:8080/jey-blog/rest-api/posts/xml");
    }
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
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
        assertNotNull("Should return post list", response.getEntity());
        assertTrue("application/json".equals(response.getMediaType().toString()));
        log.debug(response.getEntity());
    }


    /**
     * Test fetch all with 404 error.
     */
    @Test
    public void testFetchAllWith404Error() {
        BlogResource resource =  new BlogResource();
        Response response = posts404.request().get();
        assertEquals("should return status 404", 404, response.getStatus());
    }


    /**
     * Test put post.
     * https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-jerseytest.html
     */
    @Test
    public void testPutPost(){
        Post post = new Post();
        post.setDescription("Jersey Test Framework");
        Response response =  posts.path("30/"+ post.getDescription()).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(post, MediaType.APPLICATION_JSON));
        assertTrue(response.getStatus() == 200);
    }

    /**
     * Test put post xml.
     */
    @Test
    public void testPutPostXML(){
        Post post = new Post();
        post.setDescription("Jersey Test Framework in XML format.");
        Response response =  postXML.path("12/"+ post.getDescription()).request(MediaType.APPLICATION_XML)
                .put(Entity.entity(post, MediaType.APPLICATION_XML));
        assertTrue(response.getStatus() == 200);
    }

    /**
     * Test get post by id.
     */
    @Test
    public void testGetPostByID() {
        posts = posts.path("/2");
        Response response = posts.request().get();
        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return postId 2", response.getEntity().toString());
        System.out.println(response);
        System.out.println(response.readEntity(String.class));
    }

    /**
     * Test get post by id.
     * WebTarget target = target("items");
     */
    @Test
    public void testGetPostByIDXML() {

        postXML = postXML.path("/17");
        Response response = postXML.request().get();
        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return postId 2", response.getEntity().toString());
        assertTrue(MediaType.APPLICATION_XML.equals(response.getMediaType().toString()));
        log.debug(response.getEntity().toString());
        System.out.println(response.readEntity(String.class));
    }













    /**
     * Test delete post by id.
     * https://www.javaguides.net/2018/06/how-to-test-jersey-rest-api-with-junit.html
     */
    @Disabled
    @Test
    public void testDeletePostByID() {
        posts = posts.path("64/delete");
        System.out.println(posts);
        Response response = posts.request().delete();
        assertEquals("Should return status 200", 200, response.getStatus());
        //System.out.println(response.readEntity(String.class));
        assertNull(posts.request().get());
    }

    /**
     * Test delete post.
     */
    @Disabled
    @Test
    public void testDeletePost() {
        WebTarget delPosts = client.target("http://localhost:8080/jey-blog/rest-api/posts/delete-json/15");
        Response response = delPosts.request().delete();
        assertEquals("Should return status 200", 200, response.getStatus());
        assertTrue(MediaType.APPLICATION_JSON.equals(response.getMediaType().toString()));
        assertTrue(posts.path("/15").request().get().equals(null));
    }


    @Disabled
    @Test
    public void testAddPost() throws JsonProcessingException {
        PostModel newPost = new PostModel();
        newPost.setTitle("Jersey Unit Test Framework");
        newPost.setAuthor("Emma Marie");
        newPost.setCategory("Singer");
        newPost.setDescription("This is working!!!!!!!!");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String post =  mapper.writeValueAsString(newPost);
        Response response =  posts.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(post, MediaType.APPLICATION_JSON));

        System.out.println(response.readEntity(String.class));
        assertTrue(response.getStatus() == 400 );

    }

}

