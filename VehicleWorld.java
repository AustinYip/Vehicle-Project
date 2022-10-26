import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * 
 * The theme of this vehicle simulation is outer space highway.
 * Meant to simulate how a fantasy space world might interact with each other with effects (such as super nova and superman rescuing people)
 * 
 * Actors and their functions:
 * Car:
 * Standard car,knocks down pedestrian when coming into contact
 * 
 * Bus (aka the blue ufo):
 * Moves slower than normal car, when coming into contact with pedestrian, it stops, kidnaps it, and moves along (does not kidnap knocked down civilian)
 * 
 * Ambulance (aka the wide grey space shuttle):
 * Moves like a standard car, heals knocked down pedestrians when coming into contact with the body, does not affect anything else
 * 
 * Superman:
 * Moves faster than any other vehicle (on average), does not kill pedestrian
 * Prioritizing saving civilians (when encountering a knocked down pedestrian, takes the pedestrian, clears all the cars infront so it gets a clear path to the edge of the map using his laser eyes)
 * Only destroys vehicles when detecting a downed pedestrian
 * 
 * Normal pedestrian:
 * The standard pedestrian, walks straight
 * 
 * Super pedestrian:
 * Standard pedestrian but slower, has supermode (every 3 seconds, super pedestrian will enter super mode making him revive himself when he gets knocked down)
 * 
 * Laser:
 * An image actor for superman to call when he destroys the car ("shoots" the laser beam infront)
 * 
 * Sun:
 * A world wide event that happens every so often (spawns on random depending on the Greenfoot getRandom number generator)
 * summons a sun at the corner of the map that eventually blows up everything on site (everything except the background)
 * 
 * 
 * 
 * Credit:
 * Code by/inspired by: 
 * Austin Yip 
 * Mr. Cohen (explosion class, vehicleworld class, supersmoothmover class, vehiclespawner class and parts of the rest)
 * Harishan (switch lane algorithym)
 * 
 * 
 * Sounds/Music:
 * Ambient noise/background music:
 * https://www.zapsplat.com/music/ambience-drone-atmo-soundscape-scifi-space-hum-evolving-loops-seamlessly/
 * 
 * Car Honk:
 * https://www.zapsplat.com/music/car-horn-2x-short-beeps-2017-toyota-corolla/
 * 
 * Ufo Beam/Kidnapping:
 * https://www.zapsplat.com/music/cartoon-sci-fi-ufo-tractor-beam-beaming-up/
 * 
 * Shoot Laser:
 * https://www.zapsplat.com/music/anime-burst-of-energy-laser-beam-fast-and-hard/
 * 
 * Knockdown/Splat:
 * https://www.zapsplat.com/music/body-hit-ground-hard-with-splat-squelch-of-blood-and-guts/
 * 
 * Explosion:
 * Mr Cohen's VehicleSim2022v2.DEMO3
 * 
 * 
 * Pictures/Art:
 * Background and lanes:
 * Austin Yip
 * 
 * Titlescreen:
 * https://www.pixelstalk.net/outer-space-backgrounds-hd-free-download/
 * 
 * Car:
 * https://www.indiedb.com/games/space-lions-squadron/images/base-space-ship-spritesheet
 * 
 * Bus:
 * https://opengameart.org/content/alien-ufo-pack
 * 
 * Ambulance:
 * https://www.nicepng.com/ourpic/u2q8a9y3a9r5i1r5_vector-spaces-ship-8-bit-spaceship-sprite/
 * 
 * Superman:
 * https://codepen.io/kappachino85/pen/GgwBPg
 * 
 * Normal Pedestrian:
 * https://straker.github.io/kontra/api/spriteSheet
 * 
 * Super Pedestrian:
 * https://tinyurl.com/3b92ehsf
 * (Supermode: Off, shorted url because the url and/or the credits were too large and big, credits at the bottom of the webpage)
 * https://tinyurl.com/4dcyfvnt
 * (Supermode: On, shorted url because the url and/or the credits were too large and big, credits at the bottom of the webpage)
 * 
 * Laser:
 * https://www.deviantart.com/erik-red/art/Solar-Man-Sprite-sheet-130974129
 * 
 * Sun1:
 * https://www.pngwing.com/en/free-png-bczgn
 * Sun2:
 * https://www.deviantart.com/caeluz/art/Exploding-Planet-169813129
 * Sun3:
 * https://www.pngkit.com/view/u2q8u2y3o0o0t4u2_exploding-planet-png-explosion/
 * 
 * 
 * @author Austin Yip
 * @version 1
 */
