import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

public class GameGui extends JFrame {
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public  Connect4SlotButton [][]buttons;
	private Game gameRef;

		public  void resetBoard(){
		for(int i = 0; i < buttons.length; i++){
			for(int j = 0; j < buttons.length; j++){
				buttons[i][j].setForeground(Color.white);
			}
		}
	}
	
	
	public void presentMenu(String s){
		
		JFrame wFrame = new JFrame(s);
		wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton resetButton = new JButton("Reset");
		JButton quitButton = new JButton("Quit");
		
		//Add Quit button action listener.
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);	
			}
			
		});
		
		// Add action listener to Reset button
		resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resetBoard();
				gameRef.resetBoardModel();
				wFrame.dispose();
			}		
		});
	
		wFrame.setLayout(new FlowLayout());
		wFrame.add(resetButton);
		wFrame.add(quitButton);
		wFrame.pack();	
		
		wFrame.setLocation(dim.width/2-75, dim.height/3);
		wFrame.setVisible(true);
	}
	
	public void promptErrorMessage(){
//		//Must have board size no greater than 20 and connection length must be less than board size
//		JFrame errorFrame = new JFrame("Board Size must not exceed 20 | connection length must not exceed board size");
//		errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		JButton okButton = new JButton("OK");
//		
//		errorFrame.setLayout(new FlowLayout());
//		//errorFrame.
//		okButton.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//				wFrame.setLayout(new FlowLayout());
//				wFrame.add(resetButton);
//				wFrame.add(quitButton);
//				wFrame.pack();	
//				
//			}
			
//		});
		
		
		
	}
	
	
	public void startGame(){
		JFrame theFrame = new JFrame("Connect 4 Settings");
		JButton okButton = new JButton("ok");
		JTextField boardSizeField = new JTextField(5);
		JTextField connectionLengthField = new JTextField(5);
	
			
		boardSizeField.setText("8");
		connectionLengthField.setText("4");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setLayout(new FlowLayout());
		theFrame.add(new JLabel("Enter Board Size"));
		theFrame.add(boardSizeField);
		theFrame.add(new JLabel("Enter Connection Length"));
		theFrame.add(connectionLengthField);
		
		okButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(Integer.parseInt(connectionLengthField.getText()),Integer.parseInt(boardSizeField.getText()));
				// Create new game Board Gui...
				gameRef = game;
				int boardSize = game.getBoardSize();
				buttons = new Connect4SlotButton[boardSize][boardSize];
				JFrame gameBoardFrame = new JFrame("Connect 4");
				JPanel panel = new JPanel(new GridLayout(boardSize, boardSize));
				panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				gameBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameBoardFrame.setContentPane(panel);
				panel.setBackground(Color.BLUE);
				
				// The nested for loops will instantiate a custom button
				// add an action listener to the button and add it to the grid 
				for(int row = 0; row < boardSize; row++){
					for(int col = 0; col < boardSize; col++){
						Connect4SlotButton b = new Connect4SlotButton("");
						b.setColumnNumber(col);
						b.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							int column = ((Connect4SlotButton) e.getSource()).getColumnNumber();
							int row = game.addTokenAtColumn(column);
							//AddTokenAtColumn returns -1 if the column is full
							if(row != -1){
								//Set the color of the button the color of the turn
								buttons[row][column].setForeground(game.getTurn() == COLOR.YELLOW ? Color.YELLOW: Color.RED);
								if(game.checkWin(row,column, game.getTurn())) {
										presentMenu(game.getTurn() + " WON!");
										//Present Menu declaring winner and then offering a choice to replay or quit
										
										
								}
								//Check if the board is full ONLY if row = 0;
								if(row == 0 && game.checkIsBoardFull()){
									// Present Menu showing tie and allowing reset
									presentMenu("Tie Game!");
								}
								
								//Switch Turn
								//Switches color turn
								if(game.getTurn() == COLOR.RED) game.setTurn(COLOR.YELLOW); 
								else game.setTurn(COLOR.RED);
							}
						}
						
					});
					buttons[row][col] = b;
					b.setForeground(Color.white);
					gameBoardFrame.add(b);
					
					}
				}
				gameBoardFrame.pack();
				gameBoardFrame.setLocation(dim.width/2-gameBoardFrame.getSize().width/2, dim.height/3-gameBoardFrame.getSize().height/2);
				gameBoardFrame.setVisible(true);
				
				// Closes configuration menu
				theFrame.dispose();
				
			}
			
		});
		theFrame.add(okButton);
		theFrame.pack();
		theFrame.setLocation( dim.width/3-(int)this.getSize().getWidth(), dim.height/3- (int)this.getSize().getHeight());
		theFrame.setVisible(true);
		
	}
	

	
	public static void main(String[] args){
		GameGui newGame = new GameGui();
		newGame.startGame();
	}

}
