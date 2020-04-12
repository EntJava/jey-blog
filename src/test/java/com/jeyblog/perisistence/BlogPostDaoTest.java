package com.jeyblog.perisistence;

import com.jeyblog.entity.Post;
import com.jeyblog.test.utility.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Blog post dao test.
 */
public class BlogPostDaoTest {

    /**
     * The Post dao.
     */
    GenericDao postDao;

    /**
     * Set up.
     */
    @BeforeEach
    void setUp(){
        postDao =  new GenericDao(Post.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Verify that all Crews are retrieved from db
     */
    @Test
    void getAllPosts(){
        List<Post> posts = postDao.getAll();
        assertEquals(5,posts.size());
    }

    /**
     * Create post.
     */
    @Test
    void createPost(){
        Post newPost = new Post();
        newPost.setTitle("Today is over");
        newPost.setAuthor("Inema Ella");
        newPost.setCategory("Philosophy");
        newPost.setPubDate(LocalDateTime.now());
        newPost.setDescription("This is a test");
        int id = postDao.create(newPost);
        assertNotEquals(0,id);
    }

    /**
     * Get post by id.
     */
    @Test
    void getPostById(){
        Post post = (Post) postDao.getById(4);
        assertTrue(post.getAuthor().equals("Nadine"));
        assertTrue(post.getTitle().equals("The happiness"));
        assertTrue(post.getCategory().equals("Social"));
        assertTrue(post.getDescription().equals("So what is persistence?"));
    }

    /**
     * Save or update post.
     */
    @Test
    void saveOrUpdatePost(){
        Post post = (Post) postDao.getById(4);
        post.setAuthor("testAuthor");
        post.setDescription("testDescription");
        postDao.saveOrUpdate(post);
        Post updatedPost = (Post) postDao.getById(4);
        assertTrue(post.equals(updatedPost));
    }
}
