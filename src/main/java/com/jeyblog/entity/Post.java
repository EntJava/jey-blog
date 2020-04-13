package com.jeyblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

/**
 * The type Post.
 * How to format Date https://stackoverflow.com/questions/28802544/java-8-localdate-jackson-format
 * @author Jeanne
 * @version 1.0.0
 * @since 2020-04-12
 */
@Table(name = "Post")
@Entity(name = "Post")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@XmlRootElement(name = "Post")
@XmlAccessorType(XmlAccessType.FIELD)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "post_id")
    @XmlElement(name = "postId")
    private int postId;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "author")
    private String author;

    @XmlElement(name = "category")
    private String category;

    @Column(name = "publication_date")
    @EqualsAndHashCode.Exclude
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @XmlElement(name = "pubDate")
    private LocalDateTime pubDate;

    @XmlElement(name = "description")
    private String description;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @Column(name = "created_at")
    @XmlElement(name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    @XmlElement(name = "updatedAt")
    private LocalDateTime updatedAt;

    /**
     * Instantiates a new Post.
     */
    public Post() {
        pubDate = LocalDateTime.now();
    }
}