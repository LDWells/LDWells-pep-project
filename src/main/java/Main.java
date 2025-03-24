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

        Account user1 = new Account( "ld", "123");
        Account user2 = new Account(3, "", "456");
        Account user3 = new Account(4, "john", "");
        Account user4 = new Account(7, "ladarion", "3134");
        Account user5 = new Account( "wells", "564");

        Message message1 = new Message(1, "Hello World", 111119283);
        Message message2 = new Message(2, "This is a test", 4355693);
        Message message3 = new Message(1, "Today's gonna be a good day", 53295540);

        //read
        System.out.println(accountService.getAllAccounts());
        

        //should not read
        System.out.println(accountService.getAccountByID(5));

        //create
       accountService.createAccount(user1);
       System.out.println("After create:");
       System.out.println(accountService.getAllAccounts());

        //should not create
       accountService.createAccount(user2);
        accountService.createAccount(user3);

        //update
        System.out.println(accountService.updateAccount(2, user5));
        //should not update
        System.out.println(accountService.updateAccount(2, user4));
        System.out.println(accountService.updateAccount(110, user5));


        //delete
        System.out.println(accountService.deleteAccount(2));
        //should not delete
        System.out.println(accountService.deleteAccount(120));
    }
}
