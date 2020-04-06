package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeyblog.entity.Post;
import com.jeyblog.perisistence.GenericDao;

import javax.swing.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Blog resource.
 */
@Path("/")
public class BlogResource {
    private GenericDao blogPostDao = new GenericDao<>(Post.class);
    /**
     * The Object mapper.
     */
    ObjectMapper objectMapper;

    /**
     * Gets all posts json.
     *
     * @return the all posts json
     * @throws JsonProcessingException the json processing exception
     */
    @GET
    @Path("posts")
    @Produces(MediaType.APPLICATION_JSON)
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
     * Gets all posts xml.
     *https://dzone.com/articles/solving-the-xml-problem-with-jackson
     * @return the all posts xml
     * @throws JsonProcessingException the json processing exception
     */
    @GET
    @Path("posts")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllPostsXML() throws JsonProcessingException {
        List<Post> postList = blogPostDao.getAll();
        objectMapper = new XmlMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String xml =  objectMapper.writeValueAsString(postList);
        return Response.ok().entity(xml).build();
    }

}
