package me.test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;

// 全局键盘钩子
public class Test04 {

    public static HHOOK hHook;
    public static HOOKPROC lpfn;
    public static volatile boolean quit = false;

    public static void main(String[] args) {

        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        lpfn = new LowLevelKeyboardProc() {
            int count = 1;

            public LRESULT callback(int nCode, WPARAM wParam,
                    KBDLLHOOKSTRUCT keyInfo) {
                System.out.println("nCode =" + nCode + ", wParam =" + wParam
                        + ", vkCode=" + keyInfo.vkCode);
                count++;
                if (count > 100) {
                    quit = true;
                }
                return User32.INSTANCE.CallNextHookEx(hHook, nCode, wParam,
                        keyInfo.getPointer());
            }
        };

        // 如果是全局钩子，则线程ID为0
        int threadId = 0;
        // int threadId =
        // user32.GetWindowThreadProcessId(user32.FindWindow(null, "无标题 - 记事本"),
        // null);
        System.out.println("threadId = "
                + Integer.toHexString(threadId).toUpperCase());
        int idHook = User32.WH_KEYBOARD_LL;
        hHook = User32.INSTANCE.SetWindowsHookEx(idHook, lpfn, hMod, threadId);

        if (hHook == null) {
            System.out.println("键盘钩子安装失败，error = "
                    + Kernel32.INSTANCE.GetLastError());
            return;
        }
        User32.MSG msg = new User32.MSG();
        while (!quit) {
            User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (User32.INSTANCE.UnhookWindowsHookEx(hHook)) {
            System.out.println("Unhooked");
        }
    }
}
