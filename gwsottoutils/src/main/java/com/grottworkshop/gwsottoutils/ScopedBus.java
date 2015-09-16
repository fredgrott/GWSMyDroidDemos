package com.grottworkshop.gwsottoutils;

import com.squareup.otto.Bus;

import java.util.HashSet;
import java.util.Set;

/**
 * ScopedBus, as its usually better to use a bus that registers and unregisters with the
 * activity or fragment lifecycles even when doing activity result nested frags
 *
 * Usage
 *
 * <code>
 *     private final ScopedBus scoepdBus = new ScopedBus()
 *     protected ScopedBus getBus(){
 *         return scopedBus;
 *     }
 *
 *     @Override public void onStart(){
 *         super.onStart():
 *         bus.start();
 *     }
 *
 *     @Override public void onPause() {
 *              super.onPause();
 *              bus.paused();
 *      }
 *    @Override public void onResume() {
 *              super.onResume();
 *              bus.resumed();
 *      }
 * </code>
 *
 * This way BusProvider creates the singleton no matter if you want to use in activity or
 * application class and you use the ScopedBus as your main bus for your application applying
 * it in your activities or fragments.
 *
 * You can use onStart(), onStop(), onResume(), onPause() to match up with your use of
 * activities or fragments as long as you use an onStart(0 so that the bus is registered
 * when the activity or fragment starts.
 *
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public class ScopedBus {

    private final Bus bus = BusProvider.getInstance();
    private final Set<Object> objects = new HashSet<>();
    private boolean active;

    public void register(Object obj) {
        objects.add(obj);
        if (active) {
            bus.register(obj);
        }
    }


    public void unregister(Object obj) {
        objects.remove(obj);
        if (active) {
            bus.unregister(obj);
        }
    }


    public void post(Object event) {
        bus.post(event);
    }


    void paused() {
        active = false;
        for (Object obj : objects) {
            bus.unregister(obj);
        }
    }


    void resumed() {
        active = true;
        for (Object obj : objects) {
            bus.register(obj);
        }
    }

    void start() {
        active = true;
        for (Object obj: objects){
            bus.register(obj);
        }
    }

    void stop(){
        active = false;
        for (Object obj: objects){
            bus.unregister(obj);
        }
    }
}
