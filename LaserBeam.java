import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserBeam here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserBeam extends Actor
{
    private int timer = 15;
    private GreenfootImage image = new GreenfootImage("laser/Laser3.png");

    public void act() {
        //sets the image for the superman to call and summon (laser beam eyes)
        setImage(image);
        
        //timer goes down, once it hits zero remove the laser beam
        timer--;
        if (timer ==0){
            getWorld().removeObject(this);
        }
    }
}
