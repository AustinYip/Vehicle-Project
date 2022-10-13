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
         // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        // start as awake 
        awake = true;
    }

}

