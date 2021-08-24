package com.bookapp.main;

import java.util.List;
import java.util.Scanner;

import com.bookapp.bean.Book;
import com.bookapp.dao.BookImpl;
import com.bookapp.dao.BookInter;
import com.bookapp.exception.AddBookException;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotAvailableException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class Client {

	public static void main(String[] args) throws CategoryNotFoundException {
		int choice = 0;
		
		BookInter bookImpl = new BookImpl();
		try {

			do {
				Scanner kb = new Scanner(System.in);
				System.out.println("enter choice \n"
						+ "1. Add book \n"
						+ "2. Delete Book \n"
						+ "3. One Book Details \n"
						+ "4. Update Book \n"
						+ "5. All Books \n"
						+ "6. Books By Author \n"
						+ "7. Books By Category \n"
						+ "8. Exit");
				choice = kb.nextInt();
				switch (choice) {
				case 1:
					try {
						System.out.println("enter title ");
						kb.nextLine();
						String title = kb.nextLine();
						System.out.println("enter author ");
						String author = kb.nextLine();
						System.out.println("enter category ");
						String category = kb.nextLine();
						System.out.println("enter bookid ");
						int bookid = kb.nextInt();
						System.out.println("enter price ");
						int price = kb.nextInt();
						// System.out.print(title+" "+author+" "+category+" "+bookid+" "+price+" ");
						Book book = new Book();
						book.setTitle(title);
						book.setAuthor(author);
						book.setCategory(category);
						book.setBookid(bookid);
						book.setPrice(price);
						bookImpl.addBook(book);
						System.out.println();
						System.out.println("inserted");
						break;
					} catch (AddBookException e) {
						e.getMessage();
					}

				case 2:
					try {
						System.out.println("enter bookid you want to delete");
						int bookidDelete = kb.nextInt();
						boolean result = bookImpl.deleteBook(bookidDelete);
						System.out.println(result);
						if (result)
							System.out.println("deleted successfull");
					} catch (BookNotFoundException e) {
						System.out.println();
						System.out.print(e.getMessage());
					}
					break;

				case 3:
					try {
						System.out.println("enter bookid to get data");
						int bookidData = kb.nextInt();
						Book book = bookImpl.getBookById(bookidData);

						if (book != null) {
							System.out.println("BooKTitle : " + book.getTitle() + " " + "BooKAuthor : "
									+ book.getAuthor() + " " + "BooKCategory : " + book.getCategory() + " ");
							System.out.println("BooKId : " + book.getBookid() + " " + "BooKPrice : " + book.getPrice());
						}

					} catch (BookNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 4:
					try {
						System.out.println("enter bookid to update");
						int bookIdUpdate = kb.nextInt();
						System.out.println("enter price to update");
						int price = kb.nextInt();
						boolean result = bookImpl.updateBook(bookIdUpdate, price);

						if (result) {
							System.out.println("updated");
						}

					} catch (BookNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 5:
					try {

						List<Book> bookList = bookImpl.getAllBooks();
						System.out.println(bookList.size());
						for (Book book : bookList) {
							System.out
									.println(book.getTitle() + " " + book.getAuthor() + " " + book.getCategory() + " ");
							System.out.println(book.getBookid() + " " + book.getPrice() + " ");
							System.out.println();
						}

					} catch (BookNotAvailableException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 6:
					try {
						System.out.println("enter author ");
						kb.nextLine();
						String author = kb.nextLine();
						List<Book> bookByAuthor = bookImpl.getBookbyAuthor(author);
						for (Book book : bookByAuthor) {
							System.out
									.println(book.getTitle() + " " + book.getAuthor() + " " + book.getCategory() + " ");
							System.out.println(book.getBookid() + " " + book.getPrice() + " ");
							System.out.println();
						}

					} catch (AuthorNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 7:
					try {
						System.out.println("enter Category ");
						kb.nextLine();
						String category = kb.nextLine();
						List<Book> bookByCategory = bookImpl.getBookbycategory(category);
						for (Book book : bookByCategory) {
							System.out
									.println(book.getTitle() + " " + book.getAuthor() + " " + book.getCategory() + " ");
							System.out.println(book.getBookid() + " " + book.getPrice() + " ");
							System.out.println();
						}
					} catch (CategoryNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 8:
					System.exit(0);

				}
			} while (choice != 8);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
