import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class sun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sun extends Actor
{
    private GreenfootImage image = new GreenfootImage("sun.png");
    private GreenfootImage redImage = new GreenfootImage("sun2.png");
    private GreenfootImage explodingImage = new GreenfootImage("sun3.png");
    private int actsLeft;
    /**
     * Act - do whatever the sun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Sun(){
        setImage(image);
        actsLeft = 180;
    }
    public void act()
    {
        actsLeft--;
        if (actsLeft == 120){
            setImage (redImage);
        } else if (actsLeft == 60){
            setImage (explodingImage);
        }
        else if (actsLeft == 0){
            int explosionSize = 40;
            getWorld().addObject(new Explosion(explosionSize), getX(), getY());
            getWorld().removeObject(this);
        }
    }
}
