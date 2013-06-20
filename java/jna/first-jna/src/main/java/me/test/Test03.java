package me.test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinBase.SECURITY_ATTRIBUTES;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX;

// 打开计算器并发送消息
public class Test03 {

    public static void main(String[] args) {
        System.out.println("============================= CASE 3");

        // 打开计算器
        Kernel32 kernel32 = Kernel32.INSTANCE;
        SECURITY_ATTRIBUTES procSecAttr = new SECURITY_ATTRIBUTES();
        SECURITY_ATTRIBUTES threadSecAttr = new SECURITY_ATTRIBUTES();
        WinBase.PROCESS_INFORMATION.ByReference byRef = new WinBase.PROCESS_INFORMATION.ByReference();
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        startupInfo = new WinBase.STARTUPINFO();
        boolean success = kernel32.CreateProcess(null, "calc.exe", procSecAttr,
                threadSecAttr, false, new DWORD(0x00000010), null, null,
                startupInfo, byRef);
        if (!success) {
            System.out.println("计算器打开失败");
            System.out.println();
            return;
        }

        // 找到计算器的窗口
        // PS：仅仅是为了学习。如果程序是自己打开的，应该使用 startupInfo。
        User32 user32 = User32.INSTANCE;
        int tryTimes = 0;
        boolean found = false;
        HWND h1 = null;
        while (!found && tryTimes < 3) {
            h1 = user32.FindWindow(null, "计算器");
            if (h1 == null) {
                tryTimes++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                found = true;
            }
        }
        System.out.println("tryTimes = " + tryTimes);
        if (h1 == null) {
            System.out.println("找不到计算器");
            System.out.println();
            return;
        }

        // 使计算器窗口置前
        user32.SetForegroundWindow(h1);

        // 找到输入框并设置文本(XP OK，Win7 的计算器已经改变)
        OSVERSIONINFOEX osVerInfo = new OSVERSIONINFOEX();
        boolean setText = kernel32.GetVersionEx(osVerInfo);
        if (setText) {
            int major = osVerInfo.dwMajorVersion.intValue();
            int minor = osVerInfo.dwMinorVersion.intValue();
            if (!(major == 5 && minor == 0) // Windows 2000
                    && !(major == 5 && minor == 1) // Windows XP
                    && !(major == 5 && minor == 1) // Windows 2003 (R2)
            ) {
                setText = false;
            }
        }
        if (setText) {
            MyUser32 myUser32 = MyUser32.INSTANCE;
            HWND h2 = myUser32.FindWindowEx(h1, null, "Edit", null); //
            final int WM_SETTEXT = 0xC;
            myUser32.SendMessage(h2, WM_SETTEXT, new WPARAM(0), "123ABC中文");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 模拟 Esc 按键的 down 与 up，清除设置的不合法输入
        for (int i = 0; i < 3; i++) {
            //final int VK_ESCAPE = 0x1B;
            WPARAM wParam = new WPARAM(8);
            int repeatCount = 1;
            int scanCode = 0;
            int isExtendedKey = 0;
            int contextCode = 0;
            int preKeyState = 0;
            int transitionState = 0;
            LPARAM lParam = new LPARAM(repeatCount | scanCode | isExtendedKey
                    | contextCode | contextCode | preKeyState | transitionState);
            user32.PostMessage(h1, User32.WM_KEYDOWN, wParam, lParam);

            repeatCount = 1;
            scanCode = 0;
            isExtendedKey = 0;
            contextCode = 0;
            preKeyState = 1 << 31;
            transitionState = 1 << 31;
            user32.PostMessage(h1, User32.WM_KEYUP, wParam, lParam);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            System.out.println("文字应当已经被清除，请确认。");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 调用 PostMessage, 计算 1+2+3+4+5+6+7+8+9=
        // FIXME : 区别 SendInput SendMessage PostMessage
        for (int i = 0; i < 10; i++) {
            user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('0' + i),
                    new LPARAM(0));
            if (i < 9) {
                user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('+'),
                        new LPARAM(0));
            } else {
                user32.PostMessage(h1, User32.WM_CHAR, new WPARAM('='),
                        new LPARAM(0));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
