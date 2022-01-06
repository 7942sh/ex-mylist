package com.eomcs.mylist;

public class ArrayList {

  static Contact[] contacts = new Contact[5];
  static int size = 0;

  static int indexOf(String email) {
    for (int i = 0; i < size; i++) {
      Contact contact = contacts[i];
      if (contact.email.equals(email)) {
        return i;
      }
    }
    return -1;
  }

  static Contact remove(int index) {
    Contact old = contacts[index];
    for (int i = index + 1; i < size; i++) {
      contacts[i - 1] = contacts[i];
    }
    size--;
    return old;
  }

  static Contact[] grow() {
    Contact[] arr = new Contact[newLength()];
    copy(contacts, arr);
    return arr;
  }

  static int newLength() {
    return contacts.length + (contacts.length >> 1);
  }

  static void copy(Contact[] source, Contact[] target) {
    int length = source.length;
    if (target.length < source.length) {
      length = target.length;
    }
    for (int i = 0; i < length; i++) {
      target[i] = source[i];
    }
  }

  static Contact[] toArray() {
    Contact[] arr = new Contact[size];
    for (int i = 0; i < size; i++) {
      arr[i] = contacts[i];
    }
    return arr;
  }

  static void add(Contact contact) {
    if (size == contacts.length) {
      contacts = grow();
    }
    contacts[size++] = contact;
  }

  static Contact set(int index, Contact contact) {
    if (index < 0 || index >= size) { // 값이 저장된 위치가 무효한 인덱스라면
      return null;
    }
    Contact old = contacts[index];
    contacts[index] = contact;
    return old;
  }
}
