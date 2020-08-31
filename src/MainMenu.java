/*
 * 
 * This class handles the setup for the main menu which is the first
 * thing someone sees when starting up the application
 * 
 */

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
public class MainMenu extends VBox{
	private Button startButton;
	private Button exitButton;
	private Button rulesButton;
	
	public MainMenu()
	{
		//main menu gui setup
		Label label = new Label("CHECKERS 2.0");
		label.setFont(Font.font("Verdana", 26));
		label.setTextFill(Color.RED);
		startButton = new Button("Start Game");
		startButton.setMinWidth(200);
		startButton.setMinHeight(200);
		startButton.setStyle("-fx-font-size: 2em; ");
		exitButton = new Button("Exit");
		exitButton.setMinWidth(200);
		exitButton.setMinHeight(50);
		exitButton.setStyle("-fx-text-fill: #0000ff");
		rulesButton = new Button("Rules");
		rulesButton.setMinWidth(200);
		rulesButton.setMinHeight(100);
		rulesButton.setStyle("-fx-font-size: 2em; ");
		this.setSpacing(4);
		this.getChildren().addAll(label, startButton, rulesButton, exitButton);
		
		rulesButton.setOnAction(new RulesHandler());
		startButton.setOnAction(new StartHandler());
		exitButton.setOnAction(new ExitHandler());
	}
	
	private class StartHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent click) 
		{
			Checkers.setToBoard();
		}
		
	}
	
	private class RulesHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent click) 
		{
			Checkers.setToRules();
		}
	}
	
	private class ExitHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent click) 
		{
			Platform.exit();
		}
	}
	
}
