package dk.blogpost.moodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String content;
    private String translatedContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public Comment() {}

    public Comment(String author, String content, Post post) {
        this.author = author;
        this.content = content;
        this.post = post;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getTranslatedContent() {
        return translatedContent;
    }

    public void setTranslatedContent(String translatedContent) {
        this.translatedContent = translatedContent;
    }
}

