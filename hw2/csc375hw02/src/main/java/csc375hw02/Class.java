package csc375hw02;

public class Class {
    private String name;
    private int numberOfStudents;
    private long[] studentsInSection;
    private int classSubject;

    public Class(String name, int numberOfStudents, int classSubject) {
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.classSubject = classSubject;
        studentsInSection = new long[numberOfStudents];
        for(int i=0, length=studentsInSection.length; i<length; ++i) studentsInSection[i] = 0;
    }

    public int getClassSubject() {
        return this.classSubject;
    }

    public String getName() {
        return this.name;
    }

    /**
       adds a threads id to the current section of the class
       @param id the id of the student being registered to the class
     */ 
    public void addStudentToSection(long id ) {
        boolean alreadyRegistered = false;
	for(int i=0, length=studentsInSection.length; i<length; ++i) { //searches through the current students in the section 
	    if(studentsInSection[i] == id) { //if its there sets a boolean value for later use
		alreadyRegistered = true;
	    }
	}
	if(!alreadyRegistered) { //checks the boolean value to see if its false
	    int position = Integer.MAX_VALUE; //arbitrary value
	    
	    for (int i = studentsInSection.length - 1; i >= 0; --i) {
                if (studentsInSection[i] == 0) { //checks to find the next open spot in the class
                    position = i;
                }
            }
            if (position < numberOfStudents) { //if there is still space left in the class
                studentsInSection[position] = id;
                try {
                    System.out.println(Thread.currentThread().getName() + " is registering for " + this.name + ".");
                    Thread.currentThread().sleep(1000); //simulates the actual time it would take someone to actually physically register for the class
                    System.out.println(Thread.currentThread().getName() + " has completed registration for " + this.name + ".");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " was interrupted while registering for " + this.name + ".");
                }
            }
        }
    }

    /**
       removes the thread's id from the current section of the class
       @param id the thread being removed from the class
     */
    public void removeStudentFromSection(long id) {
        boolean inSection = false;
        for(int i=0, length=studentsInSection.length; i<length; ++i) {
            if(studentsInSection[i] == id) {
                inSection = true;
                for(int j=i, newLength = studentsInSection.length-i; i<newLength-1; ++i){ //resets the rest of the array after the student was removed
                    studentsInSection[j] = studentsInSection[j+1];
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " is removing itself from " + this.name + ".");
                    Thread.currentThread().sleep(1000); // simulates the amount of time it would physically take to unregister from a class
                    System.out.println(Thread.currentThread().getName() + "was successfully removed from " + this.name + ".");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " was interrupted while removing itself from " + this.name + ".");
                }

            }
        }
        if(!inSection) System.out.println(Thread.currentThread().getName() + " is not currently enrolled in " + this.name + ", therefore it was not removed from the class");
    }
}
