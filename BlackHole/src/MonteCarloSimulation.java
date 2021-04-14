import java.util.ArrayList;
import java.util.Random;

public class MonteCarloSimulation {
    
	Random random;
	private Tile[] tiles;
	int[][] map;
	int NUMBER_OF_TIMES;
	boolean[] playerUsed;
	boolean[] botUsed;
	int playerCur = 0;
	int botCur = 0;
	int holes = 21;
	ArrayList<Integer> l;
	boolean[] used; 
	
	public MonteCarloSimulation(Tile[] tiles, boolean[] playerUsed, boolean[] botUsed, int NUMBER_OF_TIMES) {
		 used = new boolean[21];
		 l = new ArrayList<>();
		 random = new Random();
		 map = new int[21][2];
		 this.playerUsed = new boolean[10];
		 this.botUsed = new boolean[10];
         this.tiles = new Tile[21];
         this.NUMBER_OF_TIMES = NUMBER_OF_TIMES;
         for(int  i = 0; i < this.tiles.length; i++) {
        	 this.tiles[i] = new Tile(tiles[i].startX,tiles[i].startY,tiles[i].val,tiles[i].from,tiles[i].neighbours);
        	 if(tiles[i].from==0) l.add(i);
         }
         
         for(int i = 0; i < 10; i++) {
        	 this.playerUsed[i] = playerUsed[i];
        	 this.botUsed[i] = botUsed[i];
        	 if(playerUsed[i]) {playerCur++;holes--;}
        	 if(botUsed[i])  {botCur++; holes--;}
         }
         
         runSimulation();
	}
	
    public void runSimulation() {
    	//System.out.println("Currently Running runSimulation....");
    	if(holes==1) return;
    	for(int i = 0; i  < NUMBER_OF_TIMES; i++) {
    		int cur = botCur;
    		int ind = random.nextInt(l.size());
    		used[l.get(ind)] = true;
    		tiles[l.get(ind)].from = 2;
    		tiles[l.get(ind)].val = ""+(cur+1);
    		cur++;
    		runGameUntilEnd(l.get(ind));
    		tiles[l.get(ind)].from = 0;
    		tiles[l.get(ind)].val = "?";
    		cur--;
    		used[l.get(ind)] = false;
    	}
    }
    
    public void runGameUntilEnd(int firstMove) {
    	   //System.out.println("Currently Running runGameUntilEnd....");
    	    boolean[] localUsed = new boolean[21];
    	    for(int i = 0; i < 21; i++) localUsed[i] = used[i];
    	    Tile[] localtiles = new Tile[21];
    	    for(int  i = 0; i < this.tiles.length; i++) {
           	  localtiles[i] = new Tile(tiles[i].startX,tiles[i].startY,tiles[i].val,tiles[i].from,tiles[i].neighbours);
            }
        	int turn = 1;
        	boolean winOrDraw = false;
        	int curp = playerCur;
        	int curb = botCur+1;
        	int curholes = holes-1; 
        	while(curholes>1) {
        		int ind = random.nextInt(l.size());
    			while(localUsed[l.get(ind)]) {
    				ind = random.nextInt(l.size());
//    				System.out.println(l.get(ind));
//    				System.out.println(Arrays.toString(used));
    			}
        		if(turn==1) {
        			localUsed[l.get(ind)] = true;
            		localtiles[l.get(ind)].from = 1;
            		localtiles[l.get(ind)].val = ""+(curp+1);
            		curp++;
            		curholes--;
        		}
        		else {
        			localUsed[l.get(ind)] = true;
            		localtiles[l.get(ind)].from = 2;
            		localtiles[l.get(ind)].val = ""+(curb+1);
            		curb++;
            		curholes--;
        		}
        		turn = 1-turn;
        		//System.out.println(curholes);
        	}
        	
//        	for(int  i = 0; i < 21; i++) {
//            	System.out.println(localtiles[i]);
//            }
        	
        	
        	int playerSuck = 0;
        	int botSuck = 0;
    		for(int i = 0; i < 21; i++) {
    			if(localtiles[i].from==0) {
  
    				for(int j : localtiles[i].neighbours) {
    					if(localtiles[j].from==1) playerSuck += Integer.parseInt(localtiles[j].val.equals("?")?"0":localtiles[j].val);
    					else botSuck += Integer.parseInt(localtiles[j].val.equals("?")?"0":localtiles[j].val);
    				}
    			}
    		}
    		
    		//System.out.println(playerSuck+" "+botSuck);
    		
    		winOrDraw |= playerSuck>=botSuck;
        	map[firstMove][winOrDraw?1:0]++;
    }
    
	
	public int bestMove() {
		
		int ans = l.get(random.nextInt(l.size()));
		double avg = 0.0;
		for(int  i = 0; i  < 21; i++) {
			//System.out.println(Arrays.toString(map[i]));
			if(map[i][0]==0 && map[i][1]==0) continue;
				double prob = map[i][1]*1.0/(map[i][1]+map[i][0]);
				
				if(prob>avg) {
					ans = i;
					avg = prob;
				}	
		}
		
		return ans;
	}
	
	//test
//	public static void main(String[] args) {
//		Tile[] tiles = new Tile[21];
//		for(int i = 0; i < 21; i++) {
//			if(i==0) tiles[i] = new Tile(0,0,"1",1);
//			else tiles[i] = new Tile(0,0,"?",0);
//		}
//		addNeighbours(tiles);
//		boolean[] playerUsed = new boolean[10], botUsed = new boolean[10];
//		
//		playerUsed[0] = true;
//		
//			
//		MonteCarloSimulation mcs = new MonteCarloSimulation(tiles,playerUsed,botUsed,100);
//		
//		System.out.println(mcs.bestMove());
//	}
	
//	private static void addNeighbours(Tile[] tiles) {
//		// TODO Auto-generated method stub
//		
//		tiles[0].neighbours.add(1);
//		tiles[0].neighbours.add(2);
//
//        for(int i = 1; i < 6; i++) {
//        	for(int j = 0; j < matrix[i].length; j++) {
//        		for(int k = 0; k < 6; k++) {
//        			int curx = i+dirx[k];
//        			int cury = j+diry[k];
//        			
//        			if(curx==i) {
//        				if(-1<cury && cury<i+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
//        			}
//        			else if(curx==i+1) {
//        				if(curx<6 && -1<cury && cury<curx+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
//        			}
//        			else {
//        				if(-1<cury && cury<curx+1) tiles[matrix[i][j]].neighbours.add(matrix[curx][cury]);
//        			}
//        		}
//        	}
//        }
//        
////        for(int  i = 0; i < 21; i++) {
////        	System.out.println(tiles[i]);
////        }
//		
//	}

}
