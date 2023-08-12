package dk.blogpost.service;

import dk.blogpost.dto.CommentDTO;
import dk.blogpost.dto.TranslatedPostDTO;
import dk.blogpost.dto.TranslatedPostWithCommentsDTO;
import dk.blogpost.moodel.Post;
import dk.blogpost.repository.CommentRepository;
import dk.blogpost.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private OpenAITranslationService translationService;

    @Autowired
    private CommentRepository commentRepository;



    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public TranslatedPostWithCommentsDTO getTranslatedContent(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return null;
        }

        Post post = postOptional.get();

        String translatedTitle = post.getTranslatedTitle();
        String translatedContent = post.getTranslatedContent();

        if (translatedTitle == null || translatedContent == null) {
            TranslatedPostDTO translation = translationService.translatePostToEnglish(post.getTitle(), post.getContent());
            post.setTranslatedTitle(translation.getTranslatedTitle());
            post.setTranslatedContent(translation.getTranslatedContent());
        }

        TranslatedPostWithCommentsDTO resultDTO = new TranslatedPostWithCommentsDTO();
        resultDTO.setId(post.getId());
        resultDTO.setTitle(translatedTitle);
        resultDTO.setContent(translatedContent);

        // Convert and translate the comments
        List<CommentDTO> commentDTOs = post.getComments().stream().map(comment -> {
            String commentTranslation = comment.getTranslatedContent();
            if (commentTranslation == null) {
                commentTranslation = translationService.translateToEnglish(comment.getContent());
                comment.setTranslatedContent(commentTranslation);
                commentRepository.save(comment);
            }

            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setAuthor(comment.getAuthor());
            commentDTO.setContent(commentTranslation);
            return commentDTO;
        }).collect(Collectors.toList());

        resultDTO.setComments(commentDTOs);

        postRepository.save(post);

        return resultDTO;
    }

}

