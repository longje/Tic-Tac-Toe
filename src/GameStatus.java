import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameStatus extends JPanel {
	public Player winner;
	private TicTacToe toe;
	private JLabel status;
	private JButton button;
	
	public GameStatus(TicTacToe toe)
	{
		status = new JLabel("", JLabel.CENTER);
		button = new JButton("Reset Game");
		this.toe = toe;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(status);
		this.add(button);
		status.setAlignmentX(CENTER_ALIGNMENT);
		button.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	public void resetStatus()
	{
		this.winner = null;
	}

	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, getWidth(), getHeight());
		if (winner != null)
			status.setText("Player " + winner + " Wins!");
		else
			status.setText("");
		status.setFont(new Font("Serif", Font.BOLD, 48));
		status.setForeground(Color.white);
        button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				toe.resetGame();
			}
        });
	}
}
