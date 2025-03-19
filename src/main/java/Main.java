import java.util.ArrayList;
import java.util.List;

import Controller.SocialMediaController;
import DAO.*;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);


        //THE FOLLOWING LINES OF CODE ARE TEST CODE

        ConnectionUtil.getConnection();
        ConnectionUtil.resetTestDatabase();
        AccountDAO accountDAO = new AccountDAO();
        MessageDAO messageDAO = new MessageDAO();

        Account user1 = new Account( "ld", "123");
        Account user2 = new Account(3, "well", "456");
        Account user3 = new Account(4, "john", "12356");

        Message message1 = new Message(1, "Hello World", 111119283);
        Message message2 = new Message(2, "This is a test", 4355693);
        Message message3 = new Message(1, "Today's gonna be a good day", 53295540);

        //read
        System.out.println("Initial array");
        for(Message message : messageDAO.getAllMessages()){
            System.out.println(message);
        }
        //create
        Message test1 = messageDAO.createMessage(message1);
        //should not work since there is no account with id 2
        Message test2 = messageDAO.createMessage(message2);
        System.out.println("After add");

        for(Message message : messageDAO.getAllMessages()){
            System.out.println(message);
        }

        System.out.println("Reattempt to add after Account with id 2 has been created");
        Account add = accountDAO.createAccount(user1);
        Message test3 = messageDAO.createMessage(message2);

        for(Message message : messageDAO.getAllMessages()){
            System.out.println(message);
        }
        //update
        messageDAO.updateMessage(2, message3);
        System.out.println("After update");
        for(Message message : messageDAO.getAllMessages()){
            System.out.println(message);
        }
        //delete
        messageDAO.deleteMessage(1);
        System.out.println("After delete");
        for(Message message : messageDAO.getAllMessages()){
            System.out.println(message);
        }
        
    }
}
