package com.khit.library.service;

import com.khit.library.entity.Board;
import com.khit.library.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void insert(Board board) {
        boardRepository.save(board);
    }
}
