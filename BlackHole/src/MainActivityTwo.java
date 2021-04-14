import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class MainActivityTwo extends JFrame {
	  
	  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1807187752974444011L;
	static ImageIcon image = new ImageIcon("blackhole.png");
	
	 
	  public MainActivityTwo() {
		  
		  
		  this.add(new MainActivityTwoPanel());
    	  this.setTitle("BlackHole");
    	  this.setIconImage(image.getImage());
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setResizable(false);
    	  this.pack();
    	  this.setVisible(true);
    	  this.setLocationRelativeTo(null);
	  
	  }
	  
	  

}