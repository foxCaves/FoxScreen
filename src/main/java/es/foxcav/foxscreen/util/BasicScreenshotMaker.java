package es.foxcav.foxscreen.util;

import java.awt.*;

public class BasicScreenshotMaker extends AbstractScreenshotMaker {
    @Override
    public Rectangle getCurrentWindowRect() throws Exception {
        throw new Exception("Not implemented for this OS");
    }
}
