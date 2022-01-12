package com.eomcs.mylist;

import java.sql.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

  ArrayList boardList = new ArrayList();

  @RequestMapping("/board/list")
  public Object list() {
    return boardList.toArray();
  }

  @RequestMapping("/board/add")
  public Object add(Board board) {

    board.setCreatedDate(new Date(System.currentTimeMillis()));
    boardList.add(board);
    return boardList.size;
  }


  @RequestMapping("/board/get")
  public Object get(int index) {
    if (index < 0 || index >= boardList.size) {
      return "";
    }
    Board board = (Board) boardList.list[index];
    board.viewCount++;
    return board;
  }

  @RequestMapping("/board/update")
  public Object update(int index, Board board) {
    if (index < 0 || index >= boardList.size) {
      return 0;
    }

    Board old = (Board) boardList.list[index];
    board.viewCount = old.viewCount;
    board.createdDate = old.createdDate;

    return boardList.set(index, board) == null ? 0 : 1;
  }

  @RequestMapping("/board/delete")
  public Object delete(int index) {
    if (index < 0 || index >= boardList.size) {
      return 0;
    }
    return boardList.remove(index) == null ? 0 : 1;
  }
}
