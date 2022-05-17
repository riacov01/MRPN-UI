package rpnsim.application.editor;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rpnsim.application.ScrollArea;
import rpnsim.application.rpn.EditableNode;
import rpnsim.application.rpn.Place;
import rpnsim.application.rpn.RPN;
import rpnsim.application.rpn.SelectableNode;
import rpnsim.application.rpn.Token;
import rpnsim.application.rpn.Transition;

public class PopupWindow extends AnchorPane{


    @FXML
    private AnchorPane root_pane;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameText;

    @FXML
    private Button editButton;
    
    private SelectableNode node;
    Stage stage;
    
    public PopupWindow(String titleLabel,EditableNode node){
    	this.node = node;
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/popupWindow.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);
		
		try { 
			//Parent root = FXMLLoader.load(getClass().getResource("/popupWindow.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/style.css");
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit");
			stage.setScene(scene);
			this.titleLabel.setText(titleLabel);
			this.setOnKeyPressed(keyClicked);
			
			stage.show();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
		
		
	}
    
    @FXML
	private void initialize() {
    	
	}
    
    public EventHandler<KeyEvent> keyClicked = new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(final KeyEvent keyEvent) {
	    	
	    	String keyCode = keyEvent.getCode().toString();
	    	if(keyCode.equals("ENTER")) {
	    		System.out.println(keyCode);
	    		if(nameText.getText().length()>0) {
	    			
	    			if(node instanceof SelectableNode) {
	    				SelectableNode selNode = (SelectableNode)node;
	    				RPN rpn = selNode.getRPN();
	    				ScrollArea scrollArea = rpn.getScrollArea();
	    				if( scrollArea instanceof EditorArea ) {
	    					scrollArea.SomethingChanged();
	    				}
	    			}
	    			
	    			if(node instanceof Place) {
	    				((Place) node).rename(nameText.getText());
	    			}
	    			else if(node instanceof Token) {
	    				((Token) node).rename(nameText.getText());
	    			}
	    			else if (node instanceof Transition) {
	    				((Transition) node).rename(nameText.getText());
	    			}
	    			stage.close();
	    		}
	    		else {
	    			
	    			stage.close();
	    			
	    		}
	    	}
	    	
	    	keyEvent.consume();
	    }
	};
	
}
