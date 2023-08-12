package dk.blogpost.controller;

import dk.blogpost.dto.TranslatedPostWithCommentsDTO;
import dk.blogpost.moodel.Post;
import dk.blogpost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/translated")
    public ResponseEntity<TranslatedPostWithCommentsDTO> getTranslatedContent(@PathVariable Long id) {
        TranslatedPostWithCommentsDTO translation = postService.getTranslatedContent(id);
        if (translation != null) {
            return new ResponseEntity<>(translation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return new ResponseEntity<>(postService.savePost(post), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        if (postService.getPostById(id).isPresent()) {
            return new ResponseEntity<>(postService.savePost(post), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
