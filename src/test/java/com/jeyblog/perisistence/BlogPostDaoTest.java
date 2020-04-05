package com.jeyblog.perisistence;

import com.jeyblog.entity.Post;
import com.jeyblog.test.utility.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlogPostDaoTest {

    GenericDao postDao;
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


}
