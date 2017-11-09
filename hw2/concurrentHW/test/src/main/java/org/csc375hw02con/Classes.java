package org.csc375hw02con;

import java.util.concurrent.ConcurrentHashMap;

public class Classes {
    private ConcurrentHashMap<String, Class> classes;

    public Classes(Class[] classArray) {
        classes = new ConcurrentHashMap<String, Class>(classArray.length);
        for(int i=0, length=classArray.length; i<length;  ++i) {
            addClass(classArray[i]);
        }
    }

    public boolean addClass(Class c) {
        classes.putIfAbsent(c.getName(), c);
        if(c == classes.get(c.getName())) {
            System.out.println(Thread.currentThread().getName() + " added " + c.getName());
            return true;
        }
        return false;
    }

    public void removeClass(String className) {
        if(classes.get(className) != null) {
            classes.remove(className);
            System.out.println(Thread.currentThread().getName() + " removed " + className);
        }
    }

    public Class getClass(String className) {
        return classes.get(className);
    }

    public int getSize() {
        return classes.size();
    }
}
