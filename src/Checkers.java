/*
 * 
 * This class basically handles all the scene switching
 * along with setting up the game board stackpanes which overlay
 * each other to create the checkerboard effect
 * 
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Checkers extends Application{
	//setting vars
	private static Stage primaryStage;
	
	private static Scene mainMenu;
	private static Scene board;
	private static Scene redWins;
	private static Scene whiteWins;
	private static Scene rulesScene;
	
	private Board boardGui;

	// piece will be created on game init and it will have event handler
	public void start(Stage primaryStage)
	   {
		  //javafx gui setup
		  this.primaryStage = primaryStage;
	      MainMenu mainMenuGui = new MainMenu();
	      boardGui = new Board();
	      
	      StackPane menuRootPane = new StackPane();
	      menuRootPane.getChildren().add(mainMenuGui);
	      
	      StackPane boardRootPane = new StackPane();
	      boardRootPane.getChildren().add(boardGui);
	      
	      //setting dimensions for scenes
	      mainMenu = new Scene(menuRootPane, 200, 400);
	      board = new Scene(boardRootPane, 902, 902);
	      
	      //**setting background for game**//
	      StackPane redWinsPane = new StackPane();
	      Rectangle redBackground = new Rectangle();
	      redBackground.setWidth(500);
	      redBackground.setHeight(500);
	      redBackground.setFill(Color.RED);
	      Label redWinsLabel = new Label("RED WINS!");
	      redWinsLabel.setFont(Font.font("Verdana", 70));
	      
	      redWinsPane.getChildren().addAll(redBackground, redWinsLabel);
	      redWins = new Scene(redWinsPane, 500, 500);
	      
	      StackPane whiteWinsPane = new StackPane();
	      Rectangle whiteBackground = new Rectangle();
	      whiteBackground.setWidth(500);
	      whiteBackground.setHeight(500);
	      whiteBackground.setFill(Color.WHITE);
	      Label whiteWinsLabel = new Label("WHITE WINS!");
	      whiteWinsLabel.setFont(Font.font("Verdana", 70));
	      
	      whiteWinsPane.getChildren().addAll(whiteBackground, whiteWinsLabel);
	      whiteWins = new Scene(whiteWinsPane, 500, 500);
	      //*****************//
	      
	      //main menu setup
		  BorderPane rules = new BorderPane();
		  Label rulesBox = new Label();
		  rulesBox.setText("1. Checkers 2.0 plays almost exactly like normal\n checkers just with a slight twist.\n 2. You can jump over your own pieces.\n 3. You get one more turn if you eliminate a piece \non the other team.\n 4. Red starts first");
		  Button mainButton = new Button("Back");
		  rules.setCenter(rulesBox);
		  rules.setBottom(mainButton);
		  rulesScene = new Scene(rules, 400, 400);
			
		  //setting actions to click and buttons
	      board.setOnMouseClicked(new MouseHandler());	 
	      mainButton.setOnAction(new MainButtonHandler());
	      primaryStage.setTitle("Checkers 2.0"); 
	      primaryStage.setScene(mainMenu); // Place the scene in the stage
	      primaryStage.show(); // Display the stage
	   }
	   public static void main(String[] args)
	   {
	      launch(args);
	   }
	   
	   //scene switches
	   public static void setToBoard()
	   {
		   primaryStage.setScene(board);
		   primaryStage.show();
	   }
	   
	   public static void setToRedWins()
	   {
		   primaryStage.setScene(redWins);
		   primaryStage.show();
	   }
	   
	   public static void setToWhiteWins()
	   {
		   primaryStage.setScene(whiteWins);
		   primaryStage.show();
	   }
	   
	   public static void setToRules()
	   {
		   primaryStage.setScene(rulesScene);
		   primaryStage.show();
	   }
	   
	   //handling mouse click events
	   private class MouseHandler implements EventHandler<MouseEvent>
	   {
	      public void handle(MouseEvent event)
	       {
	    	  //System.out.println("X: " + event.getX());
	    	  //System.out.println("Y: " + event.getY());
	    	  boardGui.select(event.getX(), event.getY());
	       }
	   }
	   
	   private class MainButtonHandler implements EventHandler<ActionEvent>
	   {
		   public void handle(ActionEvent event)
		   {
			   primaryStage.setScene(mainMenu);
		   }
	   }
}
