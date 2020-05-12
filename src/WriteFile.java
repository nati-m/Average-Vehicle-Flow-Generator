import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class WriteFile {
	
	protected static final WriteFile INSTANCE = new WriteFile();
	
	private PrintWriter writer;
	private boolean printWriterError = false;
	
	private WriteFile(){}
	
	protected void writeToFile(String nameOfFile, String trafficSensorName, Map<String, QuarterHourCollection> timesAndValues){
		
		initPrintWriter(nameOfFile);
		if(printWriterError){
			return;
		}
		
		writer.println("Traffic demands average Tuesday to Thursday for " + trafficSensorName + ",");
		writer.println(",,");
		
		//now begin the main table
		writer.println("Time, Average Vehicle Numbers Tue-Thu for " + trafficSensorName);
		for (Map.Entry<String, QuarterHourCollection> entry : timesAndValues.entrySet()) {
		    writer.println(entry.getValue().getTime() + "," + entry.getValue().findAverage());
		}
		
		
		
		
		writer.close();
		
	}
	
	protected void initPrintWriter(String fileName){
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("initPrintWriter error - file not found exception");
			printWriterError = true;
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("initPrintWriter error - unsupported encoding exception");
			printWriterError = true;
			e.printStackTrace();
		}
		
	}
	
	private String getFormattedStringOfDouble(double value){
		String toReturn = "" + value;
		
		int numberOfCharactersNeeded = 5;
		if(toReturn.charAt(1) == '.'){ //if it's a single digit number we need one less character
			numberOfCharactersNeeded = 4;
		}
		
		for (int i = toReturn.length(); i < numberOfCharactersNeeded; i++){
			toReturn = toReturn + "0";
		}
		
		toReturn += ":00";
		
		toReturn = toReturn.replace('.', ':');
		
		
		return toReturn;
	}

}
