package com.toyclone.ex.mapper;

import com.toyclone.ex.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@Mapper
public interface BoardMapper {

    public List<BoardVO> getList();

    public int insert(BoardVO board);

    public void insertSelectKey(BoardVO board);

    public BoardVO read(long l);

    public int delete(long l);

    public int update(BoardVO board);

}
