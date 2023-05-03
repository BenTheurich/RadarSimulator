import java.awt.*;

public class FighterJet extends Aircraft{

    public FighterJet(){
        type = "Fighter";
        speed = 0.6;
        color = new Color(255, 0, 0);
        colorString = "Red";
        setDir();
        setCoords();
    }
}
