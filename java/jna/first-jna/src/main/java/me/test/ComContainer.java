package me.test;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
import org.eclipse.swt.widgets.Text;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;

/**
 * An container for ActiveX/COM components with windows UI. <br/>
 * For those without UI, U can also consider using Jacob etc. <br/>
 */
public class ComContainer extends Thread {

    private Display display = null;

    // private Shell shell = null;

    private OleClientSite clientSite = null;

    private OleAutomation ole = null;

    private boolean stop = false;

    private String progId = null;

    private boolean ready = false;

    // ----------------------------------------
    private int passwordMaxLen = 6;

    private String passwordChars = "1234567890aA";

    private String publicKey = "BF6C2C496593917FEEDFE0F6C62BA237C32A99886D66CC3D20DBAEB38484D001C86EE38576C6A92CA3C94C03B1AD284A0F85498D3DEB9134DFC57BABE8271401";

    private String JsOnKey = "JsOnKey";

    private Properties initProps;

    /**
     * @param clsId
     *            e.g. "Word.Document",
     *            "{11111111-2222-3333-4444-555555555555}"
     */
    public ComContainer(String progId) {
        this.progId = progId;
        ready = false;
        stop = false;
    }

    public Text t;

    public OleFrame frame;

    private BlockingQueue<String> queue = new LinkedBlockingDeque<String>(1);

    public BlockingQueue<String> resultQueue = new LinkedBlockingDeque<String>(
            1);

    protected void init() {
        // 设置密码最大长度
        int[] rgdispid = ole.getIDsOfNames(new String[]{"PasswordMaxLen"});
        Variant var = ole.getProperty(rgdispid[0]);
        System.out.println(var.getInt());
        ole.setProperty(rgdispid[0], new Variant(passwordMaxLen));

        // 设置允许的密码字符
        rgdispid = ole.getIDsOfNames(new String[]{"PasswordChars"});
        ole.setProperty(rgdispid[0], new Variant(passwordChars));

        // 设置公钥
        rgdispid = ole.getIDsOfNames(new String[]{"PublicKey"});
        ole.setProperty(rgdispid[0], new Variant(publicKey));

        rgdispid = ole.getIDsOfNames(new String[]{"JsOnKey"});
        ole.setProperty(rgdispid[0], new Variant(JsOnKey));

        // int[] rgdispid = null;
        if (initProps != null) {
            for (Entry<Object, Object> entry : initProps.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                rgdispid = ole.getIDsOfNames(new String[]{key});
                ole.setProperty(rgdispid[0], new Variant(value));
            }
        }
    }

    public String enc(String password) {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        User32 user32 = User32.INSTANCE;
        MyUser32 myUser32 = MyUser32.INSTANCE;
        kernel32.SetLastError(0);
        final int MAPVK_VK_TO_VSC = 0;

        // find the window handle of OleFrame
        HWND h = new HWND(new Pointer(clientSite.handle));

        // find the window handle of the ActiveX
        h = user32.GetWindow(h, new DWORD(User32.GW_CHILD));

        // find the window handle of the password input control
        h = user32.GetWindow(h, new DWORD(User32.GW_CHILD));

        OleAutomation ole = new OleAutomation(clientSite);

        init();

        // 清空密码
        t.setFocus();
        frame.setFocus();

        // send the new password string
        for (char c : password.toCharArray()) {
            int errorNo = 0;
            int tryTimes = 0;
            kernel32.SetLastError(0);
            do {
                LParamUnion lUnion = LParamUnion.getDefaultVmChar();
                lUnion.setScanCode((byte) myUser32.MapVirtualKey(VK.VK_A,
                        MAPVK_VK_TO_VSC));
                MyUser32.INSTANCE.SendMessage(h, User32.WM_CHAR, new WPARAM(c),
                        new LPARAM(lUnion.toInt()));
                tryTimes++;
                errorNo = kernel32.GetLastError();
            } while (errorNo != 0 && tryTimes < 3);
            if (errorNo != 0) {
                throw new RuntimeException("Could not complete the "
                        + "password encoding. error code = " + errorNo);
            }
        }

        // get the encrypted password
        t.setFocus();
        int[] rgdispid = ole.getIDsOfNames(new String[]{"Password"});
        Variant var = ole.getProperty(rgdispid[0]);
        System.out.println("ddddddddddddd" + var);
        if (OLE.VT_NULL == var.getType()) {
            return null;
        } else if (OLE.VT_EMPTY == var.getType()) {
            return "";
        }
        return var.getString();
    }

    public String encPp(String p) {
        try {
            queue.put(p);
            return resultQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public void run() {
        ready = false;
        stop = false;
        display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        display.asyncExec(new Runnable() {

            public void run() {
                while (!stop) {

                    try {
                        String password = queue.take();
                        String encd = enc(password);
                        // t.setFocus();
                        // frame.setFocus();
                        resultQueue.put(encd);
                    } catch (InterruptedException e) {
                        break;
                    }

                }

            }

        });

        try {
            frame = new OleFrame(shell, SWT.NONE);
            clientSite = new OleClientSite(frame, SWT.NONE, progId);
            clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
            ole = new OleAutomation(clientSite);

            addFileMenu(frame); // TODO delete

        } catch (SWTError e) {
            e.printStackTrace();
            display.dispose();
            throw e;
        }

        t = new Text(shell, SWT.BORDER | SWT.MULTI);
        t.forceFocus();

        stop = false;
        ready = true;
        shell.setSize(400, 300);

        shell.open(); // You can uncomment this for an direct view

        // start to dispatch event
        while (!stop && !shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }

    public void notifyStop() {
        stop = true;
    }

    public OleClientSite getOleClientSite() {
        return clientSite;
    }

    public boolean isReady() {
        return ready;
    }

    void addFileMenu(final OleFrame frame) {
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
            @Override
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
            @Override
            public void widgetSelected(SelectionEvent e) {
                OleAutomation ole = new OleAutomation(clientSite);
                int[] rgdispid = ole.getIDsOfNames(new String[]{"Password"});
                // rgdispid = ole.getIDsOfNames(new String[] { "PublicKey" });

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
                System.out.println(messageBox.getMessage());
                // messageBox.open();
            }
        });

        MenuItem menuDelete = new MenuItem(menuFile, SWT.CASCADE);
        menuDelete.setText("Delete last input char");
        menuDelete.addSelectionListener(new SelectionAdapter() {
            @Override
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
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
    }

}
