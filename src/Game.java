
public class Game {
	private int boardSize;
	private Board gameBoard;
	private int connectionLength;
	private COLOR turn = COLOR.RED; 
	
	
	public Game(int connectionLength, int boardSize){
		this.boardSize = boardSize;
		this.connectionLength = connectionLength;
		gameBoard = new Board(boardSize);
		
	}
	
	public COLOR getTurn(){
		return this.turn;
	}
	
	public void setTurn(COLOR c){
		this.turn = c;
	}
	
	public void setConnectionLength(int s){
		this.connectionLength = s;
	}
	
	public void setBoardSize(int s){
		this.boardSize = s;
	}
	
	public int getConnectionLength(){
		return this.connectionLength;
	}
	
	public int getBoardSize(){
		return this.boardSize;
	}
	
	public int addTokenAtColumn(int c){
		int row = this.boardSize-1;
		for(int r=row; r >= 0; r--){
			if(!gameBoard.isSlotFilled(r, c)){
				// create new token based on current turn and change turn
				Token t = new Token();
				t.setColor(turn);
				gameBoard.setTokenAt(t, r, c);
				return r;
			}
			row = r;
		}
		row = -1;
		return row;
	}
	
	public void resetBoardModel(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				gameBoard.clearTokenAt(row, col);
			}
		}
	}
	
	public boolean checkIsBoardFull(){
		for(int col = 0; col < boardSize-1; col++){
			if(!gameBoard.isSlotFilled(0, col)) return false;
		}
		return true;
	}
	
	public boolean checkVertical(int row, int col, COLOR turn){
		int count = 0;
			//token is at bottom so just check up.
			int r = row;
			if(row != boardSize-1){
				while(gameBoard.getTokenAt(r, col).getColor() == turn && r < boardSize-1){
					r++;
				}
			}
			//At this point it is presumed that r is at the bottom token on the vertical
			while(gameBoard.isSlotFilled(r, col) && gameBoard.getTokenAt(r, col).getColor() == turn){
				r--;
				if(++count == connectionLength) return true;
			}
			
		return false;
	}
	
	// checkDiagonalUp checks the / diagonal for a win.
	// it first iterates left on the / diagonal until it reaches the last token of the same color as the placed token
	
	public boolean checkDiagonalUp(int row, int col, COLOR turn){
		int count = 1;
		int r = row, c = col;
		if(col != 0 && row != boardSize-1){
			while(gameBoard.isSlotFilled(r+1, c-1) && gameBoard.getTokenAt(r+1, c-1).getColor() == turn && r < boardSize-1 && c > 0){
				r++; c--;
			}
		}
		//At this point r and c should point to the token of the same color that is all the way to the left of this / diagonal
		while(gameBoard.isSlotFilled(r-1, c+1) && gameBoard.getTokenAt(r-1,c+1).getColor() == turn && c < boardSize && r > 0){
			r--; c++;
			if(++count == connectionLength) return true;
		}
		return false;
	}
	
	//This function checks for this diagonal. \
	public boolean checkDiagonalDown(int row, int col, COLOR turn){
		int count = 0;
		int r = row, c = col;
		if(col != 0 && row != 0){
			while(gameBoard.isSlotFilled(r-1,c-1) && gameBoard.getTokenAt(r-1, c-1).getColor() == turn && c > 0 && r > 0){
				c--;
				r--;
			}
		}
		//At this point c and r have traversed up the \ diagonal to the highest token of the same color
		while(gameBoard.isSlotFilled(r, c) && gameBoard.getTokenAt(r, c).getColor() == turn && c < boardSize && r < boardSize){
			c++;
			r++;
			//System.out.println("" + gameBoard.getTokenAt(r-1, c-1).getColor() + " @ " + (r-1) + " " + (c-1) );
			if(++count == connectionLength){ //System.out.println("WON \\");
					 return true;}
		}
		//System.out.println("-------");
		
		return false;
	}
	
	public boolean checkHorizontal(int row, int col, COLOR turn){
		int count = 0;
		int c = col;
		if(col != 0){
			while(gameBoard.isSlotFilled(row, c-1) && gameBoard.getTokenAt(row, c-1).getColor() == turn && c > 0){
				c--;
			}
		}
		//At this point it is presumed that c is at the right edge of the consecutive token sequence of the same color
		while(gameBoard.isSlotFilled(row,c) && gameBoard.getTokenAt(row, c).getColor() == turn){
			//System.out.println(gameBoard.getTokenAt(row, c) +"@ " + row + " " + c);
			c++;
			//System.out.println("TRAVERSING");
			if(++count == connectionLength) return true;
		}
		//System.out.println("----------");
		return false;
	}	
	

	public boolean checkWin(int r, int c, COLOR turn){
		//Check vertical |
		//Check horizontal –
		//Check DiagonalDown \		
		//Check DiagonalUP /
		if(checkVertical(r, c, turn) || checkHorizontal(r,c, turn) || checkDiagonalDown(r,c, turn) || checkDiagonalUp(r,c, turn)) return true;
		return false;
	}
	
	
}
