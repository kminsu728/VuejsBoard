package com.mskim.demo.rest.board;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.web.board.Board;
import com.mskim.demo.web.board.BoardService;
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
public class BoardRestController {

    private final BoardService boardService;


    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getType(HttpServletRequest request) {
        List<Board> boards = boardService.getBoardType();

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("boards", boards);
        }});
    }


    @PostMapping("/createtype")
    public ResponseEntity<VueJsResponse> createType(HttpServletRequest request,
                                                    @RequestParam("type") String type,
                                                    @RequestParam("name") String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin) {
            boardService.addBoardType(type, name);
        } else {
            throw new AccessDeniedException("You do not have permission to add Board");
        }

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("type", type);
        }});
    }
}
