import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends  Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread Chat;
    Server(){
        textField=new TextField();
        textField.setBounds(100,100,200,50);

        textArea=new TextArea();
        textArea.setBounds(100,200,300,150);

        send=new Button("Send");
        send.setBounds(150,400,80,50);

        send.addActionListener(this);
        try {
            serverSocket = new ServerSocket(2000);
            socket=serverSocket.accept();
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception E){

        }
        add(textField);
        add(textArea);
        add(send);
        Chat=new Thread(this);
        Chat.setDaemon(true);//high priotity
        Chat.start();



        setSize(500,500);
        setTitle("Chat Server");
        setLayout(null);
        setBackground(Color.GRAY);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg=textField.getText();
        textArea.append("Server:"+msg+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
        catch (IOException ex){

        }

    }

    public static void main(String[] args) {
           new Server();
    }

    @Override
    public void run() {
       while (true){
           try{
               String msg=dataInputStream.readUTF();
               textArea.append("Client:"+msg+"\n");
           } catch (Exception e) {
               new Server();
           }
       }
    }
}
