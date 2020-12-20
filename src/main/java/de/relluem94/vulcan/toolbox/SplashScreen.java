package de.relluem94.vulcan.toolbox;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import de.relluem94.vulcan.main.Main;

public class SplashScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JProgressBar pbar;
	private static Image img;
	private static ImageIcon imgico;
	private static JLabel label;
	private static JLabel imglabel;
	
	public SplashScreen(){
		super(Variables.title);
		imgico = new ImageIcon("assets/textures/gui/relluengine.png");
		ImageIcon ico = new ImageIcon(Main.iconX32);
		setSize(425,155);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		setBackground(new Color(0, 255, 0, 0));
		setContentPane(new ContentPane());
		img = imgico.getImage();
		setIconImage(ico.getImage());
	    Image newimg = img.getScaledInstance(305, 305,  java.awt.Image.SCALE_SMOOTH);
	    getContentPane().setBackground(new Color(10, 10, 10, 0));
	    
		setLayout(null);
		pbar = new JProgressBar();
		pbar.setMinimum(0);
		pbar.setMaximum(100);
		pbar.setStringPainted(true);
		pbar.setForeground(Color.GRAY);
		pbar.setBackground(Color.LIGHT_GRAY);
//		pbar.setBorder();
		add(pbar);
		//pbar.setPreferredSize(new Dimension(410, 30));
		pbar.setBounds(15, 125, 393, 20);
		
		label = new JLabel();
		label.setText("Loading..");
		label.setForeground(Color.GRAY);
		add(label);
		label.setBounds(15, 105, 384, 20);
		
		imglabel = new JLabel(new ImageIcon(newimg));
		add(imglabel);
		imglabel.setBounds(10, 0, 404, 105);
	}
	
	public void increaseProgress(){
		 setProgress(getProgress() + 5);
	}
	
	public void setProgress(int i){
		if(i < 100){
			pbar.setValue(i);	
		}
		else{
			pbar.setValue(100);
			}
	}
	
	public int getProgress(){
		return pbar.getValue();		
	}
}
