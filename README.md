# Mandelbrot_ClientServer

### This project has two applications:  Server part and client part, together they renders the Mandelbort set image.  
1.  Server application accepts requests on a TCP port and generate the requested part of the Mandelbrot image
The server accepts a request that has this form:  

    - _**min_c_re,min_c_im,max_c_re,max_c_im max_n,x,y,inf_n**_

    - In return, it returns a gray-scale image of dimesion x times y, where each pxel corresponds to the number of iterations it takes, until |Z<sub>n</sub>| > C, Z<sub>n+1</sub> = Z<sub>n</sub><sup>2</sup> + C (C is a complex), with the pixel value is modulo 256, and a maximum of inf_n iterations is performed.  C is varied over the image between the boundaries given.  The format of the image is in jpg format.

    - The server is capable of handleing multiple call.  And it is psssible to speicfy which port the server listens to.

2.  Client application divides the task into multiple requests and spreads the workload over a set of severs.  Afterwards, it merges the sub-pictures returned from the servers, shows it on screen and saves the image in jpg format into a local folder.  

# Getting Started

### Prerequisites
java runtime environment.

### Setup & Installation
All *.class files should be placed in the same directory.

Put all server side files in the a folder on the server side. Compile all files with "javac *.java" command.  Note that the TCPIP port number is fixed as "5056".  However, it can be changed by modifying the port number in the MandelbrotServer main functiopn and recompile the files.

Put all client side files int a folder on the client side.  Compile all files with "javac *.java" command and ions list-of servers.**

### Execution 

Server side:  run the main program by excuting the command "Java MandelbrotServer" in the  *.class file folder.

Client side:  run the main program by executing the coammand "java ClientMain" int the java in *.class with the form like this:  _**ClientMain min_c_re min_c_im max_c_re max_c_im max_n x y division.**_

  - _**E.g. :  -1 -1.5 2 1.5 1024 10000 10000 4 localhost:111 192.168.1.123:1234 192.168.1.222:3456**_

This should divide the 10000x1000 picture in 4x4 sub-pitures, and spread the work to the three given servers.  The output image client will be displayed on client screen and a copy of the image file "FullMandelbrotImage.jpg" will be stored in the client side folder.

Note: Execute the server side program before executing the programing on the client side.

# Open Source & Copying
Mandelbrot_ClientServer is for free and provide its entire source code for free as well. In the spirit of openness, Mandelbrot_ClientServer is licensed under MIT so that you can use the code in your app, if you choose.

However, please do not ship this app under your own account!!!

