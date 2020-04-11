package com.jeyblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

/**
 * The type Post.
 * How to format Date https://stackoverflow.com/questions/28802544/java-8-localdate-jackson-format
 */
@Table(name = "Post")
@Entity(name = "Post")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@XmlRootElement
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "post_id")
    private int postId;
    private String title;
    private String author;
    private String category;
    @Column(name = "publication_date")
    @EqualsAndHashCode.Exclude
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pubDate;
    private String description;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    public Post() {
        pubDate = LocalDateTime.now();
    }
    public Post(String title, String author, String category, String summary) {
        title = this.title;
        author =  this.author;
        category =  this.category;
        summary =  this.description;
    }


}