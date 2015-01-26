## Vehicle Survey Coding Challenge

### Design and Approach.
The idea was to first capture and formulate the data into a more coarse form which is easy to store as well easy to processed on further, and then calculate appropriate parameters from it.

For example, lets capture and summarize the count of cars for 30mins in a unit period of 5 mins each. 
Lets say the count of cars captured into unit records are:
* 0-5mins=10
* 5-10mins=5
* 10-15mins=2
* 15-20mins=10
* 20-25mins=4
* 25-30mins=1

Now to aggregate them into period of 15 mins:
* 0-15= 17 (10+5+2)
* 15-20= 15 (10+4+1)

Factors considered for the design.
* Scalable for larger data set.
* Plugable enough to get data into different forms.
* Maximum code-reuse and modularity.

Following are the sub-modules:
* DataLoader: 
 This module consists of various classes to basically load, and classify the data into north or south form, and then  further delegate grouping and coarsing of values to the UnittimePeriodProcessors. 
 
* Processors and Aggregator: 
Here multiple UnittimePeriodProcessors processes and stores the data into a coarse form in a unit period.
Further this coarse data is aggregated as desired to larger periods which are multiples of the unit period. 
The aggregator has functions to calculate the final indices like average count, speed, speed distribution etc. 

* Utilities:
They help run the data loader and generate data/result into a CSV form. 

### Building the code.
The code can be built using maven. The root directory of this solution has the file pom.xml.
Execute the following where you see the pom.xml(in the top most dir)
* mvn clean install

### Executing the code. 
#### Executing the maven build jar
After the build, go to the target dir, this dir would be at the same level as pom.xml
Inside the target dir execute the following
* java -jar ./SensorDataLoader.jar ../data.txt ./results.csv 

../data.txt -> is the input data file ; 
./results.csv -> is the output.

#### Executing it on an IDE
Run the Main class com.sample.util.ExecuteDataLoader with the arguments ./data.txt and ./results.csv 

../data.txt -> is the input data file ; 
./results.csv -> is the output.

### Reason to choose this challenge. 
  One of the prime reasons to choose this challenge over another was that, this challenge is more closer to a practical, real-life data-mining problem. The incoming data could be in a form, which is very straight to parse or else tangled yet correctly formed. And again, the size of the data is a factor, which could be huge. Overall the complexity, which this problem offers was worth to give it a try.  
  Additionally, this challenge would allow me to demonstrate my logical as well as programming skills to a greater extent. Hence this was indeed a better choice to be taken up.
