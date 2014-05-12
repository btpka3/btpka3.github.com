package me.test;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.ole.win32.OleClientSite;

public class OleFactory {

    private Map<String, ComContainer> oleContainerMap = new HashMap<String, ComContainer>();

    private static OleFactory instance = null;

    private OleFactory() {
    };

    public final static OleFactory getInstance() {
        if (instance == null) {
            instance = new OleFactory();
        }
        return instance;
    }

    /**
     * NOTICE : when using the return object, pay attention about
     * synchonization.
     * 
     * @param clsId e.g. "Word.Document",
     *            "{11111111-2222-3333-4444-555555555555}"
     */
    public synchronized OleClientSite registAndGetOle(String progId) {
        ComContainer container = oleContainerMap.get(progId);
        if (container == null) {
            container = new ComContainer(progId);
            oleContainerMap.put(progId, container);
        }
        if (!container.isAlive()) {
            container.start();
            while (!container.isReady()) {
                try {
                    container.join(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return container.getOleClientSite();
    }
    public synchronized ComContainer reg(String progId) {
        ComContainer container = oleContainerMap.get(progId);
        if (container == null) {
            container = new ComContainer(progId);
            oleContainerMap.put(progId, container);
        }
        if (!container.isAlive()) {
            container.start();
            while (!container.isReady()) {
                try {
                    container.join(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return container;
    }

    @SuppressWarnings("deprecation")
    public synchronized void unregistOle(String progId) {
        ComContainer container = oleContainerMap.get(progId);
        if (container != null) {
            if (container.isAlive()) {
                container.notifyStop();
                try {
                    container.join(1000);
                } catch (InterruptedException e) {
                }
                if (container.isAlive()) {
                    container.stop();
                }
            }
            oleContainerMap.remove(progId);
        }
    }

    public synchronized void unregistAll() {
        for (String progId : oleContainerMap.keySet()) {
            unregistOle(progId);
        }
    }
}
