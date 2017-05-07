package controllers;

import Beans.BookBean;
import Beans.UserBean;
import server.SqlHandler;

import java.util.HashSet;

/**
 * Created by david on 07/05/2017.
 */
public class PurchaseController {

    private SqlHandler handler;
    private HashSet<Integer> bookIds;
    private Email email;

    public PurchaseController(HashSet<Integer> bookIds){
        this.handler = new SqlHandler();
        this.bookIds = bookIds;
        this.email = new Email();
        notifySellers();
    }


    public void notifySellers() {

        for (int bookID : bookIds) {

            handler.connect();
            int seller = handler.getBooksellerId(bookID);
            handler.closeConnection();

            if (seller != -1) {

                handler.connect();
                UserBean u = handler.findUserById(Integer.toString(seller));
                BookBean b = handler.getSingleBook(bookID, false);
                handler.closeConnection();
                email.sendEmail(u.getEmail(), "Bookseller Admin", "Someone has purchased your book", b.getTitle(), "localhost");
            }
        }
    }
}
