Date Calculator

Given 2 dates, in the format dd/MM/yyyy, this calculator returns the number of days elapsed between the dates, excluding both begin and end dates

Build code using Maven : mvn clean package

Instructions to run :
$ java -jar DateCalculator-0.0.1-SNAPSHOT.jar

Sample output :

Enter Experiment begin date as dd/mm/yyyy : 
12/12/2000
Enter Experiment end date as dd/mm/yyyy : 
01/01/2001
Dec 17, 2017 11:46:57 AM com.snug.DateCalculator calculateDuration
INFO: Number of whole years passed : 0
Dec 17, 2017 11:46:57 AM com.snug.DateCalculator calculateDuration
INFO: Number of whole leap years passed : 0
Dec 17, 2017 11:46:57 AM com.snug.DateCalculatorHelperService getDaysLeftInMonth
INFO: Number of whole days passed in start month: 19
-----------------------------------------------
Result : Duration of Experiment in days : 19

Limitations:
1. Current implementation expects date to be in the format dd/MM/yyyy
2. Year should be between 1901 to 2999

Enhancements:
1. System should be able to handle all date formats ( ask user to enter the format or understand itself )
