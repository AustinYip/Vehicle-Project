import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the superclass for Vehicles.
 * 
 */
public abstract class Vehicle extends SuperSmoothMover
{
    protected double maxSpeed;
    protected double speed;
    protected int direction; // 1 = right, -1 = left
    protected boolean moving;
    protected int yOffset;
    protected VehicleSpawner origin;

    private GreenfootSound honk = new GreenfootSound("honk.mp3");

    protected boolean slowed;
    protected abstract boolean checkHitPedestrian();

    public Vehicle (VehicleSpawner origin) {
        this.origin = origin;
        moving = true;
        slowed = false;
        if (origin.facesRightward()){
            direction = 1;
        } else {
            direction = -1;
            getImage().mirrorHorizontally();
        }
    }

    public void addedToWorld (World w){
        setLocation (origin.getX() - (direction * 100), origin.getY() - yOffset);
    }

    /**
     * A method used by all Vehicles to check if they are at the edge
     */
    protected boolean checkEdge() {
        if (direction == 1){
            if (getX() > getWorld().getWidth() + 200){
                return true;
            }
        } else {
            if (getX() < -200){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that deals with movement. Speed can be set by individual subclasses in their constructors
     */
    public void drive() {
        // Ahead is a generic vehicle - we don't know what type BUT
        // since every Vehicle "promises" to have a getSpeed() method,
        // we can call that on any vehicle to find out it's speed
        Vehicle ahead = (Vehicle) getOneObjectAtOffset (direction * (int)(speed + getImage().getWidth()/2 + 4), 0, Vehicle.class);

        if (ahead!=null){
            VehicleSpawner laneAbove;
            VehicleSpawner laneBelow;
            VehicleWorld vw = (VehicleWorld)getWorld();
            honk.play();
            //if the vehicle is facing right/bottom half
            if (direction == 1){
                //create and spawn vehicle spawners above and below vehicle
                laneAbove = new VehicleSpawner(true, 48);
                laneBelow = new VehicleSpawner(true, 48);
                vw.addObject(laneAbove, this.getX(), this.getY()-54);
                vw.addObject(laneBelow, this.getX(), this.getY()+54);

                if(!(laneAbove.isTouchingVehicle())){   //if left lane is clear (merging into right lanes)
                    if(!(laneAbove.getY() <=375)){  //if the vehicle spawner is not below the bottom lane/offroad
                        setLocation(this.getX(),this.getY() -54);
                    }
                    else if (!(laneBelow.isTouchingVehicle())){ //if the right lane is clear
                        if(!(laneBelow.getY() >= 540)){ //if the vehiclespawner is not below the bottom lane/offroad
                            setLocation(this.getX(), this.getY() +54);
                        }
                    }
                    else{ //if nothing then just keep moving at the speed of the vehicle infront
                        speed = ahead.getSpeed();
                    }
                }
                else if(!(laneBelow.isTouchingVehicle())){ // if the right lane is clear
                    if(!(laneBelow.getY() >=540)){ // if the vehicle spawner is not below the bottom lane/offroad
                        setLocation(this.getX(), this.getY() +54);
                    }
                    else{
                        speed = ahead.getSpeed();
                    }
                } //if no lane is clear
                else{
                    speed = ahead.getSpeed();
                }
                vw.removeObject(laneAbove);
                vw.removeObject(laneBelow);
            }

            //if vehicle is facing left
            if (direction == -1){
                //create and spawn vehicle spawners above and below vehicle
                laneAbove = new VehicleSpawner(false, 48);
                laneBelow = new VehicleSpawner(false, 48);
                vw.addObject(laneAbove, this.getX(), this.getY()-54);
                vw.addObject(laneBelow, this.getX(), this.getY()+54);

                if(!(laneAbove.isTouchingVehicle())){   //if right lane is clear (merging into right lanes)
                    if(!(laneAbove.getY() <=225)){  //if the vehicle spawner is not offroad
                        setLocation(this.getX(),this.getY() -54);
                    }
                    else if (!(laneBelow.isTouchingVehicle())){ //if the right lane is clear
                        if(!(laneBelow.getY() >= 375)){ //if the vehiclespawner is not below the bottom lane/offroad
                            setLocation(this.getX(), this.getY() +54);
                        }
                    }
                    else{ //if nothing then just keep moving at the speed of the vehicle infront
                        speed = ahead.getSpeed();
                    }
                }
                else{ //if no lane is clear
                    speed = ahead.getSpeed();
                }
                vw.removeObject(laneAbove);
                vw.removeObject(laneBelow);
            }
        }
        
        //back to max speed
        else{
            speed = maxSpeed;
        }
        
        //makes the car goes
        move (speed * direction);

    }   

    /**
     * An accessor that can be used to get this Vehicle's speed. Used, for example, when a vehicle wants to see
     * if a faster vehicle is ahead in the lane.
     */
    public double getSpeed(){
        return speed;
    }
}
