import java.util.ArrayList;

public class QuarterHourCollection {
	
	private double time;
	private ArrayList<Integer> vehicleNumbersList;
	
	protected QuarterHourCollection(double time){
		this.time = time;
		vehicleNumbersList = new ArrayList<>();
	}
	
	protected void addToVehicleNumbersList(Integer vehicleNumber){
		vehicleNumbersList.add(vehicleNumber);
	}
	
	protected double getTime(){
		return time;
	}
	
	protected double findAverage(){
		if(vehicleNumbersList.size() == 0){
			return 0.0;
		}
		
		double toReturn = 0.0;
		
		for (int i = 0; i < vehicleNumbersList.size(); i++){
			toReturn += vehicleNumbersList.get(i).doubleValue();
		}
		
		toReturn = toReturn / vehicleNumbersList.size();
		
		return toReturn;
		
	}

}
