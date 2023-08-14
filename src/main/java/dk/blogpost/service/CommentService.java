package dk.blogpost.service;

import dk.blogpost.moodel.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> getCommentsForPost(Long postId);

    Comment createCommentForPost(Long postId, Comment comment);

    Comment updateComment(Long commentId, Comment comment);

    void deleteComment(Long commentId);

}

