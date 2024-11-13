package Penghitungkata;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PenghitungTeks extends JFrame {
    private JTextArea textArea;
    private JLabel lblWordCount, lblCharCount, lblSentenceCount, lblParagraphCount;
    private JTextField txtSearch;
    private JButton btnCount, btnSave;

    public PenghitungTeks() {
        setTitle("Penghitung Teks");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Text area with scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel for results and controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 1));

        lblWordCount = new JLabel("Jumlah Kata: 0");
        lblCharCount = new JLabel("Jumlah Karakter: 0");
        lblSentenceCount = new JLabel("Jumlah Kalimat: 0");
        lblParagraphCount = new JLabel("Jumlah Paragraf: 0");

        controlPanel.add(lblWordCount);
        controlPanel.add(lblCharCount);
        controlPanel.add(lblSentenceCount);
        controlPanel.add(lblParagraphCount);

        // Search field and buttons
        txtSearch = new JTextField();
        btnCount = new JButton("Hitung");
        btnSave = new JButton("Simpan");

        controlPanel.add(txtSearch);
        controlPanel.add(btnCount);
        controlPanel.add(btnSave);

        panel.add(controlPanel, BorderLayout.SOUTH);
        add(panel);

        // ActionListener for the Count button
        btnCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countText();
            }
        });

        // DocumentListener for real-time counting
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                countText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                countText();
            }
        });

        // ActionListener for the Save button
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        setVisible(true);
    }

    private void countText() {
        String text = textArea.getText();
        int wordCount = text.isEmpty() ? 0 : text.trim().split("\\s+").length;
        int charCount = text.length();
        int sentenceCount = text.isEmpty() ? 0 : text.split("[.!?]").length;
        int paragraphCount = text.isEmpty() ? 0 : text.split("\\n").length;

        lblWordCount.setText("Jumlah Kata: " + wordCount);
        lblCharCount.setText("Jumlah Karakter: " + charCount);
        lblSentenceCount.setText("Jumlah Kalimat: " + sentenceCount);
        lblParagraphCount.setText("Jumlah Paragraf: " + paragraphCount);
    }

    private void saveToFile() {
        String text = textArea.getText();
        String results = String.format("Jumlah Kata: %s\nJumlah Karakter: %s\nJumlah Kalimat: %s\nJumlah Paragraf: %s\n",
                lblWordCount.getText(), lblCharCount.getText(), lblSentenceCount.getText(), lblParagraphCount.getText());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("text_analysis.txt"))) {
            writer.write("Teks:\n" + text + "\n\n");
            writer.write("Hasil Perhitungan:\n" + results);
            JOptionPane.showMessageDialog(this, "Teks dan hasil perhitungan disimpan ke text_analysis.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saat menyimpan file: " + e.getMessage());
        }
    }

    public static void main(String[] args){ 
            new PenghitungTeks();}
}