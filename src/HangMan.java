import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangMan extends JFrame {

    // Deklaration der GUI-Komponenten und der Spiel-Logik
    private t_game gameLogic;
    private JPanel panel1;
    private JButton confirmBTN;
    private JTextField guessTF;
    private JLabel triesL;
    private JLabel progressL;
    private JLabel pictureL;
    private JLabel maxFailsL;
    private JLabel versuchteBuchstabenL;
    private JMenuBar menuBar;
    private JMenu settingsMenu;
    private JMenuItem setTriesMenuItem;
    private JCheckBoxMenuItem showHistoryMenuItem;
    private JRadioButtonMenuItem easyMenuItem;
    private JRadioButtonMenuItem normalMenuItem;
    private JRadioButtonMenuItem hardMenuItem;
    private ButtonGroup difficultyGroup;

    // Konstruktor der HangMan-Klasse
    public HangMan() {
        // Setzt den Titel des Fensters
        setTitle("Hangman Game");
        // Setzt die Größe des Fensters
        setSize(1200, 800);
        // Beendet das Programm beim Schließen des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setzt das Layout des Fensters
        setLayout(new BorderLayout());

        // Initialisiert die Spiel-Logik und übergibt die aktuelle Instanz
        gameLogic = new t_game(this);

        // Setzt die Anfangswerte für die Labels
        triesL.setText("Tries: 0");
        progressL.setText(gameLogic.getMaskedWord());
        pictureL.setIcon(new ImageIcon("src/images/00.png"));
        maxFailsL.setText("Max Tries: " + gameLogic.getMaxFails());

        // Menüleiste erstellen
        menuBar = new JMenuBar();
        settingsMenu = new JMenu("Settings");
        showHistoryMenuItem = new JCheckBoxMenuItem("Show Guessed Letters");

        // Schwierigkeitsgrad-Menüelemente erstellen
        easyMenuItem = new JRadioButtonMenuItem("Easy");
        normalMenuItem = new JRadioButtonMenuItem("Normal", true);
        hardMenuItem = new JRadioButtonMenuItem("Hard");
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyMenuItem);
        difficultyGroup.add(normalMenuItem);
        difficultyGroup.add(hardMenuItem);

        // Menüelemente hinzufügen

        settingsMenu.add(showHistoryMenuItem);
        settingsMenu.addSeparator();
        settingsMenu.add(easyMenuItem);
        settingsMenu.add(normalMenuItem);
        settingsMenu.add(hardMenuItem);
        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);

        showHistoryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                versuchteBuchstabenL.setVisible(showHistoryMenuItem.isSelected());
            }
        });

        easyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.setMaxFails(9);
                updatePanel();
            }
        });

        normalMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.setMaxFails(5);
                updatePanel();
            }
        });

        hardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.setMaxFails(3);
                updatePanel();
            }
        });

        // Fügt einen ActionListener zum Textfeld hinzu, um Eingaben zu verarbeiten
        guessTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Holt den Text aus dem Textfeld
                String input = guessTF.getText();
                // Überprüft, ob die Eingabe ein einzelner Buchstabe ist
                if (input.length() == 1) {
                    // Verarbeitet den Benutzereingabe-Buchstaben
                    gameLogic.processGuess(input.charAt(0));
                    // Aktualisiert das Panel
                    updatePanel();
                }
                // Setzt das Textfeld zurück
                guessTF.setText("");
            }
        });

        // Fügt einen ActionListener zum Bestätigungs-Button hinzu, um Eingaben zu verarbeiten
        confirmBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Holt den Text aus dem Textfeld
                String input = guessTF.getText();
                // Überprüft, ob die Eingabe ein einzelner Buchstabe ist
                if (input.length() == 1) {
                    // Verarbeitet den Benutzereingabe-Buchstaben
                    gameLogic.processGuess(input.charAt(0));
                }
                // Setzt das Textfeld zurück
                guessTF.setText("");
            }
        });

        // Fügt das Hauptpanel zum Frame hinzu
        add(panel1, BorderLayout.CENTER);

        // Macht das Fenster sichtbar
        setVisible(true);
    }

    public void updateGuessedLetters(String guessedLetters) {
        versuchteBuchstabenL.setText("Guessed Letters: " + guessedLetters);
    }

    // Methode zur Aktualisierung des Panels
    public void updatePanel() {
        // Setzt den Text des Versuche-Labels
        triesL.setText("Tries: " + gameLogic.getFails());
        // Setzt den Text des Maximalversuche-Labels
        maxFailsL.setText("Max Tries: " + gameLogic.getMaxFails());
        // Setzt den Text des Fortschritts-Labels
        progressL.setText(gameLogic.getMaskedWord());
        // Holt den Pfad des aktuellen Bildes
        String imagePath = gameLogic.updateImage();
        // Erstellt ein neues ImageIcon mit dem Bildpfad
        ImageIcon icon = new ImageIcon(imagePath);
        // Setzt das Bild-Label auf das neue Bild
        pictureL.setIcon(icon);
    }

    // Hauptmethode zum Starten der Anwendung
    public static void main(String[] args) {
        // Erstellt eine neue Instanz der HangMan-Klasse
        new HangMan();
    }
}