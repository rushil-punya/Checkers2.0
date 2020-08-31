/*
 * 
 * This class handles the main logic for the game including:
 * moving pieces, eliminating pieces, determining who's turn it is, etc.
 * When a piece is selected the cell turns green 
 * 
 */


import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
public class Board extends StackPane{
	//initializing vars
	private StackPane rootPane;
	private GridPane boardPane;
	private GridPane checkPane;
	
	private Rectangle[][] rectArray;
	private Piece[][] pieceArr;
	private Circle[][] circArr;
	
	private int prevI;
	private int prevJ;
	private boolean pieceSelected;
	private Paint colorPiece;
	private int changeIndex;
	private Piece selectedPiece;
	private boolean pieceKilled;
	
	private int whitePiecesRemaining;
	private int redPiecesRemaining;
	
	private int whoseTurn;
	
	
	public Board()
	{
		//setting up vars
		
		pieceKilled = false;
		prevI = -1;
		prevJ = -1;
		whoseTurn = 0;
		whitePiecesRemaining = 12;
		redPiecesRemaining = 12;
		selectedPiece = null;
		colorPiece = Color.RED;
		changeIndex = 0;
		pieceSelected = false;
		rootPane = new StackPane();
		boardPane = new GridPane();
		checkPane = new GridPane();
		checkPane.setPrefSize(900, 900);
		
		//initializing board with pieces on it
		circArr = new Circle[8][8];
		rectArray = new Rectangle[8][8];
		//initializes rectangle background
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   rectArray[i][j] = new Rectangle();
	    		   rectArray[i][j].setWidth(900/8);
	    		   rectArray[i][j].setHeight(900/8);
	    		   rectArray[i][j].setX(900/8 *i);
	    		   rectArray[i][j].setY((900/8)*j);
	    		   if((i % 2) + (j % 2) == 1)
	    			   rectArray[i][j].setFill(Color.RED);
	    		   else
	    			   rectArray[i][j].setFill(Color.BLACK);
	    		   rectArray[i][j].setStroke(Color.BLACK);
	    	   }
	       }
		//adds boxes to the board
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   boardPane.add(rectArray[i][j], i, j);
	    	   }
	       }
		
		//adds checkers to board
		 int pieceIndex = 1;
	      pieceArr = new Piece[8][8];
	      for(int i = 0; i<8; i++)
	      {
	    	  for(int j = 0; j<8; j++)
	    		  {
	    		   Circle piece = new Circle();
	    		  		pieceArr[i][j] =  new Piece(pieceIndex, rectArray[i][j].getX() + 56.25, rectArray[i][j].getY() + 56.25, false);
	    		  		if(getColor(i, j).equals(Color.BLACK))
	    		  		{
	    		  			
	    		  			
	    		  			if(j < 3)
	    		  			{
		    		  			piece.setCenterX(pieceArr[i][j].getX());
	    		  				piece.setCenterY(pieceArr[i][j].getY());
	    		  				piece.setRadius(56.25);
	    		  				piece.setFill(Color.RED);
	    		  				pieceIndex++;
	    		  			}
	    		  			else if(j > 4)
	    		  			{
	    		  				piece.setCenterX(pieceArr[i][j].getX());
	    		  				piece.setCenterY(pieceArr[i][j].getY());
	    		  				piece.setRadius(56.25);
	    		  				piece.setFill(Color.WHITE);
	    		  				pieceIndex++;
	    		  			}
	    		  			else
	    		  			{
	    		  				piece.setCenterX(pieceArr[i][j].getX());
	    		  				piece.setCenterY(pieceArr[i][j].getY());
	    		  				piece.setRadius(56.25);
	    		  				piece.setFill(Color.BLACK);
	    		  				pieceArr[i][j] = null;
	    		  			}
	    		  		}
	    		  		else
	    		  		{
	    		  			pieceArr[i][j] = null;
	    		  		}			  		
	    		  		circArr[i][j] = piece;
	    		  		checkPane.add(piece, i, j);
	    		  }
	      }
	    rootPane.getChildren().addAll(boardPane, checkPane);
		this.getChildren().add(rootPane);
	}
	private Paint getColor(int i , int j)
	{
		return rectArray[i][j].getFill();
	}
	/********
	
	after constructor
	
	********/
	
	//this method handles the selection of an individual piece
	public void select(double x, double y)
	{
		int checkForNull = 0;
		int index = 0;
		if(pieceSelected)
			checkForValidMove(x, y);
		else
		{
			for(int i = 0; i<pieceArr.length; i++)
			{
				for(int j = 0; j<pieceArr[0].length; j++)
				{
					
					try {
						checkForNull = pieceArr[i][j].getPieceIndex();
						if((rectArray[i][j].getX() + 112.5 > x) && (rectArray[i][j].getX() < x) && (rectArray[i][j].getY() + 112.5 > y) && (rectArray[i][j].getY() < y) && !pieceSelected)
						{
							
							//this is only checking for checker slots not non-checker slots
							selectSquare(rectArray[i][j].getX(), rectArray[i][j].getY());
							index = pieceArr[i][j].getPieceIndex();
							break;
						}
					}
					catch(NullPointerException ne)
					{
					}
				}
			}			
		}
	}
	
	private boolean selectSquare(double x, double y)
	{
		
		boolean flag = false;
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   //if already selected then exit method
	    		   if(getColor(i, j) == Color.GREEN)
	    			   return flag;
	    	   }
	       }
		//iterate through the board and if index matches with selected piece 
		//set color green, set selected true, and index previous piece
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   if(rectArray[i][j].getX() == x && rectArray[i][j].getY() == y && rectArray[i][j].getFill() == Color.BLACK && (((circArr[i][j].getFill() == Color.RED || circArr[i][j].getFill() == Color.BLUE) && whoseTurn % 2 == 0) || ((circArr[i][j].getFill() == Color.WHITE || circArr[i][j].getFill() == Color.YELLOW) && whoseTurn % 2 == 1)))
	    		   {
		    			   selectedPiece = pieceArr[i][j];
		    			   changeIndex = pieceArr[i][j].getPieceIndex();
		    			   colorPiece = circArr[i][j].getFill();
		    			   pieceArr[i][j].setSelected(true);
		    			   pieceSelected = true;
		    			   rectArray[i][j].setFill(Color.GREEN);
		    			   prevI = i;
		    			   prevJ = j;
		    			   flag = true;
	    		   }
	    	   }
	       }
		return flag;
	}
	
	//this method hosts other methods which also check for valid move
	//goes through all conditions to make sure and then places piece
	//in desired area. If not, selected piece is reset and becomes not green
	private boolean checkForValidMove(double x, double y)
	{
		boolean ret = false;
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   try
	    		   {
	    			   if(notHasPiece(x,y) && (rectArray[i][j].getX() + 112.5 > x) && (rectArray[i][j].getX() < x) && (rectArray[i][j].getY() + 112.5 > y) && (rectArray[i][j].getY() < y) && circArr[i][j].getFill() == Color.BLACK && pieceSelected && rectArray[i][j].getFill() == Color.BLACK)
		    		   {
		    			   if(isWithinRange(rectArray[i][j].getX(), rectArray[i][j].getY()))
		    			   {
			    			   placePiece(rectArray[i][j].getX(), rectArray[i][j].getY());
			    			   ret = true;
			    			   checkForKing(i, j);
		    			   }

		    		   }

	    		   }
	    		   catch(NullPointerException ne)
					{
					}
	    	   }
	       }
		if(!ret)
		{
			for(int i = 0; i<rectArray.length; i++)
		       {
		    	   for(int j = 0; j< rectArray[i].length; j++)
		    	   {
		    		   try
		    		   {
			    		   if(changeIndex == pieceArr[i][j].getPieceIndex())
			    		   {
			    			   rectArray[i][j].setFill(Color.BLACK);
			    			   colorPiece = Color.WHITE;
			    			   pieceArr[i][j].setSelected(false);
			    			   pieceSelected = false;
			    		   }		    			   
		    		   }

		    		   catch(NullPointerException ne)
						{
						}
		    	   }
		       } 
		}
		return ret;
	}
	
	//this method makes sure that the desired piece is within the range of the rules of the game
	//this method was a lot of work because I just had to check so many different conditions and
	//places
	private boolean isWithinRange(double x, double y)
	{
		boolean flag = false;
		double selectedPieceX = selectedPiece.getX()-56.25;
		double selectedPieceY = selectedPiece.getY()-56.25;
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   if(rectArray[i][j].getX() == x && rectArray[i][j].getY() == y && rectArray[prevI][prevJ].getFill() == Color.GREEN)
	    		   {
	    			   
    				   if((colorPiece == Color.RED || colorPiece == Color.BLUE || colorPiece == Color.YELLOW) && ((selectedPiece.getY() - y) < 0))
	    			   {
    					   //try block because these statements often go out of bounds when trying to jump over
    					   //pieces near corners or walls
    					   try 
    	    			   {
	    					   if((rectArray[i+1][j-1].getX() == selectedPieceX && rectArray[i+1][j-1].getY() == selectedPieceY)||(rectArray[i-1][j-1].getX() == selectedPieceX && rectArray[i-1][j-1].getY() == selectedPieceY))
		    				   {
		    					   flag = true;
		    					   whoseTurn++;
		    					   
		    				   }
	    					   else if((rectArray[i-2][j-2].getX() == selectedPieceX && rectArray[i-2][j-2].getY() == selectedPieceY) && circArr[i-1][j-1].getFill() != Color.BLACK)
	    					   {
	    						   checkForKill(i, j, 1, 1);
	    						   flag = true;
	    					   } 
	    					   else if((rectArray[i+2][j-2].getX() == selectedPieceX && rectArray[i+2][j-2].getY() == selectedPieceY) && circArr[i+1][j-1].getFill() != Color.BLACK)
	    					   {
	    						   checkForKill(i, j, 2, 1);
	    						   flag = true;
	    					   }

    	    			   }
    	    			   catch(IndexOutOfBoundsException ie)
        				   {
    	    				 //if running into ie in the corners make a case above these ones which address particular [i][j]
    	    				   if(i == 0)
    	    				   {
 		    					  if((rectArray[i+2][j-2].getX() == selectedPieceX && rectArray[i+2][j-2].getY() == selectedPieceY) && circArr[i+1][j-1].getFill() != Color.BLACK)
 		    					  {
 		    						  checkForKill(i, j, 2, 1);
 		    						  flag = true;
 		    					  }    	    					   
    	    				   }
    	    				   else if(i ==7)
    	    				   {
    	    					  if((rectArray[i-1][j-1].getX() == selectedPieceX && rectArray[i-1][j-1].getY() == selectedPieceY))
 	        					  {
 	        						  flag = true;
 	        						  whoseTurn++;
 	        					  }
    	    					  else if((rectArray[i-2][j-2].getX() == selectedPieceX && rectArray[i-2][j-2].getY() == selectedPieceY) && circArr[i-1][j-1].getFill() != Color.BLACK)
	        					  {
	        						  checkForKill(i, j, 1, 1);
	        						  flag = true;
	        					  }
    	    				   }

    	    				   else
    	    				   {
		    					   if((rectArray[i+2][j-2].getX() == selectedPieceX && rectArray[i+2][j-2].getY() == selectedPieceY) && circArr[i+1][j-1].getFill() != Color.BLACK)
		    					   {
		    						   checkForKill(i, j, 2, 1);
		    						   flag = true;
		    					   }    	    						   
    	    				   }
        				   }
    					   catch(NullPointerException ne)
    					   {
    						   
    					   }
	    			   }

    				   if((colorPiece == Color.WHITE || colorPiece == Color.BLUE || colorPiece == Color.YELLOW) && ((selectedPiece.getY() - y) > 0))
    				   {
    					   //same reason for try here
    					   try
    					   {
    						   if((rectArray[i+1][j+1].getX() == selectedPieceX && rectArray[i+1][j+1].getY() == selectedPieceY) || (rectArray[i-1][j+1].getX() == selectedPieceX && rectArray[i-1][j+1].getY() == selectedPieceY))
    	    				   {
    	    					   flag = true;
    	    					   whoseTurn++;
    	    				   }
    						   else if((rectArray[i+2][j+2].getX() == selectedPieceX && rectArray[i+2][j+2].getY() == selectedPieceY) && circArr[i+1][j+1].getFill() != Color.BLACK)
    						   {
    							   checkForKill(i, j, 2, 2);
    							   flag = true;
    						   }
    						   else if((rectArray[i-2][j+2].getX() == selectedPieceX && rectArray[i-2][j+2].getY() == selectedPieceY) && circArr[i-1][j+1].getFill() != Color.BLACK)
    						   {
    							   checkForKill(i, j, 1, 2);
    							   flag = true;
    						   }
    					   }
    	    			   catch(IndexOutOfBoundsException ie)
        				   {
    	    				   if(i == 0)
    	    				   {
 		    					  if((rectArray[i+2][j+2].getX() == selectedPieceX && rectArray[i+2][j+2].getY() == selectedPieceY) && circArr[i+1][j+1].getFill() != Color.BLACK)
 		    					  {
 		    						 checkForKill(i, j, 2, 2);
 		    						  flag = true;
 		    					  }    	    					   
    	    				   }
    	    				   else if(i ==7)
    	    				   {
    	    					  if((rectArray[i-1][j+1].getX() == selectedPieceX && rectArray[i-1][j+1].getY() == selectedPieceY))
 	        					  {
 	        						  flag = true;
 	        						  whoseTurn++;
 	        					  }
    	    					  else if((rectArray[i-2][j+2].getX() == selectedPieceX && rectArray[i-2][j+2].getY() == selectedPieceY) && circArr[i-1][j+1].getFill() != Color.BLACK)
	        					  {
	        						  checkForKill(i, j, 1, 2);
	        						  flag = true;
	        					  }
    	    				   }
    	    				  else
    	    				   {
       	    					   if((rectArray[i-2][j+2].getX() == selectedPieceX && rectArray[i-2][j+2].getY() == selectedPieceY) && circArr[i-1][j+1].getFill() != Color.BLACK)
       	    					   {
       	    						   checkForKill(i, j, 1, 2);
       	    						   flag = true;
       	    					   }   	    						  
    	    				   }
        				   }
    					   catch(NullPointerException ne)
    					   {
    						   
    					   }
    				   }
	    		   }
	    	   }
	       }
		return flag;
	}
	//1 is left 2 is right for int direction
	//1 is red 2 is white
	//this method checks if a piece was eliminated then turns the piece back to the background
	//color to make it look like its gone. The values are then made null to reflect this.
	private void checkForKill(int i, int j, int direction, int asColor)
	{
		if(asColor == 1)
		{
			if(colorPiece == Color.YELLOW)
			{
				if(direction == 1)
				{
					if(circArr[i-1][j-1].getFill() == Color.RED || circArr[i-1][j-1].getFill() == Color.BLUE)
					{
						pieceArr[i-1][j-1] = null;
						circArr[i-1][j-1].setFill(Color.BLACK);
						redPiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
				else if(direction == 2)
				{
					if(circArr[i+1][j-1].getFill() == Color.RED || circArr[i+1][j-1].getFill() == Color.BLUE)
					{
						pieceArr[i+1][j-1] = null;
						circArr[i+1][j-1].setFill(Color.BLACK);
						redPiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
			}
			else
			{
				if(direction == 1)
				{
					if(circArr[i-1][j-1].getFill() == Color.WHITE || circArr[i-1][j-1].getFill() == Color.YELLOW)
					{
						pieceArr[i-1][j-1] = null;
						circArr[i-1][j-1].setFill(Color.BLACK);
						whitePiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
				else if(direction == 2)
				{
					if(circArr[i+1][j-1].getFill() == Color.WHITE || circArr[i+1][j-1].getFill() == Color.YELLOW)
					{
						pieceArr[i+1][j-1] = null;
						circArr[i+1][j-1].setFill(Color.BLACK);
						whitePiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
			}
			
		}
		if(asColor == 2)
		{
			if(colorPiece == Color.BLUE)
			{
				if(direction == 1)
				{
					if(circArr[i-1][j+1].getFill() == Color.WHITE || circArr[i-1][j+1].getFill() == Color.YELLOW)
					{
						pieceArr[i-1][j+1] = null;
						circArr[i-1][j+1].setFill(Color.BLACK);
						whitePiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
				else if(direction == 2)
				{
					if(circArr[i+1][j+1].getFill() == Color.WHITE || circArr[i+1][j+1].getFill() == Color.YELLOW)
					{
						pieceArr[i+1][j+1] = null;
						circArr[i+1][j+1].setFill(Color.BLACK);
						whitePiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
			}
			else
			{
				if(direction == 1)
				{
					if(circArr[i-1][j+1].getFill() == Color.RED || circArr[i-1][j+1].getFill() == Color.BLUE)
					{
						pieceArr[i-1][j+1] = null;
						circArr[i-1][j+1].setFill(Color.BLACK);
						redPiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
				else if(direction == 2)
				{
					if(circArr[i+1][j+1].getFill() == Color.RED || circArr[i+1][j+1].getFill() == Color.BLUE)
					{
						pieceArr[i+1][j+1] = null;
						circArr[i+1][j+1].setFill(Color.BLACK);
						redPiecesRemaining--;
						whoseTurn++;
						pieceKilled = true;
					}
				}
			}
		}
		if(whitePiecesRemaining == 0)
		{
			Checkers.setToRedWins();
		}
		if(redPiecesRemaining == 0)
		{
			Checkers.setToWhiteWins();
		}
		whoseTurn++;
	}
	
	//this method checks whether the desired location the player wants to go to
	//has a piece or not in it.
	private boolean notHasPiece(double x, double y)
	{
		boolean ret = false;
		for(int i = 0; i<pieceArr.length; i++)
	       {
	    	   for(int j = 0; j< pieceArr[i].length; j++)
	    	   {
	    		   try
	    		   {
	    			   //trying a nullpointerexception
	    			   pieceArr[i][j].getX();
	    			   
	    		   }
	    		   catch(NullPointerException ne)
	    		   {
	    			   if((rectArray[i][j].getX() + 112.5 > x) && (rectArray[i][j].getX() < x) && (rectArray[i][j].getY() + 112.5 > y) && (rectArray[i][j].getY() < y))
	    			   {
	    				   ret = true;
	    			   } 
	    		   }
	    	   }
	       }
		return ret;
	}
	
	//this method simply places the piece in the desired location if all conditions are fulfilled
	private void placePiece(double x, double y)
	{
		for(int i = 0; i<rectArray.length; i++)
	       {
	    	   for(int j = 0; j< rectArray[i].length; j++)
	    	   {
	    		   try
	    		   {
		    		   if(pieceArr[i][j].getSelected())
		    		   {
		    			   
		    			   rectArray[i][j].setFill(Color.BLACK);
		    			   circArr[i][j].setFill(Color.BLACK);
		    			   pieceArr[i][j] = null;
		    		   }
	    		   }
	    		   catch(NullPointerException ne)
	    		   {
	    		   }
	    		   if(rectArray[i][j].getX() == x && rectArray[i][j].getY() == y)
	    		   {
	    			   pieceSelected = false;
	    			   //checkForKill
	    			   whoseTurn--;
	    			   if(!pieceKilled)
	    			   {
		    			   if(colorPiece == Color.RED && whoseTurn % 2 == 0)
		    				   circArr[i][j].setFill(Color.RED);
		    			   else if(colorPiece == Color.WHITE && whoseTurn % 2 == 1)
		    				   circArr[i][j].setFill(Color.WHITE);
		    			   else if(colorPiece == Color.YELLOW && whoseTurn % 2 == 1)
		    				   circArr[i][j].setFill(Color.YELLOW);
		    			   else if(colorPiece == Color.BLUE && whoseTurn % 2 == 0)
		    				   circArr[i][j].setFill(Color.BLUE);
	    			   }
	    			   else
	    			   {
	    				   whoseTurn--;
	    				   if(colorPiece == Color.RED && whoseTurn % 2 == 0)
		    				   circArr[i][j].setFill(Color.RED);
		    			   else if(colorPiece == Color.WHITE && whoseTurn % 2 == 1)
		    				   circArr[i][j].setFill(Color.WHITE);
		    			   else if(colorPiece == Color.YELLOW && whoseTurn % 2 == 1)
		    				   circArr[i][j].setFill(Color.YELLOW);
		    			   else if(colorPiece == Color.BLUE && whoseTurn % 2 == 0)
		    				   circArr[i][j].setFill(Color.BLUE);
	    				   whoseTurn++;
	    				   pieceKilled = false;
	    			   }
	    			   whoseTurn++;
	    			   pieceArr[i][j] = new Piece(changeIndex, rectArray[i][j].getX() + 56.25, rectArray[i][j].getY() + 56.25, false);
	    		   }
		    	}
	    	   }
	}
	
	//this method checks whether a piece has became a king piece or not
	public void checkForKing(int i, int j)
	{
		if(circArr[i][j].getFill() == Color.RED && j == 7)
		{
			circArr[i][j].setFill(Color.BLUE);
		}
		else if(circArr[i][j].getFill() == Color.WHITE && j == 0)
		{
			circArr[i][j].setFill(Color.YELLOW);
		}
	}
	}
