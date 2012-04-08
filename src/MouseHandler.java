import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
	
	private TicTacToeBoard ticToe;
	
	public MouseHandler(TicTacToeBoard ticToe)
	{
		this.ticToe = ticToe;
	}

	public void mouseClicked(MouseEvent arg0) {
		ticToe.userMakeMove(arg0.getX(), arg0.getY());
	}

	public void mouseEntered(MouseEvent arg0) {
		

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		ticToe.setHighLight(arg0.getX(), arg0.getY(), true);

	}

	public void mouseReleased(MouseEvent arg0) {
		ticToe.setHighLight(0, 0, false);
	}

}
