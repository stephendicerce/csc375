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

package org.sample;

import org.csc375hw02con.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import java.util.Random;

public class MyBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
	volatile String[] args = null;
    }

    @Benchmark
    public static void main(String[] args) {
        final int initialNumberOfClasses = 100;
        org.csc375hw02con.Class[] classList = new org.csc375hw02con.Class[initialNumberOfClasses];
        Random r = new Random();
        for(int i=0, length=classList.length; i<length; ++i) {
            classList[i] = new org.csc375hw02con.Class("Class" + i, r.nextInt(100) +10, r.nextInt(100));
        }
	
        org.csc375hw02.Classes classes = new org.csc375hw02.Classes(classList);
	
        for(int i=0; i<500; ++i) {
            new Thread(() -> {
		    int counter = 0;
		    Random fnr = new Random();
		    int favoriteNumber = fnr.nextInt(100);
		    Random random = new Random();
		    while(counter < 5) {
			String className = "Class" + random.nextInt(classes.getSize());
			org.csc375hw02con.Class selectedClass = classes.getClass(className);
			if(selectedClass != null) {
			    if((selectedClass.getClassSubject()-favoriteNumber <= 5 && selectedClass.getClassSubject()-favoriteNumber >= 0) || (favoriteNumber-selectedClass.getClassSubject()<= 5 && favoriteNumber-selectedClass.getClassSubject() >= 0)) {
				selectedClass.addStudentToSection(Thread.currentThread().getId());
				++counter;
			    }
			}
		    }
            }, "Student" + i).start();
        }
	
	new Thread(() -> {
		int createdClasses = 0;
                Random random = new Random();
                int totalNumberOfClasses = initialNumberOfClasses;
                while(createdClasses < 50) {
                    if(random.nextInt(10000000) == 18) {
                        classes.addClass(new org.csc375hw02con.Class("Class" + totalNumberOfClasses++, random.nextInt(100)+10, random.nextInt(100)));
			++createdClasses;
                    }
                }
	}, "Class Creator").start();
	
	new Thread(() -> {
		int removedClasses = 0;
                Random random = new Random();
                while(removedClasses < 50) {
                    if(random.nextInt(10000000) == 24) {
                        classes.removeClass("Class" + random.nextInt(classes.getSize()));
			++removedClasses;
                    }
                }
	}, "Class Remover").start();
    }
    
}
