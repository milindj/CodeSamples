### Problem statement.
Write a simple chain of fact processors that will take observations coming in from the field, and dimension them in to a framework suitable for a BI system to report insights. 

Assume observations come in to the system as a POJO that looks like this

```
public class Observation { 

String siteUUID; // UUID of the site of the observation 
String observationUUID; 
Double observedValue; 
Date observationTime; 
GeoLocation location; 
String observeddUnits;// SI description of the recorded units (e.g. m/s) 

} ``` 


Assume we have air temperature, wind speed and soil moisture readings coming in from multiple locations, and we want to dimensionalise readings to enable BI insight about growing conditionsFeel free to use pseudo-frameworks that you are familiar with, and database/persistence frameworks as stubs/assumptions