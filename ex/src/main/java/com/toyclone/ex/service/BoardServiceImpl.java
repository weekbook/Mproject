package com.toyclone.ex.service;

import com.toyclone.ex.domain.BoardVO;
import com.toyclone.ex.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
    @Setter(onMethod_ = @Autowired)
    private BoardMapper mapper;

    @Override
    public List<BoardVO> getList() {
        log.info("목록출력");
        return mapper.getList();
    }

    @Override
    public void register(BoardVO board) {
        mapper.insertSelectKey(board);
    }

    @Override
    public BoardVO get(Long bno) {
        return mapper.read(bno);
    }

    @Override
    public boolean modify(BoardVO board) {
        return mapper.update(board)==1;
    }

    @Override
    public boolean remove(Long bno) {
        return mapper.delete(bno)==1;
    }
}
