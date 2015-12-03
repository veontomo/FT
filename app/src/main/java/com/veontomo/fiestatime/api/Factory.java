package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory for events
 */
public class Factory<T> {

    /**
     * Returns an instance of one of the subclasses of {@link Event} based on given string
     *
     * @param data
     * @return
     */
    public T produce(String data) {
        String[] arr = data.split("#", -2);
        T h = null;
        if (arr.length == 4) {
            String className = arr[0];
            long id = Long.parseLong(arr[1], 10);
            long next = Long.parseLong(arr[2], 10);
            String name = arr[3];
            try {
                Class cl = Class.forName(className);
                Constructor<T> c = cl.getConstructor(new Class[]{Long.TYPE, String.class, Long.TYPE});
                h = c.newInstance(id, name, next);
            } catch (ClassNotFoundException e) {
                Logger.log("Failed to find class for " + className);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                Logger.log("Class " + className + " has no requested constructor");
            } catch (InstantiationException e) {
                Logger.log("Requested constructor for class " + className + " is not accessible");
            } catch (IllegalAccessException e) {
                Logger.log("Requested method for class " + className + " is not accessible");
            }
        }
        return h;
    }




    /**
     * Returns an instance of a given class
     *
     * @param className
     * @param id
     * @param name
     * @param next
     * @return
     */
    public T produce(String className, long id, String name, long next) {
        T h = null;
        try {
            Class cl = Class.forName(className);
            Constructor<? extends T> c = cl.getConstructor(Long.TYPE, String.class, Long.TYPE);
            h = c.newInstance(id, name, next);
        } catch (ClassNotFoundException e) {
            Logger.log("Failed to find class for " + className);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Logger.log("Class " + className + " has no requested constructor");
        } catch (InstantiationException e) {
            Logger.log("Requested constructor for class " + className + " is not accessible");
        } catch (IllegalAccessException e) {
            Logger.log("Requested method for class " + className + " is not accessible");
        }
        return h;
    }


}
