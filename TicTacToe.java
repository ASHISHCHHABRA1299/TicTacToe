package tictactoe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
	public static int BOARD_SIZE = 3;

	public static enum GameStatus {
		Incomplete, Xwins, Zwins, Tie
	}

	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
	boolean crossturn = true;

	public TicTacToe() {
		super.setTitle("TIC TAC TOE");
		super.setSize(800, 800);
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font f = new Font("Comic Sans", 1, 150);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton();
				buttons[row][col] = button;
				button.setFont(f);
				button.addActionListener(this);
				super.add(button);

			}
		}
		super.setVisible(true);
		super.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		MakeMove(clickedButton);
		GameStatus gs = this.gamestatus();
		if (gs == GameStatus.Incomplete)
			return;
		declarewinner(gs);
		int choice = JOptionPane.showConfirmDialog(this, "DO YO WANT PLAY AGAIN");
		if (choice == JOptionPane.YES_OPTION) {
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					buttons[row][col].setText("");
				}
			}
			crossturn = true;
		} else {
			super.dispose();
		}
	}

	private void declarewinner(GameStatus gs) {
		if (gs == GameStatus.Xwins)
			JOptionPane.showMessageDialog(this, "X WINS ");
		else if (gs == GameStatus.Zwins)
			JOptionPane.showMessageDialog(this, "Z WINS ");
		else
			JOptionPane.showMessageDialog(this, "IT's A TIE");
	}

	private void MakeMove(JButton clickedButton) {
		String btntxt = clickedButton.getText();
		if (btntxt.length() > 0) {
			JOptionPane.showMessageDialog(this, "INVALID MOVE");
		} else {
			if (crossturn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("O");
			}
			crossturn = !crossturn;
		}
	}

	private GameStatus gamestatus() {
		String text1 = "", text2 = "";
		int row = 0, col = 0;
		// check values in row
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0 || text2.length() == 0) {
					break;
				}
				col++;
			}
			if (col == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwins;
				} else {
					return GameStatus.Zwins;
				}
			}
			row++;
		}
		// check values in col
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = buttons[col][row].getText();
				text2 = buttons[col][row + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0 || text2.length() == 0) {
					break;
				}
				row++;
			}
			if (row == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwins;
				} else {
					return GameStatus.Zwins;
				}
			}
			col++;
		}

		// check values in diagonal1
		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row + 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0 || text2.length() == 0) {
				break;
			}
			row++;
			col++;
		}
		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.Xwins;
			} else {
				return GameStatus.Zwins;
			}
		}

		// check values in diagonal2
		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row - 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0 || text2.length() == 0) {
				break;
			}
			row--;
			col++;
		}
		if (row == 0) {
			if (text1.equals("X")) {
				return GameStatus.Xwins;
			} else {
				return GameStatus.Zwins;
			}
		}

		String txt = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				txt = buttons[row][col].getText();
				if (txt.length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}
		return GameStatus.Tie;
	}
}
