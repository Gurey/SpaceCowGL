package spacecow.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.Border;

public class ScoreGUI extends JPanel {

	private ScrollPane scroll;
	private JLabel scoreLabel;
	private GridLayout gLay;
	private ArrayList<JLabel> scoreList;
	/**
	 * Create the panel.
	 */
	public ScoreGUI() {
		scoreList = new ArrayList<JLabel>();
		gLay = new GridLayout(1, 2);
		this.setLayout(gLay);
		scroll = new ScrollPane();
		BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
		scroll.setLayout(b);
		scoreLabel = new JLabel("hej");
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setBorder(BorderFactory.createEtchedBorder());
		scroll.add(scoreLabel);
		
		this.add(scroll);

	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

}
