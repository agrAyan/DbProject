package com.bookapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bookapp.bean.Book;
import com.bookapp.exception.AddBookException;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotAvailableException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class BookImpl implements BookInter {

	@Override
	public void addBook(Book book) throws AddBookException {
		try {
			String sql = "insert into book values (?,?,?,?,?)";
			Connection connection = ModelDAO.openConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, book.getTitle());
			st.setString(2, book.getAuthor());
			st.setString(3, book.getCategory());
			st.setInt(4, book.getBookid());
			st.setInt(5, book.getPrice());
			st.execute();
			ModelDAO.closeConnection();
			st.close();

		} catch (SQLException e) {
			System.out.println("some error occured");
			e.printStackTrace();
			throw new AddBookException("cannot add book");

		}
	}

	@Override
	public boolean deleteBook(int bookid) throws BookNotFoundException {
		// TODO Auto-generated method stub
		boolean check = true;

		String sql = "delete from book where bookid=?";
		Connection connection = ModelDAO.openConnection();
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(sql);
			st.setInt(1, bookid);
			int result = st.executeUpdate();
			// System.out.println(result);
			if (result == 0) {
				throw new BookNotFoundException("book not available.. try Again");
			}
			} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}

		return check;

	}

	@Override
	public Book getBookById(int bookid) throws BookNotFoundException {
		PreparedStatement st = null;
		try {
			Book book = new Book();
			String sql = "select * from book where bookid=?";
			Connection connection = ModelDAO.openConnection();
			st = connection.prepareStatement(sql);

			st.setInt(1, bookid);
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next() == false) {
				throw new BookNotFoundException("no book id found.. try Again..");
			} else {
				do {

					book.setTitle(resultSet.getString(1));
					book.setAuthor(resultSet.getString(2));
					book.setCategory(resultSet.getString(3));
					book.setBookid(resultSet.getInt(4));
					book.setPrice(resultSet.getInt(5));

				} while (resultSet.next());
			}
			return book;

		} catch (SQLException e) {
			System.out.println("some error occured");
			e.printStackTrace();

		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}
		return null;

	}

	@Override
	public boolean updateBook(int bookid, int price) throws BookNotFoundException {
		boolean check = true;
		PreparedStatement st = null;
		try {
			String sql = "update book set price=? where bookid=?";
			Connection connection = ModelDAO.openConnection();
			st = connection.prepareStatement(sql);
			st.setInt(1, price);
			st.setInt(2, bookid);

			int result = st.executeUpdate();
			if (result == 0) {
				check = false;
				throw new BookNotFoundException("bookid is not available.. pls enter correct Book Id");
			}

		} catch (SQLException e) {
			System.out.println("some error occured");
			e.printStackTrace();

		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}
		return check;

	}

	@SuppressWarnings("unused")
	@Override
	public List<Book> getAllBooks() throws BookNotAvailableException {
		PreparedStatement st = null;
		List<Book> bookList = new ArrayList<>();

		try {
			String sql = "select * from book";
			Connection connection = ModelDAO.openConnection();
			st = connection.prepareStatement(sql);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				Book book = new Book();
				book.setTitle(resultSet.getString(1));
				book.setAuthor(resultSet.getString(2));
				book.setCategory(resultSet.getString(3));
				book.setBookid(resultSet.getInt(4));
				book.setPrice(resultSet.getInt(5));
				bookList.add(book);
			}
			if (bookList == null) {
				throw new BookNotAvailableException("no books available");
			} else {

				return bookList;

			}
		} catch (SQLException e) {
			System.out.println("some error occured");

		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}

		return null;
	}

	@Override
	public List<Book> getBookbyAuthor(String author) throws AuthorNotFoundException {

		PreparedStatement st = null;
		try {
			List<Book> bookByAuthor = new ArrayList<>();
			String sql = "select * from book where author=?";
			Connection connection = ModelDAO.openConnection();
			st = connection.prepareStatement(sql);

			st.setString(1, author);
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next() == false) {
				throw new AuthorNotFoundException("no Author found.. try Again!!");
			} else {
				do {
					Book book = new Book();
					book.setTitle(resultSet.getString(1));
					book.setAuthor(resultSet.getString(2));
					book.setCategory(resultSet.getString(3));
					book.setBookid(resultSet.getInt(4));
					book.setPrice(resultSet.getInt(5));
					bookByAuthor.add(book);
				} while (resultSet.next());
			}
			return bookByAuthor;

		} catch (SQLException e) {
			System.out.println("some error occured");
			e.printStackTrace();

		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}
		return null;

	}

	@Override
	public List<Book> getBookbycategory(String category) throws CategoryNotFoundException {

		PreparedStatement st = null;
		try {
			List<Book> bookByCategory = new ArrayList<>();
			String sql = "select * from book where category=?";
			Connection connection = ModelDAO.openConnection();
			st = connection.prepareStatement(sql);

			st.setString(1, category);
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next() == false) {
				throw new CategoryNotFoundException("no book at this category..!! try Again");
			} else {
				do {
					Book book = new Book();
					book.setTitle(resultSet.getString(1));
					book.setAuthor(resultSet.getString(2));
					book.setCategory(resultSet.getString(3));
					book.setBookid(resultSet.getInt(4));
					book.setPrice(resultSet.getInt(5));
					bookByCategory.add(book);
				} while (resultSet.next());
			}
			return bookByCategory;

		} catch (SQLException e) {
			System.out.println("some error occured");
			e.printStackTrace();

		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();

		}
		return null;

	}

}
