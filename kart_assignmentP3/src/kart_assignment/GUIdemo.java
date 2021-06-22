//GUIdemoPart3
package kart_assignment;
import java.awt.GridLayout;

import javax.swing.*;

public class GUIdemo extends JFrame    
{
	
    static final String newline = System.getProperty("line.separator");   
    public static void main(String[] args) {
    	GUIdemo window = new GUIdemo(); // set up window
    }
    
    GUIdemo()
    {
    	
    	DataPanel animation = new DataPanel();
        JFrame window = new JFrame("Animator test"); // set up window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(animation); // add panel to frame
        window.pack(); // make window just large enough for its GUI
        window.setVisible(true); // display window
        window.addKeyListener(animation);
        window.setFocusable(true);
        animation.startAnimation(); // begin animation
           
    }
    
}