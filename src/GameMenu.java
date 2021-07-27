import javax.swing.*;

public class GameMenu extends JFrame {

    private JButton newGame;
    private JButton lightP2;
    private JButton lightP1;
    private JButton mediumP1;
    private JButton mediumP2;
    private JButton heavyP2;
    private JButton heavyP1;
    private JPanel menu;
    private JTextField tankTypeP1;
    private JTextField tankTypeP2;
    private JTextField nameP1;
    private JTextField nameP2;
    private String type1;
    private String type2;
    private boolean gameBegins = false;

    public JTextField getNameP1() { return nameP1; }
    public JTextField getNameP2() { return nameP2; }

    public JPanel getMenu() {
        return menu;
    }

    public JTextField getTankTypeP1() { return tankTypeP1; }
    public JTextField getTankTypeP2() { return tankTypeP2; }

    public void setType1(String type1) { this.type1 = type1; }
    public void setType2(String type2) { this.type2 = type2; }

    public void setGameStatus(boolean gameBegins) { this.gameBegins =  gameBegins; }
    public boolean getGameStatus() { return gameBegins; }

    public GameMenu() {

        lightP1.addActionListener(e -> {
            Panel.getHealthP1().setValue(80);
            Panel.getPlayer1().setVelocityFactor(5);
            type1 = "Light Tank";
            refresh();
        });

        lightP2.addActionListener(e -> {
            Panel.getHealthP2().setValue(80);
            Panel.getPlayer2().setVelocityFactor(5);
            type2 = "Light Tank";
            refresh();
        });

        mediumP1.addActionListener(e -> {
            Panel.getHealthP1().setValue(90);
            Panel.getPlayer1().setVelocityFactor(4);
            type1 = "Medium Tank";
            refresh();
        });

        mediumP2.addActionListener(e -> {
            Panel.getHealthP2().setValue(90);
            Panel.getPlayer2().setVelocityFactor(4);
            type2 = "Medium Tank";
            refresh();
        });

        heavyP1.addActionListener(e -> {
            Panel.getHealthP1().setValue(100);
            Panel.getPlayer1().setVelocityFactor(3);
            type1 = "Heavy Tank";
            refresh();
        });

        heavyP2.addActionListener(e -> {
            Panel.getHealthP2().setValue(100);
            Panel.getPlayer2().setVelocityFactor(3);
            type2 = "Heavy Tank";
            refresh();
        });

        newGame.addActionListener(e -> {
            if(nameP1.getText().isEmpty()) {
                nameP1.setText("Player 1");
                gameBegins = true;
            }
            if(nameP2.getText().isEmpty()) {
                nameP2.setText("Player 2");
                gameBegins = true;
            }
            if(type1 != null && type2 != null) {
                Panel.getMenuFrame().dispose();
                Panel.getFrame().setVisible(true);
                Panel.getHealthP1().setString(nameP1.getText());
                Panel.getHealthP2().setString(nameP2.getText());
                refresh();
                gameBegins = true;
            }
            if(type1 == null) {
                tankTypeP1.setText("Choose tank type");
            }
            if(type2 == null) {
                tankTypeP2.setText("Choose tank type");
            }
        });

    }

    private void refresh() {
        tankTypeP1.setText(type1);
        tankTypeP2.setText(type2);
    }


}
