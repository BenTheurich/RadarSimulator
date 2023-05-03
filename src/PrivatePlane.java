import java.awt.*;

public class PrivatePlane extends Aircraft{

    public PrivatePlane(){
        type = "Private";
        speed = 0.4;
        color = new Color(255, 234, 0);
        colorString = "Yellow";
        setDir();
        setCoords();
    }
}
