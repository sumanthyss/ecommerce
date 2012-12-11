# Shopping Cart Sim

## About

You can see the live version [here](http://cs232.epanahi.com)

The purpose of ShoppingCart is 1) a final project for CS 232 and 2) to learn how to build a web application in java.  This app is built on the [Play!](http://www.playframework.org/) framework.  In the code you will find examples of :
* Using a jdbc adapter to access a database (H2 in development, MySQL in production)
* Skeletal views built in HTML with some Scala thrown in
* Coffeescript functions that use jQuery to tie in the interface with the backend (Ajax calls to make the application a single page)
* A basic Raphael.js pie chart
* Some JUnit tests
* Using [webjars](http://www.webjars.org/) to easily import dependencies

See [the wiki](https://github.com/panahi/ShoppingCart/wiki) for more information  

## Installation  
### Running the app

1) You can download v2.0.4 of the framework [here](http://download.playframework.org/releases/play-2.0.4.zip)  
2) Once it's installed, set the path to the extracted directory on your PATH  
3) `git clone https://github.com/panahi/ShoppingCart.git`  
4) `cd ShoppingCart`  
5) `play run`  

This will download some framework dependencies and the Webjars I've included (just Twitter Bootstrap).  If you only type `play` in the shopping cart directory, you will enter the Play Console which has a variety of commands.

### Configuring

* As a basic security precaution, the user must enter a password to add a new item to the database.  This password is compared to an environment variable on the server.  To enable adding items to the database, set a password in a variable named `PLAYUPLOAD`
* The primary configurable aspect is the database used.  H2 is an in-memory database suitable for development and is enabled by default  

To use MySQL, comment out the lines for H2  
In **conf/application.conf**  
      
      #db.default.driver=org.h2.Driver
      #db.default.url="jdbc:h2:mem:play"

And uncomment the lines for MySQL (replacing url, user, and password with your information)  

      db.default.driver=com.mysql.jdbc.Driver
      db.default.url="jdbc:mysql://localhost/play"
      db.default.user=someuser
      db.default.password=somepassword

## Running the tests

Play includes the command `play test` which will compile and execute any JUnit tests it finds in the test/ directory.  There are a handful there now to test a few of the routes and the view rendering.  

