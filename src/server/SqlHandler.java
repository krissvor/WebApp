package server;

import Beans.BookBean;
import Beans.UserBean;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import controllers.SearchController;

import java.awt.print.Book;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static controllers.SearchController.SEARCHATTRIBUTE.AUTHOR;


public class SqlHandler {

	private Connection connection = null;
	private java.sql.Statement statement = null;
	private ResultSet resultSet = null;

	public SqlHandler() {

		try {
			System.out.println("Loading MYSQL driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MYSQL Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
	}

	public void connect() {

		System.out.println("Connecting to SQLdatabase...");
		try {

			Properties connectionProps = new Properties();
			connectionProps.put("user", "root");
			connectionProps.put("password", "password");

			connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/webApp", connectionProps);
			System.out.println("connection to Mysql established");
		} catch (SQLException e) {
			System.out.println("Could not establish a connection to the SQL database");
			e.printStackTrace();
			System.out.println("connection to Mysql established");
		}
	}


	public void closeConnection() {

		if (connection != null) {

			try {
				System.out.println("Closing Mysql connection...");
				connection.close();

				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Failed to close connections" + e);
			}
			System.out.println("Connection to Mysql closed!");
		}
	}


	public boolean usernameExists(String username){
		boolean exists = true;
		try {
			statement = connection.createStatement();

			java.sql.PreparedStatement add = connection.prepareStatement(
					"SELECT * FROM user WHERE username = ?");

			add.setString(1, username);

			ResultSet rs = add.executeQuery();


			if(rs.next()){
				exists = true;
			}
			else{
				exists = false;
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}finally {
			return exists;
		}

	}


	public int addBook(BookBean book) {

		int bookKey = -1;
		int authorKey = -1;
		int venueKey = -1;

		ArrayList<Integer> generatedAuthorKeys = new ArrayList<Integer>();

		if(book.getAuthor() == null){
			throw new IllegalArgumentException("Error: author field can not be NULL when adding a book to the database");
		}

		//Adds the venue to the database
		venueKey = addVenue(book.getVenues());
		if(venueKey == -1) {
			System.out.println("Failed to add venue");
		}


		//Adds the authors to the database.
		for(String author : book.getAuthor()){

			authorKey = addAuthor(author);

			if(authorKey != -1){
				generatedAuthorKeys.add(authorKey);
			}
		}

		try {

			statement = connection.createStatement();

			java.sql.PreparedStatement add = connection.prepareStatement(
					"INSERT INTO book (publicationtype, publicationdate,title,pages,url,ee,price) VALUES(?, ?, ? ,? ,?, ?,?)", Statement.RETURN_GENERATED_KEYS);
			add.setString(1, book.getPublicationType());
			add.setString(2, book.getPublicationDate());
			add.setString(3, book.getTitle());
			add.setString(4, book.getPages());
			add.setString(5, book.getUrl());
			add.setString(6, book.getEe());
			add.setString(7, book.getPrice());

			int affectedRows = add.executeUpdate();

			ResultSet generatedKeys = add.getGeneratedKeys();
			if (generatedKeys.next()) {
				bookKey = generatedKeys.getInt(1);
				System.out.println("addvenueBOOK: "+addVenueBook(bookKey, venueKey));
			}
			else{return -1;}


			//Adding the relation between book and author.
			int res;
			for(int key : generatedAuthorKeys){
				res = addBookAuthorRelation(bookKey, key);

				if(res == -1){
					System.out.println("klarte ikke lage bookauthor relasjon");
				}
			}

		} catch (SQLException e) {
			System.out.println("failed to add Book to database");
			e.printStackTrace();
			return -1;
		} finally {

		}

		return -1;
	}

	public List<BookBean> findBooks(String term, SearchController.SEARCHATTRIBUTE attr, int page) {
		List<BookBean> resultList = new ArrayList<>();
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			// If the attribute was author, select on author, else determine selection based on search attribute
			ResultSet rs = (attr == AUTHOR) ? getAuthorSearchResultSet(term, page) : getSearchResultSet(term, attr, page);
			while(rs.next()) {
				resultList.add(bookFromResultSet(rs));
			}

		} catch (SQLException e) {
			System.err.println("An error occurred while selecting books");
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return resultList;
	}

	public BookBean getSingleBook(int id, boolean shouldClose) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			// If the attribute was author, select on author, else determine selection based on search attribute
			ResultSet rs = getSingleBookResultSet(id);
			return (rs.next()) ? bookFromResultSet(rs) : null;
		} catch (SQLException e) {
			System.err.println("An error occurred while selecting books");
			e.printStackTrace();
		} finally {
			if(shouldClose) {
				this.closeConnection();
			}
		}
		return null;
	}

	private ResultSet getAuthorSearchResultSet(String searchTerm, int page) throws SQLException {
		PreparedStatement authorStatement = connection.prepareStatement("SELECT DISTINCT(book.id), publicationtype, publicationdate, " +
				"title, pages, url, ee, price, picture, venue.name AS venue " +
				"FROM book, author, venue, book_author, book_venue " +
				"WHERE book_author.author_id = author.id AND " +
				"book_author.book_id = book.id AND " +
				"book_venue.book_id = book.id AND " +
				"book_venue.venue_id = venue.id AND " +
				"book_author.author_id = author.id AND " +
				"author.name RLIKE ? " +
				"ORDER BY title " +
				"LIMIT 10 OFFSET " + page*10);

		authorStatement.setString(1, searchTerm);
		authorStatement.execute();
		return authorStatement.getResultSet();
	}

	private ResultSet getSearchResultSet(String searchTerm, SearchController.SEARCHATTRIBUTE searchattribute, int page) throws SQLException {
		String selectionClause = getSelectionClause(searchattribute);
		PreparedStatement bookStatement = connection.prepareStatement("SELECT DISTINCT(book.id), publicationtype, publicationdate, " +
				"title, pages, url, ee, price, picture, venue.name AS venue " +
				"FROM book, author, venue, book_author, book_venue " +
				"WHERE book_author.author_id = author.id AND " +
				"book_author.book_id = book.id AND " +
				"book_venue.book_id = book.id AND " +
				"book_venue.venue_id = venue.id AND " +
				selectionClause +
				"ORDER BY title " +
				"LIMIT 10 OFFSET " + page*10);

		bookStatement.setString(1, searchTerm);
		bookStatement.execute();
		return bookStatement.getResultSet();
	}

	private ResultSet getSingleBookResultSet(int bookId) throws SQLException {
		PreparedStatement bookStatement = connection.prepareStatement("SELECT DISTINCT(book.id), publicationtype, publicationdate, " +
				"title, pages, url, ee, price, picture, venue.name AS venue " +
				"FROM book, author, venue, book_author, book_venue " +
				"WHERE book_author.author_id = author.id AND " +
				"book_author.book_id = book.id AND " +
				"book_venue.book_id = book.id AND " +
				"book_venue.venue_id = venue.id AND " +
				"book.id = ?");

		bookStatement.setInt(1, bookId);
		bookStatement.execute();
		return bookStatement.getResultSet();
	}

	public List<BookBean> getActiveUserWishes(int userId) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			ArrayList<BookBean> wishedBooks = new ArrayList<>();
			PreparedStatement wishStatement = connection.prepareStatement("SELECT book_id " +
					"FROM user, user_wishes " +
					"WHERE user.id = user_wishes.user_id " +
					"AND user_wishes.active = TRUE");

			wishStatement.execute();
			ResultSet rs = wishStatement.getResultSet();
			while(rs.next()) {
				wishedBooks.add(getSingleBook(rs.getInt("book_id"), false));
			}
			return wishedBooks;
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.closeConnection();
		}
		return null;
	}

	private String getSelectionClause(SearchController.SEARCHATTRIBUTE attr) {
		switch(attr) {
			case TITLE: return "book.title RLIKE ? ";
			case VENUE: return "venue.name RLIKE ? ";
			case YEAR: return "publicationdate = ? ";
			default: return null;
		}
	}


	private ArrayList<String> getAuthors(int bookId) throws SQLException {
		ArrayList<String> authors = new ArrayList<>();

		PreparedStatement authorStatement = connection.prepareStatement("SELECT name FROM author, book_author " +
				"WHERE book_author.author_id = author.id AND " +
				"book_author.book_id = ?");
		authorStatement.setInt(1, bookId);
		authorStatement.execute();

		ResultSet rs = authorStatement.getResultSet();

		while(rs.next()) {
			authors.add(rs.getString("name"));
		}
		return authors;


	}

	private BookBean bookFromResultSet(ResultSet rs) throws SQLException {
		BookBean resultBean = new BookBean();
		int bookId = rs.getInt("id");

		resultBean.setId(bookId);
		resultBean.setPublicationType(rs.getString("publicationtype"));
		resultBean.setPublicationDate(rs.getString("publicationdate"));
		resultBean.setAuthor(getAuthors(bookId));
		resultBean.setTitle(rs.getString("title"));
		resultBean.setPages(rs.getString("pages"));
		resultBean.setUrl(rs.getString("url"));
		resultBean.setEe(rs.getString("ee"));
		resultBean.setPrice(rs.getString("price"));
		resultBean.setPicture(rs.getString("picture"));
		resultBean.setVenues(rs.getString("venue"));

		return resultBean;
	}

	public UserBean verifyPassword(String username, String password){

		boolean result = false;
		UserBean user = new UserBean();;


		try{
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			statement = connection.createStatement();

			java.sql.PreparedStatement add = connection.prepareStatement(
					"SELECT * FROM user WHERE username = ? AND password = ?");
			add.setString(1, username);
			add.setString(2, password);

			ResultSet rs = add.executeQuery();

			if(rs.next()){
				user.setBirthYear(rs.getInt("yearofbirth"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCreditCard(rs.getString("creditCardNumber"));
				user.setId(rs.getInt("id"));

				System.out.println(user.toString());
				result = true;
			}
			else
				result = false;

		}catch (SQLException e){
			System.out.println(e.getMessage());
		}finally{
			this.closeConnection();
			if(result){
				return user;
			}
			else
				return null;
		}


	}



	private int addAuthor(String author){

		try {
			statement = connection.createStatement();

			PreparedStatement add = connection.prepareStatement("SELECT * FROM author WHERE name='"+author+"'", Statement.RETURN_GENERATED_KEYS);

			add.execute();

			ResultSet entries = add.getResultSet();

			if(!entries.next()){
				add = connection.prepareStatement("INSERT INTO author(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
				add.setString(1, author);
				add.executeUpdate();

				ResultSet generatedKeys = add.getGeneratedKeys();

				if(generatedKeys.next()){
					System.out.println(generatedKeys.getInt(1));
					return generatedKeys.getInt(1);
				}
			}
			else{return entries.getInt(1);}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}


	private int addBookAuthorRelation(int bookID, int authorID){

		try {
			statement = connection.createStatement();

			PreparedStatement add = connection.prepareStatement("INSERT INTO book_author(book_id, author_id) VALUES(?,?)");

			add.setInt(1, bookID);
			add.setInt(2, authorID);

			int affectedRows = add.executeUpdate();

			if(affectedRows >= 1){
				return 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}






	public int addUser(UserBean user) {
		try {
			statement = connection.createStatement();
			java.sql.PreparedStatement add = connection.prepareStatement(
					"INSERT INTO user (username, password,email,nickname,firstname,lastname,creditcardnumber,yearofbirth,address) VALUES(?, ?, ? ,? ,?, ?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			add.setString(1, user.getUsername());
			add.setString(2, user.getPassword());
			add.setString(3, user.getEmail());
			add.setString(4, user.getNickname());
			add.setString(5, user.getFirstName());
			add.setString(6, user.getLastName());
			add.setString(7, user.getCreditCard());
			add.setInt(8, user.getBirthYear());
			add.setString(9, user.getAddress());



			int affectedRows = add.executeUpdate();



			ResultSet generatedKeys = add.getGeneratedKeys();
			if (generatedKeys.next()) {

				System.out.println(generatedKeys.getInt(1));
				return generatedKeys.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("failed to add user to database");
			e.printStackTrace();
			return -1;
		} finally {
		}

		return -1;
	}


	public ArrayList<UserBean> getAllUsers(){
		ArrayList<UserBean> users = new ArrayList<>();

		try {
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(
					"SELECT id,username,email,nickname,firstname,lastname,creditcardnumber,yearofbirth FROM user;");
			while(rs.next()){
				UserBean user = new UserBean();
				user.setBirthYear(rs.getInt("yearofbirth"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCreditCard(rs.getString("creditCardNumber"));
				user.setId(rs.getInt("id"));

				users.add(user);
			}
			return users;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally {
			return users;
		}

	}

	public void deleteUser(int id){

		try {
			String deleteSQL = "DELETE FROM user WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		}catch (SQLException e){
			System.out.println(e.getMessage());
		}finally {

		}

	}

	private int addVenue(String venue){
		try{
			PreparedStatement add = connection.prepareStatement("SELECT * FROM venue WHERE name='"+venue+"'", Statement.RETURN_GENERATED_KEYS);

			add.execute();

			ResultSet entries = add.getResultSet();

			if(!entries.next()){
				add = connection.prepareStatement("INSERT INTO venue(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
				add.setString(1, venue);
				add.executeUpdate();


				ResultSet generatedKeys = add.getGeneratedKeys();

				if(generatedKeys.next()){
					System.out.println(generatedKeys.getInt(1));
					return generatedKeys.getInt(1);
				}
			}
			else{return entries.getInt(1);}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	private int addVenueBook(int bookID, int venueID){


		try {
			statement = connection.createStatement();

			PreparedStatement add = connection.prepareStatement("INSERT INTO book_venue(book_id, venue_id) VALUES(?,?)");

			add.setInt(1, bookID);
			add.setInt(2, venueID);

			int affectedRows = add.executeUpdate();

			if(affectedRows >= 1){
				return 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}


	public int updateUser(UserBean user){

		try {
			statement = connection.createStatement();

			PreparedStatement update = connection.prepareStatement("UPDATE user SET username=?, password=?, email=?, nickname=?, firstname=?, lastname=?, yearofbirth=?, address=?, creditcardnumber=? WHERE id="+user.getId());

			update.setString(1, user.getUsername());
			update.setString(2, user.getPassword());
			update.setString(3, user.getEmail());
			update.setString(4, user.getNickname());
			update.setString(5, user.getFirstName());
			update.setString(6, user.getLastName());
			update.setInt  (7,  user.getBirthYear());
			update.setString(8, user.getAddress());
			update.setString(9, user.getCreditCard());


			int affectedRows = update.executeUpdate();

			if(affectedRows >= 1){
				return 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public void addWish(int userId, int bookId) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			PreparedStatement wishStatement = connection.prepareStatement("INSERT INTO user_wishes(user_id, book_id, active) VALUES(?, ?, TRUE)");
			wishStatement.setInt(1, userId);
			wishStatement.setInt(2, bookId);

			int affectedRow = wishStatement.executeUpdate();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.closeConnection();
		}
	}

	public void removeWish(int userId, int bookId) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			PreparedStatement wishStatement = connection.prepareStatement("DELETE FROM user_wishes WHERE user_id = ? AND book_id = ?");
			wishStatement.setInt(1, userId);
			wishStatement.setInt(2, bookId);

			int affectedRow = wishStatement.executeUpdate();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.closeConnection();
		}
	}

	public List<BookBean> getBooksForUser(int userId) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			PreparedStatement bookStatement = connection.prepareStatement("SELECT book_id FROM user, book_user " +
					"WHERE user_id = id AND " +
					"id = ?");
			bookStatement.setInt(1, userId);
			bookStatement.execute();
			ResultSet rs = bookStatement.getResultSet();

			List<BookBean> books = new ArrayList<>();
			while(rs.next()) {
				books.add(getSingleBook(rs.getInt("book_id"), false));
			}
			return books;

		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.closeConnection();
		}
		return null;
	}

	public void removeBookFromSale(int bookId, int userId) {
		try {
			if(this.connection == null || this.connection.isClosed()) {
				this.connect();
			}
			PreparedStatement wishStatement = connection.prepareStatement("DELETE FROM book_user WHERE book_id = ? AND user_id = ?");
			wishStatement.setInt(1, bookId);
			wishStatement.setInt(2, userId);

			int affectedRow = wishStatement.executeUpdate();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.closeConnection();
		}
	}
}