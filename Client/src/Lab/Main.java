package Lab;

import Lab.Communication.MessageFormer;
import Lab.Communication.MessageReceiver;
import Lab.Communication.MessageSender;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String ipsum = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna. Nunc viverra imperdiet enim. Fusce est.\n" +
                "Vivamus a tellus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci. Aenean nec lorem.\n";
        MessageFormer mf = new MessageFormer(ipsum);
        MessageSender ms = new MessageSender(mf);
        ms.sendMessage();
        MessageReceiver mr = new MessageReceiver();
        String answer = mr.receiveMessage();
        if(answer.equals(ipsum)){
            System.out.println("ОНО ЖИВОЕ!!!!!");
        }
        else
            System.out.println("FUCKKKKKKKKKKKKKK!!!!!!!!!!!!!");
    }
}
