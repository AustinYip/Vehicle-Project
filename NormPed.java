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
    
    protected double speed;
    protected double maxSpeed;
    protected int direction;
    public static boolean awake;
    public NormPed(int direction) {
        super(direction);
    }
    public void act()
    {
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
