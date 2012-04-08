import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class TicTacToeBoard extends JPanel implements Board {
	public TicTacToe game;
	
	int[] highlightCoords;
	Graphics2D ga;

	Boolean drawHighlight;

	/*
	 * Initialization
	 */
	public TicTacToeBoard()
	{
		highlightCoords = new int[2];
		drawHighlight = false;
		game = new TicTacToe(this);
		setLayout(new BorderLayout());
	}
	
	/*
	 * Draw the game
	 */
	public void paint(Graphics g) {
		this.ga = (Graphics2D)g;
		this.setBackground(Color.BLACK);
		ga.fillRect(0, 0, getWidth(), getHeight());
	
		ga.setColor(Color.WHITE); 
		ga.setStroke(new BasicStroke(4));
		drawBoard();
		if (game.isGameWon())
			gameWon();
		
		if (drawHighlight)
			highLightMove(highlightCoords[0], highlightCoords[1]);

	}
	
	/*
	 * Called on mouse click
	 * Create the move in player one played - where they clicked
	 * Random move refers to player two
	 */
	public void userMakeMove(int j, int k)
	{
		int x = roundHundred(j);
		int y = roundHundred(k);
		game.userMakeMove(x, y);
		repaint();
	}

	/*
	 * Set status indicating winner and draw line indicating three in a row.
	 */
	private void gameWon()
	{
		game.status.winner = game.winner;
		game.status.repaint();
		ga.setColor(Color.GREEN);
		int[] winningCoords = game.getWinningMoveCoords();
		if (game.wayWon == WayToWin.Vertical)
			drawLine(winningCoords[0] + 50,
						winningCoords[1] - 25,
						winningCoords[2] + 50,
						winningCoords[3] + 125);
		else if (game.wayWon == WayToWin.Horizontal)
			drawLine(winningCoords[0] - 25,
						winningCoords[1] + 50,
						winningCoords[2] + 125,
						winningCoords[3] + 50);
		else if (game.wayWon == WayToWin.Diagonal2)
			drawLine(winningCoords[0] - 25,
						winningCoords[1] - 25,
						winningCoords[2] + 125,
						winningCoords[3] + 125);
		else
			drawLine(winningCoords[0] + 125,
						winningCoords[1] - 25,
						winningCoords[2] - 25,
						winningCoords[3] + 125);
	}
	
	private void drawLine(int startX, int startY, int endX, int endY)
	{
		ga.drawLine(startX, startY, endX, endY);
	}
	
	
	/*
	 * Draw moves made thus far and grid
	 */
	private void drawBoard()
	{
		for (Move m: game.getMovesMade())
			drawMove(m);
		
		drawGrid(ga);
	}
	
	/*
	 * Determine if X's or O's should be drawn
	 */
	private void drawMove(Move m)
	{
		if (m.move == MoveShape.O)
			drawO(m.x, m.y);
		else
			drawX(m.x, m.y);
	}
	
	/*
	 * Draw an X
	 */
	private void drawX(int x, int y)
	{
		Line2D one = new Line2D.Double(x, y, x+99, y+99);
		Line2D two = new Line2D.Double(x+99, y, x, y+99);
		ga.draw(one);
		ga.draw(two);
	}
	
	/*
	 * Draw an O
	 */
	private void drawO(int x, int y)
	{
		ga.draw(new Ellipse2D.Float(x, y, 99.0f, 99.0f));
	}
	
	/*
	 * Draw the grid
	 */
	private void drawGrid(Graphics2D ga) {
		ga.draw(new Line2D.Double(100, 200, 400, 200));
		ga.draw(new Line2D.Double(100, 300, 400, 300));
		ga.draw(new Line2D.Double(200, 100, 200, 400));
		ga.draw(new Line2D.Double(300, 100, 300, 400));
	}
	
	/*
	 * Turn mouse coordinates into grid coords
	 */
	private int roundHundred(int x)
	{
		if (x >= 100 && x <= 400)
			return (x / 100) * 100;
		return 0;
	}

/*
 * Features for the game
 */
	
	/*
	 * Highlight square when user clicks
	 */
	public void setHighLight(int j, int k, Boolean v)
	{
		if (j > 100  && j < 400 && k < 400 && k > 100) {
			drawHighlight = v;
			highlightCoords[0] = j;
			highlightCoords[1] = k;
		}
		else
			drawHighlight = false;
		this.repaint();
	}
	
	/*
	 * Will be drawn if user has clicked on square and hasn't let up.
	 */
	private void highLightMove(int j, int k)
	{
		int x = roundHundred(j);
		int y = roundHundred(k);
		//System.out.println(x + " " + y);
		this.paintComponents((Graphics)ga);
		ga.setColor(Color.BLUE); 
		ga.setStroke(new BasicStroke(4));
		ga.draw(new Rectangle(x, y, 100, 100));
		drawHighlight = false;
	}
}
