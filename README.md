# Mandelbrot_ClientServer

### This project has two applications:  Server part and client part, together they renders the Mandelbort set image.  
1.  Server application accepts requests on a TCP port and generate the requested part of the Mandelbrot image
The server accpts a request that hass this form:  
** _min_c_re,min_c_im,max_c_re,max_c_im max_n,x,y,inf_n_**

2.  Client application divides the Mandelbrot image generation requests and spreads the workload over a set of severs.

# Getting Started

### Prerequisites
java runtime environment.

### Setup & Installation
All *.class files should be placed in the same directory.

Put all server side files in the a folder on the server side. Compile all files with "javac *.java" command.  Note that the TCPIP port number is fixed as "5056".  However, it can be changed by modifying the port number in the MandelbrotServer main functiopn and recompile the files.

Put all client side files int a folder on the client side.  Compile all files with "javac *.java" command and ions list-of servers.**

### Execution 

Server side:  run the main program by excuting the command "Java MandelbrotServer" in the  *.class file folder.

Client side:  run the main program by executing the coammand "java ClientMain" int the java in *.class with the form like this: **ClientMain min_c_re min_c_im max_c_re max_c_im max_n x y division.**

E.g. :  -1 -1.5 2 1.5 1024 10000 10000 4 localhost:111 192.168.1.123:1234 192.168.1.222:3456

This should divide the 10000x1000 picture in 4x4 sub-pitures, and spread the work to the three given servers.  The output image client will be displayed on client screen and a copy of the image file "FullMandelbrotImage.jpg" will be stored in the client side folder.

# Open Source & Copying
Mandelbrot_ClientServer is for free and provide its entire source code for free as well. In the spirit of openness, ToDoList is licensed under MIT so that you can use my code in your app, if you choose.

However, please do not ship this app under your own account. Paid or free.

