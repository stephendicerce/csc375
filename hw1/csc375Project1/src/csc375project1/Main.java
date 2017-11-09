/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375project1;

import java.util.Scanner;

/**
 *
 * @author stephendicerce
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String input = "hello";
        int p1, p2;
        Scanner kb = new Scanner(System.in);
        Person[] people = new Person[32];
        
        for(int i=0; i<people.length; ++i) {
            people[i] = new Person();
            people[i].setID(i);
        }
        
        for(int i=0; i<people.length; ++i) {
            people = people[i].setAffinities(people);
        }
        
        do{
            System.out.println("the two people you want to check");
            p1 = kb.nextInt();
            p2 = kb.nextInt();
            kb.nextLine();
            
            System.out.println(people[p1].getID() + "'s affinity to " + people[p2].getID() + " is: " + people[p1].getAffinity(p2));
            
        } while(!input.equals("quit"));
    }
    
}
