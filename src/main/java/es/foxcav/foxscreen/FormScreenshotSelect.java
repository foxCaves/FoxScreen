package es.foxcav.foxscreen;

import es.foxcav.foxscreen.util.AbstractScreenshotMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class FormScreenshotSelect extends JFrame {
    private final BufferedImage image;
    private Rectangle selectorRect = null;

    private final Color SELECTOR_RECT_COLOR = Color.RED;
    private final Color SELECTOR_OUTSIDE_COLOR = new Color(64, 64, 64, 64);

    public FormScreenshotSelect(AbstractScreenshotMaker screenshotMaker) throws Exception {
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Rectangle screenBounds = screenshotMaker.getScreenBounds();

        image = screenshotMaker.captureRectangle(screenBounds);

        add(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(image, 0, 0, null);

                if(selectorRect != null) {
                    int width = getWidth();
                    int height = getHeight();
                    int y2 = selectorRect.y + selectorRect.height;
                    int x2 = selectorRect.x + selectorRect.width;

                    g.setColor(SELECTOR_RECT_COLOR);
                    g.drawRect(selectorRect.x, selectorRect.y, selectorRect.width, selectorRect.height);

                    g.setColor(SELECTOR_OUTSIDE_COLOR);
                    g.fillRect(0, 0, width, selectorRect.y);
                    g.fillRect(0, y2, width, height - y2);
                    g.fillRect(0, selectorRect.y, selectorRect.x, selectorRect.height);
                    g.fillRect(x2, selectorRect.y, width - x2, selectorRect.height);
                }
            }
        });

        pack();

        setBounds(screenBounds);

        MouseAdapter mouseListener = new MouseAdapter() {
            private int startX, startY;
            private boolean mousePressed = false;

            private void refreshPosition(MouseEvent e) {
                int endX = e.getX();
                int endY = e.getY();

                int x1 = Math.min(startX, endX);
                int x2 = Math.max(startX, endX);
                int y1 = Math.min(startY, endY);
                int y2 = Math.max(startY, endY);

                selectorRect = new Rectangle(x1, y1, x2 - x1, y2 - y1);

                FormScreenshotSelect.this.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    mousePressed = true;
                    startX = e.getX();
                    startY = e.getY();
                    refreshPosition(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if (mousePressed) {
                        refreshPosition(e);
                        shoot();
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if(mousePressed)
                    refreshPosition(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(mousePressed)
                    refreshPosition(e);
            }
        };
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    cancel();
            }
        });
    }

    private void shoot() {
        close();
    }

    private void cancel() {
        close();
    }

    private void close() {
        setVisible(false);
        dispose();
    }
}
