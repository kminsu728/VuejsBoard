package com.mskim.demo.rest.board;

import com.mskim.demo.web.board.Board;
import com.mskim.demo.web.board.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardRestService {

    private final BoardRepository boardRepository;

    public List<Board> getBoard() {
        return boardRepository.findAll();
    }

    public void createBoard(String type, String name) {
        Board board = Board.builder()
                .type(type)
                .name(name).build();

        boardRepository.save(board);
    }
}
