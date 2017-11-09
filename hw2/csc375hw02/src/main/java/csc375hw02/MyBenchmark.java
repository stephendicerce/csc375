/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package csc375hw02;


import java.util.Random;

public class MyBenchmark {

    public static void main(String[] args) {
        final int initialNumberOfClasses = 100;
        Class[] classArray = new Class[initialNumberOfClasses];
        Random r = new Random();
        for(int i=0, length = classArray.length; i<length; ++i) {
            classArray[i] = new Class("Class" + i, r.nextInt(100) + 10, r.nextInt(100));
        }

        Classes classes = new Classes(classArray);

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
