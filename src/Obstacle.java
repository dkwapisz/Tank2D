import javax.swing.*;
import java.awt.*;

public class Obstacle extends JFrame {
    private final Position[] obsLocations = new Position[40];

    public Position[] getObsLocations() {
        return obsLocations;
    }

    public Obstacle() throws HeadlessException {
        // Lokalizacje przeszkód
        obsLocations[0] = new Position(100, 0); // 1
        obsLocations[1] = new Position(200, 0);
        obsLocations[2] = new Position(300, 0);
        obsLocations[3] = new Position(500, 0);
        obsLocations[4] = new Position(600, 0);
        obsLocations[5] = new Position(800, 0);
        obsLocations[6] = new Position(900, 0);
        obsLocations[7] = new Position(100, 100); //2
        obsLocations[8] = new Position(300, 100);
        obsLocations[9] = new Position(100, 200); //3
        obsLocations[10] = new Position(500, 200);
        obsLocations[11] = new Position(600, 200);
        obsLocations[12] = new Position(800, 200);
        obsLocations[13] = new Position(900, 200);
        obsLocations[14] = new Position(300, 300); //4
        obsLocations[15] = new Position(600, 300);
        obsLocations[16] = new Position(0, 400); //5
        obsLocations[17] = new Position(200, 400);
        obsLocations[18] = new Position(300, 400);
        obsLocations[19] = new Position(500, 400);
        obsLocations[20] = new Position(600, 400);
        obsLocations[21] = new Position(700, 400);
        obsLocations[22] = new Position(900, 400);
        obsLocations[23] = new Position(0, 500); //6
        obsLocations[24] = new Position(600, 500);
        obsLocations[25] = new Position(200, 600); //7
        obsLocations[26] = new Position(400, 600);
        obsLocations[27] = new Position(800, 600);
        obsLocations[28] = new Position(0, 700); //8
        obsLocations[29] = new Position(100, 700);
        obsLocations[30] = new Position(200, 700);
        obsLocations[31] = new Position(400, 700);
        obsLocations[32] = new Position(500, 700);
        obsLocations[33] = new Position(600, 700);
        obsLocations[34] = new Position(800, 700);
        obsLocations[35] = new Position(600, 800); //9
        obsLocations[36] = new Position(0, 900); //10
        obsLocations[37] = new Position(200, 900);
        obsLocations[38] = new Position(400, 900);
        obsLocations[39] = new Position(600, 900);

    }

    // Sprawdza możliwość wystąpienia kolizji z przeszkodami lub granicami mapy
    public boolean checkCollision(int x, int y, int w, int h) {
        boolean collision = true;
        if (x <= 0 || x >= 1000 || y <= 0 || y >= 1000) { // Granice mapy
            return false;
        }
        for (Position obsLocation : obsLocations) { // Przeszkody
            if (new Rectangle(x, y, w, h).intersects(new Rectangle((int) obsLocation.getX(), (int) obsLocation.getY(), 100, 100))) {
                collision = false;
                break;
            }
        }
        return collision;
    }

}
