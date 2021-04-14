import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class MainActivityTwoPanel extends JPanel {
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7722275847504409966L;
	static final int SCREEN_SIZE = 600;
	static final int UNIT_SIZE = 8;
	static final int NUMBER_OF_UNITS = 5;
	static final int SIZE = UNIT_SIZE*NUMBER_OF_UNITS;
	static Tile[] tiles;
	static int[][] player1_Coord, player2_Coord;
	static boolean[] player1_Used, player2_Used, gUsed;
	static Color[] color = {new Color(229,228,226),new Color(100,149,237),new Color(250,128,114)};
	static boolean isPlayer1_Bot = MainActivityOne.isBot1;
	static boolean isPlayer2_Bot = MainActivityOne.isBot2;
	static String player1_Name = isPlayer1_Bot?"Bot-1":MainActivityOne.player1_Name;
	static String player2_Name = isPlayer2_Bot?"Bot-2":MainActivityOne.player2_Name;
	int holes = 21;
	int player1_Suck= 0;
	int player2_Suck= 0;
	int candidate = -1;
	int flag = 0;
	int progress = 0;
	int ind;
	static int[][] matrix = {
			                  {0},
			                 {1 , 2},
			                {3 , 4, 5},
			               {6 , 7, 8, 9},
			             {10,11,12,13,14},
			            {15,16,17,18,19,20}};
	
	static int[] dirx = {-1,-1,1,1,0,0};
	static int[] diry = {-1,0,1,0,1,-1};
	static boolean running;
	Timer timer;
	int turn = -1;
	MonteCarloSimulation mcs;
	String fileName = "C:\\Users\\B JAGDISH REDDY\\OneDrive\\Desktop\\mixkit-playground-fun-12\\playgroundFun.wav";
	Clip clip;
	

	
	public MainActivityTwoPanel() {
		
		
		
		turn = 1;
		running = true;
		player1_Coord = new int[10][2];
		player2_Coord = new int[10][2];
		player1_Used = new boolean[10];
		player2_Used = new boolean[10];
		gUsed = new boolean[21];
		tiles = new Tile[21];
		this.setPreferredSize(new Dimension(SCREEN_SIZE,SCREEN_SIZE));
		this.setLayout(null);
		this.setBackground(MainActivity.BACKGROUND_COLOR);
		this.setFocusable(true);
		this.addMouseListener(new CustomMouseListener());
		this.addKeyListener(new CustomKeyListener());
		
		int x = UNIT_SIZE*((SCREEN_SIZE/UNIT_SIZE - NUMBER_OF_UNITS)/2);
		int y = UNIT_SIZE*3;
		int cur = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j <= i ;j++) {
				tiles[cur] = new Tile(x+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE, y,"?",0);
				cur++;
			}
			x -= (NUMBER_OF_UNITS-NUMBER_OF_UNITS/2)*UNIT_SIZE;
			y += (NUMBER_OF_UNITS+1)*UNIT_SIZE;
		}
		
		x = UNIT_SIZE*2;
	    y = UNIT_SIZE*2;
		
		for(int i = 0; i < 10; i++) {
           		player1_Coord[i] = new int[] {x, y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE};
		}
		
		 x = SCREEN_SIZE-UNIT_SIZE*(NUMBER_OF_UNITS+2);
		 y = UNIT_SIZE*2;
		 
		for(int i = 0; i < 10; i++) {
			player2_Coord[i] = new int[] {x, y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE};
		}
		
		addNeighbours();
        try {
			
			File soundFile = new File(fileName);
			
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		   
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    	
    }
	
	private void addNeighbours() {
		
		tiles[0].neighbours.add(1);
		tiles[0].neighbours.add(2);

        for(int i = 1; i < 6; i++) {
        	for(int j = 0; j < matrix[i].length; j++) {
        		for(int k = 0; k < 6; k++) {
        			int curx = i+dirx[k];
        			int cury = j+diry[k];
        			
        			if(curx==i) {
        				if(-1<cury && cury<i+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
        			}
        			else if(curx==i+1) {
        				if(curx<6 && -1<cury && cury<curx+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
        			}
        			else {
        				if(-1<cury && cury<curx+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
        			}
        		}
        	}
        }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		 //TODO Auto-generated method stub
			if(running) {
				if(!clip.isRunning()) clip.setFramePosition(0);
				clip.start();
				int x = UNIT_SIZE*((SCREEN_SIZE/UNIT_SIZE - NUMBER_OF_UNITS)/2);
				int y = UNIT_SIZE*3;
				int cur = 0;
				for(int i = 0; i < 6; i++) {
					for(int j = 0; j <= i ;j++) {
						g.setColor(color[tiles[cur].from]);
						if(holes==1 && !gUsed[cur]) g.setColor(Color.black);
						g.fillRoundRect(x+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE, y,NUMBER_OF_UNITS*UNIT_SIZE, NUMBER_OF_UNITS*UNIT_SIZE,1000,1000);
						g.setColor(Color.black);
						g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
						FontMetrics metrics2 = getFontMetrics(g.getFont());
						g.drawString(tiles[cur].val, x+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth(tiles[cur].val))/2,y+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
						cur++;
					}
					x -= (NUMBER_OF_UNITS-NUMBER_OF_UNITS/2)*UNIT_SIZE;
					y += (NUMBER_OF_UNITS+1)*UNIT_SIZE;
				}
				
				if(turn == 1 && !isPlayer1_Bot) {
					g.setColor(new Color(100,149,237));
					g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
					FontMetrics metrics2 = getFontMetrics(g.getFont());
					g.drawString(player1_Name+" Turn...", x+(6*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player1_Name+" Turn..."))/2,y+(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
				}
				if(turn==0 && !isPlayer2_Bot) {
					g.setColor(new Color(250,128,114));
					g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
					FontMetrics metrics2 = getFontMetrics(g.getFont());
					g.drawString(player2_Name+" Turn...", x+(6*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player2_Name+" Turn..."))/2,y+(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
				}
				
				x = UNIT_SIZE*2;
			    y = UNIT_SIZE*2;
				
				for(int i = 0; i < 10; i++) {
	               	if(!player1_Used[i]) {
						g.setColor(new Color(100,149,237));
						g.fillRoundRect(x, y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE,NUMBER_OF_UNITS*UNIT_SIZE, NUMBER_OF_UNITS*UNIT_SIZE,1000,1000);
						g.setColor(Color.black);
						g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
						FontMetrics metrics2 = getFontMetrics(g.getFont());
						g.drawString(""+(i+1), x+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth(""+(i+1)))/2,y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
					}
				}
				
				g.setColor(new Color(100,149,237));
				g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
				FontMetrics metrics1 = getFontMetrics(g.getFont());
				g.drawString(player1_Name, x+(NUMBER_OF_UNITS*UNIT_SIZE-metrics1.stringWidth(player1_Name))/2,y+10*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
				
				
				
				 x = SCREEN_SIZE-UNIT_SIZE*(NUMBER_OF_UNITS+2);
				 y = UNIT_SIZE*2;
				
				for(int i = 0; i < 10; i++) {
					if(!player2_Used[i]) {
						g.setColor(new Color(250,128,114));
						g.fillRoundRect(x, y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE,NUMBER_OF_UNITS*UNIT_SIZE, NUMBER_OF_UNITS*UNIT_SIZE,1000,1000);
						g.setColor(Color.black);
						g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
						FontMetrics metrics2 = getFontMetrics(g.getFont());
						g.drawString(""+(i+1), x+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth(""+(i+1)))/2,y+i*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
				    }
				}
				
				g.setColor(new Color(250,128,114));
				g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
				metrics1 = getFontMetrics(g.getFont());
				g.drawString(player2_Name, x+(NUMBER_OF_UNITS*UNIT_SIZE-metrics1.stringWidth(player2_Name))/2,y+10*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
				
				
				if(isPlayer2_Bot && turn==0) {
					player_Turn(turn);
				}
				
				if(isPlayer1_Bot && turn==1) {
					player_Turn(turn);
				}
				
			}
			
			else {
				int x1 = UNIT_SIZE*((SCREEN_SIZE/UNIT_SIZE - NUMBER_OF_UNITS)/2);
				int y1 = UNIT_SIZE*3;
				int cur1 = 0;
				for(int i = 0; i < 6; i++) {
					for(int j = 0; j <= i ;j++) {
						    if(tiles[ind].neighbours.contains(cur1)) {
						    	g.setColor(Color.black);
								g.fillRoundRect(x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE, y1,progress, progress,1000,1000);
								g.setColor(Color.black);
								g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
								FontMetrics metrics2 = getFontMetrics(g.getFont());
								g.drawString(tiles[cur1].val, x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth(tiles[cur1].val))/2,y1+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
							    cur1++;
							    continue;
						    }
						    if(!gUsed[cur1]) {
						    	g.setColor(Color.black);
								g.fillRoundRect(x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE, y1,NUMBER_OF_UNITS*UNIT_SIZE, NUMBER_OF_UNITS*UNIT_SIZE,1000,1000);
								g.setColor(Color.white);
								g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
								FontMetrics metrics2 = getFontMetrics(g.getFont());
								g.drawString("H", x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth("H"))/2,y1+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
						    	cur1++;
						    	continue;
						    }
							g.setColor(color[tiles[cur1].from]);
							g.fillRoundRect(x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE, y1,NUMBER_OF_UNITS*UNIT_SIZE, NUMBER_OF_UNITS*UNIT_SIZE,1000,1000);
							g.setColor(Color.black);
							g.setFont(new Font(Font.MONOSPACED,Font.BOLD,21));
							FontMetrics metrics2 = getFontMetrics(g.getFont());
							g.drawString(tiles[cur1].val, x1+j*(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS*UNIT_SIZE-metrics2.stringWidth(tiles[cur1].val))/2,y1+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
						    cur1++;
					}
					x1 -= (NUMBER_OF_UNITS-NUMBER_OF_UNITS/2)*UNIT_SIZE;
					y1 += (NUMBER_OF_UNITS+1)*UNIT_SIZE;
				}
			}
			
			if(holes==1) {
				running = false;
				clip.stop();
				resultAnnouncement(g);
			}
		
		
	}

	private void player_Turn(int turn) {
		// TODO Auto-generated method stub
		
		//if(player2_Used[0]==false) {
		   if(turn==0) {
			mcs = new MonteCarloSimulation(tiles,player1_Used,player2_Used,100000);
			
			for(int i = 0; i < 10; i++) {
				 if(!player2_Used[i]) {
					 player2_Used[i] = true;
					 int ind = mcs.bestMove();
					 tiles[ind].val = ""+(i+1);
					 tiles[ind].from = 2;
					 holes--;
					 this.turn = 1- turn;
					 gUsed[ind] = true;
					 break;
				 }
			}
		   }
		   else {
			   mcs = new MonteCarloSimulation(tiles,player1_Used,player2_Used,1000);
				
				for(int i = 0; i < 10; i++) {
					 if(!player1_Used[i]) {
						 player1_Used[i] = true;
						 int ind = mcs.bestMove();
						 tiles[ind].val = ""+(i+1);
						 tiles[ind].from = 1;
						 holes--;
						 this.turn = 1- turn;
						 gUsed[ind] = true;
						 break;
					 }
				}
		   }
		   repaint();

	}
	
	

	private void resultAnnouncement(Graphics g) {
		// TODO Auto-generated method stub
	
		ind  = -1;
		for(int i = 0; i < 21; i++) {
			if(!gUsed[i]) {
				ind = i;
				break;
			}
		}
		
		timer = new Timer(150,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(progress<SIZE) {
					repaint();
					progress+=2;
				}
				else timer.stop();
			}
		});
		
		timer.start();
		
		if(progress>=SIZE) {
			
			timer.stop();
			
			for(int j : tiles[ind].neighbours) {
				if(tiles[j].from==1) player1_Suck += Integer.parseInt(tiles[j].val.equals("?")?"0":tiles[j].val);
				else player2_Suck += Integer.parseInt(tiles[j].val.equals("?")?"0":tiles[j].val);
			}
			
			int x1 = UNIT_SIZE*((SCREEN_SIZE/UNIT_SIZE - NUMBER_OF_UNITS)/2);
			int y1 = UNIT_SIZE*3;
			for(int i = 0; i < 6; i++) {
				x1 -= (NUMBER_OF_UNITS-NUMBER_OF_UNITS/2)*UNIT_SIZE;
				y1 += (NUMBER_OF_UNITS+1)*UNIT_SIZE;
			}
			
			int player1_Scored = (55-player1_Suck);
			int player2_Scored = (55-player2_Suck);
		
			
			g.setColor(new Color(100,149,237));
			g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString(player1_Name+" SCORED : "+player1_Scored, x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player1_Name+" SCORED : "+player1_Scored))/2,y1+(NUMBER_OF_UNITS+1)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			

			g.setColor(new Color(250,128,114));
			g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
			metrics2 = getFontMetrics(g.getFont());
			g.drawString(player2_Name+" SCORED : "+player2_Scored, x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player2_Name+" SCORED : "+player2_Scored))/2,y1+(NUMBER_OF_UNITS+5)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			
			
			if(player1_Suck==player2_Suck) {
				g.setColor(Color.gray);
				g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
				metrics2 = getFontMetrics(g.getFont());
				g.drawString("IT'S A DRAW", x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth("IT'S A DRAW"))/2,y1+(NUMBER_OF_UNITS+9)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			}
			else if(player1_Suck>player2_Suck) {
				g.setColor(Color.GREEN);
				g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
				metrics2 = getFontMetrics(g.getFont());
				g.drawString(player2_Name+" WON", x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player2_Name+" WON"))/2,y1+(NUMBER_OF_UNITS+9)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			}
			else {
				g.setColor(Color.green);
				g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
				metrics2 = getFontMetrics(g.getFont());
				g.drawString(player1_Name+" WON", x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth(player1_Name+" WON"))/2,y1+(NUMBER_OF_UNITS+9)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			}
			
			
			g.setColor(Color.red);
			g.setFont(new Font(Font.MONOSPACED,Font.TYPE1_FONT,15));
			metrics2 = getFontMetrics(g.getFont());
			g.drawString("PRESS 'R' to RESTART OR ANY KEY TO QUIT", x1+(7*(NUMBER_OF_UNITS+1)*UNIT_SIZE-metrics2.stringWidth("PRESS 'R' to RESTART OR ANY KEY TO QUIT"))/2,y1+(NUMBER_OF_UNITS+13)*UNIT_SIZE+(NUMBER_OF_UNITS+1)*UNIT_SIZE/2);
			
			
		
			
	       
			
			
		}
		
	}
	
	
	private class CustomKeyListener extends KeyAdapter{
		public void keyTyped(KeyEvent e) {
			MainActivityOne.matRef.dispose();
			if(e.getKeyChar()=='R') {
				new MainActivity();
			}
			else System.exit(0);
		}
	}
	
	
	private class CustomMouseListener extends MouseAdapter{

		public void mousePressed(MouseEvent e) {
			
			int x = (int) e.getX();
			int y = (int) e.getY();
			
			
			if(turn==1 && !isPlayer1_Bot) {
				for(int i = 0; i < 10; i++) {
					 if(!player1_Used[i]) {
						 int curX = x - player1_Coord[i][0];
						 int curY = y - player1_Coord[i][1];
						 
						 if(0<=curX && curX<SIZE && 0<=curY && curY<SIZE) {
							 candidate = i;
							 flag = 1;
							return;
						 }
						 candidate = -1;
						 return;
					 }
				}
			}
			if(turn==0 && !isPlayer2_Bot) {
				for(int i = 0; i < 10; i++) {
					 if(!player2_Used[i]) {
						 int curX = x - player2_Coord[i][0];
						 int curY = y - player2_Coord[i][1];
						 
						 if(0<=curX && curX<SIZE && 0<=curY && curY<SIZE) {
							 candidate = i;
							 flag = 2;
							return;
						 }
						 candidate = -1;
						 return;
					 }
				}
			}
		}


		
		public void mouseReleased(MouseEvent e) {
			
			if(candidate!=-1) {
				int x = (int) e.getX();
				int y = (int) e.getY();
				
				for(int i = 0; i < 21; i++) {
					 if(!gUsed[i]) {
						 int curX = x - tiles[i].startX;
						 int curY = y - tiles[i].startY;
						 
						 if(0<=curX && curX<SIZE && 0<=curY && curY<SIZE) {
							 gUsed[i] = true;
							 if(flag==1) player1_Used[candidate] = true;
							 if(flag==2) player2_Used[candidate] = true;
							 tiles[i].val = ""+(candidate+1);
							 tiles[i].from = flag;
							 holes--;
							 turn = 1- turn;
							 break;
						 }
					 }
				}
				repaint();
			}
		}	
	}
}
