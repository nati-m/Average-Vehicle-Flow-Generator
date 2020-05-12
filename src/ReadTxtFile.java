import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadTxtFile {
	
	private static File monthData;
	private static String trafficSensorName;
	private static Map<String, QuarterHourCollection> timesAndValues;

	public static void main(String[] args) {
		Scanner scanMonth;
		//monthData = new File("C:/dev/HUDDERSFIELDTRAFFIC/WRAC1_C1_20_02.txt");
		monthData = new File("C:/dev/HUDDERSFIELDTRAFFIC/MonthTxts/WRAC1_D1_20_02.txt");
		
		try {
			scanMonth = new Scanner(monthData);
		} catch (FileNotFoundException e) {
			System.out.println("Input Month Data File Not Found.");
			return;
		}
		
		System.out.println("File Successfully Found.");
		
		
		initTimes();
		
		getTuesdayToThursdayData(scanMonth);
		
		printAverages();
		
		String[] trafficSensorNameNoColon = trafficSensorName.split(":");
		String nameOfFile = trafficSensorNameNoColon[0] + trafficSensorNameNoColon[1] + "-AVERAGES.csv";
		WriteFile.INSTANCE.writeToFile(nameOfFile, trafficSensorName, timesAndValues);

	}
	
	private static void printAverages(){
		for (Map.Entry<String, QuarterHourCollection> entry : timesAndValues.entrySet()) {
		    System.out.println(entry.getKey()+" Vehicle Average: "+entry.getValue().findAverage());
		}
	}
	
	/*
	 * This creates a LinkedHashMap of QuarterHourCollection objects, each for a fifteen minute time between 0.00am
	 * and 23.45pm. This will be used in storing data. LinkedHashMaps keep the order of pair insertion, so the times
	 * will not get out of order as they would with a normal HashMap.
	 */
	private static void initTimes(){
		timesAndValues = new LinkedHashMap<String, QuarterHourCollection>();
		
		//There are 96 time values in all. Each is 15 minutes, or 25% (0.25) of an hour apart, starting at 0.
		//This next for loop generates them all. It happens 96 times for this reason
		
		double toAdd = 0.0;
		
		for (int i = 0; i < 96; i++){
			String time = getFormattedStringOfDouble(toAdd);
			QuarterHourCollection quarterHourCollection = new QuarterHourCollection(toAdd);
			timesAndValues.put(time, quarterHourCollection);
			toAdd += 0.25;
			System.out.println(time);
		}
		
	}
	
	/*
	 * This returns a String of a double that is formatted to be 5 characters long, i.e. to 3 decimal places.
	 * This is how the text file formats its times - decimal numbers to 3 places.
	 */
	private static String getFormattedStringOfDouble(double value){
		String toReturn = "" + value;
		
		int numberOfCharactersNeeded = 6;
		if(toReturn.charAt(1) == '.'){ //if it's a single digit number we need one less character
			numberOfCharactersNeeded = 5;
		}
		
		for (int i = toReturn.length(); i < numberOfCharactersNeeded; i++){
			toReturn = toReturn + "0";
		}
		
		
		return toReturn;
	}
	
	private static void getTuesdayToThursdayData(Scanner scan){
		scan.next();
		trafficSensorName = scan.next(); //It's the second word in the txt file
		System.out.println("Traffic Sensor Name = " + trafficSensorName); 
		
		while(scan.hasNext()){
			
			String nextLine = scan.nextLine();
			
			if(nextLine.contains("\"TU\"" ) || nextLine.contains("\"WE\"" ) || nextLine.contains("\"TH\"" )){
				
				String[] partsArray = nextLine.split("\\s+"); //This splits the line if there are spaces of any length between items
				String time = partsArray[3];
				
				int vehicles;
				
				try {
					vehicles = Integer.parseInt(partsArray[5]);
				} catch (NumberFormatException e){
					System.out.println("Error - number of vehicles not written as a number on this line:");
					System.out.println(nextLine);
					return;
				}
				
				//This adds the number of vehicles to the list of this data for this QuarterHour time
				timesAndValues.get(time).addToVehicleNumbersList(new Integer(vehicles));
				
				System.out.println("From hashmap " + timesAndValues.get(time).getTime());
				
				System.out.println("Time: " + time + " Vehicles: " + vehicles);
			
			}		
			
		}
		
		
	}

}
