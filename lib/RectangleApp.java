import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RectangleApp {

    public static void main(String[] args) {
        // Create the first frame
        JFrame introFrame = new JFrame("Introduction");
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setSize(600, 400);

        // Add introductory message
        JLabel introLabel = new JLabel("Welcome to the Flag Creator App!", SwingConstants.CENTER);
        introLabel.setFont(new Font("Arial", Font.BOLD, 16));
        introFrame.add(introLabel, BorderLayout.CENTER);

        // Add button to proceed to the next frame
        JButton proceedButton = new JButton("Start");
        introFrame.add(proceedButton, BorderLayout.SOUTH);
        introFrame.setVisible(true);

        proceedButton.addActionListener(e -> {
            introFrame.dispose(); // Close the first frame
            showMainFrame(); // Open the main frame
        });
    }

    private static void showMainFrame() {
        JFrame mainFrame = new JFrame("Flag Creator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 800);

        // Create panels
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 800));
        CustomPanel rightPanel = new CustomPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Dropdown for partition selection
        String[] partitionOptions = {"Vertical", "Horizontal", "Three-tiered Vertical", "Three-tiered Horizontal"};
        JComboBox<String> partitionDropdown = new JComboBox<>(partitionOptions); 
        leftPanel.add(new JLabel("Select Partition Type:"));
        leftPanel.add(partitionDropdown);

        // Buttons in the left panel
        JButton firstColorButton = new JButton("Color 1");
        JButton secondColorButton = new JButton("Color 2");
        JButton thirdColorButton = new JButton("Color 3");
        JButton fourthColorButton = new JButton("Color 4");
        JButton saveButton = new JButton("Save Flag");

        JLabel labelRed = new JLabel("Enter an integer (0-255) for the red color:");
        JTextField redField = new JTextField();
        JLabel labelGreen = new JLabel("Enter an integer (0-255) for the green color:");
        JTextField greenField = new JTextField();
        JLabel labelBlue = new JLabel("Enter an integer (0-255) for the blue color:");
        JTextField blueField = new JTextField();

        leftPanel.add(labelRed);
        leftPanel.add(redField);
        leftPanel.add(labelGreen);
        leftPanel.add(greenField);
        leftPanel.add(labelBlue);
        leftPanel.add(blueField);
        leftPanel.add(firstColorButton);
        leftPanel.add(secondColorButton);
        leftPanel.add(thirdColorButton);
        leftPanel.add(fourthColorButton);
        leftPanel.add(saveButton);

        // Add action listeners to buttons
        firstColorButton.addActionListener(e -> {
            try {
                int red = validateColorValue(redField, mainFrame, "Red");
                int green = validateColorValue(greenField, mainFrame, "Green");
                int blue = validateColorValue(blueField, mainFrame, "Blue");

                rightPanel.setFirstColor(red, green, blue);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter valid integers for all colors.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        secondColorButton.addActionListener(e -> {
            try {
                int red = validateColorValue(redField, mainFrame, "Red");
                int green = validateColorValue(greenField, mainFrame, "Green");
                int blue = validateColorValue(blueField, mainFrame, "Blue");

                rightPanel.setSecondColor(red, green, blue);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter valid integers for all colors.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        thirdColorButton.addActionListener(e -> {
            try {
                int red = validateColorValue(redField, mainFrame, "Red");
                int green = validateColorValue(greenField, mainFrame, "Green");
                int blue = validateColorValue(blueField, mainFrame, "Blue");

                rightPanel.setThirdColor(red, green, blue);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter valid integers for all colors.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        fourthColorButton.addActionListener(e -> {
            try {
                int red = validateColorValue(redField, mainFrame, "Red");
                int green = validateColorValue(greenField, mainFrame, "Green");
                int blue = validateColorValue(blueField, mainFrame, "Blue");

                rightPanel.setFourthColor(red, green, blue);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter valid integers for all colors.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            BufferedImage image = new BufferedImage(rightPanel.getWidth(), rightPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            rightPanel.paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "jpg", new File("flag.jpg"));
                JOptionPane.showMessageDialog(mainFrame, "Flag saved as flag.jpg");
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(mainFrame, "Error saving image: " + ioException.getMessage());
            }
        });

        // Add partition selection listener
        partitionDropdown.addActionListener(e -> {
            String selected = (String) partitionDropdown.getSelectedItem();
            switch (selected) {
                case "Horizontal" -> rightPanel.setPartitionType("Horizontal");
                case "Vertical" -> rightPanel.setPartitionType("Vertical");
                case "Three-tiered Vertical" -> rightPanel.setPartitionType("Three-tiered Vertical");
                case "Three-tiered Horizontal" -> rightPanel.setPartitionType("Three-tiered Horizontal");
                default -> throw new AssertionError();
            }
            
        });

        // Layout setup
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private static int validateColorValue(JTextField textField, JFrame frame, String colorName) {
        String text = textField.getText();
        int value = Integer.parseInt(text);

        if (value < 0) {
            JOptionPane.showMessageDialog(frame, colorName + " value is below 0. Setting to 0.", "Warning", JOptionPane.WARNING_MESSAGE);
            value = 0;
            textField.setText("0");
        } else if (value > 255) {
            JOptionPane.showMessageDialog(frame, colorName + " value is above 255. Setting to 255.", "Warning", JOptionPane.WARNING_MESSAGE);
            value = 255;
            textField.setText("255");
        }

        return value;
    }


    static class CustomPanel extends JPanel {
        private Color color1 = Color.RED;
        private Color color2 = Color.YELLOW;
        private Color color3 = Color.GREEN;
        private Color color4 = Color.BLUE;
        private String partitionType = "Vertical";

        public void setFirstColor(int R, int G, int B) {
            this.color1 = new Color(R,G,B);
            repaint();
        }

        public void setSecondColor(int R, int G, int B) {
            this.color2 = new Color(R,G,B);
            repaint();
        }

        public void setThirdColor(int R, int G, int B) {
            this.color3 = new Color(R,G,B);
            repaint();
        }

        public void setFourthColor(int R, int G, int B) {
            this.color4 = new Color(R,G,B);
            repaint();
        }


        public void setPartitionType(String partitionType) {
            this.partitionType = partitionType;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (null == partitionType) {
                g.setColor(color1);
                g.fillRect(0, 0, getWidth() / 3, getHeight());
                g.setColor(color2);
                g.fillRect(getWidth() / 3, 0, getWidth() / 3, getHeight());
                g.setColor(color3);
                g.fillRect(2 * getWidth() / 3, 0, getWidth() / 3, getHeight());
            } else switch (partitionType) {
                case "Horizontal":
                    g.setColor(color1);
                    g.fillRect(0, 0, getWidth(), getHeight() / 2);
                    g.setColor(color2);
                    g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
                    break;
                case "Vertical":
                    g.setColor(color3);
                    g.fillRect(0, 0, getWidth() / 2, getHeight());
                    g.setColor(color4);
                    g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
                    break;
                case "Three-tiered Vertical":
                    g.setColor(color1);
                    g.fillRect(0, 0, getWidth() / 3, getHeight());
                    g.setColor(color2);
                    g.fillRect(getWidth() / 3, 0, getWidth() / 3, getHeight());
                    g.setColor(color3);
                    g.fillRect(2 * getWidth() / 3, 0, getWidth() / 3, getHeight());
                    break;
                default:
                    g.setColor(color2);
                    g.fillRect(0, 0, getWidth(), getHeight() / 3);
                    g.setColor(color3);
                    g.fillRect(0, getHeight() / 3, getWidth(), getHeight() / 3);
                    g.setColor(color4);
                    g.fillRect(0, 2 * getHeight() / 3, getWidth(), getHeight() / 3);
                    break;
            }
        }
    }
}
