package me.test;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface MyUser32 extends StdCallLibrary, WinUser {

    static MyUser32 INSTANCE = (MyUser32) Native.loadLibrary("user32",
            MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);

    HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter,
            String lpszClass, String lpszWindow);

    // 注意：最后一个参数的类型，该函数在Java里可以声明多个类型。
    LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);
    LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, String lParam);

    LPARAM GetMessageExtraInfo();

    int MapVirtualKey(int uCode, int uMapType);
}