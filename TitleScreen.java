import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class TitleScreen extends World
{
    //private GreenfootImage background;
    
    // create the labels for the title screen
    Label titleLabel = new Label ("Space Highway Simulator", 60);
    Label start = new Label("Click 'enter' to start", 40);
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {
        // Create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        
        //background = new GreenfootImage ("titlescreen.png");
        //add the labels
        addObject (titleLabel, 400, 150);
        addObject(start, 400, 400);
    }

    /*
     * Start the game by starting the simulation screens
     */
    public void act()
    {
        //checks to see if user is ready to start the game
        if(Greenfoot.isKeyDown("enter"))
        {
            VehicleWorld story = new VehicleWorld();
            Greenfoot.setWorld(story);
        }
    }
}
