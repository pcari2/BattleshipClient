
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{

	
	TextField c1;
	TextField usernameField;
	Button sendMessageButton;
	Button setUsernameButton;
	String username;
	HashMap<String, Scene> sceneMap;
	VBox clientBox;
	VBox usernameBox;
	HBox clientAndUsername;
	Client clientConnection;
	ComboBox<String> recipientDropdown;
	
	ListView<String> listItems2;
	ListView<String> connectedUsersListView;
	HBox sendAndRecipients;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientConnection = new Client(data->{
				Platform.runLater(()->{
					listItems2.getItems().add(data.toString());

					// Updates ListView of online clients
					if (clientConnection.connectedUsernames != null) {
						connectedUsersListView.getItems().clear();
						connectedUsersListView.getItems().add("Online:");
						connectedUsersListView.getItems().addAll(clientConnection.connectedUsernames);
					}

					// Updates ComboBox of online clients
					if (clientConnection.connectedUsernames != null) {
						recipientDropdown.getItems().clear();
						recipientDropdown.getItems().add("All");
						recipientDropdown.getItems().addAll(clientConnection.connectedUsernames);
					}

			});
		});
							
		clientConnection.start();

//---------------------------------------------------------------------------------------------------------------------

		// Initializations
		listItems2 = new ListView<String>();
		usernameField = new TextField();
		connectedUsersListView = new ListView<String>();
		recipientDropdown = new ComboBox<>();

//---------------------------------------------------------------------------------------------------------------------

		// USERNAME SECTION
		setUsernameButton = new Button("Set Username");
		setUsernameButton.setOnAction(e -> {
			username = usernameField.getText();
			if (!username.isEmpty()) { // if username field is not empty

				// Send username to the server
				Message usernameSend = new Message(Message.MessageType.USER_ID_CREATE, username);
				clientConnection.send(usernameSend);

				// pause in time for username to register
				try {
					Thread.sleep(300);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				// if the username is not taken, make the box uneditable and the button disabled
				if (!clientConnection.usernameChangeTaken) {
					usernameField.setEditable(false);
					setUsernameButton.setDisable(true);
					clientConnection.setUsername(username); // set the username for the client
				}
				else { // if username is taken
					username = null;
				}
			}
		});

//---------------------------------------------------------------------------------------------------------------------

		// TEXTBOX MESSAGE SEND
		c1 = new TextField();
		sendMessageButton = new Button("Send");
		sendMessageButton.setOnAction(e->{

			String selectedUser = recipientDropdown.getValue();
			String messageContent = c1.getText();

			// if the Selected user is all or a username then change type to Group Message or Private message
			if (selectedUser == null) {
				listItems2.getItems().add("Message not sent, choose recipient.");
			}
			else if (selectedUser.equals("All")) { // sends to all
				Message clientMessage = new Message(Message.MessageType.GROUP_MESSAGE, username, messageContent);
				clientConnection.send(clientMessage);
			}
			else if (clientConnection.connectedUsernames.contains(selectedUser)) {
				Message clientMessage = new Message(Message.MessageType.PRIVATE_MESSAGE, username, selectedUser, messageContent);
				clientConnection.send(clientMessage);
			}
			else {
				Message clientMessage = new Message(Message.MessageType.PRIVATE_MESSAGE, username, username, messageContent);
				clientConnection.send(clientMessage);
			}

			c1.clear();

		});

//---------------------------------------------------------------------------------------------------------------------

		// Scene Establishing
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("client",  createClientGui());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		primaryStage.setScene(sceneMap.get("client"));
		primaryStage.setTitle("Client");
		primaryStage.show();
		
	}
	

	
	public Scene createClientGui() {
		usernameBox = new VBox(10, usernameField, setUsernameButton, connectedUsersListView);
		HBox sendAndRecipients = new HBox(10, sendMessageButton, recipientDropdown);
		clientBox = new VBox(10, c1, sendAndRecipients,listItems2);
		clientAndUsername = new HBox(10, usernameBox, clientBox);
		clientBox.setStyle("-fx-background-color: blue;"+"-fx-font-family: 'serif';");
		return new Scene(clientAndUsername, 500, 300);
		
	}

}
