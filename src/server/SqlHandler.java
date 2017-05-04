package server;

import Beans.BookBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SqlHandler {

	private String url = "jdbc:mysql://127.0.0.1:3306/webApp";
	private String username = "root";
	private String passwd = "password";
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

			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/webApp", connectionProps);
			System.out.println("connection to Mysql established");
		} catch (SQLException e) {
			System.out.println("Could not establish a connection to the SQL database");
			e.printStackTrace();
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


		try {
			statement = connection.createStatement();

			PreparedStatement add = connection.prepareStatement(
					"INSERT INTO book (publicationtype, publicationdate,title,pages,url,ee,price,picture) VALUES(?, ?, ? ,? ,?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
			add.setString(1, book.getPublicationType());
			add.setString(2, book.getPublicationDate());
			add.setString(3, book.getTitle());
			add.setString(4, book.getPages());
			add.setString(5, book.getUrl());
			add.setString(6, book.getEe());
			add.setString(7, book.getPrice());
			add.setString(8, book.getPicture());

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
			closeConnection();
		}

		return -1;
	}


	public List<BookBean> findBooksByTitle(String searchTerm) {
		List<BookBean> resultList = new ArrayList<>();
		try {
			PreparedStatement bookStatement = connection.prepareStatement("SELECT DISTINCT(book.id), publicationtype, publicationdate, " +
                    "title, pages, url, ee, price, picture, venue.name AS venue " +
                    "FROM book, author, venue, book_author, book_venue " +
                    "WHERE book_author.author_id = author.id AND " +
                    "book_author.book_id = book.id AND " +
                    "book_venue.book_id = book.id AND " +
                    "book_venue.venue_id = venue.id AND " +
                    "book.title RLIKE ?");
			bookStatement.setString(1, searchTerm);

			bookStatement.execute();
			ResultSet rs = bookStatement.getResultSet();
			while(rs.next()) {
				resultList.add(BookFromResultSet(rs));
			}

		} catch (SQLException e) {
			System.err.println("An error occurred while selecting books by title");
			e.printStackTrace();
		}
		return resultList;
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

	private BookBean BookFromResultSet(ResultSet rs) throws SQLException {
        BookBean resultBean = new BookBean();
        int bookId = rs.getInt("id");
        ArrayList<String> authors = getAuthors(bookId);
        String[] authorArray = new String[authors.size()];

        resultBean.setId(bookId);
        resultBean.setPublicationType(rs.getString("publicationtype"));
        resultBean.setPublicationDate(rs.getString("publicationdate"));
        resultBean.setAuthor(authors.toArray(authorArray));
        resultBean.setTitle(rs.getString("title"));
        resultBean.setPages(rs.getString("pages"));
        resultBean.setUrl(rs.getString("url"));
        resultBean.setEe(rs.getString("ee"));
        resultBean.setPrice(rs.getString("price"));
        resultBean.setPicture(rs.getString("picture"));
        resultBean.setVenues(rs.getString("venue"));

        return resultBean;
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
