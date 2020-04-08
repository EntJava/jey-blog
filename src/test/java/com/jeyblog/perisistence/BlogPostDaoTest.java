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

    @Test
    void getPostById(){
        Post post = (Post) postDao.getById(4);
        assertTrue(post.getAuthor().equals("Nadine"));
        assertTrue(post.getTitle().equals("The happiness"));
        assertTrue(post.getCategory().equals("Social"));
        assertTrue(post.getDescription().equals("So what is persistence?"));
    }

    @Test
    void saveOrUpdatePost(){
        Post post = (Post) postDao.getById(4);
        post.setAuthor("testAuthor");
        post.setDescription("testDescription");
        postDao.saveOrUpdate(post);
        Post updatedPost = (Post) postDao.getById(4);
        assertTrue(post.equals(updatedPost));
    }

    @Test
    void getPostByTitle(){
        Post post = (Post) postDao.getPostByTitle("title", "Hope");
        assertTrue(post.getPostId() == 2);
        assertTrue(post.getTitle().equals("Hope"));
        assertTrue(post.getCategory().equals("Education"));
        assertTrue(post.getDescription().equals("So what is persistence? Persistence simply means that we would like our application’s data to outlive the applications process."));
    }

    /**
     * Test delete post.
     * @throws Exception the exception
     */
    @Test
    public void testDeletePost() throws Exception {
        int sizeBeforeDelete = postDao.getAll().size();
        Post postToDelete = (Post) postDao.getById(1);
        int id = postToDelete.getPostId();
        postDao.delete(postToDelete);
        int sizeAfterDelete = postDao.getAll().size();

        Post deletedPost = (Post) postDao.getById(id);

        assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
        assertNull(deletedPost);
    }
}
