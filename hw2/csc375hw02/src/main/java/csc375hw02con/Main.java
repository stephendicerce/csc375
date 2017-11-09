package csc375hw02con;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int initialNumberOfClasses = 100;
        Class[] classList = new Class[initialNumberOfClasses];
        Random r = new Random();
        for(int i=0, length=classList.length; i<length; ++i) {
            classList[i] = new Class("Class" + i, r.nextInt(100) +10, r.nextInt(100));
        }

        Classes classes = new Classes(classList);

        for(int i=0; i<500; ++i) {
            new Thread(() -> {
                Random fnr = new Random();
                int favoriteNumber = fnr.nextInt(100);
                Random random = new Random();
                while(true) {
                    String className = "Class" + random.nextInt(classes.getSize());
                    Class selectedClass = classes.getClass(className);
                    if(selectedClass != null) {
                        if(selectedClass.getClassSubject()-favoriteNumber <= 5 || selectedClass.getClassSubject()-favoriteNumber <= 5) {
                            System.out.println("THREAD ID: " + Thread.currentThread().getId());
                            selectedClass.addStudentToSection(Thread.currentThread().getId());
                        }
                    }
                }
            }, "Student" + i).start();
        }

        for(int i=0; i<500; ++i) {
            new Thread(() -> {
                Random random = new Random();
                int totalNumberOfClasses = initialNumberOfClasses;
                while(true) {
                    if(random.nextInt(10000000) == 18) {
                        classes.addClass(new Class("Class" + totalNumberOfClasses+1, random.nextInt(100)+10, random.nextInt(100)));
                    }
                }
            }, "Class Creator").start();

            new Thread(() -> {
                Random random = new Random();
                while(true) {
                    if(random.nextInt(10000000) == 24) {
                        classes.removeClass("Class" + random.nextInt(classes.getSize()));
                    }
                }
            }, "Class Remover").start();
        }
    }
}
