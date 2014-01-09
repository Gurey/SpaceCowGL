package spacecow.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import spacecow.engine.Game;

public class InteractionGUI implements ActionListener {

	private StartGUI gui;
	
	public InteractionGUI(StartGUI gui){
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e.getSource());
		if (e.getSource().equals(gui.getBtnStartGame())) {
			System.out.println("start a new Game");
			gui.getBtnStartGame().setEnabled(false);
			new Game().start();
		}
		
	}

}
