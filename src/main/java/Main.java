import java.util.ArrayList;
import java.util.List;

import Controller.SocialMediaController;
import DAO.*;
import Model.Account;
import Model.Message;
import Service.*;
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

        AccountService accountService = new AccountService();
        MessageService messageService = new MessageService();

        Account user1 = new Account( "ld", "12345");
        Account user2 = new Account(3, "", "456");
        Account user3 = new Account(4, "john", "");
        Account user4 = new Account(7, "ladarion", "31349");
        Account user5 = new Account( "wells", "56498");

        Message message1 = new Message(1, "Hello World", 111119283);
        Message message2 = new Message(2, "This is a test", 4355693);
        Message message3 = new Message(1, "", 53295540);

    


    }
}
