
public class Board {
	private int connectionLength;
	private int boardSize;
	private Token[][] board;
	
	
	public Board(int size){
		board = new Token[size][size];
	}
	public int getConnectionLength() {
		return connectionLength;
	}
	public void setConnectionLength(int connectionLength) {
		this.connectionLength = connectionLength;
	}
	public int getBoardSize() {
		return this.boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	
	public Token getTokenAt(int row, int col){
		return (board[row][col] != null) ? board[row][col] : null ;
	}
	
	public void setTokenAt(Token t, int row, int col){
		board[row][col] = t;
	}
	
	public void clearTokenAt(int row, int col){
		board[row][col] = null;
	}
	
	public boolean isSlotFilled(int row, int col){
		try{
		return board[row][col] != null;}
		catch(Exception ex){
			return false;
		}
		
	}
		
}
