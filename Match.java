import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Match extends JFrame {

	private JPanel contentPane;
	private Button cell00;
	private Button cell01;
	private Button cell02;
	private Button cell10;
	private Button cell11;
	private Button cell12;
	private Button cell20;
	private Button cell21;
	private Button cell22;
	private JLabel endText;
	private JButton btnBack;
	
	private int turn = 0;
	private String[] simbol = {"O","X"};
	
	private String[][] board = new String[3][3];
	private HashSet<Button> cells = new HashSet<Button>();
	
	private boolean match = true;
	private JButton btnRestart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Match frame = new Match();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Match() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cell00 = new Button();
		cell00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell00,0,0);
			}
		});
		cell00.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell00.setBounds(181, 155, 100, 100);
		contentPane.add(cell00);
		cells.add(cell00);
		
		cell01 = new Button();
		cell01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell01,0,1);
			}
		});
		cell01.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell01.setBounds(281, 155, 100, 100);
		contentPane.add(cell01);
		cells.add(cell01);
		
		cell02 = new Button();
		cell02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell02,0,2);
			}
		});
		cell02.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell02.setBounds(381, 155, 100, 100);
		contentPane.add(cell02);
		cells.add(cell02);
		
		cell10 = new Button();
		cell10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell10,1,0);
			}
		});
		cell10.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell10.setBounds(181, 255, 100, 100);
		contentPane.add(cell10);
		cells.add(cell10);
		
		cell11 = new Button();
		cell11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell11,1,1);
			}
		});
		cell11.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell11.setBounds(281, 255, 100, 100);
		contentPane.add(cell11);
		cells.add(cell11);
		
		cell12 = new Button();
		cell12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell12,1,2);
			}
		});
		cell12.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell12.setBounds(381, 255, 100, 100);
		contentPane.add(cell12);
		cells.add(cell12);
		
		cell20 = new Button();
		cell20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell20,2,0);
			}
		});
		cell20.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell20.setBounds(181, 355, 100, 100);
		contentPane.add(cell20);
		cells.add(cell20);
		
		cell21 = new Button();
		cell21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell21,2,1);
			}
		});
		cell21.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell21.setBounds(281, 355, 100, 100);
		contentPane.add(cell21);
		cells.add(cell21);
		
		cell22 = new Button();
		cell22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(cell22,2,2);
			}
		});
		cell22.setFont(new Font("Arial Black", Font.BOLD, 50));
		cell22.setBounds(381, 355, 100, 100);
		contentPane.add(cell22);
		cells.add(cell22);
		
		endText = new JLabel();
		endText.setFont(new Font("Arial", Font.BOLD, 20));
		endText.setHorizontalAlignment(SwingConstants.CENTER);
		endText.setBounds(191, 461, 290, 46);
		contentPane.add(endText);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(181, 519, 117, 25);
		contentPane.add(btnBack);
		
		btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartMatch();
			}
		});
		btnRestart.setBounds(364, 519, 117, 25);
		contentPane.add(btnRestart);
	}
	
	public void buttonClicked(Button b, int x, int y) {
		if (match && b.getLabel().isEmpty()) {
			b.setLabel(simbol[turn]);
			board[x][y] = simbol[turn];
			haveWinner();
			
			if (turn == 0) turn++;
			else turn--;
		}
	}
	
	public void haveWinner() {
		if (completedRow() || completedColumn() || completedDiagonal()) {
			endText.setText("The winner is " + simbol[turn]);
			match = false;
		} else if (allFill()) {
			endText.setText("Draw!");
			match = false;
		}
	}
	
	public boolean completedRow() {
		int row = 0;
		for (String[] r: board) {
			row = 0;
	        for (String c: r) {
	            if (c != null && c.equals(simbol[turn])) {
	                row++;
	            }
	        }
	        if(row == 3) return true;
		}
	    return false;
	}
	
	public boolean completedColumn() {
		int col = 0;
		for (int c = 0; c < board.length; c++) {
			col = 0;
	        for (int r = 0; r < board[c].length; r++) {
	            if (board[r][c] != null && board[r][c].equals(simbol[turn])) {
	                col++;
	            }
	        }
	        if(col == 3) return true;
	       
		}
	    return false;
	}

	public boolean completedDiagonal() {
		int diag = 0;
		for (int i = 0; i < board.length; i++) {
		    if (board[i][i] != null && board[i][i].equals(simbol[turn])) {
		        diag++;
		    }
		}
		if (diag == 3) return true;
		
		diag = 0;
		for (int i = 0; i < board.length; i++) {
		    if (board[i][2-i] != null && board[i][2-i].equals(simbol[turn])) {
		        diag++;
		    }
		}
		if (diag == 3) return true;
		
		return false;
	}
	
	public boolean allFill() {
		int empty = 0;
		for (String[] r: board) {
	        for (String c: r) {
	            if (c == null) {
	                empty++;
	            }
	        }
		}
		return empty == 0;
	}
	
	public void restartMatch() {
		if (!match) {
			Iterator<Button> iter = cells.iterator();
			while (iter.hasNext()) {
				iter.next().setLabel("");
			}
			
			for (int r = 0; r < board.length; r++) {
		        for (int c = 0; c < board[r].length; c++) {
		            board[r][c] = null;
		        }
			}
			
			turn = 0;
			endText.setText("");
			match = true;
		}
	}
}
