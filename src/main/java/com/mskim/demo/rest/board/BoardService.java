package com.mskim.demo.rest.board;

import com.mskim.demo.rest.post.Post;
import com.mskim.demo.rest.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    PostRepository postRepository;

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
