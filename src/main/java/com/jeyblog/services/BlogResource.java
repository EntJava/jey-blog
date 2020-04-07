package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;

import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/blog")
public class BlogResource {
    private GenericDao blogPostDao = new GenericDao<>(Post.class);
    ObjectMapper objectMapper;
    @GET
    @Path("/posts")
    public Response getAllPosts() throws JsonProcessingException {
        List<Post> postList = blogPostDao.getAll();
        objectMapper =  new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String posts = objectMapper.writeValueAsString("All Posts");
        return Response.status(200).entity(posts).build();
    }

    @GET
    @Path("/{post}")
    public Response getPostByID(@PathParam("post") int id) throws JsonProcessingException {
        Post post = (Post) blogPostDao.getById(id);
        objectMapper =  new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);
        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/update")
    public Response updatePost(
            @FormParam("id") int id,
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("category") String category,
            @FormParam("description") String description) throws JsonProcessingException {
        Post post = (Post) blogPostDao.getById(id);
        post.setTitle(title);
        post.setAuthor(author);
        post.setCategory(category);
        post.setDescription(description);
        blogPostDao.saveOrUpdate(post);
        String output = "Post ID " + id + " has been successfully updated to: " + post.toString();

        return Response.status(200).entity(output).build();
    }
}
