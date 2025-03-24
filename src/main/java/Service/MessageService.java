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
    public Message createMessage(Message message) {
        if(messageValidation(message)){
            System.out.println("Message created!");
            messageDAO.createMessage(message);
            return message;
        }
        return null;
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


    public Message getMessageByID(int message_id){

        return messageDAO.getMessageById(message_id);
    }

    //update
    public Message updateMessage(int id, Message message) {
        if (messageValidation(message) && messageDAO.getMessageById(id) != null) {
            Message updatedMessage = messageDAO.updateMessage(id, message);
            return updatedMessage;  // Ensure the DAO returns the updated message with the correct ID.
        }
        return null;
    
}

    //delete
    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }
}
