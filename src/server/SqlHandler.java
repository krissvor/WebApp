package server;

import Beans.BookBean;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import model.Book;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class 	SqlHandler {

	private String url = "127.0.0.1:8081";
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
			connection = (Connection) DriverManager.getConnection(url, username, passwd);
		} catch (SQLException e) {
			System.out.println("Could not establish a connection to the SQL database");
			e.printStackTrace();
		}
		System.out.println("connection to Mysql established");
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

			java.sql.PreparedStatement add = connection.prepareStatement(
					"INSERT INTO book (publicationtype, publicationdate,year,title,pages,journal,url,ee,price,picture) VALUES(?, ?, ?, ?, ? ,? ,?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
			add.setString(1, book.getPublicationType());
			add.setString(2, book.getPublicationDate());
			add.setString(3, book.getYear());
			add.setString(4, book.getTitle());
			add.setString(5, book.getPages());
			add.setString(6, book.getJournal());
			add.setString(7, book.getUrl());
			add.setString(8, book.getEe());
			add.setString(9, book.getPrice());
			add.setString(10, book.getPicture());


			int affectedRows = add.executeUpdate();


			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
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
