

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainActivityOne extends JFrame implements ActionListener{
	  
	  
	  /**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1807187752974444011L;
	static ImageIcon image = new ImageIcon("blackhole.png");
	static final int SCREEN_SIZE = 600;
    static final int UNIT_SIZE = 15;
    static MainActivityTwo matRef;
     
    static JComboBox<String> jcmb1, jcmb2;
    static JTextArea tx1, tx2;
    
    static String[] choices = {"BOT","PLAYER"};
    
    static JButton btn;
    static boolean isBot1 = true, isBot2 = true;
    static String player1_Name = "Player1", player2_Name = "Player2";
    static Color txColor;
	
	  public MainActivityOne() {
		  
		  txColor = MainActivity.BACKGROUND_COLOR.equals(Color.white)?Color.black:Color.white;
		  tx1 = new JTextArea();
     	 tx1.setOpaque(false);
     	 tx1.setLineWrap(true);
     	 tx1.setWrapStyleWord(true);
     	 tx1.setText("SELECT FIRST PLAYER : ");
     	 tx1.setBounds(UNIT_SIZE*5,UNIT_SIZE*10,UNIT_SIZE*15,UNIT_SIZE*2);
     	 tx1.setForeground(txColor);
     	 tx1.setEditable(false);
     	 tx1.setFont(new Font(Font.MONOSPACED,Font.TRUETYPE_FONT,UNIT_SIZE));
     	 //tx1.setBorder(BorderFactory.createLineBorder(Color.green));
     	 
     	 jcmb1 = new JComboBox<>(choices);
     	 jcmb1.setForeground(Color.black);
     	 jcmb1.setBackground(Color.cyan);

     	 jcmb1.setBounds(UNIT_SIZE*20,UNIT_SIZE*10,UNIT_SIZE*10,UNIT_SIZE*2);
     	 
     	 
     	 tx2 = new JTextArea();
     	 tx2.setOpaque(false);
     	 tx2.setLineWrap(true);
     	 tx2.setWrapStyleWord(true);
     	 tx2.setText("SELECT SECOND PLAYER : ");
     	 tx2.setBounds(UNIT_SIZE*5,UNIT_SIZE*14,UNIT_SIZE*15,UNIT_SIZE*2);
     	 tx2.setForeground(txColor);
     	 tx2.setEditable(false);
     	 tx2.setFont(new Font(Font.MONOSPACED,Font.TRUETYPE_FONT,UNIT_SIZE));
     	 //tx2.setBorder(BorderFactory.createLineBorder(Color.green));
     	 
     	 jcmb2 = new JComboBox<>(choices);
     	 jcmb2.setForeground(Color.black);
     	 jcmb2.setBackground(Color.cyan);
     	 jcmb2.setBounds(UNIT_SIZE*20,UNIT_SIZE*14,UNIT_SIZE*10,UNIT_SIZE*2);
     	 
     	 jcmb1.addActionListener(this);
     	 jcmb2.addActionListener(this);
     	 
     	 
     	btn = new JButton();
	   	btn.setText("START");
	   	btn.setFocusable(false);
	   	btn.setBorderPainted(false);
	   	btn.setFont(new Font(Font.MONOSPACED,Font.TRUETYPE_FONT,UNIT_SIZE));
        btn.setBounds(UNIT_SIZE*15,UNIT_SIZE*20,UNIT_SIZE*8,UNIT_SIZE*2);  
        btn.addActionListener(this);
        btn.setForeground(Color.white);
        btn.setBackground(new Color(93,63,211));
     	 
     	 
     	 
     	  this.add(jcmb1);
		  this.add(tx1); 
		  this.add(jcmb2);
		  this.add(tx2);
		  this.add(btn);
    	  this.setTitle("BlackHole");
    	  this.setLayout(null);
    	  this.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
    	  this.getContentPane().setBackground(MainActivity.BACKGROUND_COLOR);
    	  this.setIconImage(image.getImage());
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setResizable(false);
    	  this.pack();
    	  this.setVisible(true);
    	  this.setLocationRelativeTo(null);
	  }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jcmb1) {
			if(jcmb1.getSelectedItem().equals("PLAYER")) {
				isBot1 = false;
				String temp = JOptionPane.showInputDialog("Enter Player name : ");
				if(temp!=null && !temp.equals(""))  player1_Name = temp;
			}
		}
		
		if(e.getSource()==jcmb2) {
			if(jcmb2.getSelectedItem().equals("PLAYER")) {
				isBot2 = false;
				String temp = JOptionPane.showInputDialog("Enter Player name : ");
				if(temp!=null && !temp.equals(""))  player2_Name = temp;
			}
		}
		
		if(e.getSource()==btn) {
			MainActivityOne.this.dispose();
			matRef = new MainActivityTwo();
		}
	}

}

