import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Panel extends JFrame {
    private static JLabel Player1Icon;
    private static JLabel Player2Icon;
    private static JLabel bulletIcon;
    private static JLabel bullet2Icon;
    private static Icon tank1Right;
    private static Icon tank1Left;
    private static Icon tank1Down;
    private static Icon tank1Up;
    private static Icon tank2Right;
    private static Icon tank2Left;
    private static Icon tank2Down;
    private static Icon tank2Up;
    private static Icon bullet;
    private static final JFrame frame = new JFrame();
    private static final JFrame menuFrame = new JFrame();
    private static final JFrame gameOverFrame = new JFrame();
    private static Controls Player1;
    private static Controls Player2;
    private static JProgressBar healthP1;
    private static JProgressBar healthP2;
    private static GameMenu menu = new GameMenu();


    public static JProgressBar getHealthP1() { return healthP1; }
    public static JProgressBar getHealthP2() { return healthP2; }

    public static Controls getPlayer1() {
        return Player1;
    }
    public static Controls getPlayer2() {
        return Player2;
    }

    public static JFrame getFrame() {
        return frame;
    }
    public static JFrame getMenuFrame() {
        return menuFrame;
    }
    public static JFrame getGameOverFrame() {
        return gameOverFrame;
    }
    public static GameMenu getMenu() { return menu; }


    public Panel() {

        JLabel contentPanel = new JLabel(); // Tworzy panel gry
        contentPanel.setLayout(null);
        contentPanel.setIcon(new ImageIcon("src/graphics/Background.png"));
        add(contentPanel);
        validate();

        tank1Down = new ImageIcon("src/graphics/TankDown.png"); // Dodaje ikony gracza nr 1
        tank1Up = new ImageIcon("src/graphics/TankUp.png");
        tank1Right = new ImageIcon("src/graphics/TankRight.png");
        tank1Left = new ImageIcon("src/graphics/TankLeft.png");

        tank2Down = new ImageIcon("src/graphics/Tank2Down.png"); // Dodaje ikony gracza nr 2
        tank2Up = new ImageIcon("src/graphics/Tank2Up.png");
        tank2Right = new ImageIcon("src/graphics/Tank2Right.png");
        tank2Left = new ImageIcon("src/graphics/Tank2Left.png");

        bullet = new ImageIcon("src/graphics/bullet.png");

        ImageIcon brick = new ImageIcon("src/graphics/Brick2.png"); // Dodaje ikone cegły

        Player1Icon = new JLabel(tank1Down); // Tworzy ikonę startową gracza 1
        Player2Icon = new JLabel(tank2Up); // Tworzy ikonę startową gracza 2
        JLabel brickIcon; // Tworzy ikonę cegły

        Player1Icon.setSize(Player1Icon.preferredSize()); // Ustawia wielkość ikony gracza 1 i dodaje do panelu
        contentPanel.add(Player1Icon);

        Player2Icon.setSize(Player2Icon.preferredSize()); // Ustawia wielkość ikony gracza 2 i dodaje do panelu
        contentPanel.add(Player2Icon);

        bulletIcon = new JLabel(bullet);
        bulletIcon.setSize(bulletIcon.preferredSize());
        bulletIcon.setLocation(25, 25);
        contentPanel.add(bulletIcon);
        bullet2Icon = new JLabel(bullet);
        bullet2Icon.setSize(bulletIcon.preferredSize());
        bullet2Icon.setLocation(420, 25);
        contentPanel.add(bullet2Icon);

        for (int i = 0; i < 40; i++) { // Tworzy graficzne przeszkody na mapie
            brickIcon = new JLabel(brick);
            brickIcon.setSize(brickIcon.preferredSize());
            Obstacle obs = new Obstacle();
            brickIcon.setLocation((int) obs.getObsLocations()[i].getX(), (int) obs.getObsLocations()[i].getY());
            contentPanel.add(brickIcon);
        }

        healthP1 = new JProgressBar();
        healthP2 = new JProgressBar();

        // Utworzenie listenera dla gracza nr 1, ustawienie pozycji początkowej i dodanie akcji
        Player1 = new Controls(Player1Icon, bulletIcon, 24, 1);
        Player1.setComponentLocation(25, 25);
        Player1.addAction("Left", -1, 0, KeyEvent.VK_LEFT);
        Player1.addAction("Right", 1, 0, KeyEvent.VK_RIGHT);
        Player1.addAction("Up", 0, -1, KeyEvent.VK_UP);
        Player1.addAction("Down", 0, 1, KeyEvent.VK_DOWN);
        Player1.addAction("Shot", 0, 0, KeyEvent.VK_SPACE);

        // Utworzenie listenera dla gracza nr 2, ustawienie pozycji początkowej i dodanie akcji
        Player2 = new Controls(Player2Icon, bullet2Icon, 24, 2);
        Player2.setComponentLocation(925, 925);
        Player2.addAction("Left", -1, 0, KeyEvent.VK_A);
        Player2.addAction("Right", 1, 0, KeyEvent.VK_D);
        Player2.addAction("Up", 0, -1, KeyEvent.VK_W);
        Player2.addAction("Down", 0, 1, KeyEvent.VK_S);
        Player2.addAction("Shot", 0, 0, KeyEvent.VK_Z);

        menuFrame.getContentPane().add(menu.getMenu());
        menuFrame.pack();
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
        menuFrame.setResizable(false);
        menuFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - menuFrame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - menuFrame.getSize().height) / 2);

        healthP1.setBounds((int) Player1Icon.getLocation().getX(), (int) Player1Icon.getLocation().getY()-25,50,10);
        healthP1.setSize(50,20);
        healthP1.setStringPainted(true);

        healthP2.setBounds((int) Player2Icon.getLocation().getX(), (int) Player2Icon.getLocation().getY()-25,50,10);
        healthP2.setSize(50,20);
        healthP2.setStringPainted(true);

        frame.getContentPane().add(healthP1);
        frame.getContentPane().add(healthP2);

        frame.getContentPane().add(contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(1015, 1039));
        frame.setResizable(false);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height-30) / 2);
    }

    // Funkcja odpowiadająca za dopasowywanie ikony czołgu względem tego, w którym kierunku jedzie
    public static void setTankIcon(TankState tankState) {
        if(tankState.isRight() && tankState.getPlayer() == 1)
            Player1Icon.setIcon(tank1Right);
        if(tankState.isLeft() && tankState.getPlayer() == 1)
            Player1Icon.setIcon(tank1Left);
        if(tankState.isUp() && tankState.getPlayer() == 1)
            Player1Icon.setIcon(tank1Up);
        if(tankState.isDown() && tankState.getPlayer() == 1)
            Player1Icon.setIcon(tank1Down);

        if(tankState.isRight() && tankState.getPlayer() == 2)
            Player2Icon.setIcon(tank2Right);
        if(tankState.isLeft() && tankState.getPlayer() == 2)
            Player2Icon.setIcon(tank2Left);
        if(tankState.isUp() && tankState.getPlayer() == 2)
            Player2Icon.setIcon(tank2Up);
        if(tankState.isDown() && tankState.getPlayer() == 2)
            Player2Icon.setIcon(tank2Down);

        healthP1.setLocation((int) Player1Icon.getLocation().getX(), (int) Player1Icon.getLocation().getY()-25);
        healthP2.setLocation((int) Player2Icon.getLocation().getX(), (int) Player2Icon.getLocation().getY()-25);
    }

    // Funkcja odpowiada za blokowanie możliwości kolizji dwóch czołgów
    public static boolean checkTankCollision(int x, int y) {
        if((new Rectangle(x, y, 50, 50).intersects(new Rectangle(Player2Icon.getX(), Player2Icon.getY(), 50, 50))) && (new Rectangle(Player1Icon.getX(), Player1Icon.getY(), 50, 50).intersects(new Rectangle(x, y, 50, 50)))) {
            return false;
        };
        return true;
    }

    // Funkcja sprawdza czy nastąpiło trafienie pocisku
    public static boolean isHit(int x, int y, TankState tankState, boolean shotState) {
        refresh();
        if (shotState && new Rectangle(x, y, 5, 5).intersects(new Rectangle(Player2Icon.getX(), Player2Icon.getY(), 50, 50)) && tankState.getPlayer() == 1) {
            healthP2.setValue(healthP2.getValue()-10);
            return true;
        }
        else if (shotState && new Rectangle(x, y, 5, 5).intersects(new Rectangle(Player1Icon.getX(), Player1Icon.getY(), 50, 50)) && tankState.getPlayer() == 2) {
            healthP1.setValue(healthP1.getValue()-10);
            return true;
        }
        if (healthP1.getValue() == 0 && menu.getGameStatus()) {
            String name = "Player 2";
            if (!menu.getNameP2().getText().isEmpty()) {
                name = menu.getNameP2().getText();
            }
            menu.setGameStatus(false);
            makeGameOver(name);
        }
        if (healthP2.getValue() == 0 && menu.getGameStatus()) {
            String name = "Player 1";
            if (!menu.getNameP1().getText().isEmpty()) {
                name = menu.getNameP1().getText();
            }
            menu.setGameStatus(false);
            makeGameOver(name);
        }
        return false;
    }

    private static void makeGameOver(String name) {
        GameOver gameOver = new GameOver();
        gameOver.getTextField1().setText(name + " wins!");
        frame.dispose();
        gameOverFrame.getContentPane().add(gameOver.getWindow());
        gameOverFrame.pack();
        gameOverFrame.setVisible(true);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - gameOverFrame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - gameOverFrame.getSize().height) / 2);
        gameOverFrame.setResizable(false);
        menu.setGameStatus(false);

    }

    // Funkcja odświeżająca paski zdrowia graczy
    private static void refresh() {
        healthP1.repaint();
        healthP1.revalidate();
        healthP2.repaint();
        healthP2.revalidate();
    }

    public static void healthToStartPos() {
        healthP1.setLocation((int) Player1Icon.getLocation().getX(), (int) Player1Icon.getLocation().getY()-25);
        healthP2.setLocation((int) Player2Icon.getLocation().getX(), (int) Player2Icon.getLocation().getY()-25);
    }
}