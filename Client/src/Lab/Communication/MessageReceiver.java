package Lab.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class MessageReceiver {
    public String receiveMessage(){
        MessageFormer mf = new MessageFormer();
        try {
            InetSocketAddress address = new InetSocketAddress("localhost",8989);
            DatagramSocket ds = new DatagramSocket(address);
            byte[] message = new byte[100];
            DatagramPacket packet = new DatagramPacket(message,100);
            while(!mf.hasEnded) {
                ds.receive(packet);
                mf.formFromByte(message);
            }
            ds.close();
        }
        catch (SocketException e){
            System.out.println("Сокет не открылся");
        }
        catch (IOException e){
            System.out.println("Не удалось получить сообщение");
        }
        return mf.line;
    }
}

