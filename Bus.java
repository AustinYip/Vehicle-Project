import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bus extends Vehicle
{
    private int timer = 0;
    private GreenfootSound[] kidnap = new GreenfootSound[10];
    int soundIndex = 0;
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first

        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it a up (this may result in some collision check issues)
        yOffset = 15;
        for (int i = 0; i<kidnap.length; i++)
        {
            kidnap[i] = new GreenfootSound("sounds/kidnap.mp3");
        }
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        checkHitPedestrian();
        if (timer==0){
            drive();
        }
        else{
            timer--;
        }
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }

    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        
        //checks if vehicle and pedestrian collision
        if (p != null){
            //if awake, removes the civilian, stop the bus for 1 second, plays the kidnap sound and continues after the timer ends
            if (p.isAwake()){
                timer = 60;
                speed = 0;
                getWorld().removeObject(p);
                kidnap[soundIndex].setVolume(50);
                kidnap[soundIndex].play();
                soundIndex = (soundIndex+1)%kidnap.length; 

                return true;
            }
            return false;
        }
        return false;
    }
}
