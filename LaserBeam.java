import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserBeam here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserBeam extends Actor
{
    private int timer = 300;
    GreenfootImage[] laserEffect = new GreenfootImage[3];
    
    int imageIndex = 0;
    public void act()
    {
        
        setImage(laserEffect[imageIndex]);
        if(timer % 10 == 0)
            imageIndex = (imageIndex+1)%laserEffect.length; 
        timer--;
        if (timer ==0){
            getWorld().removeObject(this);
        }
        
    }
    
    public void LaserBeam(){
        for (int i = 0; i<laserEffect.length; i++)
        {
            laserEffect[i] = new GreenfootImage("images/laser/Laser" + i + ".png");
        }
    }
}
