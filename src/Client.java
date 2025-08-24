import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends  Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread Chat;
    Client(){
        textField=new TextField("Enter a message");
        textField.setBounds(100,100,200,50);
        textArea=new TextArea();
        textArea.setBounds(100,200,300,150);
        send=new Button("Send");
        send.setBounds(150,400,80,50);

        send.addActionListener(this);
        try {
            socket = new Socket("localhost",2000);

            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception E){

        }
        add(textField);
        add(textArea);
        add(send);
        Chat=new Thread(this);
        Chat.start();



        setSize(500,500);
        setTitle("Chat Client");
        setLayout(null);
        setBackground(Color.PINK);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg=textField.getText();
        textArea.append("Client:"+msg+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
        catch (IOException ex){

        }

    }

    public static void main(String[] args) {
          new Client();
    }

    @Override
    public void run() {
        while (true){
            try{
                String msg=dataInputStream.readUTF();
                textArea.append("Server:"+msg+"\n");
            } catch (Exception e) {

            }
        }
    }
}
