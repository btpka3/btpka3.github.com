package me.test;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
//
public class Test05 {
    static OleClientSite clientSite;

    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);
        shell.setText("Test Password ActiveX Control");
        shell.setLayout(new FillLayout());
        try {
            OleFrame frame = new OleFrame(shell, SWT.NONE);
            clientSite = new OleClientSite(frame, SWT.NONE,
                    "{488A4255-3236-44B3-8F27-FA1AECAA8844}");
            clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
            addFileMenu(frame);

        } catch (SWTError e) {
            System.out.println("Unable to open activeX control");
            display.dispose();
            return;
        }
        shell.setSize(400, 300);

        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }

    static void addFileMenu(final OleFrame frame) {
        final Shell shell = frame.getShell();
        Menu menuBar = shell.getMenuBar();
        if (menuBar == null) {
            menuBar = new Menu(shell, SWT.BAR);
            shell.setMenuBar(menuBar);
        }
        MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
        fileMenu.setText("&Action");
        Menu menuFile = new Menu(fileMenu);
        fileMenu.setMenu(menuFile);
        frame.setFileMenus(new MenuItem[]{fileMenu});

        MenuItem menuHandle = new MenuItem(menuFile, SWT.CASCADE);
        menuHandle.setText("View Activex Container's window handle");
        menuHandle.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                MessageBox messageBox = new MessageBox(shell,
                        SWT.ICON_INFORMATION | SWT.OK);
                messageBox.setText("Info");

                // useful with SpyXX.exe
                messageBox.setMessage("handle = ["
                        + Long.toHexString(clientSite.handle).toUpperCase()
                        + "]");
                messageBox.open();
            }
        });

        MenuItem menuTextData = new MenuItem(menuFile, SWT.CASCADE);
        menuTextData.setText("View encrypted password");
        menuTextData.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                OleAutomation ole = new OleAutomation(clientSite);
                int[] rgdispid = ole.getIDsOfNames(new String[]{"TextData"});
                Variant var = ole.getProperty(rgdispid[0]);
                MessageBox messageBox = new MessageBox(shell,
                        SWT.ICON_INFORMATION | SWT.OK);
                messageBox.setText("Info");
                String str = null;
                if (OLE.VT_NULL == var.getType()) {
                    str = null;
                } else if (OLE.VT_EMPTY == var.getType()) {
                    str = "";
                } else {
                    str = var.getString();
                }
                messageBox.setMessage("encrypted password = [" + str + "]");
                messageBox.open();
            }
        });

        MenuItem menuDelete = new MenuItem(menuFile, SWT.CASCADE);
        menuDelete.setText("Delete last input char");
        menuDelete.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Kernel32 kernel32 = Kernel32.INSTANCE;
                kernel32.SetLastError(0);
                User32 user32 = User32.INSTANCE;

                // 找到包含密码框的java窗体的handle
                HWND handle = new HWND(new Pointer(clientSite.handle));

                // 找到ActiveX的handle
                handle = user32.GetWindow(handle, new DWORD(User32.GW_CHILD));

                // 找到ActiveX中密码输入框的handle
                handle = user32.GetWindow(handle, new DWORD(User32.GW_CHILD));

                // 发送消息
                final WPARAM CHAR_BS = new WPARAM(0x08);
                final int WM_CHAR = 0x0102;
                LPARAM l = new LPARAM(0);
                user32.PostMessage(handle, WM_CHAR, CHAR_BS, l);
                int lastError = kernel32.GetLastError();
                if (lastError != 0) {
                    System.out.println("last error = " + lastError);
                }
            }
        });

        MenuItem menuPostMessage = new MenuItem(menuFile, SWT.CASCADE);
        menuPostMessage.setText("Append a char '1'");
        menuPostMessage.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Kernel32 kernel32 = Kernel32.INSTANCE;
                kernel32.SetLastError(0);
                User32 user32 = User32.INSTANCE;

                // 找到包含密码框的java窗体的handle
                HWND handle = new HWND(new Pointer(clientSite.handle));

                // 找到ActiveX的handle
                handle = user32.GetWindow(handle, new DWORD(User32.GW_CHILD));

                // 找到ActiveX中密码输入框的handle
                handle = user32.GetWindow(handle, new DWORD(User32.GW_CHILD));

                // 发送消息
                final int WM_CHAR = 0x0102;
                WPARAM w = new WPARAM('1');
                LPARAM l = new LPARAM(0);
                user32.PostMessage(handle, WM_CHAR, w, l);
                int lastError = kernel32.GetLastError();
                if (lastError != 0) {
                    System.out.println("last error = " + lastError);
                }
            }
        });
    }
}
