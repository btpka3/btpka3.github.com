package me.test;
//http://msdn.microsoft.com/en-us/library/windows/desktop/dd375731(v=vs.85).aspx
public interface VK {
    int VK_LBUTTON = 0x01;

    int VK_RBUTTON = 0x02;

    int VK_CANCEL = 0x03;

    int VK_MBUTTON = 0x04;

    int VK_XBUTTON1 = 0x05;

    int VK_XBUTTON2 = 0x06;

    int VK_BACK = 0x08;

    int VK_TAB = 0x09;

    int VK_CLEAR = 0x0C;

    int VK_RETURN = 0x0D;

    int VK_SHIFT = 0x10;

    int VK_CONTROL = 0x11;

    int VK_MENU = 0x12;

    int VK_PAUSE = 0x13;

    int VK_CAPITAL = 0x14;

    int VK_KANA = 0x15;

    int VK_HANGUEL = 0x15;

    int VK_HANGUL = 0x15;

    int VK_JUNJA = 0x17;

    int VK_FINAL = 0x18;

    int VK_HANJA = 0x19;

    int VK_KANJI = 0x19;

    int VK_ESCAPE = 0x1B;

    int VK_CONVERT = 0x1C;

    int VK_NONCONVERT = 0x1D;

    int VK_ACCEPT = 0x1E;

    int VK_MODECHANGE = 0x1F;

    int VK_SPACE = 0x20;

    int VK_PRIOR = 0x21;

    int VK_NEXT = 0x22;

    int VK_END = 0x23;

    int VK_HOME = 0x24;

    int VK_LEFT = 0x25;

    int VK_UP = 0x26;

    int VK_RIGHT = 0x27;

    int VK_DOWN = 0x28;

    int VK_SELECT = 0x29;

    int VK_PRINT = 0x2A;

    int VK_EXECUTE = 0x2B;

    int VK_SNAPSHOT = 0x2C;

    int VK_INSERT = 0x2D;

    int VK_DELETE = 0x2E;

    int VK_HELP = 0x2F;

    int VK_0 = 0x30;

    int VK_1 = 0x31;

    int VK_2 = 0x32;

    int VK_3 = 0x33;

    int VK_4 = 0x34;

    int VK_5 = 0x35;

    int VK_6 = 0x36;

    int VK_7 = 0x37;

    int VK_8 = 0x38;

    int VK_9 = 0x39;

    int VK_A = 0x41;

    int VK_B = 0x42;

    int VK_C = 0x43;

    int VK_D = 0x44;

    int VK_E = 0x45;

    int VK_F = 0x46;

    int VK_G = 0x47;

    int VK_H = 0x48;

    int VK_I = 0x49;

    int VK_J = 0x4A;

    int VK_K = 0x4B;

    int VK_L = 0x4C;

    int VK_M = 0x4D;

    int VK_N = 0x4E;

    int VK_O = 0x4F;

    int VK_P = 0x50;

    int VK_Q = 0x51;

    int VK_R = 0x52;

    int VK_S = 0x53;

    int VK_T = 0x54;

    int VK_U = 0x55;

    int VK_V = 0x56;

    int VK_W = 0x57;

    int VK_X = 0x58;

    int VK_Y = 0x59;

    int VK_Z = 0x5A;

    int VK_LWIN = 0x5B;

    int VK_RWIN = 0x5C;

    int VK_APPS = 0x5D;

    int VK_SLEEP = 0x5F;

    int VK_NUMPAD0 = 0x60;

    int VK_NUMPAD1 = 0x61;

    int VK_NUMPAD2 = 0x62;

    int VK_NUMPAD3 = 0x63;

    int VK_NUMPAD4 = 0x64;

    int VK_NUMPAD5 = 0x65;

    int VK_NUMPAD6 = 0x66;

    int VK_NUMPAD7 = 0x67;

    int VK_NUMPAD8 = 0x68;

