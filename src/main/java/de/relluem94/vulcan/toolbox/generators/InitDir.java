package de.relluem94.vulcan.toolbox.generators;

import static de.relluem94.vulcan.toolbox.Variables.MusicDir;
import static de.relluem94.vulcan.toolbox.Variables.OptionsFile;
import static de.relluem94.vulcan.toolbox.Variables.SavesDir;
import static de.relluem94.vulcan.toolbox.Variables.ScreenshotDir;

import java.io.IOException;
import java.nio.charset.Charset;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.Variables;

public class InitDir {
	
	
	public static void checkDirs(){
		if(!SavesDir.exists()){
			SavesDir.mkdirs();
			Utils.log("Creating new Saves Directory", 0);
		}
		if(!MusicDir.exists()){
			MusicDir.mkdirs();
			Utils.log("Creating new Music Directory", 0);
		}
		if(!ScreenshotDir.exists()){
			ScreenshotDir.mkdirs();
			Utils.log("Creating new Screenshots Directory", 0);
		}
		if(!OptionsFile.exists()){
			createDefaults();
		}
		else{
			Utils.log("Copy Options from " + OptionsFile.getName(), 4);
		}
	}

	public static void createDefaults(){
		try {
			OptionsFile.createNewFile();
			Utils.log("Creating new " + OptionsFile.getName(), 0);
			
			Utils.writeFile(OptionsFile, Main.default_options);
		} catch (IOException e) {}
	}
	
	public static void loadOptions(){
		Main.options = Utils.readFile(Variables.OptionsFile.getPath(), Charset.defaultCharset());
		for(int i = 0; i < Main.options.size(); i++){
			String temp;
			if(Main.options.get(i).startsWith("version")){
				temp = Main.options.get(i).replace("version:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				

				String[] temparray = temp.split("[.]");
				String[] array = Variables.version.split("[.]");
				int v0 = Integer.parseInt(array[0]);
				int v1 = Integer.parseInt(array[1]);
				int v2 = Integer.parseInt(array[2]);
				
				int f0 = Integer.parseInt(temparray[0]);
				int f1 = Integer.parseInt(temparray[1]);
				int f2 = Integer.parseInt(temparray[2]);
				
				Utils.log("Options Version: " + temp + " & Game Version: " + Variables.version, 0);
				
				
				if(v0 == f0){
					if(v1 == f1){
						if(v2 == f2){
							
						}
						else{
							OptionsFile.delete();
							createDefaults();
						}
					}
					else{
						OptionsFile.delete();
						createDefaults();
					}
				}
				else{
					OptionsFile.delete();
					createDefaults();
				}
			}
			else if(Main.options.get(i).startsWith("SCREENSHOT_FORMAT")){
				temp = Main.options.get(i).replace("SCREENSHOT_FORMAT:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				temp = temp.toUpperCase();
				if(!(temp.equals("PNG")) || !(temp.equals("JPG")) || !(temp.equals("GIF"))){
					temp = "PNG";
				}
				Main.screenshot_format = temp;
				Utils.log("SCREENSHOT_FORMAT:" + temp, 4);
			}
			else if(Main.options.get(i).startsWith("WIDTH")){
				temp = Main.options.get(i).replace("WIDTH:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.WIDTH = Integer.parseInt(temp);
				Utils.log("WIDTH:" + temp, 4);
			}
			else if(Main.options.get(i).startsWith("HEIGHT")){
				temp = Main.options.get(i).replace("HEIGHT:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.HEIGHT = Integer.parseInt(temp);
				Utils.log("HEIGHT:" + temp, 4);
			}
			else if(Main.options.get(i).startsWith("FPS_CAP")){
				temp = Main.options.get(i).replace("FPS_CAP:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.FPS_CAP = Integer.parseInt(temp);
				Utils.log("FPS_CAP:" + temp, 4);
			}
			else if(Main.options.get(i).startsWith("particle")){
				temp = Main.options.get(i).replace("particle:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.particle = Boolean.valueOf(temp);
				Utils.log("PARTICLE:" + temp, 4);
			}
			else if(Main.options.get(i).startsWith("ANTIALASING_FILTERING")){
				temp = Main.options.get(i).replace("ANTIALASING_FILTERING:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Utils.log("ANTIALASING_FILTERING: " + temp, 4);
				Main.ANTIALASING_FILTERING = Integer.parseInt(temp);
				
			}
			else if(Main.options.get(i).startsWith("WORLDSIZE")){
				temp = Main.options.get(i).replace("WORLDSIZE:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.WORLDSIZE = Integer.parseInt(temp);
				Utils.log("WORLDSIZE: " + temp, 4);
			}
			else if(Main.options.get(i).startsWith("WORLDOFFSET")){
				temp = Main.options.get(i).replace("WORLDOFFSET:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.WORLDOFFSET = Integer.parseInt(temp);
				Utils.log("WORLDOFFSET: " + temp, 4);
			}
//			else if(Main.options.get(i).startsWith("COOLDOWN")){
//				temp = Main.options.get(i).replace("COOLDOWN:", "");
//				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
//				temp = temp.replaceAll(" ", "");
//				Main.COOLDOWN = Integer.parseInt(temp);
//				Utils.log("COOLDOWN: " + temp, 4);
//			}
			else if(Main.options.get(i).startsWith("SEELEVEL")){
				temp = Main.options.get(i).replace("SEELEVEL:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				temp = temp.replaceAll(" ", "");
				Main.SEELEVEL = Integer.parseInt(temp);
				Utils.log("SEELEVEL: " + temp, 4);
			}
			else if(Main.options.get(i).startsWith("mipmapping")){
				temp = Main.options.get(i).replace("mipmapping:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				Main.mipmapping = Boolean.valueOf(temp);
				Utils.log("MIPMAPPING: " + temp, 4);
			}
			else if(Main.options.get(i).startsWith("debug")){
				temp = Main.options.get(i).replace("debug:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				Main.debug = Boolean.valueOf(temp);
				Utils.log("DEBUG: " + temp, 4);
			}
			else if(Main.options.get(i).startsWith("vsync")){
				temp = Main.options.get(i).replace("vsync:", "");
				temp = temp.replaceAll("[\\\t|\\\n|\\\r]","");
				Main.vsync = Boolean.valueOf(temp);
				Utils.log("VSYNC: " + temp, 4);
			}
		}
	}
}
