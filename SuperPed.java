import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperPed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperPed extends Pedestrian
{
    //variables for timer and if supermode is true
    private int timer = 240;
    private boolean supermode;

    GreenfootImage[] walkingRight = new GreenfootImage[9];
    GreenfootImage[] walkingRightSuper = new GreenfootImage[9];
    int imageIndex = 0;
    int imageIndex2 = 0;

    private GreenfootImage image = new GreenfootImage("dead2.png");
    public SuperPed(int direction) {
        super(direction);
        // choose a random speed (slower than a normal pedestrian due to its bigger size and its power
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

    public void act() {
        timer--;

        //once the timer reaches 60, meaning 3 seconds has passed, supermode is activated
        if (timer == 60){
            supermode = true;
        }

        //once the timer reaches 0, meaning supermode's 1 second has passed, supermode is deactivated and timer is reset
        if (timer == 0){
            supermode = false;
            timer = 480;
        }

        //if supermode is on, the superpedestrian heals himself
        superact();

        //animates the super pedestrian depending on the mode
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

        //if the super pedestrian is dead, set image
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
