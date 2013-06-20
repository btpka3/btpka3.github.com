package me.test;

public class LParamUnion {
    /**
     * 31: The transition state. The value is 1 if the key is being released, or
     * it is 0 if the key is being pressed.
     */
    private boolean keyReleased = false;

    /**
     * 30: The previous key state. The value is 1 if the key is down before the
     * message is sent, or it is 0 if the key is up.
     */
    private boolean preKeyDown = false;

    /**
     * 29: The context code. The value is 1 if the ALT key is held down while
     * the key is pressed; otherwise, the value is 0.
     */
    private boolean altHeld = false;

    /**
     * 24: Indicates whether the key is an extended key, such as the right-hand
     * ALT and CTRL keys that appear on an enhanced 101- or 102-key keyboard.
     * The value is 1 if it is an extended key; otherwise, it is 0.
     */
    private boolean extendedKey = false;

    /**
     * 16-23: The scan code. The value depends on the OEM.
     */
    private byte scanCode = 0;

    /**
     * 0-15:The repeat count for the current message.
     */
    private short repeatCount = 1;

    public LParamUnion() {
    }

    public LParamUnion(byte scanCode) {
        this.scanCode = scanCode;
    }

    public int toInt() {

        int value = 0;
        if (keyReleased) {
            value |= 1 << 31;
        }
        if (preKeyDown) {
            value |= 1 << 30;
        }
        if (altHeld) {
            value |= 1 << 29;
        }
        if (extendedKey) {
            value |= 1 << 24;
        }
        value |= (scanCode & 0xFF) << 16;
        value |= repeatCount;
        return value;
    }

    public static LParamUnion getDefaultVmKeyUp() {
        LParamUnion p = new LParamUnion();
        p.setPreKeyDown(true);
        p.setKeyReleased(true);
        return p;
    }

    public static LParamUnion getDefaultVmKeyDown() {
        return new LParamUnion();
    }

    public static LParamUnion getDefaultVmChar() {
        return new LParamUnion();
    }

    @Override
    public String toString() {
        return "WmKeyDownLParam [keyReleased=" + keyReleased + ", preKeyDown="
                + preKeyDown + ", altHeld=" + altHeld + ", extendedKey="
                + extendedKey + ", scanCode=" + scanCode + ", repeatCount="
                + repeatCount + " ]";
    }

    public boolean isKeyReleased() {
        return keyReleased;
    }

    public void setKeyReleased(boolean keyReleased) {
        this.keyReleased = keyReleased;
    }

    public boolean isPreKeyDown() {
        return preKeyDown;
    }

    public void setPreKeyDown(boolean preKeyDown) {
        this.preKeyDown = preKeyDown;
    }

    public boolean isAltHeld() {
        return altHeld;
    }

    public void setAltHeld(boolean altHeld) {
        this.altHeld = altHeld;
    }

    public boolean getExtendedKey() {
        return extendedKey;
    }

    public void setExtendedKey(boolean extendedKey) {
        this.extendedKey = extendedKey;
    }

    public byte getScanCode() {
        return scanCode;
    }

    public void setScanCode(byte scanCode) {
        this.scanCode = scanCode;
    }

    public short getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(short repeatCount) {
        this.repeatCount = repeatCount;
    }


}
