import java.awt.*;

public class Drone extends Aircraft{

    public Drone(){
        type = "Drone";
        speed = 1;
        color = new Color(115, 0, 255);
        colorString = "Purple";
        setDir();
        setCoords();
    }
}
