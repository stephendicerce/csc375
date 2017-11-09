/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375project1;

import java.util.Random;

/**
 *
 * @author stephendicerce
 */
public class Person {

    private int id;
    private int rowLocated;
    private int columnLocated;
    int[] affinities = new int[32];

    public Person() {

    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }

    public void setRowLocated(int r) {
        rowLocated = r;
    }

    public int getRowLocated() {
        return rowLocated;
    }

    public void setColumnLocated(int c) {
        columnLocated = c;
    }

    public int getColumnLocated() {
        return columnLocated;
    }

    /**
     * what the method does
     * @param people this object is a people
     * @return a person
     */
    public Person[] setAffinities(Person[] people) {
        for (int i = 1; i <= 32; ++i) {
            boolean set = false;

            while (!set) {
                Random random = new Random();

                int r = random.nextInt(32);
               

                if (affinities[r] == 0 && r != id) {
                    affinities[r] = i;
                    people[id].affinities[r] = i;
                    if (people[r].affinities[id] == 0) {
                        people[r].affinities[id] = i;
                        if (people[id].affinities[r] == people[r].affinities[id]) {
                            System.out.println("reached");
                            set = true;
                        }
                    }

                } else if (affinities[r] == 0 && r == id) {
                    set = true;
                }
            }
        }
        return people;
    }

    public int getAffinity(int p) {
        return affinities[p];
    }

    public Person[][] swap(Person[][] assignedSeats, Person p1, Person p2) {

        return assignedSeats;
    }
}
