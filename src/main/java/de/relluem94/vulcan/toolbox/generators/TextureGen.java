package de.relluem94.vulcan.toolbox.generators;

import java.awt.image.BufferedImage;
import java.util.Random;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.maths.Color3i;

public class TextureGen {
	
	public static final int ChessColorTexture = 0;
	public static final int StripesColorTexture = 1;
	public static final int NoiseColorTexture = 2;
	public static final int SolidColorTexture = 3;
	private static Random random = new Random();
	
	public BufferedImage generateChessImage(Color3i color1, Color3i	 color2, int resolution){
		BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
	
		int res = resolution/2 -1;
		
		for(int x = 0; x < resolution; x++){
        	for(int y = 0; y < resolution; y++){
        		if(x < res){
        			if(y < res){
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color1.r << 16) | (color1.g << 8) | color1.b);
        			}
        			else{
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color2.r << 16) | (color2.g << 8) | color2.b);
        			}
        		}
        		else{
        			if(y > res){
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color1.r << 16) | (color1.g << 8) | color1.b);
        			}
        			else{
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color2.r << 16) | (color2.g << 8) | color2.b);
        			}
        		}
                
        	}
        }
		return image;
	}
	
	public BufferedImage generateNoiseImage(Color3i color, int resolution){
		BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
		
		for(int x = 0; x < resolution; x++){
        	for(int y = 0; y < resolution; y++){
        		int rot = random.nextInt(Main.SEED) * (color.r - 0 * 1) + 0;
        		int gruen = random.nextInt(Main.SEED) * (color.g - 0 * 1) + 0;
        		int blau = random.nextInt(Main.SEED) * (color.b - 0 * 1) + 0;
        		image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (rot << 16) | (gruen << 8) | blau);
        	}
        }
		return image;
	}
	
	public BufferedImage generateStripesImage(Color3i color1, Color3i color2, int resolution){
		BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
	
		int res = resolution/2;
		
		for(int x = 0; x < resolution; x++){
        	for(int y = 0; y < resolution; y++){
        		if(x < res){
        			if(y < res){
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color2.r << 16) | (color2.g << 8) | color2.b);
        			}
        			else{
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color2.r << 16) | (color2.g << 8) | color2.b);
        			}
        		}
        		else{
        			if(y < res){
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color1.r << 16) | (color1.g << 8) | color1.b);
        			}
        			else{
        				image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color1.r << 16) | (color1.g << 8) | color1.b);
        				
        			}
        		}
                
        	}
        }
		return image;
	}
	
	public BufferedImage generateSolidImage(Color3i color, int resolution){
		BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
		 
        for(int x = 0; x < resolution; x++){
        	for(int y = 0; y < resolution; y++){        		image.setRGB(x, resolution - (y + 1), (0xFF << 24) | (color.r << 16) | (color.g << 8) | color.b);
        	}
        }
        return image;
	}
}
