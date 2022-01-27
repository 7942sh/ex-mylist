package com.eomcs.mylist.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.mylist.domain.Board;
import com.eomcs.util.ArrayList;

@RestController
public class BoardController {

  ArrayList boardList = new ArrayList();

  public BoardController() throws Exception {
    System.out.println("BoardController() 호출됨!");

    //1) 주 작업 객체(concrete component) 준비
    FileReader in = new FileReader("boards.csv");

    //2) 한 줄 단위로 데이터를 읽는 작업을 수행하는 데코레이터 준비
    BufferedReader in2 = new BufferedReader(in);

    String line;
    while ((line = in2.readLine()) != null) { // 한 줄의 문자열을 읽었으면,
      boardList.add(Board.valueOf(line));
    }

    in2.close();
    //in.close(); // 데코레이터를 close() 하면 그 데코레이터와 연결된 객체들도 모두 close() 된다.
  }

  @RequestMapping("/board/list")
  public Object list() {
    return boardList.toArray();
  }

  @RequestMapping("/board/add")
  public Object add(Board board) {

    board.setCreatedDate(new Date(System.currentTimeMillis()));
    boardList.add(board);
    return boardList.size();
  }


  @RequestMapping("/board/get")
  public Object get(int index) {
    if (index < 0 || index >= boardList.size()) {
      return "";
    }
    Board board = (Board) boardList.get(index);
    board.setViewCount(board.getViewCount() + 1);

    return board;
  }

  @RequestMapping("/board/update")
  public Object update(int index, Board board) {
    if (index < 0 || index >= boardList.size()) {
      return 0;
    }

    Board old = (Board) boardList.get(index);
    board.setViewCount(old.getViewCount());
    board.setCreatedDate(old.getCreatedDate());

    return boardList.set(index, board) == null ? 0 : 1;
  }

  @RequestMapping("/board/delete")
  public Object delete(int index) {
    if (index < 0 || index >= boardList.size()) {
      return 0;
    }
    return boardList.remove(index) == null ? 0 : 1;
  }

  @RequestMapping("/board/save")
  public Object save() throws Exception {
    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("boards.data")));

    Object[] arr = boardList.toArray();
    for (Object obj : arr) {
      Board board = (Board) obj;
      out.writeUTF(board.getTitle());
      out.writeUTF(board.getContent());
      out.writeInt(board.getViewCount());
      out.writeUTF(board.getCreatedDate().toString());
    }

    out.close();
    return arr.length;
  }
}
