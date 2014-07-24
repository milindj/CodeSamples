##Assumptions & Requirements:
-Application is written and compiled using JDK1.6 
-java / jre 1.6 or greater is installed on linux machine and is defined in path.

##Executing in linux:
- Execute ATMSim.jar in ./dist directory. It can be directly executed to start the ATMSim in a console.
	It has a dependency on a 3rd party jar(asg.cliche.jar), it is placed in the lib directory in dist.
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


##Build and Setup: 
Use build.xml at the project root directory to build a new jar.
It creates the ATMSim.jar in ./dist directory.