package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Holiday Factory
 *
 */
public class FactoryHoliday {
    /**
     * Returns an instance of one of the subclasses of {@link Holiday} based on given string
     * @param data
     * @return
     */
    public Holiday produce(String data){
        String[] arr = data.split("#", -2);
        Holiday h = null;
        if (arr.length == 4){
            String className = arr[0];
            long id = Long.parseLong(arr[1], 10);
            long next = Long.parseLong(arr[2], 10);
            String name = arr[3];
            try {
                Class cl = Class.forName(className);
                Constructor<Holiday> c =  cl.getConstructor( new Class[] {Long.TYPE, String.class, Long.TYPE});
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
     * @param className
     * @param id
     * @param name
     * @param next
     * @return
     */
    public Holiday produce(String className, long id, String name, long next){
        // TODO
        Holiday h = null;
        return h;
    }
}
