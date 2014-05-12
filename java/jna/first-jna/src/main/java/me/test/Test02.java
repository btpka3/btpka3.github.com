package me.test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinBase.SECURITY_ATTRIBUTES;
import com.sun.jna.platform.win32.WinDef.DWORD;

// 打开浏览器
public class Test02 {

    public static void main(String[] args) {
        System.out.println("============================= CASE 2");

        // 打开 IE 浏览器
        // http://msdn.microsoft.com/en-us/library/windows/desktop/ms682425%28v=vs.85%29.aspx
        Kernel32 kernel32 = Kernel32.INSTANCE;
        SECURITY_ATTRIBUTES procSecAttr = new SECURITY_ATTRIBUTES();
        SECURITY_ATTRIBUTES threadSecAttr = new SECURITY_ATTRIBUTES();
        WinBase.PROCESS_INFORMATION.ByReference pi = new WinBase.PROCESS_INFORMATION.ByReference();
        WinBase.STARTUPINFO startupInfo = new WinBase.STARTUPINFO();
        boolean success = kernel32.CreateProcess(null,
                "explorer.exe http://news.163.com", procSecAttr, threadSecAttr,
                false, new DWORD(0x00000010), null, null, startupInfo, pi);

        if (!success) {
            System.out.println("打开IE浏览器失败");
        } else {
            System.out.println("打开IE浏览器成功");
        }

        kernel32.CloseHandle(pi.hProcess);
        kernel32.CloseHandle(pi.hThread);

        // 将使用默认浏览器打开（我这里是火狐浏览器）
        Shell32.INSTANCE.ShellExecute(null, "open", "http://news.baidu.com",
                null, null, 9);

        System.out.println();
    }

}
