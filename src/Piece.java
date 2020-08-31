/*
 * 
 * This class lays out the template for all of the pieces on the board
 * each piece has an index, x position, y position, and a boolean
 * to see if it has been selected or not.
 * 
 */


public class Piece {
	private int index;
	private double x;
	private double y;
	private boolean selected;
	public Piece(int index, double x, double y, boolean selected)
	{
		this.index = index;
		this.x = x;
		this.y = y;
		this.selected = selected;
	}
	public void setPieceIndex(int num)
	{
		index = num;
	}
	public void setX(double num)
	{
		x = num;
	}
	public void setY(double num)
	{
		y = num;
	}
	public void setSelected(boolean select)
	{
		selected = select;
	}
	public int getPieceIndex()
	{
		return index;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public boolean getSelected()
	{
		return selected;
	} 
	
}
