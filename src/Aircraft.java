import java.awt.*;
import java.util.Random;

public abstract class Aircraft {
    protected String type;
    private Direction dir;
    protected double speed;
    private double[] coords;
    private final double[] lastSeen = {0, 0};
    private double distFromTower;
    private int fade = 0;
    protected Color color;
    protected String colorString;
    private final Random rand = new Random();

    // initialize direction
    protected void setDir(){
        Direction[] dirs = Direction.values();
        dir = dirs[rand.nextInt(4)];
    }

    // initialize coordinates
    protected void setCoords(){
        coords = new double[]{rand.nextDouble(501) - 50, rand.nextDouble(501) - 50};
        lastSeen[0] = coords[0];
        lastSeen[1] = coords[1];
    }

    public Color getColor(){
        return color;
    }

    public String getColorString(){
        return colorString;
    }

    public String getType() { return type; }

    public double[] getCoords() { return coords; }

    public double[] getLastSeen() { return lastSeen; }

    public double getDist() { return distFromTower; }

    public void setDist(double dist) { distFromTower = dist; }

    public int getFade() { return fade; }

    public void decreaseFade(int dec) { fade -= dec; }

    public void hitByRadar(double[] rCoords){
        // a bit complex but basically this calculates the distance between
        // the aircraft and the two points of the radar line using the pythagorean theorem twice
        double distance = Math.abs(200 -
                (Math.sqrt(Math.pow(Math.abs(coords[0]-rCoords[0]), 2) +
                Math.pow(Math.abs(coords[1]-rCoords[1]), 2)) +
                Math.sqrt(Math.pow(Math.abs(200-coords[0]), 2) + Math.pow(Math.abs(200-coords[1]), 2))));
        // if hit by the radar, reset the fade to be fully opaque
        if(distance < 2){
            fade = 255;
            lastSeen[0] = coords[0];
            lastSeen[1] = coords[1];
        }
    }

    // update the plane in the correct direction
    public void move(){
        switch (dir){
            case EAST -> coords[0] -= speed;
            case WEST -> coords[0] += speed;
            case NORTH -> coords[1] -= speed;
            case SOUTH -> coords[1] += speed;
        }
        if(coords[0] < -50){
            coords[0] = 450;
            coords[1] = rand.nextDouble(501) - 50;
        }else if(coords[0] > 450){
            coords[0] = -50;
            coords[1] = rand.nextDouble(501) - 50;
        }else if(coords[1] < -50){
            coords[0] = rand.nextDouble(501) - 50;
            coords[1] = 450;
        }else if(coords[1] > 450){
            coords[0] = rand.nextDouble(501) - 50;
            coords[1] = -50;
        }
    }
}
