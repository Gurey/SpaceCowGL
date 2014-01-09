package spacecow.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class StartGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnStartGame;
	private InteractionGUI action;
	private JButton btnOptions;


	/**
	 * Create the frame.
	 */
	public StartGUI() {
		action = new InteractionGUI(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		btnOptions = new JButton("Options");
		contentPane.add(btnOptions);
		btnOptions.addActionListener(action);
		
		btnStartGame = new JButton("Start Game");
		contentPane.add(btnStartGame);
		btnStartGame.addActionListener(action);
		
	}

	public JButton getBtnStartGame() {
		return btnStartGame;
	}
	public JPanel getContentPane(){
		return contentPane;
	}
	public JButton getBtnOptions() {
		return btnOptions;
	}
}
