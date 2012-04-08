public class Move {
	public int x;
	public int y;
	public MoveShape move;
	public GridCoord gridLoc;
	public Player player;
	
	public Move(int x, int y, MoveShape move, GridCoord gridCoord, Player player)
	{
		this.x = x;
		this.y = y;
		this.move = move;
		this.gridLoc = gridCoord;
		this.player = player;
	}
}