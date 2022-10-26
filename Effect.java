import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * fade away effect 
 * Credit: Mr Cohen's VehicleSim2022v2.DEMO3
 * 
 * @author Austin Yip
 * @version 1
 */
public class Effect extends Actor
{
    protected GreenfootImage image;

    protected void fade (int timeLeft, int fadeTime) {
        double percent = timeLeft / (double)fadeTime;
        // Transparency 0 -- 255
        int newTransparency = (int)(percent * 255);
        image.setTransparency(newTransparency);
    }
}
