package com.eomcs.mylist.controller;

import java.sql.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.io.FileWriter2;
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

  public BoardController() throws Exception {
    System.out.println("BoardController() 호출됨!");
    com.eomcs.io.FileReader2 in = new com.eomcs.io.FileReader2("boards.csv");

    String line;
    while ((line = in.readLine()).length() != 0) { // 빈 줄을 리턴 받았으면 읽기를 종료한다.
      boardList.add(Board.valueOf(line));
    }

    in.close();
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
    FileWriter2 out = new FileWriter2("boards.csv"); // 따로 경로를 지정하지 않으면 파일은 프로젝트 폴더에 생성된다.

    Object[] arr = boardList.toArray();
    for (Object obj : arr) {
      Board board = (Board) obj;
      out.println(board.toCsvString());
    }

    out.close();
    return arr.length;
  }
}
