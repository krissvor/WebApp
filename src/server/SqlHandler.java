package server;

import Beans.BookBean;
import Beans.UserBean;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import controllers.SearchController;

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

		if(this.connection == null) {
			this.connect();
		}

		List<BookBean> resultList = new ArrayList<>();
		try {
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

	public void verifyPassword(String username, String password){

		int id;

		try{
			statement = connection.createStatement();

			java.sql.PreparedStatement add = connection.prepareStatement(
					"SELECT * FROM user WHERE username = ? AND password = ?");
			add.setString(1, username);
			add.setString(2, password);

			ResultSet rs = add.executeQuery();

			if(rs.next()){
				UserBean user = new UserBean();
				user.setBirthYear(rs.getInt("yearofbirth"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCreditCard(rs.getString("creditCardNumber"));
				user.setId(rs.getInt("id"));

				System.out.println(user.toString());
			}
			else
				System.out.println("denne finnes ikke\n");

		}catch (SQLException e){
			System.out.println(e.getMessage());
		}finally{

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
	}





/*
	public boolean validate(String Email, String password){

		Boolean valid = false;

		connect();

		if(connection != null){
			try {
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(
						"SELECT Email FROM users WHERE " +
								"Email=" +  "'" + Email + "'" + " AND PasswordHash= " + "'" + password +  "'");

				if(rs.next()){
					System.out.println("Yep, correct");
					valid = true;
				}

			} catch (SQLException e) {
				System.out.println("sql exception while retrieveing username");
				e.printStackTrace();
			}

			finally{
				closeConnection();
			}
		}
		return valid;
	}

	public boolean newUser(String Email, String Password, String Username){

		String email;
		String pwd;
		String uname;

		if(Email != null){
			email = Email;
		}

		else return false;

		if(Password != null){
			pwd = Password;
		}

		else return false;

		if(Username != null){
			uname = Username;
		}
		else return false;

		connect();

		try {
			statement = connection.createStatement();
			java.sql.PreparedStatement add = connection.prepareStatement(
					"INSERT INTO users (Email, Username, PasswordHash) VALUES(?, ?, ?)");
			add.setString(1, email);
			add.setString(2, uname);
			add.setString(3, pwd);

			add.executeUpdate();

			System.out.println("sucsessfully added user to database: " + email);
			return true;

		} catch (SQLException e) {
			System.out.println("failed to add user to database");
			e.printStackTrace();
			return false;
		}

		finally{
			closeConnection();
		}
	}

	public int getUserIdFromUsername(String Email){
		int userID = 0;

		connect();

		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT UserID FROM users WHERE Email = " + "'" + Email + "'");

			rs.next();
			userID = rs.getInt("UserID");

		} catch (SQLException e) {
			System.out.println("failed to get user id from username");
			e.printStackTrace();
		}

		finally{
			closeConnection();
		}

		return userID;
	}

	public boolean addFriends(String username, String username2){

		connect();

		int u1 = getUserIdFromUsername(username);
		int u2 = getUserIdFromUsername(username2);

		try{
			java.sql.PreparedStatement add = connection.prepareStatement("INSERT INTO friendRelations(UID1, UID2) VALUES (?,?)");
			add.setInt(1, u1);
			add.setInt(2, u2);
			add.executeUpdate();

			java.sql.PreparedStatement add2 = connection.prepareStatement("INSERT INTO friendRelations(UID1, UID2) VALUES (?,?)");
			add.setInt(1, u2);
			add.setInt(2, u1);
			add2.executeUpdate();

			System.out.println("sucsessfully created friendship");
			return true;
		}

		catch (SQLException e){
			System.out.println("could not insert new friendship into friends");
			e.printStackTrace();
		}

		return false;
	}
	
	public ArrayList<User> getFriends(String username){
		
		int userid = getUserIdFromUsername(username);
		ArrayList<User> friends = new ArrayList<User>();
		
		connect();
		
		try {
			
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(
							"SELECT Username, Email FROM users"
							+" INNER JOIN friendRelations ON friendRelations.UID2 = users.UserID"
							+" WHERE friendRelations.UID1 = " + "'" + userid + "'");
			
			while(rs.next()){
				User u = new User(rs.getString("Username"), rs.getString("Email"));
				friends.add(u);
			}
			
		} catch (SQLException e) {
			System.out.println("failed to fetch friends from database.");
			e.printStackTrace();
		}
		
		finally{
			closeConnection();
		}
		
		for(User u : friends){
			System.out.println(u.getEmail());
		}
		
		return friends;
	}
	
	public User getUser(String searchKey, String columnName){
		
		String colName = null;
		
		if(columnName.equals(this.Username)){
			colName = columnName;
		}
		
		else if(columnName.equals(this.email)){
			colName = columnName;
		}
		
		else{
			return null;
		}
		
		User user = null;
		
		connect();
		
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT Email, Username FROM users "
					+ "WHERE " + colName +  "=" + "'" + username + "'" );
			
			while(rs.next()){
				user = new User(rs.getString(this.Username), rs.getString(this.email));
			}
			
			rs.close();
			
			
		} catch (SQLException e) {
			System.out.println("failed to get user");
			e.printStackTrace();
		}
		
		finally{
			
			closeConnection();
		}
		
		return user;
	}
	
	public boolean createGroup(String groupName, ArrayList<User> users){
		
		connect();
		int key = 0;
		
		try {
			
			java.sql.PreparedStatement add = null;
			add = connection.prepareStatement("INSERT INTO chatGroup(ChatGroupName) VALUES (?)", add.RETURN_GENERATED_KEYS);
			
			add.setString(2, groupName);
			key = add.executeUpdate();
			
			if(key != 0 && key > 0){
				
				for(User u : users){
					
					add = connection.prepareStatement("INSERT INTO chatGroup_users (UID, GID) VALUES(?, ?)", add.RETURN_GENERATED_KEYS);
					add.setInt(1, key);
					add.setInt(2, getUserIdFromUsername(u.getEmail()));
					add.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			System.out.println("failed to createGroup");
			e.printStackTrace();
		}
		finally{
			closeConnection();
		}
		
		return false;
	}
	
	public ArrayList<Group> getGroups(String Email){
		ArrayList<Group> groups = new ArrayList<Group>();
		
		int userID = getUserIdFromUsername(Email);
		
		connect();
		
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT ChatGroupID, ChatGroupName FROM "
					+ "chatGroup INNER JOIN chatGroup_users ON UID = " + "'" + userID +"'" + "AND ChatGroupID = GID");
			
			while(rs.next()){
				
				int groupID = rs.getInt("ChatGroupID");
				String groupName = rs.getString("ChatGroupName");
				
				Group g = new Group(groupID, groupName);
				
				groups.add(g);
			}
			
			for(Group gr : groups){
				
						rs = statement.executeQuery("SELECT UserID, Username, Email FROM users "
						+ "INNER JOIN chatGroup_users ON UID = UserID"
						+ " WHERE GID = " + "'" + gr.getID() + "'");
				
				
				
				while (rs.next()){
					gr.addUser(new User(rs.getString("UserName"), rs.getString("Email")));
				}
			}
			
		} catch (SQLException e) {
			System.out.println("could not get groups");
			e.printStackTrace();
		}
		
		finally{
			closeConnection();
		}
		
		return groups;
	}
	*/
