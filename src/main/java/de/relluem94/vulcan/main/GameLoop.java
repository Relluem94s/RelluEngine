package de.relluem94.vulcan.main;

import static de.relluem94.vulcan.main.Main.cleanUp;
import static de.relluem94.vulcan.main.Main.createEntities;
import static de.relluem94.vulcan.main.Main.createTerrain;
import static de.relluem94.vulcan.main.Main.defineText;
import static de.relluem94.vulcan.main.Main.doPhysics;
import static de.relluem94.vulcan.main.Main.init;
import static de.relluem94.vulcan.main.Main.initGUI;
import static de.relluem94.vulcan.main.Main.initMousePicker;
import static de.relluem94.vulcan.main.Main.initPhysics;
import static de.relluem94.vulcan.main.Main.loadModelsAndTextures;
import static de.relluem94.vulcan.main.Main.prepare;
import static de.relluem94.vulcan.main.Main.render2D;
import static de.relluem94.vulcan.main.Main.render3D;
import static de.relluem94.vulcan.main.Main.showDebugInfo;
import static de.relluem94.vulcan.main.Main.update;
import static de.relluem94.vulcan.main.Things.closeRequest;

import org.lwjgl.openal.AL;

import de.relluem94.vulcan.toolbox.Utils;

public class GameLoop {

	public static void main(String[] args) {
		try{
			// ************* First Steps *************//	
			init();
			defineText();
			// ************* Models and Textures *************//
			loadModelsAndTextures();
			createTerrain();
			// ************* GUIS *************//
			initGUI();
			// ************* Entities *************//
			createEntities();
			initPhysics();
			showDebugInfo();		
			// ************* OtherStuff *************//
			initMousePicker();
			// ************* Main Loop *************//
			while (!closeRequest) {	
				update();
				render3D();
				render2D();
				prepare();
				doPhysics();
			}
			// ************* Clean Up *************//
			cleanUp();
		}
		catch(Exception e){
			Utils.log("A Problem happend", 1);
			AL.destroy();
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}