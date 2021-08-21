/*
 Author: Sachin Saigal
 Due Date: 6/1/21
 */
package cmsc405;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CMSC405project extends JPanel {
    private int frameNumber;
    private long elapsedTimeMillis;
    private float pixelSize;

    static int translateX = 0;
    static int translateY = 0;
    static double rotation = 0.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    ImageTemplate myImages = new ImageTemplate();
    BufferedImage tImage = myImages.getImage(ImageTemplate.letterT);
    BufferedImage lImage = myImages.getImage(ImageTemplate.letterL);
    BufferedImage dImage = myImages.getImage(ImageTemplate.letterD);
    /**
     * @param args 
     */
    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Java Animation");  // The parameter shows in the window title bar.
        final CMSC405project panel = new CMSC405project(); // The drawing area.
        window.setContentPane(panel); // Show the panel in the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack();  // Set window size based on the preferred sizes of its contents.
        window.setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( // Center window on screen.
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
        Timer animationTimer;  // A Timer that will emit events to drive the animation.
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(1600, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (panel.frameNumber > 3) {
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true); // Open the window, making it visible on the screen.
        animationTimer.start();  // Start the animation running.
    }

    public CMSC405project() {
        // Size of Frame
        setPreferredSize(new Dimension(800, 600));
    }

    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight()); // From the old graphics API!

        // Controls your zoom and area you are looking at
        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);
        switch (frameNumber) {
            case 1: // First frame is unmodified.
                 translateX = 0;
                 translateY = 0;
                 scaleX = 1.0;
                 scaleY = 1.0;
                 rotation = 0;
                break;
            case 2: // Second frame translates each image by (-9, 5).
                translateX = -5;
                translateY = 7;
                break;
            case 3: // Third frame rotates each image by 60 degrees Counter
                rotation = 45*Math.PI / 180.0;
                break;
            case 4: // Third frame rotates each image by 60 degrees Counter
                rotation = -45*Math.PI / 180.0;
                scaleX = 2.0;
                scaleY = 0.5;
                break;
           // Can add more cases as needed 
            default:
                break;
        } // End switch
        g2.translate(translateX, translateY); // Move image.
        g2.translate(-10,10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(tImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
       
        g2.translate(translateX, translateY); // Move image.
        g2.translate(-40,40);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(dImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);

        g2.translate(translateX, translateY); // Move image.
        g2.translate(30,30);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(lImage, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
       

    }

    // Method taken directly from AnimationStarter.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }

}
