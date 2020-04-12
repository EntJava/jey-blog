package com.jeyblog.utility;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Post model.
 * Model for required fields in blog Post
 */
@Getter
@Setter
public class PostModel {
    private String title;
    private String author;
    private String category;
    private String description;
}