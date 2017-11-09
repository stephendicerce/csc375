# CSC375

Projects are stored according to their appropriate name. Everything needed to run should be included in this repo along 
with some Graphs for homework 2 showing throughput for the structure I created vs the Concurrent Hashmap built into the JDK.


### Homework 2
This Project simulates a course scheduling system where clients are simulated as running in parallel. This was done in two
ways. The first being a hashmap that is locked by a seq lock everytime it is written or read from. The other being a concurrent
hashmap that is built into the JDK. This project is meant to be run using JDK 9, and contains a JMH benchmark for each project
that processes throughput for each structure. This subset includes 2 Maven projects. One for each of the structures I built. 

The graphs will be in the main folder of this project along with the directories for each project.
