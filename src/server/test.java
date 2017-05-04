package server;

import Beans.BookBean;

import java.util.ArrayList;

/**
 * Created by david on 03/05/2017.
 */
public class test {

    public static void main(String[] args){

        ArrayList<String> authors = new ArrayList<String>();
        authors.add("David");
        authors.add("Dildo");
        authors.add("Satan");
        authors.add("St.Peter");
        BookBean b = new BookBean("BOOK", "2998", "heaven","2901","How to become nothing","666","dfass","ww.com","nei","669","f", authors);
        SqlHandler s = new SqlHandler();
        s.connect();
        s.addBook(b);
        s.closeConnection();
    }
}
