package org.projectClasses;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int initialNumberOfClasses = 100;
        Class[] classArray = new Class[initialNumberOfClasses];
        Random r = new Random();
        for(int i=0, length = classArray.length; i<length; ++i) { //creates an array of completely randomized classes
            classArray[i] = new Class("Class" + i, r.nextInt(100) + 10, r.nextInt(100));
        }

        Classes classes = new Classes(classArray); // adds the array to the classes hashmap
	
        for(int i=0; i<500; ++i) { //creates 500 threads simulating 500 students(clients)
            new Thread(() -> {
		    Random fnr = new Random();
		    int favoriteNumber = fnr.nextInt(100); // picks the students favorite subject
		    Random random = new Random();
		    while(true) {
			String className = "Class" + random.nextInt(classes.getSize()); //selects a random class
			Class selectedClass = classes.getClass(className);
			if(selectedClass != null) {
			    if((selectedClass.getClassSubject()-favoriteNumber <= 5 && selectedClass.getClassSubject()-favoriteNumber >= 0) || (favoriteNumber-selectedClass.getClassSubject() <= 5 && favoriteNumber-selectedClass.getClassSubject() >= 0)) { //if the class subject is within 5 of the student's favorite subject then the student will register for it
				selectedClass.addStudentToSection(Thread.currentThread().getId());
			    }
			}
		    }
            }, "Student" + i).start();
        }
	
        new Thread(() -> {
		Random random = new Random();
		int totalNumberOfClasses = initialNumberOfClasses;
		while(true) {
		    if(random.nextInt(10000000) == 18) { // picked my favorite number as a limiter so that classes wouldnt be created at an incredibly fast speed
			classes.addClass(new Class("Class" + totalNumberOfClasses++, random.nextInt(100)+10, random.nextInt(100)));
		    }
		}
        }, "Class Creator").start();
	
        new Thread(() -> {
		Random random = new Random();
		while(true) {
		    if(random.nextInt(10000000) == 24) { //picked my least favorite number as a limiter so that classes wouldnt be removed instantly
			classes.removeClass("Class" + random.nextInt(classes.getSize()));
		    }
		}
        }, "Class Remover").start();
    }
}
