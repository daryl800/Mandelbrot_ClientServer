# Mandelbrot_ClientServer

### This project has two applications:  Server part and client part, which together renders parts of the Mandelbort set.  
1.  Server application accepts requests on a TCP port.
2.  Client application spreads the workload over a set of severs.

# Getting Started

### &nbsp;&nbsp;&nbsp; Prerequisites
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java runtime environment.

### &nbsp;&nbsp;&nbsp; Setup & Installation
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All *.class files should be placed in the same directory.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put all sever side files in the same folder on the server side. Compile all files with "javac *.java" and execute the main program by executing "Java MandelbrotServer"

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pull all client side files int eh same folder on the client side.  Compile all files with "javac I.java" and execute the main program by executing the ClientMain in the form like: mi_c_re min_c_im max_c_re max_c_im max_n x y divisions list-of servers

Example:  -1 -1.5 2 1.5 1024 10000 10000 4 localhost:1234 localhost:1234 localhost:3456

This should divide the 10000x1000 picture in 4x4 sub-pitures, and spread the work to the three given servers.  The output from the client will display on the screen and a copy of the image "FullMandelbrotImage.jpg" will be stored in the execution folder.


### &nbsp;&nbsp;&nbsp; Execution 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;javac Run

# Open Source & Copying
ToDoList is for free and provide its entire source code for free as well. In the spirit of openness, ToDoList is licensed under MIT so that you can use my code in your app, if you choose.

However, please do not ship this app under your own account. Paid or free.

