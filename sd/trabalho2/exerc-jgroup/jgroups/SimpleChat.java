import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.*;
import java.util.*;  
import java.util.List;
import java.util.LinkedList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import java.io.IOException;

public class SimpleChat implements Receiver {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<>();

    public void setUsername(String name){
        this.user_name = name;
    }

    public void viewAccepted(View new_view) {
        System.out.println("**** View Activity on user: " + new_view + " ****");
    }

    public void receive(Message msg) {
        String line= "<< " + msg.getSrc() + msg.getObject();
        System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        List<String> list=Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
        }
        System.out.println("received state (" + list.size() + " messages in chat history):");
        list.forEach(System.out::println);
    }


    private void start() throws Exception {
        channel=new JChannel().setReceiver(this);
        String server_name = "testserver";
        channel.connect(server_name);
        channel.getState(null, 10000);
        System.out.println("Welcome to " + server_name);
        String os = System.getProperty("os.name");  
        if (os.contains("Windows")){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print(">> ");System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("sair") || line.startsWith("fechar")) {
                    break;
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();  
                line=" -> " + user_name + " @ " + dtf.format(now) + " >> " + line;
                Message msg=new ObjectMessage(null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.print("Tell us your new username: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        SimpleChat chat = new SimpleChat();
        chat.setUsername(name);
        System.out.println("Calling JGroups...");
        chat.start();
    }
}
