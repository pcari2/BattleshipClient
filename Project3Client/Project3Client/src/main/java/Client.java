import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;

/*
Paolo Carino CS 342 Project 3
	A messaging application between clients and the server, where clients can
	message each other as a whole group, or to individual clients they choose.
	Additionally, the server has a GUI that keeps track of all the inputs and
	messages being sent
 */

public class Client extends Thread{


	Socket socketClient;

	ObjectOutputStream out;
	ObjectInputStream in;

	private Consumer<Message> callback;
	private String username;
	boolean usernameChangeTaken = false;
	List<String> connectedUsernames;
	GuiClient guiClient;
	Client(Consumer<Message> call){
		callback = call;
	}

	public void run() {

		try {
			socketClient= new Socket("127.0.0.1",5555);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		Message requestID = new Message();
		requestID.setMessageType(Message.MessageType.REQUEST_USERNAME);
		send(requestID);

		while(true) {

			try {
				Message message = (Message) in.readObject();
				callback.accept(message);
			} catch(Exception e) {}
		}

	}

	public void send(Message data) {

		try {
			out.writeObject(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	public List<String> getConnectedUsernames() {
		return connectedUsernames;
	}
}