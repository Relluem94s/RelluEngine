//package de.relluem94.vulcan.toolbox;
//
//import java.awt.Color;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;
//import java.io.PrintStream;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//import de.relluem94.vulcan.main.Main;
//
//
//public class Console extends JFrame implements Runnable{
//	
//	private static final long serialVersionUID = 1L;
//	//private BufferedReader in; 
//	private JTextArea textArea = new JTextArea();
//
//	
//	
//	private boolean quit;
//	private final PipedInputStream pin=new PipedInputStream();	
//	
//	public Console(){
//		super("Console");
//		ImageIcon ico = new ImageIcon(Main.iconX32);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		setIconImage(ico.getImage());
//		setVisible(true);
//		setResizable(false);
//		
//		JPanel contentPane = new JPanel(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		
//		textArea.setEditable(false);
//		textArea.setForeground(Color.WHITE);
//		textArea.setBackground(Color.BLACK);
//		textArea.setBorder(null);
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 0.5;
//		c.gridwidth = 4;
//		c.anchor = GridBagConstraints.PAGE_START;
//		c.ipady = 400;  
//		c.ipadx = 800;
//		c.gridx = 0;
//		c.gridy = 0;
//		contentPane.add(new JScrollPane(textArea), c);
//		
//		
//		JTextField input = new JTextField();
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 0.5;
//		c.weighty = 1;
//		c.gridwidth = 4;
//		c.anchor = GridBagConstraints.PAGE_END;
//		c.ipady = 10;  
//		c.ipadx = 800; 
//		c.gridx = 1;
//		c.gridy = 1;
//		input.setForeground(Color.WHITE);
//		input.setBackground(Color.BLACK);
//		input.setBorder(null);
//		contentPane.add(input, c);
//		
//		
//		input.addActionListener(new ActionListener(){
//		    public void actionPerformed(ActionEvent e){
//		    	String text = input.getText();
//		    	if(text.equals("")){
//		    		
//		    	}
//		    	else{
//		    		String[] args = text.split(" ");
//		    		input.setText("");
//		    		Utils.execute(args);
//		    	}
//		    }
//		});
//		
//		add(contentPane);
//
//		try
//		{
//			PipedOutputStream pout=new PipedOutputStream(pin);
//			System.setOut(new PrintStream(pout,true)); 
//		} 
//		catch (java.io.IOException io)
//		{
//			textArea.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
//		}
//		catch (SecurityException se)
//		{
//			textArea.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
//	    } 
//		
//		quit=false;
//				
//		
////		Main.console_thread=new Thread(this);
////		Main.console_thread.setDaemon(true);	
////		Main.console_thread.start();
//		
//		pack();		
//	}
//
//	public synchronized void run(){
//		try
//		{			
//			while (Thread.currentThread()== Main.console_thread)
//			{
//				try { this.wait(10);}catch(InterruptedException ie) {}
//				if (pin.available()!=0){
//					String input=this.readLine(pin);
//					textArea.append(input);
//					textArea.setCaretPosition(textArea.getDocument().getLength());
//				}
//				if (quit) return;
//			}	
//		} catch (Exception e)
//		{
//			textArea.append("\nConsole reports an Internal error.");
//			textArea.append("The error is: "+e);			
//		}
//	}
//	
//	public synchronized String readLine(PipedInputStream in) throws IOException{
//		String input="";
//		do{
//			int available=in.available();
//			if (available==0) break;
//			byte b[]=new byte[available];
//			in.read(b);
//			input=input+new String(b,0,b.length);	
//		}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
//		return input;
//	}
//	
//	public void clear(){
//		textArea.setText("");
//	}
//}
