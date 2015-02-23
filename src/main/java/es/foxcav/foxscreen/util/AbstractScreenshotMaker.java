package es.foxcav.foxscreen.util;

import es.foxcav.foxscreen.FormScreenshotSelect;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractScreenshotMaker {
    public Rectangle getScreenBounds() {
        Rectangle virtualBounds = new Rectangle();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for(GraphicsDevice gd : ge.getScreenDevices())
            for(GraphicsConfiguration gc : gd.getConfigurations())
                virtualBounds = virtualBounds.union(gc.getBounds());
        return virtualBounds;
    }

    public BufferedImage captureAllScreens() throws Exception {
        return captureRectangle(getScreenBounds());
    }

    public BufferedImage captureCurrentWindow() throws Exception {
        return captureRectangle(getCurrentWindowRect());
    }

    public abstract Rectangle getCurrentWindowRect() throws Exception;

    public BufferedImage captureRectangle(Rectangle rect) throws Exception {
        return new Robot().createScreenCapture(rect);
    }

    public BufferedImage captureSelection() throws Exception {
        FormScreenshotSelect form = new FormScreenshotSelect(this);
        form.setVisible(true);
        return null;
    }
}
