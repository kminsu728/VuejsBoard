package com.mskim.demo.rest.message;

import com.mskim.demo.rest.board.BoardRestService;
import com.mskim.demo.rest.comment.CommentService;
import com.mskim.demo.web.board.Board;
import com.mskim.demo.web.post.Post;
import com.mskim.demo.web.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueConsumer {

    private final PostService postService;
    private final CommentService commentService;
    private final BoardRestService boardRestService;

    @RabbitListener(queues = "${services.rabbitmq.queue-name}")
    public void receive(QueueMessage message) {
        QueueMessageType messageType = message.getQueueMessageType();

        log.debug("Queue Consumer: message type is {}", messageType);
        switch (messageType) {
            case CREATE_POST: {
                Post post = (Post) message.getArgs();
                postService.createPost(post.getType(), post.getTitle(), post.getAuthor(), post.getContent());
                break;
            }
            case DELETE_POST: {
                Post post = (Post) message.getArgs();
                postService.deletePost(post.getId());
                break;
            }
            case UPDATE_POST: {
                Post post = (Post) message.getArgs();
                postService.updatePost(post.getType(), post.getId(), post.getTitle(), post.getContent());
                break;
            }
            case ADD_COMMENT: {
                Post comment = (Post) message.getArgs();
                commentService.addComment(comment.getPid(), comment.getAuthor(), comment.getContent());
                break;
            }
            case DELETE_COMMENT: {
                Post comment = (Post) message.getArgs();
                commentService.deleteComment(comment.getId());
                break;
            }
            case CREATE_BOARD: {
                Board board = (Board) message.getArgs();
                boardRestService.createBoard(board.getType(), board.getName());
                break;
            }
            case INCREASE_VIEWS: {
                Post post = (Post) message.getArgs();
                postService.increaseViews(post);
                break;
            }
            default: {
                log.error("Unknown message type: {}", messageType);
                break;
            }
        }
    }

}
