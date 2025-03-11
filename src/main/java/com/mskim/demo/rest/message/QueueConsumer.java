package com.mskim.demo.rest.message;

import com.mskim.demo.rest.board.BoardService;
import com.mskim.demo.rest.comment.CommentService;
import com.mskim.demo.rest.websocket.WebSocketService;
import com.mskim.demo.rest.board.Board;
import com.mskim.demo.rest.post.Post;
import com.mskim.demo.rest.post.PostService;
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
    private final BoardService boardService;
    private final WebSocketService webSocketService;

    @RabbitListener(queues = "${services.rabbitmq.queue-name}")
    public void receive(QueueMessage message) {
        QueueMessageType messageType = message.getQueueMessageType();

        log.debug("Queue Consumer: message type is {}", messageType);
        switch (messageType) {
            case CREATE_POST: {
                Post post = (Post) message.getArgs();
                postService.createPost(post.getType(), post.getTitle(), post.getAuthor(), post.getContent());

                //webSocketService.sendAlert("새로운 글이 등록되었습니다.");
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
                boardService.createBoard(board.getType(), board.getName());
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
