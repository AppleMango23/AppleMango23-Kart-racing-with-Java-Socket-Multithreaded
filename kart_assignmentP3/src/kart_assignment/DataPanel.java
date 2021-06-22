//Kart Part 3
package kart_assignment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

public class DataPanel extends JPanel implements KeyListener {
    private final static String IMAGE_NAME = "image1_"; // base image name
    private final static String IMAGE_NAME2 = "image2_"; // base image name
    protected ImageIcon images[]; // array of images
    protected ImageIcon images2[]; // array of images
    private final int TOTAL_IMAGES = 16; // number of images
    private int currentImage = 0; // current image index
    private int currentImage1 = 0; // current image index
    private final int ANIMATION_DELAY = 80; // millisecond delay
    private int width; // image width
    private int height; // image height
    private Timer animationTimer; // Timer drives animation
    int x = 374, y = 500, x1 = 374, y1 = 550;
    int speedf1 = 0, speedf2 = 0, direction1 = 4, direction2 = 4;
    boolean crashStatus = false;
    private JLabel label;
    JButton button;
    JLabel test1, test2;
    JLabel test11, test22;
    int runningStatus = 0;
    int runningStatus1 = 0;
    
    //Testing with thread
    private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 5000;
	String asciiToSend="aa";
	int connectionCounter=0;
	String command;
	int counterThread=0;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String serverResponse = null;
	String line=null;
	String instruction=null;

	
	int player = 0;

