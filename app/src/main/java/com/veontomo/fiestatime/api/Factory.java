package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory for events
 */
public class Factory<T> {
    /**
     * list of available event types (
     */
    private final String[] mapper;

    /**
     * Constructor.
     *
     * @param classes array of fully qualified available classes each of which extends {@link Event}.
     */
    public Factory(String[] classes){
        this.mapper = classes;
    }

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
     * @param index
     * @param id
     * @param name
     * @param next
     * @return
     */
    public T produce(int index, long id, String name, long next) {
        T h = null;
        if (index >= mapper.length){
            return null;
        }
        String className = mapper[index];
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

    /**
     * Returns index at which {@link Event} subclasses are present in {@link #mapper}.
     * <br>
     * If nothing is found, -1 is returned.
     *
     * @param name
     * @return
     */
    public int indexOf(String name) {
        int len = mapper.length;
        for (int i = 0; i < len; i++) {
            if (name.equals(mapper[i])) {
                return i;
            }
        }
        return -1;
    }
}
