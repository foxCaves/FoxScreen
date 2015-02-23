package es.foxcav.foxscreen.util;

import java.awt.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class Win32ScreenshotMaker extends AbstractScreenshotMaker {
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
        WinDef.HWND GetForegroundWindow();
        int GetWindowRect(WinDef.HWND handle, int[] rect);
    }

    @Override
    public Rectangle getCurrentWindowRect() throws Exception {
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        if (hwnd == null) {
            throw new Exception("Internal error: No hWND");
        }

        int[] rect = {0, 0, 0, 0};
        int result = User32.INSTANCE.GetWindowRect(hwnd, rect);
        if (result == 0) {
            throw new Exception("Internal error: No Rect");
        }

        return new Rectangle(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
    }
}
