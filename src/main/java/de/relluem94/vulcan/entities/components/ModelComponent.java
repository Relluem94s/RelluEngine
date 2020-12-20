package de.relluem94.vulcan.entities.components;

import java.util.ArrayList;
import java.util.List;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.TexturedModel;
import de.relluem94.vulcan.renderEngine.MasterRenderer;
import de.relluem94.vulcan.toolbox.generators.Store;

public class ModelComponent {
	

	
	
	public static void init(int id){
		Main.stores.get(id).set(4, new Store(0));
		Main.stores.get(id).set(5, new Store(true));
	}
	
	public static TexturedModel getModel(int id) {
		return (TexturedModel) Main.stores.get(id).get(3).getValue();
	}
	
	public static void setRender(int id, boolean shouldrender) {
		Main.stores.get(id).set(36, new Store(shouldrender));
	}
	
	public static boolean getRender(int id) {
		return (boolean) Main.stores.get(id).get(36).getValue();
	}
	
	public static int getTextureIndex(int id) {
		return (int) Main.stores.get(id).get(4).getValue();
	}

	public static void setModel(int id, TexturedModel model) {
		Main.stores.get(id).set(3, new Store(model));
	}

	public static float getTextureXOffset(int id){
		int column = getTextureIndex(id)%MasterRenderer.getTexture(getModel(id).getTextureID()).getNumberOfRows();
		return (float)column/(float)MasterRenderer.getTexture(getModel(id).getTextureID()).getNumberOfRows();
	}
	
	public static float getTextureYOffset(int id){
		int row = getTextureIndex(id)/MasterRenderer.getTexture(getModel(id).getTextureID()).getNumberOfRows();
		return (float)row/(float)MasterRenderer.getTexture(getModel(id).getTextureID()).getNumberOfRows();
	}

	public static void set(int id, TexturedModel model, float shineDamper, float reflectivity) {
		Main.stores.get(id).set(3, new Store(model));
		if(Main.texmodels.containsKey(model)){

			Main.texmodels.get(model).add(id);
		}
		else{
			List<Integer> intlist = new ArrayList<>();
			intlist.add(id);
			Main.texmodels.put(model, intlist);
		}
		MasterRenderer.getTexture(model.getTextureID()).setReflectivity(reflectivity);
		MasterRenderer.getTexture(model.getTextureID()).setShineDamper(shineDamper);
	}
	
	public static void set(int id, TexturedModel model) {
		Main.stores.get(id).set(3, new Store(model));
	}
	
	public static void set(int id, TexturedModel model, int index) {
		Main.stores.get(id).set(3, new Store(model));
		Main.stores.get(id).set(4, new Store(index));
	}
	
	public static void set(int id, TexturedModel model, int index, float shineDamper, float reflectivity) {
		Main.stores.get(id).set(3, new Store(model));
		Main.stores.get(id).set(4, new Store(index));
		MasterRenderer.getTexture(model.getTextureID()).setReflectivity(reflectivity);
		MasterRenderer.getTexture(model.getTextureID()).setShineDamper(shineDamper);
	}

	
	/**
	 * default = true
	 * <br>
	 * 
	 * @return if this Model should have an Shadow or not
	 */
	public static boolean hasShadow(int id) {
		return (boolean) Main.stores.get(id).get(5).getValue();
	}

	public static void setShadow(int id, boolean hasShadow) {
		Main.stores.get(id).set(5, new Store(hasShadow));
	}
}
