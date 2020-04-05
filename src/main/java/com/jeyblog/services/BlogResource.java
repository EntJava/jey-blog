package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;

import javax.swing.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
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
}
