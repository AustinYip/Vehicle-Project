import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Explosion actor
 * 
 * @Credit Jordan Cohen
 * @version November 2015
 */



public class Explosion extends Effect
{

    // This is static, so a single Array is created and shared by all Explosion objects.
    // This is safe because Greenfoot runs these commands consecutively, not simultaneously,
    // so there is no threat of a conflict. 
    private static GreenfootSound[] explosionSounds;
    private static int explosionSoundsIndex = 0; 
    private GreenfootImage fireImage;

    private Color currentColor;

    private int radius;
    private int speed;
    private int steps;
    private int red, green, blue;
    private int transparency;
    private int maxSize;
    private int additionalExplosions;

    public Explosion (int maxSize, int additionalExplosions){
        this(maxSize);
        this.additionalExplosions = additionalExplosions;
    }

    /**
     * Constructor to create an Explosion
     * 
     * @param   maxSize     The size at which the explosion will stop growing and remove itself
     */
    public Explosion (int maxSize)
    {
        // Create image to manage this graphic
        fireImage = new GreenfootImage (maxSize, maxSize);
        // Variables that control Colors for fire effect:
        red = 255;
        green = 40;
        blue = 1;
        // Start as fully opaque
        transparency = 255;
        additionalExplosions = 0;

        // Dynamic way to set speed so small explosions don't
        // disappear to fast and large explosions don't linger
        speed = Math.max((int)Math.sqrt(maxSize / 2), 1);
        // Set starting Color
        currentColor = new Color (red, green, blue);
        // Set starting Radius
        radius = 5;
        // Figure out how many times this will run, so opacity can decrease at
        // an appropriate rate
        steps = (maxSize) / speed;
        // Store maxSize (from parameter) in instance variable for
        // future user
        this.maxSize = maxSize;

        // Method to actually draw the circle
        redraw();
        // Set this Actor's graphic as the image I just created
        this.setImage(fireImage);
    }

    public void addedToWorld (World w){
        if (maxSize > 30){
            explosionSounds[explosionSoundsIndex].setVolume(Math.min(maxSize, 60));
            explosionSounds[explosionSoundsIndex].play();
            explosionSoundsIndex++;
            if (explosionSoundsIndex >= explosionSounds.length){
                explosionSoundsIndex = 0;
            }
        }
    }

    /**
     * Pre-load sounds for explosions
     */
    public static void init(){
        explosionSoundsIndex = 0;
        explosionSounds = new GreenfootSound[48]; // lots of simultaneous explosioning!
        for (int i = 0; i < explosionSounds.length; i++){
            explosionSounds[i] = new GreenfootSound("explosion2.wav");

        }   
    }

    /**
     * Act method gets called by Greenfoot every frame. In this class, this method
     * will serve to increase the size each act until maxSize is reached, at which
     * point the object will remove itself from existence.
     */
    public void act() 
    {
        redraw();   // redraw the circle at its new size


        if (radius + speed <= maxSize)  // If the explosion hasn't yet hit its max
            radius += speed;            // size, keep growing

        else{ // explosion has finished growing
            for (Pedestrian p : getObjectsInRange(maxSize, Pedestrian.class)){
                p.knockDown();
                getWorld().removeObject(p);
            }
            for (Vehicle v : getObjectsInRange(maxSize, Pedestrian.class)){
            
            getWorld().removeObject(this);
            }
        // remove it from the World
    }    

    /**
     * redraw() method is a private method called by this object each act
     * in order to redraw the graphic
     */
    private void redraw()
    {
        // adjust colors
        green = Math.min(255, Math.max(0, green + (150 / steps)));
        blue = Math.min(255, Math.max(0, blue + (10 / steps)));
        // reduce transparency, but ensure it doesn't fall below zero - that would cause
        // a crash
        if (transparency - (255 / steps) > 0)
            transparency -= (255 / steps);
        else
            transparency = 0;

        // update Color
        currentColor = new Color (red, green, blue);

        // update transparency
        fireImage.setTransparency(transparency);
        fireImage.setColor (currentColor);
        // redraw image
        fireImage.fillOval ((maxSize - radius)/2, (maxSize - radius)/2, radius, radius);
        fireImage.setTransparency(transparency);
    }

}
