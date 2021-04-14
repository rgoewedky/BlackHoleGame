import java.util.HashSet;

public class Tile{
	int startX;
	int startY;
	String val;
	int from; // 1 -player, 0 - bot, -1 empty
	HashSet<Integer> neighbours;
	
	public Tile(int startX, int startY, String val, int from) {
		this.neighbours = new HashSet<Integer>();
		this.startX = startX;
		this.startY = startY;
		this.val = val;
		this.from = from;
	}
	
	public Tile(int startX, int startY, String val, int from, HashSet<Integer> neighbours) {
		this(startX,startY,val,from);
		this.neighbours = neighbours;
	}
	
	public String toString() {
		return "["
				+ "\n\tstartX : " + startX
				+ "\n\tstartY : " + startY
				+ "\n\tval : " + val
				+ "\n\tfrom : " + from
				+ "\n\tneignbours :"+ neighbours.toString()
				+ "]";
	}
}