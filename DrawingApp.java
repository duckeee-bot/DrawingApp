package com.mycompany.drawingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingApp extends JFrame {

    private JButton pencilButton;
    private JButton brushButton;
    private JButton eraserButton;
    private JComboBox<String> colorPicker;
    private JButton clearAllButton;
    private JPanel drawingPanel;
    private JPanel toolPanel; // Panel to hold the buttons
    private Color selectedColor;
    private String selectedTool;

    public DrawingApp() {
        super("Drawing App");

        // Initialize UI components
        pencilButton = new JButton("Pencil");
        brushButton = new JButton("Brush");
        eraserButton = new JButton("Eraser");
        colorPicker = new JComboBox<>(new String[]{"Black", "Blue", "Red", "Green"});
        clearAllButton = new JButton("Clear All");
        drawingPanel = new JPanel();
        toolPanel = new JPanel();

        // Set layout manager for tool panel
        toolPanel.setLayout(new FlowLayout());

        // Add action listeners
        pencilButton.addActionListener(new ToolButtonListener());
        brushButton.addActionListener(new ToolButtonListener());
        eraserButton.addActionListener(new ToolButtonListener());
        colorPicker.addActionListener(new ColorPickerListener());
        clearAllButton.addActionListener(new ClearAllButtonListener());

        // Add mouse listener
        drawingPanel.addMouseListener(new DrawingMouseListener());
        drawingPanel.addMouseMotionListener(new DrawingMouseListener());

        // Add buttons to tool panel
        toolPanel.add(pencilButton);
        toolPanel.add(brushButton);
        toolPanel.add(eraserButton);
        toolPanel.add(colorPicker);
        toolPanel.add(clearAllButton);

        // Set default values
        selectedColor = Color.BLACK;
        selectedTool = "Pencil";

        // Set layout manager for the drawing panel
        drawingPanel.setLayout(new BorderLayout());

        // Add tool panel and drawing panel to frame
        add(toolPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true); // Show the JFrame
    }

    private class DrawingMouseListener extends MouseAdapter {
        private int prevX, prevY;

        @Override
        public void mousePressed(MouseEvent e) {
            prevX = e.getX();
            prevY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Graphics g = drawingPanel.getGraphics();
            int x = e.getX();
            int y = e.getY();

            if (selectedTool.equals("Pencil")) {
                g.setColor(selectedColor);
                g.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            } else if (selectedTool.equals("Brush")) {
                g.setColor(selectedColor);
                g.fillOval(x, y, 10, 10);
            } else if (selectedTool.equals("Eraser")) {
                g.setColor(drawingPanel.getBackground());
                g.fillRect(x, y, 10, 10);
            }
        }
    }

    private class ToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            selectedTool = source.getText();
        }
    }

    private class ColorPickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String color = (String) colorPicker.getSelectedItem();
            switch (color) {
                case "Black":   
                    selectedColor = Color.BLACK;
                    break;
                case "Blue":
                    selectedColor = Color.BLUE;
                    break;
                case "Red":
                    selectedColor = Color.RED;
                    break;
                case "Green":
                    selectedColor = Color.GREEN;
                    break;
                // Add additional cases for more colors as needed
                default:
                    selectedColor = Color.BLACK;
                    break;
            }
        }
    }

    private class ClearAllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Graphics g = drawingPanel.getGraphics();
            g.setColor(drawingPanel.getBackground());
            g.fillRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingApp());
    }
}