import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLobbyWithCardLayout extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField player1Field;
    private JButton joinPlayer1Button;
    private JTextField player2Field;
    private JButton joinPlayer2Button;
    private JButton startGameButton;
    private JLabel player1Label;
    private JLabel player2Label;

    public GameLobbyWithCardLayout() {
        setTitle("Game Lobby");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createPlayerPanel(1), "player1Panel");
        cardPanel.add(createPlayerPanel(2), "player2Panel");
        cardPanel.add(createGamePanel(), "gamePanel");

        cardLayout.show(cardPanel, "player1Panel");

        add(cardPanel);

        setVisible(true);

        //TRYCKA ENTER LAYOUT HÄR?
    }

    private JPanel createPlayerPanel(final int playerNumber) {
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField playerNameField = new JTextField();
        JButton joinButton = new JButton("Join Player " + playerNumber);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText();
                if (!playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(GameLobbyWithCardLayout.this, "Welcome, " + playerName + "!");
                    if (playerNumber == 1) {
                        cardLayout.show(cardPanel, "player2Panel");
                        player1Label.setText("Player 1: " + playerName);
                    } else {
                        cardLayout.show(cardPanel, "gamePanel");
                        player2Label.setText("Player 2: " + playerName);
                        startGameButton.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(GameLobbyWithCardLayout.this, "Enter a player name.");
                }
            }
        });

        playerNameField.setPreferredSize(new Dimension(200, 30));
        joinButton.setPreferredSize(new Dimension(150, 30));

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        inputPanel.add(new JLabel("Enter your player name:"));
        inputPanel.add(playerNameField);

        playerPanel.add(inputPanel, BorderLayout.CENTER);
        playerPanel.add(joinButton, BorderLayout.SOUTH);

        return playerPanel;
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        player1Label = new JLabel("Player 1: ");
        player2Label = new JLabel("Player 2: ");
        JLabel gameLabel = new JLabel("Game Panel");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));

        startGameButton = new JButton("Start Game");
        startGameButton.setEnabled(false);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        gamePanel.add(player1Label, BorderLayout.NORTH);
        gamePanel.add(player2Label, BorderLayout.CENTER);
        gamePanel.add(gameLabel, BorderLayout.SOUTH);
        gamePanel.add(startGameButton, BorderLayout.SOUTH);

        return gamePanel;
    }

    private void startGame() {
        JOptionPane.showMessageDialog(this, "Game started!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameLobbyWithCardLayout();
            }
        });
    }
}
