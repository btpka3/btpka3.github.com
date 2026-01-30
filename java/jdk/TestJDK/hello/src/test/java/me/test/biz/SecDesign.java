package me.test.biz;

import io.vavr.Function0;
import io.vavr.Function1;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/11/11
 */
public class SecDesign {

    public void demo01() {
        P p = P.defaultInstance();
        EventRegistry eventRegistry = p.eventRegistry();
        EventDef eventDef = eventRegistry.getEventDef("xxxEvent");
        Event event = eventDef.newInstance(Map.of("a", "b"));
        Object propValue = event.getCachedProperty("xxxProp");


    }

    interface P {

        static P defaultInstance() {
            return null;
        }

        <T> T getService(String prop);


        default FuncRegistry funcRegistry() {
            return getService("funcRegistry");
        }

        default Object cacheRegistry() {
            return getService("cacheRegistry");
        }

        default EventRegistry eventRegistry() {
            return getService("eventRegistry");
        }
    }

    interface FuncRegistry {
        <R> void register(String name, Function0<R> func);

        <T1, R> void register(String name, Function1<T1, R> func);
    }

    interface EventRegistry {
        EventDef getEventDef(String event);
    }

    interface EventDef {
        String getName();

        List<EventPropertyDef> getProperties();

        EventPropertyDef getPropertyDef(String prop);

        Event newInstance(Map ctx);
    }

    interface EventPropertyDef {
        EventDef getEventDef();

        String getName();

        String getVersion();

        default Object getValue(Event event) {
            Function1<Event, Object> func1 = null;
            return func1.apply(event);
        }
    }

    static final Object NULL_OBJ = new Object();

    interface Event {
        P getP();

        EventDef getDef();

        Map getCtx();

        default Object getProperty(String prop) {
            return getDef().getPropertyDef("prop").getValue(this);
        }

        default Object getCachedProperty(String prop) {
            Object obj = getCache().get(prop);
            if (obj != null) {
                return NULL_OBJ == obj ? null : obj;
            }
            obj = getProperty(prop);
            getCache().put(prop, prop == null ? NULL_OBJ : obj);
            return obj;
        }

        Map getCache();
    }

    interface RuleDef {
        String getId();

        String getVersion();
    }
}
