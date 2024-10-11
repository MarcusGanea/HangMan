import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class t_game {
    // Deklaration der Spielvariablen
    private String word;
    private int fails;
    private int maxFails;
    private StringBuilder maskedWord;
    private StringBuilder history;
    private ArrayList<String> words;
    private String difficulty;
    private HangMan hangMan;

    // Konstruktor der t_game-Klasse
    // Initialize the words list in the constructor
    public t_game(HangMan hangMan) {
        this.hangMan = hangMan;
        this.words = new ArrayList<>(); // Initialize the words list
        loadWordsFromFile("src/words.txt");
        maxFails = 5; // Standardwert
        difficulty = "normal"; // Standard-Schwierigkeitsgrad
        startNewGame();
    }

    // Methode zum Starten eines neuen Spiels
    public void startNewGame() {
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No words available to start the game.");
            return;
        }
        word = words.get((int) (Math.random() * words.size())).toUpperCase();
        fails = 0;
        maskedWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            maskedWord.append("_ ");
        }
        history = new StringBuilder();
    }
    // Method to load words from a file
    private void loadWordsFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine().toUpperCase());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Word file not found: " + fileName);
        }
    }

    // Setzt die maximale Anzahl an Fehlversuchen
    public void setMaxFails(int maxFails) {
        this.maxFails = maxFails;
    }

    // Gibt die maximale Anzahl an Fehlversuchen zurück
    public int getMaxFails() {
        return maxFails;
    }

    // Setzt den Schwierigkeitsgrad
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Gibt den Schwierigkeitsgrad zurück
    public String getDifficulty() {
        return difficulty;
    }

    // Verarbeitet den geratenen Buchstaben
    public void processGuess(char guess) {
        // Konvertiert den Buchstaben in Großbuchstaben
        guess = Character.toUpperCase(guess);
        // Überprüft, ob der Buchstabe bereits geraten wurde
        if (history.toString().contains(String.valueOf(guess))) {
            return;
        }

        // Fügt den Buchstaben zur Historie hinzu
        history.append(guess).append(" ");
        // Überprüft, ob der Buchstabe im Wort enthalten ist
        if (word.indexOf(guess) >= 0) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    // Setzt den Buchstaben an die richtige Stelle im maskierten Wort
                    maskedWord.setCharAt(i * 2, guess); // Index für Leerzeichen anpassen
                }
            }
        } else {
            // Erhöht die Anzahl der Fehlversuche
            fails++;
        }
        // Aktualisiert das Panel
        hangMan.updatePanel();

        // Überprüft, ob das Spiel verloren wurde
        if (fails >= maxFails) {
            // Zeigt das letzte Bild für eine kurze Zeit, bevor das Spiel neu gestartet wird

            JOptionPane.showMessageDialog(null, "Game Over! The word was: " + word);
            startNewGame();
            hangMan.updatePanel(); // Ensure the panel is updated after starting a new game
        } else if (maskedWord.toString().replace(" ", "").equals(word)) {
            // Überprüft, ob das Wort vollständig geraten wurde
            JOptionPane.showMessageDialog(null, "Congratulations! You've guessed the word: " + word);
            startNewGame();
            hangMan.updatePanel(); // Ensure the panel is updated after starting a new game
        }
        // Aktualisiert das Label für die geratenen Buchstaben
        hangMan.updateGuessedLetters(history.toString());
    }

    // Gibt das maskierte Wort zurück
    public String getMaskedWord() {
        return maskedWord.toString();
    }

    // Gibt die Historie der geratenen Buchstaben zurück
    public String getHistory() {
        return history.toString();
    }

    // Gibt die Anzahl der Fehlversuche zurück
    public int getFails() {
        return fails;
    }

    // Aktualisiert das Bild basierend auf der Anzahl der Fehlversuche und dem Schwierigkeitsgrad
    public String updateImage() {
        // Standardbildpfad
        String imagePath = "src/images/00.png";
        // Setzt den Bildpfad basierend auf dem Schwierigkeitsgrad und der Anzahl der Fehlversuche
        switch (difficulty) {
            case "easy":
                imagePath = "src/images/" + fails + "0.png";
                break;
            case "normal":
                if (fails == 1) imagePath = "src/images/30.png";
                else if (fails == 2) imagePath = "src/images/40.png";
                else if (fails == 3) imagePath = "src/images/60.png";
                else if (fails == 4) imagePath = "src/images/70.png";
                else if (fails == 5) imagePath = "src/images/90.png";
                break;
            case "hard":
                if (fails == 1) imagePath = "src/images/40.png";
                else if (fails == 2) imagePath = "src/images/70.png";
                else if (fails == 3) imagePath = "src/images/90.png";
                break;
        }
        // Gibt den Bildpfad zurück
        return imagePath;
    }
}