public class VehicleWorld extends World
{
    private GreenfootImage background;

    // Color Constants
    private static Color GREY_BORDER = new Color (0, 100, 158);
    private static Color GREY_STREET = new Color (26, 127, 186);
    private static Color YELLOW_LINE = new Color (255, 255, 255);

    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneHeight, laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;

    private GreenfootSound ambient = new GreenfootSound("ambient.mp3");
    private static final int corpse = 10;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public VehicleWorld(){    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 

        setPaintOrder (Pedestrian.class, Bus.class, Car.class, Ambulance.class);

        // set up background
        background = new GreenfootImage ("background01.png");
        setBackground (background);

        // Set critical variables
        laneCount = 6;
        laneHeight = 48;
        spaceBetweenLanes = 10;
        splitAtCenter = true;
        twoWayTraffic = true;

        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 210, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);
    }

    public void act () {
        spawn();
        ambient.playLoop();
        ambient.setVolume(50);
    }

    private void spawn () {
        // Chance to spawn a vehicle
        if (Greenfoot.getRandomNumber (60) == 0){
            int lane = Greenfoot.getRandomNumber(laneCount);
            if (!laneSpawners[lane].isTouchingVehicle()){
                int vehicleType = Greenfoot.getRandomNumber(4);
                if (vehicleType == 0){
                    addObject(new Car(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 1){
                    addObject(new Bus(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 2){
                    addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                }
                else if (vehicleType == 3){
                    addObject(new Superman(laneSpawners[lane]), 0, 0);
                }
            }
        }

        // Chance to spawn a Pedestrian
        if (Greenfoot.getRandomNumber (80) == 0){
            int xSpawnLocation = Greenfoot.getRandomNumber (600) + 50; // random between 99 and 699, so not near edges
            boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            if (spawnAtTop){
                addObject (new NormPed (1), xSpawnLocation, 50);
            } else {
                addObject (new NormPed (-1), xSpawnLocation, 550);
            }
        }

        //Chance to spawn a super pedestrian
        if (Greenfoot.getRandomNumber (120) == 0){
            int xSpawnLocation = Greenfoot.getRandomNumber (600) + 50; // random between 99 and 699, so not near edges
            boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            if (spawnAtTop){
                addObject (new SuperPed (1), xSpawnLocation, 50);
            } else {
                addObject (new SuperPed (-1), xSpawnLocation, 550);
            }
        }

        //chance to explode sun
        if(Greenfoot.getRandomNumber(4500)== 0){
            addObject(new Sun(),800, 0);
        }

        int corpseCount = 0;
        for (Pedestrian p : getObjects(Pedestrian.class)){
            if (!p.isAwake()){
                corpseCount++;
            }
        }
        if (corpseCount >= corpse){
            addObject(new Sun(),800, 0);
        }
    }

    /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;

        // draw top border
        target.setColor (GREY_BORDER);

        //target.fillRect (0, startY, target.getWidth(), spacing);
        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;

            // draw lane
            target.setColor(GREY_STREET); 
            // the lane body

            //target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            //target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);

            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    //target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        //target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        //target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            //target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane);
                world.addObject(spawners[i], 0, lanePositions[i]);
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        //target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
            }
        }
        // draws bottom border
        target.setColor (GREY_BORDER);
        //target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);

        return lanePositions;
    }
}
