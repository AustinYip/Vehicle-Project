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
    protected double speed;
    protected double maxSpeed;
    protected int direction;
    public static boolean awake;
    private int timer = 0;
    public SuperPed(int direction) {
        super(direction);
         // choose a random speed
        maxSpeed = Math.random() * 3 + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
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
