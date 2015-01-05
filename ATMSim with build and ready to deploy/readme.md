##Assumptions & Requirements:
-Application is written and compiled using JDK1.6 
-java / jre 1.7 or greater is installed on linux machine and is defined in path.
-Maven 4.0.0

##Build and Setup: 
- To perform the maven build; go to the root directory of the project having the pom.xml file and execute the following: 
  mvn clean install
- This would create a ./target directory with an executable jar [ATMSim.jar].

##Building & Executing in a linux environment:
- Execute ATMSim.jar in ./target directory. It can be directly executed to start the ATMSim in a console.
   It has a dependency on a 3rd party jar(asg.cliche.jar), however maven takes care of this dependency during the build process.
  Use the following command to execute: 
    java -jar ATMSim.jar

##Console Menu

-Following is the snapshot of ATMSim console Menu.
  By default the ATM would initialise with 100 notes of $50 and $20 each. 

-Command Example : 
  ATM>wd 70 

  Above shall withdraw 1 note of $50 and $20 each. 

 ########   QUICK HELP   ########
 ##
 ##
 ## ENTER==>'?list' for help on list of commands & descriptions
 ## ENTER==>'?help <cmd>' for help on specific command
 ##
 ##
 ## ENTER==>'wd <amount>' to withdraw cash
 ## ENTER==>'ac' to get count of all notes of each type
 ## ENTER==>'nc <denomination>' to get count of a particular note type
 ## ENTER==>'ri <50,12;20,12;10,12;5,12>' to re-initialise ATMsim for given bundles
 ##
 ##
 ## ENTER==>'exit' to exit the application
 ##
 ## ENTER==>'help' to see this menu again 
