package Service;

import DAO.*;
import Model.*;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    //helper method whenever a message is created or updated
    public boolean messageValidation(Message message){
        boolean messageCreated = false;
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            System.out.println("The message cannot be blank and must have no more than 255 characters!");
        } else if (accountDAO.getAccountById(message.getPosted_by()) == null) {
            System.out.println("Message cannot be created by an unauthorized user!");
        } else {
            messageCreated = true;
        }

        return messageCreated;
    }
    
    //create
    public void createMessage(Message message) {
        if(messageValidation(message)){
            System.out.println("Message created!");
            messageDAO.createMessage(message);
        }
    }
    
    //read
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserID(int account_id){
        if (accountDAO.getAccountById(account_id) == null) {
            System.out.println("Account with ID " + account_id + " does not exist.");
        }

        return messageDAO.getAllMessagesByUser(account_id);
    }

    //update
    public boolean updateMessage(int id, Message message){
        if(messageValidation(message)){
            messageDAO.updateMessage(id, message);
            message.setMessage_id(id);
            return true;
        }
        return false;
    }

    //delete
    public boolean deleteMessage(int id){
        if(messageDAO.getMessageById(id) != null){
            messageDAO.deleteMessage(id);
            System.out.println("Message ID " + id + " has been deleted!");
            return true;
        }
        else{
            System.out.println("Message ID " + id + " does not exist!");
            return false;
        }
    }
}
