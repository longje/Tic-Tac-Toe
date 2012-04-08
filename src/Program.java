import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {
		  
		  final JFrame frame = new JFrame("Tic Tac Toe");
		  final TicTacToeBoard toe = new TicTacToeBoard();
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setSize(500, 550);
		  //frame.setLayout(new BorderLayout());
		  
		  toe.setPreferredSize(new Dimension(400, 300));
		  toe.addMouseListener(new MouseHandler(toe));
		  
		  frame.add(toe);
		  frame.add(BorderLayout.SOUTH, toe.game.status);
		  frame.setVisible(true);
	  }
}
