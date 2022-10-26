import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Superman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Superman extends Vehicle
{
    //laser sound when laser spawns
    private GreenfootSound laser = new GreenfootSound("laser.mp3");    

    public Superman(VehicleSpawner origin){
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/2);
        speed = maxSpeed;
        yOffset = 0;
    }

    public void act(){
        drive(); 
        checkHitPedestrian();
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }

    //checks if the pedestrian that it hits is knocked down. If knocked down, remove the body and clear the lane infront of vehicles. If not, nothing happens
    public boolean checkHitPedestrian(){
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        //checks for vehicle and pedestrian collision
        if (p != null){
            //checks if awake
            if (!p.isAwake()){
                getWorld().removeObject(p);
                clearLane();
                laser.play();
                return true;
            }
        }
        return false;
    }

    //clears the lane infront of superman
    public void clearLane(){
        for(int i = 0; i<800; i++){
            Vehicle v = (Vehicle) getOneObjectAtOffset(i*direction, 0, Vehicle.class);
            if (v != null){
                getWorld().removeObject(v);
            }
        }
        LaserBeam l = new LaserBeam();
        getWorld().addObject(l, getX() + direction*(getImage().getWidth()/2 + l.getImage().getWidth()/2), getY());
    }
}
 