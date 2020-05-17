package Lab.Communication;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class MessageSender {
    private byte[] message;
    public MessageSender(MessageFormer mf){
        this.message=mf.message;
    }
    public void sendMessage(){
        try{
            byte[] buffer = new byte[100];
            SocketAddress address = new InetSocketAddress("localhost",8888);
            InetSocketAddress addr = new InetSocketAddress("localhost",8989);
            DatagramSocket socket = new DatagramSocket(addr);
            while(message.length>0){
                if(message.length>100){
                    System.arraycopy(message,0,buffer,0,100);
                    message= Arrays.copyOfRange(message,100,message.length);
                }
                else {
                    System.arraycopy(message, 0, buffer, 0, message.length);
                    message=new byte[0];
                }
                DatagramPacket packet = new DatagramPacket(buffer,100,address);
                socket.send(packet);
            }
            socket.close();
        }
        catch (UnknownHostException e){
            System.out.println("Не удалось подключиться к серверу");
        }
        catch (SocketException e){
            System.out.println("Сокет занят");
        }
        catch (IOException e){
            System.out.println("Сервер не принимает сообщения");
        }
    }
}
