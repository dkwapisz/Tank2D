import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JFrame {
    private JTextField whoWinsField;
    private JButton newGameButton;
    private JButton exitButton;
    private JPanel window;

    public JPanel getWindow() {
        return window;
    }

    public JTextField getTextField1() {
        return whoWinsField;
    }

    public GameOver() {
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel.getGameOverFrame().dispose();
                Panel.getMenuFrame().setVisible(true);
                Panel.getPlayer1().setComponentLocation(25, 25);
                Panel.getPlayer2().setComponentLocation(925, 925);
                Panel.getMenu().setType1(null);
                Panel.getMenu().setType2(null);
                Panel.getMenu().getTankTypeP1().setText(null);
                Panel.getMenu().getTankTypeP2().setText(null);
                Panel.healthToStartPos();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
    }
}
