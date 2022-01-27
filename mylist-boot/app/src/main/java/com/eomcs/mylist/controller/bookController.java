package com.eomcs.mylist.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.mylist.domain.Book;
import com.eomcs.util.ArrayList;

@RestController
public class BookController {

  ArrayList bookList = new ArrayList();

  public BookController() throws Exception {
    System.out.println("BookController() 호출됨!");

    BufferedReader in = new BufferedReader(new FileReader("books.csv")); // 주 객체에 데코레이터 객체를 연결

    String line;
    while ((line = in.readLine()) != null) { // readLine()이 null을 리턴한다면 더이상 읽을 데이터가 없다는 뜻!
      bookList.add(Book.valueOf(line));
    }

    in.close();
  }

  @RequestMapping("/book/list")
  public Object list() {
    return bookList.toArray();
  }

  @RequestMapping("/book/add")
  public Object add(Book book) {
    bookList.add(book);
    return bookList.size();
  }


  @RequestMapping("/book/get")
  public Object get(int index) {
    if (index < 0 || index >= bookList.size()) {
      return "";
    }
    return bookList.get(index);
  }

  @RequestMapping("/book/update")
  public Object update(int index, Book book) {
    if (index < 0 || index >= bookList.size()) {
      return 0;
    }
    return bookList.set(index, book) == null ? 0 : 1;
  }

  @RequestMapping("/book/delete")
  public Object delete(int index) {
    if (index < 0 || index >= bookList.size()) {
      return 0;
    }
    return bookList.remove(index) == null ? 0 : 1;
  }

  @RequestMapping("/book/save")
public Object save() throws Exception {
  DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("books.data")));

  Object[] arr = bookList.toArray();

  for (Object obj : arr) {
    Book book = (Book) obj;
    out.writeUTF(book.getTitle());
    out.writeUTF(book.getAuthor());
    out.writeUTF(book.getPress());
    out.writeInt(book.getPage());
    out.writeInt(book.getPrice());
    if (book.getReadDate() == null) {
      out.writeUTF("");
    } else {
      out.writeUTF(book.getReadDate().toString());
    }
    out.writeUTF(book.getFeed());
  }

  out.close();
  return arr.length;
}
}
