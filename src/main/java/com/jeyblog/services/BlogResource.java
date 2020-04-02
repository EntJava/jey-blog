package com.jeyblog.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/blog")
public class BlogResource {
    ObjectMapper objectMapper;
    @GET
    @Path("/posts")
    public Response getAllPosts() throws JsonProcessingException {
        objectMapper =  new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String posts = objectMapper.writeValueAsString("All Posts");
        return Response.status(200).entity(posts).build();
    }
}