    int VK_NUMPAD9 = 0x69;

    int VK_MULTIPLY = 0x6A;

    int VK_ADD = 0x6B;

    int VK_SEPARATOR = 0x6C;

    int VK_SUBTRACT = 0x6D;

    int VK_DECIMAL = 0x6E;

    int VK_DIVIDE = 0x6F;

    int VK_F1 = 0x70;

    int VK_F2 = 0x71;

    int VK_F3 = 0x72;

    int VK_F4 = 0x73;

    int VK_F5 = 0x74;

    int VK_F6 = 0x75;

    int VK_F7 = 0x76;

    int VK_F8 = 0x77;

    int VK_F9 = 0x78;

    int VK_F10 = 0x79;

    int VK_F11 = 0x7A;

    int VK_F12 = 0x7B;

    int VK_F13 = 0x7C;

    int VK_F14 = 0x7D;

    int VK_F15 = 0x7E;

    int VK_F16 = 0x7F;

    int VK_F17 = 0x80;

    int VK_F18 = 0x81;

    int VK_F19 = 0x82;

    int VK_F20 = 0x83;

    int VK_F21 = 0x84;

    int VK_F22 = 0x85;

    int VK_F23 = 0x86;

    int VK_F24 = 0x87;

    int VK_NUMLOCK = 0x90;

    int VK_SCROLL = 0x91;

    int VK_LSHIFT = 0xA0;

    int VK_RSHIFT = 0xA1;

    int VK_LCONTROL = 0xA2;

    int VK_RCONTROL = 0xA3;

    int VK_LMENU = 0xA4;

    int VK_RMENU = 0xA5;

    int VK_BROWSER_BACK = 0xA6;

    int VK_BROWSER_FORWARD = 0xA7;

    int VK_BROWSER_REFRESH = 0xA8;

    int VK_BROWSER_STOP = 0xA9;

    int VK_BROWSER_SEARCH = 0xAA;

    int VK_BROWSER_FAVORITES = 0xAB;

    int VK_BROWSER_HOME = 0xAC;

    int VK_VOLUME_MUTE = 0xAD;

    int VK_VOLUME_DOWN = 0xAE;

    int VK_VOLUME_UP = 0xAF;

    int VK_MEDIA_NEXT_TRACK = 0xB0;

    int VK_MEDIA_PREV_TRACK = 0xB1;

    int VK_MEDIA_STOP = 0xB2;

    int VK_MEDIA_PLAY_PAUSE = 0xB3;

    int VK_LAUNCH_MAIL = 0xB4;

    int VK_LAUNCH_MEDIA_SELECT = 0xB5;

    int VK_LAUNCH_APP1 = 0xB6;

    int VK_LAUNCH_APP2 = 0xB7;

    int VK_OEM_1 = 0xBA;

    int VK_OEM_PLUS = 0xBB;

    int VK_OEM_COMMA = 0xBC;

    int VK_OEM_MINUS = 0xBD;

    int VK_OEM_PERIOD = 0xBE;

    int VK_OEM_2 = 0xBF;

    int VK_OEM_3 = 0xC0;

    int VK_OEM_4 = 0xDB;

    int VK_OEM_5 = 0xDC;

    int VK_OEM_6 = 0xDD;

    int VK_OEM_7 = 0xDE;

    int VK_OEM_8 = 0xDF;

    int VK_OEM_102 = 0xE2;

    int VK_PROCESSKEY = 0xE5;

    int VK_PACKET = 0xE7;

    int VK_ATTN = 0xF6;

    int VK_CRSEL = 0xF7;

    int VK_EXSEL = 0xF8;

    int VK_EREOF = 0xF9;

    int VK_PLAY = 0xFA;

    int VK_ZOOM = 0xFB;

    int VK_NONAME = 0xFC;

    int VK_PA1 = 0xFD;

    int VK_OEM_CLEAR = 0xFE;
}
