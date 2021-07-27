public class TankState {
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;
    private int whichPlayer;

    public TankState() {
        this.up = false; // W którą stronę jest obrócony/jedzie
        this.down = true;
        this.right = false;
        this.left = false;
        this.whichPlayer = 0; // Nr gracza
    }

    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }

    public int getPlayer() {
        return whichPlayer;
    }
    public void setPlayer(int whichPlayer) {
        this.whichPlayer = whichPlayer;
    }
}
