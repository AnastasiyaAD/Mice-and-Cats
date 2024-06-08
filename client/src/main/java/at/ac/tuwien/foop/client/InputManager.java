package at.ac.tuwien.foop.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputManager implements KeyListener  
{
    private final int LEFT = 37;
    private  final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40; 
    
    private Mouse mouse;
    /** Creates a new instance of InputManager */
    public InputManager(Mouse mouse) 
    {
        this.mouse=mouse;
        
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) 
    {

        if(e.getKeyCode()==LEFT)
        {
            if(mouse.getDirection()==1|mouse.getDirection()==3)
            {
                
                mouse.moveLeft();
                
                // client.sendToServer(new Protocol().UpdatePacket(mouse.getXposition(),
                //           mouse.getYposition(),mouse.getMouseID(),mouse.getDirection())
                
 
            }
            else if(mouse.getDirection()==4)
            {
                mouse.moveLeft();          
            }
        }
        else if(e.getKeyCode()==RIGHT)
        {
            if(mouse.getDirection()==1|mouse.getDirection()==3)
            {
                mouse.moveRight();                        
                    
            }
            else if(mouse.getDirection()==2)
            {
                mouse.moveRight();
            }
        }
        else if(e.getKeyCode()==UP)
        {
            if(mouse.getDirection()==2|mouse.getDirection()==4)
            {
                mouse.moveForward();                            
                        
            }
            else if(mouse.getDirection()==1)
            {
                mouse.moveForward();
                            
            }
        }
        else if(e.getKeyCode()==DOWN)
        {
            if(mouse.getDirection()==2|mouse.getDirection()==4)
            {
                mouse.moveBackward();
               
                            
            }
            else if(mouse.getDirection()==3)
            {
                mouse.moveBackward();
                                    
                                
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
    
}
