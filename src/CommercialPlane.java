import java.awt.*;

public class CommercialPlane extends Aircraft{

    public CommercialPlane(){
        type = "Commercial";
        speed = 0.2;
        color = new Color(0, 255, 247);
        colorString = "Blue";
        setDir();
        setCoords();
    }
}
