import java.util.ArrayList;

public class TicTacToe {
	public GameStatus status;
	public Player winner;
	public WayToWin wayWon;
	private Boolean swapTurns;
	private ArrayList<Move> movesMade;
	private ArrayList<GridCoord> movesLeft;
	private ArrayList<Move> winningMoves;
	
	Board board;
	
	public TicTacToe(Board board)
	{
		movesMade = new ArrayList<Move>();
		movesLeft = determineMoves();
		status = new GameStatus(this);
		swapTurns = false;
		this.board = board;
	}
	
	/*
	 * Reset the game.
	 */
	public void resetGame()
	{
		winner = null;
		status.resetStatus();
		movesMade = new ArrayList<Move>();
		movesLeft = determineMoves();
		//Swap turns with computer
		swapTurns = swapTurns ^ true;
		if (swapTurns)
			makeRandomMove();
		board.repaint();
	}
	
	/*
	 * All possible grid coords that are available for the game - 3x3 = 9
	 */
	private ArrayList<GridCoord> determineMoves()
	{
		ArrayList<GridCoord> temp = new ArrayList<GridCoord>();
		for (int i = 1; i <= 3; i++)
			for (int j = 1; j <= 3; j++)
				temp.add(new GridCoord(i, j));
		return temp;
	}
	
	public int[] getWinningMoveCoords()
	{
		return new int[]{winningMoves.get(0).x,
						winningMoves.get(0).y,
						winningMoves.get(2).x,
						winningMoves.get(2).y};
	}
	
	public ArrayList<Move> getMovesMade()
	{
		return movesMade;
	}
	
	/*
	 * Get move that corresponds to grid coordinates user clicked
	 */
	private GridCoord getAvailableMove(int x, int y)
	{
		GridCoord g = null;
		int i = 0;
		for(; i < movesLeft.size(); i++)
			if (movesLeft.get(i).x == x && movesLeft.get(i).y == y) {
				g = movesLeft.get(i);
				break;
			}

		movesLeft.remove(i);
		return g;
	}
	
	/*
	 * Has the move already been played?
	 */
	private Boolean isMoveAvailable(int x, int y)
	{
		for(GridCoord m: movesLeft)
		{
			if (m.x == x && m.y == y)
				return true;
		}
		return false;
	}
	
	/*
	 * If there are moves left, play one of them.
	 */
	private void makeRandomMove()
	{
		if (movesLeft.size() > 0) {
			GridCoord g = movesLeft.get(0);
			movesLeft.remove(g);
			Move m = new Move(g.x*100, g.y*100, MoveShape.O, g, Player.Two);
			makeMove(m);
		}
	}
	
	public void userMakeMove(int x, int y)
	{
		if (x > 0 && y > 0 && isMoveAvailable(x/100, y/100)){
			makeMove(new Move(x, y,
					MoveShape.X,
					getAvailableMove(x/100, y/100),
					Player.One));
			makeRandomMove();
		}
	}
	
	/*
	 * Make a move, so long as the game hasn't been won
	 */
	private void makeMove(Move m)
	{
		if (!isGameWon())
			movesMade.add(m);
	}
	
	/*
	 * Check to see if the game has been won.
	 */
	public Boolean isGameWon()
	{
		Player[] players = {Player.One, Player.Two};

		for (int i = 0; i < players.length; i++) {
			int[][] moves = new int[3][3];
			for(Move m: getPlayerMoves(players[i])) {
				moves[m.gridLoc.x-1][m.gridLoc.y-1] = 1;
			}
			winningMoves = checkGrid(moves);
			if (winningMoves != null && winningMoves.size() > 2) {
				winner = players[i];
				break;
			}
		}
		return winningMoves != null;
	}
	
	/*
	 * Get the moves that have been made by a specific player
	 */
	private ArrayList<Move> getPlayerMoves(Player player)
	{
		ArrayList<Move> playerMoves = new ArrayList<Move>();
		for(Move m: movesMade)
			if (m.player == player)
				playerMoves.add(m);
		return playerMoves;
	}
	
	/*
	 * Helper method to determine winning moves if any
	 */
	private ArrayList<Move> checkGrid(int[][] grid)
	{
		ArrayList<Move> diag1 = new ArrayList<Move>();
		ArrayList<Move> diag2 = new ArrayList<Move>();
		for (int i = 0; i < 3; i++) {
			ArrayList<Move> horz = new ArrayList<Move>();
			ArrayList<Move> vert = new ArrayList<Move>();
			if (grid[i][i] == 1)
				diag1.add(getSpecificMove(i+1, i+1));
			if (grid[2 - i][i] == 1)
				diag2.add(getSpecificMove(2-i+1, i+1));
			if (diag1.size() == 3) {
				wayWon = WayToWin.Diagonal2;
				return diag1;
			}
			if (diag2.size() == 3) {
				wayWon = WayToWin.Diagonal;
				return diag2;
			}
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] == 1)
					horz.add(getSpecificMove(i+1, j+1));
				if (grid[j][i] == 1)
					vert.add(getSpecificMove(j+1, i+1));
				if (vert.size() == 3) {
					wayWon = WayToWin.Horizontal;
					return vert;
				}
				if(horz.size() == 3) {
					wayWon = WayToWin.Vertical;
					return horz;
				}
			}
		}
		return null;
	}
	
	/*
	 * Get move based on grid coords
	 */
	private Move getSpecificMove(int x, int y)
	{
		for(Move m: movesMade)
			if (m.gridLoc.x == x && m.gridLoc.y == y)
				return m;
		return null;
	}
	
	
}
