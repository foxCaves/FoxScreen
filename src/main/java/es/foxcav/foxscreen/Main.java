package es.foxcav.foxscreen;

import es.foxcav.foxscreen.util.AbstractScreenshotMaker;
import es.foxcav.foxscreen.util.BasicScreenshotMaker;
import es.foxcav.foxscreen.util.Win32ScreenshotMaker;

public class Main {
    public static final AbstractScreenshotMaker screenshotMaker;

    static {
        String osName = System.getProperty("os.name");
        if(osName.startsWith("Windows"))
            screenshotMaker = new Win32ScreenshotMaker();
        else
            screenshotMaker = new BasicScreenshotMaker();
    }

    public static void main(String[] args) throws Exception {
        screenshotMaker.captureSelection();
    }
}
