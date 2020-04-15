package com.jeyblog.utility;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Post model.
 * Model for required fields in blog Post
 */
@Getter
@Setter
@XmlRootElement(name = "Post")
public class PostModel {
    private String title;
    private String author;
    private String category;
    private String description;
}
