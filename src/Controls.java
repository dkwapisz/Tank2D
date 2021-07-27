import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controls extends Thread implements ActionListener {
    private final JComponent component;
    private int deltaX;
    private int deltaY;
    private final Timer timer;
    private int keysPressed;
    private final InputMap inputMap;
    private final int whichPlayer;
    private final Obstacle obs = new Obstacle();
    private final TankState tankState = new TankState();
    private int velocityFactor;
    private boolean shotState;
    private final JComponent bullet;

    public void setVelocityFactor(int velocityFactor) {
        this.velocityFactor = velocityFactor;
    }

    public void setComponentLocation(int x, int y) {
        component.setLocation(x, y);
    } // Setter do lokalizacji

    public Controls(JComponent component, JComponent bullet, int delay, int whichPlayer)
    {
        this.component = component;
        this.whichPlayer = whichPlayer;
        this.bullet = bullet;

        timer = new Timer(delay, this);
        timer.setInitialDelay( 0 );

        inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        start();
    }

    public void addAction(String name, int keyDeltaX, int keyDeltaY, int keyCode) { // Przypisawanie akcji klawiszy
        new NavigationAction(name, keyDeltaX, keyDeltaY, keyCode);
    }

    public void move(int deltaX, int deltaY) {
        int componentWidth = component.getSize().width; // Szerokość czołgu
        int componentHeight = component.getSize().height; // Wysokość czołgu

        deltaX *= velocityFactor;
        deltaY *= velocityFactor;

        if(deltaX > 0 && deltaY == 0) { // Czołg jadący w prawo
            tankState.setUp(false);
            tankState.setDown(false);
            tankState.setRight(true);
            tankState.setLeft(false);
        }
        if(deltaX < 0 && deltaY == 0) { // Czołg jadący w lewo
            tankState.setUp(false);
            tankState.setDown(false);
            tankState.setRight(false);
            tankState.setLeft(true);
        }
        if(deltaX == 0 && deltaY > 0) { // Czołg jadący w dół
            tankState.setUp(false);
            tankState.setDown(true);
            tankState.setRight(false);
            tankState.setLeft(false);
        }
        if(deltaX == 0 && deltaY < 0) { // Czołg jadący w górę
            tankState.setUp(true);
            tankState.setDown(false);
            tankState.setRight(false);
            tankState.setLeft(false);
        }
        if((deltaX > 0 && deltaY != 0) || (deltaX < 0 && deltaY != 0)) { // Blokuje jazdę na skos
            return;
        }
        tankState.setPlayer(this.whichPlayer); // Dopisuje nr gracza do TankState
        Panel.setTankIcon(tankState); // Zmienia ikonę czołgu w zależności, w którym kierunku jedzie

        Dimension parentSize = component.getParent().getSize();
        int parentWidth = parentSize.width;
        int parentHeight = parentSize.height;


        int nextX = Math.max(component.getLocation().x + deltaX, 0); // Oblicza następną pozycję dla X

        if (nextX + componentWidth > parentWidth) {
            nextX = parentWidth - componentWidth;
        }

        int nextY = Math.max(component.getLocation().y + deltaY, 0); // Oblicza następną pozycję dla Y

        if (nextY + componentHeight > parentHeight) {
            nextY = parentHeight - componentHeight;
        }

        if (obs.checkCollision(nextX, nextY, componentWidth, componentHeight) && Panel.checkTankCollision(nextX, nextY)) { // Przemieszcza czołg
            component.setLocation(nextX, nextY);
        }
    }

    @Override
    public void run() {
        int nextX = 0;
        int nextY = 0;
        this.bullet.setLocation(-25, -25);
        while (true) {
            int dX = 0;
            int dY = 0;
            if (tankState.isRight()) {
                dX = 5;
                dY = 0;
            } else if (tankState.isDown()) {
                dY = 5;
                dX = 0;
            } else if (tankState.isLeft()) {
                dX = -5;
                dY = 0;
            } else if (tankState.isUp()) {
                dY = -5;
                dX = 0;
            }
            if((obs.checkCollision(nextX, nextY, bullet.getHeight(),bullet.getWidth())) && shotState && !Panel.isHit(nextX, nextY, tankState, shotState)) {
                nextX = component.getX()+25;
                nextY = component.getY()+25;
            }
            while((obs.checkCollision(nextX, nextY, bullet.getHeight(),bullet.getWidth())) && shotState && !Panel.isHit(nextX, nextY, tankState, shotState)) {
                nextX = nextX + dX;
                nextY = nextY + dY;
                this.bullet.setLocation(nextX, nextY);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(Panel.isHit(nextX, nextY, tankState, shotState) || !obs.checkCollision(nextX, nextY, bullet.getHeight(), bullet.getWidth())){
                this.bullet.setLocation(-25, -25);
                nextX = component.getX()+25;
                nextY = component.getY()+25;
                shotState = false;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleKeyEvent(boolean pressed, int deltaX, int deltaY) {
        keysPressed += pressed ? 1 : -1;
        this.deltaX += deltaX;
        this.deltaY += deltaY;

        //  Startuje timer, gdy nacisnieto klawisz

        if (keysPressed == 1) {
            timer.start();
        }

        //  Stopuje timer, gdy puszczono klawisz

        if (keysPressed == 0) {
            timer.stop();
        }
    }

    //  Funkcja wywoływana gdy timer wystartuje
    public void actionPerformed(ActionEvent e) { // Przeczwytuje wciśnięcie klawiszu i nakazuje czołgowi jechać
        move(deltaX, deltaY);
    }


    class NavigationAction extends AbstractAction implements ActionListener {
        private final int keyDeltaX;
        private final int keyDeltaY;
        private final int keyCode;
        private final KeyStroke pressedKeyStroke;
        private boolean listeningForKeyPressed;

        public NavigationAction(String name, int keyDeltaX, int keyDeltaY, int keyCode) {
            super(name);
            this.keyCode = keyCode;
            this.keyDeltaX = keyDeltaX;
            this.keyDeltaY = keyDeltaY;

            pressedKeyStroke = KeyStroke.getKeyStroke(keyCode, 0, false);
            KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(keyCode, 0, true);

            inputMap.put(pressedKeyStroke, getValue(Action.NAME));
            inputMap.put(releasedKeyStroke, getValue(Action.NAME));
            component.getActionMap().put(getValue(Action.NAME), this);
            listeningForKeyPressed = true;
        }

        public void actionPerformed(ActionEvent e) {
            // Nacisniecie klawisza
            if (listeningForKeyPressed) {
                handleKeyEvent(true, keyDeltaX, keyDeltaY);
                inputMap.remove(pressedKeyStroke);
                listeningForKeyPressed = false;
                if (keyCode == 32 || keyCode == 90) {
                    bullet.setLocation(component.getX(), component.getY());
                    shotState = true;
                }
            } else // Puszczenie klawisza
            {
                handleKeyEvent(false, -keyDeltaX, -keyDeltaY);
                inputMap.put(pressedKeyStroke, getValue(Action.NAME));
                listeningForKeyPressed = true;
            }
        }
    }
}