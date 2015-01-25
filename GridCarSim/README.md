## Program to simulate a car driving on a grid

### Building the code.
The code can be built using maven. The root directory of this solution has the file pom.xml.
Execute the following where you see the pom.xml(in the top most directory)
* mvn clean install

### Executing the code. 

#### Executing it on an IDE
Run the Main class com.sample.gridcarsim.console.GridSimConsole, no arguments required. 

#### Executing the jar from maven build.
After the build, go to the target directory, this directory would be at the same level as pom.xml
Inside the target directory execute the following
* java -jar ./GridCarSim.jar

You would now see a console, 'GRID>'; execute commands to drive the car in the simulation.

### Commands and descriptions.
|---------------- Help ---------------- |
|
|Commands 			| Description|
|-------------------	--------------|
|?list 				| Get the list of all commands.|
|?help cmd 			| Help on the entered cmd/command.|
|help 				| Help menu |
|exit 				| Exit the simulator|
|INIT x y direction | Initialize the car position.| 
|FORWARD 			| Move the car forward by one unit| 
|TURN_LEFT 			| Turn left| 
|TURN_RIGHT 		| Turn right |
|GPS_REPORT 		| Print position and direction| 