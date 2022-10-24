import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperPed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperPed extends Pedestrian
{
    /**
     * Act - do whatever the SuperPed wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timer = 240;
    private boolean supermode;
    
    
    
    GreenfootImage[] walkingRight = new GreenfootImage[9];
    GreenfootImage[] walkingRightSuper = new GreenfootImage[9];
    private GreenfootImage image = new GreenfootImage("dead2.png");
    public SuperPed(int direction) {
        super(direction);
         // choose a random speed
        maxSpeed = Math.random() + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
        supermode = false;
        for (int i = 0; i<walkingRight.length; i++)
        {
            walkingRight[i] = new GreenfootImage("images/superwalking/tile00" + i + ".png");
        }
        for (int i = 0; i<walkingRightSuper.length; i++)
        {
            walkingRightSuper[i] = new GreenfootImage("images/superwalking2/tile00" + i + ".png");
        }
    }
    
    int imageIndex = 0;
    int imageIndex2 = 0;
    public void act()
    {
        timer--;
        
        if (timer == 60){
            supermode = true;
        }
        if (timer == 0){
            supermode = false;
            timer = 480;
        }
        superact();
        if (supermode == false){
            setImage(walkingRight[imageIndex]);
            imageIndex = (imageIndex+1)%walkingRight.length; 
        }
        else{
            setImage(walkingRightSuper[imageIndex2]);
            imageIndex2 = (imageIndex2+1)%walkingRightSuper.length; 
        }
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
        else{
            setImage(image);
        }
    }
    
    private void superact(){
        if (supermode){
            if(!awake){
                this.healMe();
            }
        }
    }
}
