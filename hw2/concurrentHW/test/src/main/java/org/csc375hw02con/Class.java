package org.csc375hw02con;

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

    public void addStudentToSection(long id ) {
        boolean alreadyRegistered = false;
        for(int i=0, length=studentsInSection.length; i<length; ++i){
            if(studentsInSection[i] == id) {
		alreadyRegistered = true;
	    }
        }
        if(!alreadyRegistered) {
            int position = Integer.MAX_VALUE;

            for (int i = studentsInSection.length - 1; i >= 0; --i) {
                if (studentsInSection[i] == 0) {
                    position = i;
                }
            }
            if (position < numberOfStudents) {
                studentsInSection[position] = id;
                try {
                    System.out.println(Thread.currentThread().getName() + " is registering for " + this.name + ".");
                    Thread.currentThread().sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " has completed registration for " + this.name + ".");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " was interrupted while registering for " + this.name + ".");
                }
            } else {
                //System.out.println(Thread.currentThread().getName() + " couldn't successfully register for " + this.name + " because the class is full.");
            }
        } else {
	    //            System.out.println(Thread.currentThread().getName() + " is already registered for this class.");
        }

    }

    public void removeStudentFromSection(long id) {
        boolean inSection = false;
        for(int i=0, length=studentsInSection.length; i<length; ++i) {
            if(studentsInSection[i] == id) {
                inSection = true;
                for(int j=i, newLength = studentsInSection.length-i; i<newLength-1; ++i){
                    studentsInSection[j] = studentsInSection[j+1];
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " is removing itself from " + this.name + ".");
                    Thread.currentThread().sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "was successfully removed from " + this.name + ".");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " was interrupted while removing itself from " + this.name + ".");
                }

            }
        }
        if(!inSection) System.out.println(Thread.currentThread().getName() + " is not currently enrolled in " + this.name + ", therefore it was not removed from the class");
    }
}
