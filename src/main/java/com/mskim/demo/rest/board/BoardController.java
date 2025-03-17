package com.mskim.demo.rest.board;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.rest.message.QueueMessageType;
import com.mskim.demo.rest.message.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final QueueProducer queueProducer;
    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getList(HttpServletRequest request) {
        List<Board> boards = boardService.getBoard();

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("boards", boards);
        }});
    }

    @PostMapping("/create")
    public ResponseEntity<VueJsResponse> create(HttpServletRequest request,
                                                    @RequestBody Board board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin) {
            queueProducer.sentToQueue(QueueMessageType.CREATE_BOARD,
                    Board.builder()
                            .type(board.getType())
                            .name(board.getName()).build());
            //boardRestService.createBoard(type, name);
        } else {
            throw new AccessDeniedException("You do not have permission to add Board");
        }

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("type", board.getType());
        }});
    }
}
