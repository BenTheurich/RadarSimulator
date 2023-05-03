import java.util.ArrayList;

public class AirController {
    public ArrayList<Aircraft> planes = new ArrayList<>();

    // add planes
    public AirController(int numFighter, int numCommercial, int numDrone, int numPrivate){
        for(int i = 0; i < numFighter; i++) {
            planes.add(new FighterJet());
        }
        for(int i = 0; i < numCommercial; i++) {
            planes.add(new CommercialPlane());
        }
        for(int i = 0; i < numDrone; i++) {
            planes.add(new Drone());
        }
        for(int i = 0; i < numPrivate; i++) {
            planes.add(new PrivatePlane());
        }
    }

    public void calculateDist(Aircraft a){
        double[] airC = a.getCoords();
        // pythagorean theorem to calculate distance from the tower
        double tempDistance = Math.sqrt(Math.pow(200 - airC[0], 2) + Math.pow(200 - airC[1], 2));
        a.setDist(tempDistance);
    }

    private void sortPlanes() {
        // use bubble sort to sort the aircraft
        // bubble sort is pretty inefficient with a time complexity of O(n^2)
        // if I had more time, I would have liked to add a better sorting algorithm like quicksort
        // or implement a priority queue
        int n = planes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (planes.get(j).getDist() > planes.get(j + 1).getDist()) {
                    // swap adjacent elements if they are out of order
                    Aircraft temp = planes.get(j);
                    planes.set(j, planes.get(j + 1));
                    planes.set(j + 1, temp);
                }
            }
        }
    }

    public String[][] topThree(){
        sortPlanes();
        String[][] sArray = new String[3][4];
        // format the top 3 closest planes
        if (planes.size() < 3) {
            throw new IndexOutOfBoundsException("There are less than 3 planes in the list");
        }
        for(int i = 0; i < 3; i++){
            sArray[i][0] = Integer.toString(i+1);
            sArray[i][1] = planes.get(i).getType();
            sArray[i][2] = planes.get(i).getColorString();
            sArray[i][3] = Double.toString(planes.get(i).getDist());
        }
        return sArray;
    }
}
