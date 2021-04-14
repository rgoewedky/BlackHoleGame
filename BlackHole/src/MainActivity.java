import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class MainActivity extends JFrame implements ActionListener{
	     
         private static final long serialVersionUID = 6944160405634284748L;
		 static final int SCREEN_SIZE = 600;
	     static final int UNIT_SIZE = 15;
	     static Color BACKGROUND_COLOR = Color.WHITE;
	     static Color DARK = new Color(34,34,34);
	     static ImageIcon image = new ImageIcon("blackhole.png");
	     static ImageIcon off = new ImageIcon("off_medium.png");
	     static ImageIcon on = new ImageIcon("on_medium.png");
	     static BufferedImage img;
	     static JButton btnStartGame;
	     static JTextArea textAreaWelcome;
	     static JLabel labelImage;
	     static JButton toggleBtn;
	     static Image scaledImage;
	     static ImageIcon imageIcon;
	     
         public MainActivity(){
        	 
        	 
        	 try {
        	   img = BACKGROUND_COLOR==Color.white?ImageIO.read(new File("newBlackholeLight.png")):ImageIO.read(new File("newBlackholeDark.png"));
        	 }
        	 catch(Exception e) {
        		 e.printStackTrace();
        	 }
        	
        
        	 
        	 labelImage = new JLabel();
        	 labelImage.setBounds(UNIT_SIZE*7,UNIT_SIZE,UNIT_SIZE*26,UNIT_SIZE*18);
        	 labelImage.setBackground(Color.DARK_GRAY);
        	 scaledImage = img.getScaledInstance(labelImage.getWidth(),labelImage.getHeight(),Image.SCALE_SMOOTH);
        	 imageIcon = new ImageIcon(scaledImage);
        	 labelImage.setIcon(imageIcon);
        	 
        	 textAreaWelcome = new JTextArea();
        	 textAreaWelcome.setOpaque(false);
        	 textAreaWelcome.setLineWrap(true);
        	 textAreaWelcome.setWrapStyleWord(true);
        	 textAreaWelcome.setText("Hi, Welcome to the BLACKHOLE game. "
        	 		+ "Intially, Player-1 and Player-2 both have 10 numbers from 1 to 10, "
        			+ "and there is a board containing 21 holes (See Image Above). "
        	 		+ "In each alternate turn player have to cover a hole using smallest of the numbers currently they have. "
        	 		+ "After all moves 1 hole remains unocuppied and that hole sucks all it's neighbour holes. "
        	 		+ "Winner is decided on the basis of who sucked least.");
        	 textAreaWelcome.setBounds(UNIT_SIZE*5,UNIT_SIZE*20,UNIT_SIZE*30,UNIT_SIZE*13);  
        	 textAreaWelcome.setForeground(Color.black);
        	 textAreaWelcome.setEditable(false);
        	 textAreaWelcome.setFont(new Font(Font.MONOSPACED,Font.TRUETYPE_FONT,UNIT_SIZE));
        	 
        	 
        	 btnStartGame = new JButton();
        	 btnStartGame.setText("NEXT");
        	 btnStartGame.setFocusable(false);
        	 btnStartGame.setBorderPainted(false);
        	 btnStartGame.setFont(new Font(Font.MONOSPACED,Font.TRUETYPE_FONT,UNIT_SIZE));
             btnStartGame.setBounds(UNIT_SIZE*15,UNIT_SIZE*34,UNIT_SIZE*8,UNIT_SIZE*2);  
             btnStartGame.addActionListener(this);
             btnStartGame.setForeground(Color.white);
             btnStartGame.setBackground(new Color(93,63,211));
            
             
             toggleBtn = new JButton();
             toggleBtn.setIcon(off);
             toggleBtn.setBorderPainted(false);
	         toggleBtn.setBackground(BACKGROUND_COLOR);
             toggleBtn.setBounds(SCREEN_SIZE-UNIT_SIZE*6,SCREEN_SIZE-6*UNIT_SIZE,UNIT_SIZE*3,UNIT_SIZE*2);
             toggleBtn.addActionListener(this);
             
             
        	 this.setTitle("BlackHole");
        	 this.setLayout(null);
             this.getContentPane().setBackground(BACKGROUND_COLOR);
             this.setPreferredSize(new Dimension(SCREEN_SIZE,SCREEN_SIZE));
             this.setDefaultCloseOperation(EXIT_ON_CLOSE);
             this.setResizable(false);
             this.setIconImage(image.getImage());
             this.add(btnStartGame);
             this.add(toggleBtn);
             this.add(textAreaWelcome);
             this.add(labelImage);
             this.pack();
             this.setLocationRelativeTo(null);
             this.setVisible(true);
         }
         
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource().equals(btnStartGame)) {
				MainActivity.this.dispose();
				new MainActivityOne();
			}
			
			else {
				if(BACKGROUND_COLOR.equals(DARK)) {
					toggleBtn.setBackground(Color.WHITE);
					toggleBtn.setIcon(off);
		            BACKGROUND_COLOR = Color.WHITE;
		            textAreaWelcome.setForeground(Color.black);
		            try {
		         	   img = ImageIO.read(new File("newBlackholeLight.png"));
		         	 }
		         	 catch(Exception ex) {
		         		 ex.printStackTrace();
		         	 }
				}
				else {
					toggleBtn.setBackground(DARK);
					toggleBtn.setIcon(on);
		            BACKGROUND_COLOR = DARK;
		            textAreaWelcome.setForeground(Color.WHITE);
		            try {
		         	   img = ImageIO.read(new File("newBlackholeDark.png"));
		         	 }
		         	 catch(Exception ex) {
		         		 ex.printStackTrace();
		         	 }
				}
				 scaledImage = img.getScaledInstance(labelImage.getWidth(),labelImage.getHeight(),Image.SCALE_SMOOTH);
	        	 imageIcon = new ImageIcon(scaledImage);
	        	 labelImage.setIcon(imageIcon);
				 this.getContentPane().setBackground(BACKGROUND_COLOR);
			}
			
		}

}