    // constructor initializes LogoAnimatorJPanel by loading images
    public DataPanel() {
        //JLabel
		JOptionPane.showMessageDialog(null, "Instruction\n"
				+ "Press SPACE to login and start the game. User able to drive the car with W,A,S,D. \n"
				+ "Users should not be turning back to the initial line. User should touch the end line. \n"
				+ "Users should not colliding the wall and opponent. \n"
				+ "\nW will be increasing the speed \nS will be reducing the speed "
				+ "\nA will be turning left \nD will be turning right."
				+ "\nSPACE will be Starting game \nR will be reset game.");

		test1 = new JLabel("Player 1 Speed:");
        test1.setBounds(50, 100, 200, 30);
        test2 = new JLabel("0");
        test2.setBounds(50, 100, 200, 30);
        this.add(test1);
        this.add(test2);
        
		test11 = new JLabel("               Player 2 Speed:");
        test11.setBounds(50, 100, 100, 60);
        test22 = new JLabel("0");
        test22.setBounds(50, 0, 100, 0);
        this.add(test11);
        this.add(test22);

        images = new ImageIcon[TOTAL_IMAGES];
        images2 = new ImageIcon[TOTAL_IMAGES];

        // load 30 images
        for (int count = 0; count < images.length; count++) {
            images[count] = new ImageIcon(getClass().getResource(
                "/kart11/" + IMAGE_NAME + count + ".png"));
            images2[count] = new ImageIcon(getClass().getResource(
                "/kart22/" + IMAGE_NAME2 + count + ".png"));
        }

        //The window width and height
        width = 870;
        height = 680;

        System.out.println("Program Running");

        //Change to when u press start
        try {
            carSound();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        label = new JLabel("   Welcome to NoahKaKart", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setOpaque(true);
        add(label);
        
		

        
    } // end LogoAnimatorJPanel constructor

    //display current image
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // call superclass paintComponent
        racingTrack(g);
        images[currentImage].paintIcon(this, g, x, y);
        images2[currentImage1].paintIcon(this, g, x1, y1);
        
        //Rectangles for collision
        Rectangle car1 = new Rectangle(x + 8, y + 10, 41, 27);
        Rectangle car2 = new Rectangle(x1 + 3, y1 + 12, 45, 30);
        Rectangle grass = new Rectangle(150, 200, 545, 306);
        Rectangle upperBound = new Rectangle(50, 60, 750, 40);
        Rectangle lowBound = new Rectangle(50, 594, 750, 40);
        Rectangle leftBound = new Rectangle(10, 100, 40, 503);
        Rectangle rightBound = new Rectangle(806, 100, 40, 503);
        Rectangle winLine = new Rectangle(425, 500, 2, 100);
        Rectangle backLineCheck = new Rectangle(370, 500, 2, 100);
        Rectangle LapChecker = new Rectangle(300, 500, 2, 100); 

        //set next image to be drawn only if Timer is running
        if (animationTimer.isRunning()) {
            if (car1.intersects(grass) || car1.intersects(upperBound) || car1.intersects(lowBound) || car1.intersects(leftBound) || car1.intersects(rightBound)) {
                System.out.println("Player1 GAME OVER!");
                crashStatus = true;
                if (crashStatus == true) {
                    try {
                        carCrash();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                label.setForeground(Color.RED);
                label.setText("   GAME OVER PLAYER1!");
                stopAnimation();
            } else {
                if (car2.intersects(grass) || car2.intersects(upperBound) || car2.intersects(lowBound) || car2.intersects(leftBound) || car2.intersects(rightBound)) {
                    System.out.println("Player2 GAME OVER!");
                    crashStatus = true;
                    if (crashStatus == true) {
                        try {
                            carCrash();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    label.setForeground(Color.RED);
                    label.setText("   GAME OVER PLAYER2!");
                    stopAnimation();
                } else {
                    if (car1.intersects(car2)) {
                        crashStatus = true;
                        if (crashStatus == true) {
                            try {
                                carCrash();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        label.setForeground(Color.RED);
                        label.setText("   YOU CRASH YOUR FRIEND!");
                        stopAnimation();
                    }
                    
                    //LapChecker for player1
                    if (car1.intersects(LapChecker)) {
                    	
                    	runningStatus = 6;
                    	
                    } 
                    else {
                    	if(car1.intersects(winLine) && runningStatus == 6)
                    	{
                    		runningStatus = 2;
                    	}
                    	else {
                    		if(car1.intersects(backLineCheck) && runningStatus == 0)
                    		{
                    			runningStatus = 1;
                    		}
                    	}
                    }
                    
                    if (runningStatus == 2 ) {
                        label.setText("   YOU WON PLAYER1!");
                        stopAnimation();
                    } 
                    else {
                    	if(runningStatus == 1)
                    	{
                            label.setForeground(Color.RED);
                            label.setText("   GO INFRONT!");
                            stopAnimation();
                    	}
                    }
                    
                  //LapChecker for player1
                    if (car1.intersects(LapChecker)) {
                    	
                    	runningStatus = 6;
                    	
                    } 
                    else {
                    	if(car1.intersects(winLine) && runningStatus == 6)
                    	{
                    		runningStatus = 2;
                    	}
                    	else {
                    		if(car1.intersects(backLineCheck) && runningStatus == 0)
                    		{
                    			runningStatus = 1;
                    		}
                    	}
                    }
                    
                    if (runningStatus == 2 ) {
                        label.setText("   YOU WON PLAYER1!");
                        stopAnimation();
                    } 
                    else {
                    	if(runningStatus == 1)
                    	{
                            label.setForeground(Color.RED);
                            label.setText("   GO INFRONT PLAYER1!");
                            stopAnimation();
                    	}
                    }
                    
                  //LapChecker for player2
                    if (car2.intersects(LapChecker)) {
                    	
                    	runningStatus1 = 6;
                    	
                    } 
                    else {
                    	if(car2.intersects(winLine) && runningStatus1 == 6)
                    	{
                    		runningStatus1 = 2;
                    	}
                    	else {
                    		if(car2.intersects(backLineCheck) && runningStatus1 == 0)
                    		{
                    			runningStatus1 = 1;
                    		}
                    	}
                    }
                    
                    if (runningStatus1 == 2 ) {
                        label.setText("   YOU WON PLAYER2!");
                        stopAnimation();
                    } 
                    else {
                    	if(runningStatus1 == 1)
                    	{
                            label.setForeground(Color.RED);
                            label.setText("   GO INFRONT PLAYER2!");
                            stopAnimation();
                    	}
                    }	
                }
                car1Running();
                car2Running();
            }
            

            //Socket and java
			try {
				
	    		if(counterThread == 0)
	    		{
	    			socket = new Socket(SERVER_IP, SERVER_PORT);
		    		out = new PrintWriter(socket.getOutputStream(), true);
		    		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			counterThread++;
	    		}

	    		//Reading the data from server
	    		line = in.readLine();
    			
	    		if(line.equals("Player 1"))
	    		{
	    			player=1;
	    		} else if(line.equals("Player 2"))
	    		{
	    			player=2;
	    		} else if(line.equals("P2speedUp") && player == 1)
	    		{
	    			speedf2+=1;
	    		} else if(line.equals("P2speedDown") && player == 1)
	    		{
	    			speedf2-=1;
	    		} else if(line.equals("P2LeftTurn") && player == 1)
	    		{
                    direction2 -= 1;
	    		} else if(line.equals("P2RightTurn") && player == 1)
	    		{
	    			direction2 += 1;
	    		} else if(line.equals("P1speedUp") && player == 2)
	    		{
	    			speedf1+=1;
	    		} else if(line.equals("P1speedDown") && player == 2)
	    		{
	    			speedf1-=1;
	    		} else if(line.equals("P1LeftTurn") && player == 2)
	    		{
                    direction1 -= 1;
	    		} else if(line.equals("P1RightTurn") && player == 2)
	    		{
	    			direction1 += 1;
	    		}
	    		
	    		else {
	    			if(line.equals("null"))
	    	        {

	    	        }
	    		}
	    		
	    		//Getting latest data
    			command = asciiToSend;
    			asciiToSend = "";
    			
    			//Send to server
    			out.println(command);
    			
    			
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    } // end method paintComponent

    // start animation, or restart if window is redisplayed
    public void startAnimation() {
        if (animationTimer == null) {
            currentImage = 3; // display first image
            currentImage1 = 3; // display first image
            //create timer
            animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());
            animationTimer.start(); // start Timer
        } // end if
        else // animationTimer already exists, restart animation
        {
            if (!animationTimer.isRunning())
                animationTimer.restart();
        } // end else
    } // end method startAnimation

    // stop animation Timer
    public void stopAnimation() {
        animationTimer.stop();
    } // end method stopAnimation  

    //return minimum size of animation
    public Dimension getMinimumSize() {
        return getPreferredSize();
    } // end method getMinimumSize

    //return preferred size of animation
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    } // end method getPreferredSize

    //inner class to handle action events from Timer
    private class TimerHandler implements ActionListener {
        // respond to Timer's event
        public void actionPerformed(ActionEvent actionEvent) {
            repaint(); // repaint animator
            Object src = actionEvent.getSource();

        } // end method actionPerformed
    } // end class TimerHandler

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {}

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        try {
			displayInfo(e, "KEY PRESSED: ");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {}

    private void displayInfo(KeyEvent e, String keyStatus) throws UnknownHostException, IOException {
        int keyCode = 0;
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode +
                " (" +
                KeyEvent.getKeyText(keyCode) +
                ")";
        }
        
        //Pressing R
        if(keyCode == 82)
        {
        	//Reset the game
        	x = 374;
        	y = 500;
        	x1 = 374;
        	y1 = 550;
        	speedf1=0;
        	speedf2=0;
        	direction1=4;
        	direction2=4;
        	currentImage=3;
        	currentImage1=3;
        	label.setForeground(Color.BLACK);
            label.setText("   Welcome to NoahKaKart");
            runningStatus=0;
            crashStatus=false;
        	animationTimer.restart();
        }
        
        //Space to start
        if (keyCode == 32) {
			JOptionPane.showMessageDialog(null, "Log in Success. \nYou are Player: " + player);
        }
        
        //Player1
        if(player == 1)
        {
        	if (keyCode == 87) {
                //W
                speedf1 += 1;
                asciiToSend= "say P1speedUp";

            } else {
                if (keyCode == 83) {
                    //S
                    speedf1 -= 1;
                    asciiToSend= "say P1speedDown";

                } else {
                    if (keyCode == 65) {
                        //A
                        direction1 -= 1;
                        asciiToSend= "say P1LeftTurn";


                    } else {
                        if (keyCode == 68) {
                            //D
                            direction1 += 1;
                            asciiToSend= "say P1RightTurn";

                        } else {}
                    }
                }
            }
        }
        else {
        	if(player == 2)
            {
            	if (keyCode == 87) {
                    //W
                    speedf2 += 1;
                    asciiToSend= "say P2speedUp";

                } else {
                    if (keyCode == 83) {
                        //S
                        speedf2 -= 1;
                        asciiToSend= "say P2speedDown";

                    } else {
                        if (keyCode == 65) {
                            //A
                            direction2 -= 1;
                            asciiToSend= "say P2LeftTurn";


                        } else {
                            if (keyCode == 68) {
                                //D
                                direction2 += 1;
                                asciiToSend= "say P2RightTurn";

                            } else {}
                        }
                    }
                }
            }
        }
    }

    public void car1Running() {
        int speedStatus = speedf1 * 10;
        Integer speedStatusLast = new Integer(speedStatus);
        String testString = speedStatusLast.toString();
        test2.setText(testString);

        if (speedf1 != 0) {
            //direction1
            if (direction1 == -1) {
                direction1 = 15;
            }

            if (direction1 == 0) {
                y = y - 2 * speedf1;
                currentImage = 15;
            } else
            if (direction1 == 1) {
                x = x + 1 * speedf1;
                y = y - 2 * speedf1;
                currentImage = 0;
            } else
            if (direction1 == 2) {
                x = x + 2 * speedf1;
                y = y - 2 * speedf1;
                currentImage = 1;
            } else
            if (direction1 == 3) {
                //Going Left
                x = x + 2 * speedf1;
                y = y - 1 * speedf1;
                currentImage = 2;
            } else
            if (direction1 == 4) {
                x = x + 2 * speedf1;
                currentImage = 3;
            } else {
                if (direction1 == 5) {
                    x = x + 2 * speedf1;
                    y = y + 1 * speedf1;
                    currentImage = 4;
                } else {
                    if (direction1 == 6) {
                        x = x + 2 * speedf1;
                        y = y + 2 * speedf1;
                        currentImage = 5;
                    } else {
                        if (direction1 == 7) {
                            x = x + 1 * speedf1;
                            y = y + 2 * speedf1;
                            currentImage = 6;
                        } else {
                            if (direction1 == 8) {
                                y = y + 2 * speedf1;
                                currentImage = 7;
                            } else {
                                if (direction1 == 9) {
                                    x = x - 1 * speedf1;
                                    y = y + 2 * speedf1;
                                    currentImage = 8;
                                } else {
                                    if (direction1 == 10) {
                                        x = x - 2 * speedf1;
                                        y = y + 2 * speedf1;
                                        currentImage = 9;
                                    } else {
                                        if (direction1 == 11) {
                                            x = x - 2 * speedf1;
                                            y = y + 1 * speedf1;
                                            currentImage = 10;
                                        } else {
                                            if (direction1 == 12) {
                                                x = x - 2 * speedf1;
                                                currentImage = 11;
                                            } else {
                                                if (direction1 == 13) {
                                                    x = x - 2 * speedf1;
                                                    y = y - 1 * speedf1;
                                                    currentImage = 12;
                                                } else {
                                                    if (direction1 == 14) {
                                                        x = x - 2 * speedf1;
                                                        y = y - 2 * speedf1;
                                                        currentImage = 13;
                                                    } else {
                                                        if (direction1 == 15) {
                                                            x = x - 1 * speedf1;
                                                            y = y - 2 * speedf1;
                                                            currentImage = 14;
                                                        } else {
                                                            direction1 = 0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {

        }

    }

    public void car2Running() {
        int speedStatus = speedf2 * 10;
        Integer speedStatusLast = new Integer(speedStatus);
        String testString = speedStatusLast.toString();
        test22.setText(testString);

        if (x1 >= 1500) {

        } else {
            if (speedf2 != 0) {

                //direction2
                if (direction2 == -1) {
                    direction2 = 15;
                }

                if (direction2 == 0) {
                    y1 = y1 - 2 * speedf2;
                    currentImage1 = 15;
                } else
                if (direction2 == 1) {
                    x1 = x1 + 1 * speedf2;
                    y1 = y1 - 2 * speedf2;
                    currentImage1 = 0;
                } else
                if (direction2 == 2) {
                    x1 = x1 + 2 * speedf2;
                    y1 = y1 - 2 * speedf2;
                    currentImage1 = 1;
                } else
                if (direction2 == 3) {
                    //Going Left
                    x1 = x1 + 2 * speedf2;
                    y1 = y1 - 1 * speedf2;
                    currentImage1 = 2;
                } else
                if (direction2 == 4) {
                    x1 = x1 + 2 * speedf2;
                    currentImage1 = 3;
                } else {
                    if (direction2 == 5) {
                        x1 = x1 + 2 * speedf2;
                        y1 = y1 + 1 * speedf2;
                        currentImage1 = 4;
                    } else {
                        if (direction2 == 6) {
                            x1 = x1 + 2 * speedf2;
                            y1 = y1 + 2 * speedf2;
                            currentImage1 = 5;
                        } else {
                            if (direction2 == 7) {
                                x1 = x1 + 1 * speedf2;
                                y1 = y1 + 2 * speedf2;
                                currentImage1 = 6;
                            } else {
                                if (direction2 == 8) {
                                    y1 = y1 + 2 * speedf2;
                                    currentImage1 = 7;
                                } else {
                                    if (direction2 == 9) {
                                        x1 = x1 - 1 * speedf2;
                                        y1 = y1 + 2 * speedf2;
                                        currentImage1 = 8;
                                    } else {
                                        if (direction2 == 10) {
                                            x1 = x1 - 2 * speedf2;
                                            y1 = y1 + 2 * speedf2;
                                            currentImage1 = 9;
                                        } else {
                                            if (direction2 == 11) {
                                                x1 = x1 - 2 * speedf2;
                                                y1 = y1 + 1 * speedf2;
                                                currentImage1 = 10;
                                            } else {
                                                if (direction2 == 12) {
                                                    x1 = x1 - 2 * speedf2;
                                                    currentImage1 = 11;
                                                } else {
                                                    if (direction2 == 13) {
                                                        x1 = x1 - 2 * speedf2;
                                                        y1 = y1 - 1 * speedf2;
                                                        currentImage1 = 12;
                                                    } else {
                                                        if (direction2 == 14) {
                                                            x1 = x1 - 2 * speedf2;
                                                            y1 = y1 - 2 * speedf2;
                                                            currentImage1 = 13;
                                                        } else {
                                                            if (direction2 == 15) {
                                                                x1 = x1 - 1 * speedf2;
                                                                y1 = y1 - 2 * speedf2;
                                                                currentImage1 = 14;
                                                            } else {
                                                                direction2 = 0;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void racingTrack(Graphics g) {
        //The racing track
        Color c1 = Color.green;
        g.setColor(c1);
        g.fillRect(150, 200, 550, 300); //grass
        Color c2 = Color.black;
        g.setColor(c2);
        g.drawRect(50, 100, 750, 500); //outer edge
        g.drawRect(50, 100, 750, 500); //inner edge
        Color c3 = Color.yellow;
        g.setColor(c3);
        g.drawRect(100, 150, 650, 400); //mid-lane marker
        Color c4 = Color.white;
        g.setColor(c4);
        g.drawLine(425, 500, 425, 600); //start line
    }

    public void carSound() throws UnsupportedAudioFileException, Exception {
        File file = new File("C:\\Users\\Noah\\eclipse-workspace\\kart_assignmentP2\\src\\engine\\engine111.wav");
        AudioInputStream audioStream;
        audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void carCrash() throws UnsupportedAudioFileException, Exception {
        File file1 = new File("C:\\Users\\Noah\\eclipse-workspace\\kart_assignmentP2\\src\\crash\\crash.wav");
        AudioInputStream audioStream;
        audioStream = AudioSystem.getAudioInputStream(file1);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

} // end class LogoAnimatorJPanel