package com.jeyblog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "Post")
@Entity(name = "Post")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "post_id")
    private int postId;
    private String title;
    private String author;
    private String category;
    @Column(name = "publication_date")
    @EqualsAndHashCode.Exclude
    private LocalDateTime pubDate;
    private String description;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}