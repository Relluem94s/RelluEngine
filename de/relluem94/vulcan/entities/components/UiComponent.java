package de.relluem94.vulcan.entities.components;

import de.relluem94.vulcan.main.Main;

public class UiComponent {
	
	public static String info = "Info ueber Entity";
	
	private static boolean isClicked(int id){
		return true;
	}
	
	public static void updateUI(int id){
		if(isClicked(id)){
			if(Main.ui_gui.isVisible()){
				Main.ui_gui.setVisible(false);
			}
			else{
				Main.ui_gui.setVisible(true);
				Main.ui_gui.setText(info);
			}
		}
		
	}
}
