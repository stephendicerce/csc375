package org.projectClasses;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Classes {
    private volatile HashMap<String, Class> classList;
    private AtomicInteger lock = new AtomicInteger(); // my seqlock 
    private AtomicInteger numberOfClasses = new AtomicInteger(); 

    public Classes(Class[] firstClasses) {
        classList = new HashMap<String, Class>(firstClasses.length);
        for(int i=0; i<firstClasses.length; ++i) {
            addClass(firstClasses[i]);
        }
    }

    /**
       adds a class object to the hashmap
       @param c the class object to be added to the hashmap
       @return true if the addition was successful, false if the addition was unsuccessul
     */
    public boolean addClass(Class c) {
        for(;;) {
            if(classList.get(c.getName()) == null) {
                int start = lock.get();
                if(start%2 == 0) { //if the lock is unlocked
                    if(lock.compareAndSet(start, start+1)) { //locks the lock
                        classList.putIfAbsent(c.getName(), c);
                        if(c == classList.get(c.getName())) {
                            numberOfClasses.incrementAndGet();
                            lock.incrementAndGet(); //unlocks the lock
                            System.out.println(Thread.currentThread().getName() + " added " + c.getName() + ".");
                            return true;
                        }
                        lock.incrementAndGet(); //unlocks the lock
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
       removes a class object from the hashmap
       @param name the name of the class to be removed
     */
    public void removeClass(String name){
        for(;;) {
            if(classList.get(name) != null) {
                int start = lock.get();
                if(start%2 == 0) {
                    if(lock.compareAndSet(start, start+1)) {
                        classList.remove(name);
                        numberOfClasses.decrementAndGet();
                        lock.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + " removed " + name);
                        return;
                    }
                }
            } else {
                return;
            }
        }
    }

    public Class getClass(String name) {
        for(;;) {
            int start = lock.get();
            if(start%2 == 0) {
                Class c = classList.get(name);
                if(start - lock.get() == 0) {
                    return c;
                }
            }
        }
    }

    public int getSize() {return numberOfClasses.get();}
}
