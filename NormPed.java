import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NormPed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NormPed extends Pedestrian
{
    /**
     * Act - do whatever the NormPed wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage[] walkingRight = new GreenfootImage[10];
    
    public NormPed(int direction) {
        super(direction);
         // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
        
        
        for (int i = 0; i<walkingRight.length; i++)
        {
            walkingRight[i] = new GreenfootImage("images/walking/tile00" + i + ".png");
        }
    }
    
    
    int imageIndex = 0;
    public void act(){
        setImage(walkingRight[imageIndex]);
        imageIndex = (imageIndex+1)%walkingRight.length; 
        if (awake){
            if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
                setLocation (getX(), getY() + (int)(speed*direction));
            }
            if (direction == -1 && getY() < 100){
                getWorld().removeObject(this);
            } else if (direction == 1 && getY() > 550){
                getWorld().removeObject(this);
            }
        }
    }
}

