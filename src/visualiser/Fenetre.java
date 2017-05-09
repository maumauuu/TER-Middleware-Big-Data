package visualiser;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	
	Pan panel = new Pan();
	
	public Fenetre(){
	    this.setSize(700, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.getContentPane().add(panel, BorderLayout.WEST);
	    this.setVisible(true);
	}

}
