import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.UIManager;

public class Connect4SlotButton extends JButton{
	private int columnNumber;
		public void setColumnNumber(int col){
		this.columnNumber = col;
	}
	
	public int getColumnNumber(){
		return this.columnNumber;
	}
	
	public Connect4SlotButton(String label) {
	    super(label);
	    super.setContentAreaFilled(false);
	    super.setBorderPainted(false);
	// These statements enlarge the button so that it 
	// becomes a circle rather than an oval.
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width, 
	      size.height);
	    setPreferredSize(size);

	// This call causes the JButton not to paint 
	   // the background.
	// This allows us to paint a round background.
	    setContentAreaFilled(false);
	  }

	// Paint the round background and label.
	  protected void paintComponent(Graphics g) {
	    if (getModel().isArmed()) {
	// You might want to make the highlight color 
	   // a property of the RoundButton class.
	      g.setColor(Color.cyan);
	    } else {
	      g.setColor(g.getColor());
	    }
	    g.fillOval(10, 10, getSize().width-10, 
	      getSize().height-10);
	    
	// This call will paint the label and the 
	   // focus rectangle.
	    super.paintComponent(g);
	  }
	
	
	

}
