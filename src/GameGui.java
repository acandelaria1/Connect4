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
	
		public void presentGameBoardGui(){
			JFrame gameBoardFrame = new JFrame("Connect 4");
			JPanel panel = new JPanel(new GridLayout(gameRef.getBoardSize(), gameRef.getBoardSize()));
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			gameBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gameBoardFrame.setContentPane(panel);
			panel.setBackground(Color.BLUE);
			
			// The nested for loops will instantiate a custom button
			// add an action listener to the button and add it to the grid 
			for(int row = 0; row < gameRef.getBoardSize(); row++){
				for(int col = 0; col < gameRef.getBoardSize(); col++){
					Connect4SlotButton b = new Connect4SlotButton("");
					b.setColumnNumber(col);
					b.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						int column = ((Connect4SlotButton) e.getSource()).getColumnNumber();
						int row = gameRef.addTokenAtColumn(column);
						//AddTokenAtColumn returns -1 if the column is full
						if(row != -1){
							//Set the color of the button the color of the turn
							buttons[row][column].setForeground(gameRef.getTurn() == COLOR.YELLOW ? Color.YELLOW: Color.RED);
							if(gameRef.checkWin(row,column, gameRef.getTurn())) {
									presentMenu(gameRef.getTurn() + " WON!");
									//Present Menu declaring winner and then offering a choice to replay or quit
									
									
							}
							//Check if the board is full ONLY if row = 0;
							if(row == 0 && gameRef.checkIsBoardFull()){
								// Present Menu showing tie and allowing reset
								presentMenu("Tie Game!");
							}
							
							//Switch Turn
							//Switches color turn
							if(gameRef.getTurn() == COLOR.RED) gameRef.setTurn(COLOR.YELLOW); 
							else gameRef.setTurn(COLOR.RED);
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
			//theFrame.dispose();
			
		}
		
	//});
		//}
	
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
	
	public void promptErrorMessage(String errorMessage){
		JFrame errorFrame = new JFrame(errorMessage);
		errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton okButton = new JButton("OK");
		
		errorFrame.setLayout(new FlowLayout());
		//errorFrame.
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				errorFrame.dispose();
			}
		});
		errorFrame.setLayout(new FlowLayout());
		errorFrame.add(okButton);
		errorFrame.pack();	
		errorFrame.setSize(500, 100);
		errorFrame.setLocation(dim.width/3,dim.height/3);
		errorFrame.setVisible(true);
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
				int connectionLength;
				int boardSize;
				try{
				connectionLength = Integer.parseInt(connectionLengthField.getText());
				boardSize = Integer.parseInt(boardSizeField.getText());
				}catch(Exception ex){
					promptErrorMessage("Using Default Settings");
					connectionLengthField.setText("4");
					boardSizeField.setText("8");
					connectionLength = 4;
					boardSize = 8;
					//Will end execution early because settings were not correct
					return;
				}
				Game game = new Game(connectionLength, boardSize);
				
				// Create new game Board Gui...
				gameRef = game;
				buttons = new Connect4SlotButton[boardSize][boardSize];
					if(boardSize > 20 || boardSize < 4){
						promptErrorMessage("Board Size Must Be between 4-20. Using Default of 8");
						boardSizeField.setText("8");
						//boardSize=8;
					}
					else if(connectionLength > boardSize  ){
						promptErrorMessage("Connection Length Must Not Exceed Board Size Using Default of 4");
						connectionLengthField.setText("4");					
					}else{
						presentGameBoardGui();
					}
					
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
