import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Superman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Superman extends Vehicle
{
    /**
     * Act - do whatever the Superman wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Superman(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 30)/2);
        speed = maxSpeed;
        yOffset = 0;
    }
    public void act()
    {
        drive(); 
        checkHitPedestrian();
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }
    public boolean checkHitPedestrian(){
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);
        if (p != null){
            if (!p.isAwake()){
                getWorld().removeObject(p);
                clearLane();
                return true;
            }
        }
        return false;
    }
    public boolean checkHitVehicle(){
        Vehicle h = (Vehicle)getOneIntersectingObject(Vehicle.class);
        if (h != null){
                clearLane();
                return true;
        }
        return false;
    }
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
  