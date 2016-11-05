package com.iboxshare.ipctest1_1;

import com.iboxshare.ipctest1_1.Book;

interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
}