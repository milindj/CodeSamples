package record;

public class UnitTimePeriodRecordInstance implements UnitTimePeriodRecord{

	public enum PeakPeriodType{
		PEAK, NOT_PEAK, UNDETERMINED
	}
	
	//avg price
// count of hotels	
// peak boolean 
// distance btwn hotels  
	// period time start
	// period time end
	
	private Integer price;
	private Integer countHotels;
	private PeakPeriodType peakPeriod; 
	private Integer distanceBetweenHotels;
	private Integer intervalStartStamp;
	private Integer intervalEndStamp;
	private Integer interval;
	
	public UnitTimePeriodRecordInstance(Integer interval){
		this.interval = interval;
	}
	
	@Override
	public void processData() {
		// TODO Auto-generated method stub
		
	}
	
	public Integer getInterval() {
		return interval;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getCountHotels() {
		return countHotels;
	}
	public void setCountHotels(Integer countHotels) {
		this.countHotels = countHotels;
	}
	public PeakPeriodType getPeakPeriod() {
		return peakPeriod;
	}
	public void setPeakPeriod(PeakPeriodType peakPeriod) {
		this.peakPeriod = peakPeriod;
	}
	public Integer getDistanceBetweenHotels() {
		return distanceBetweenHotels;
	}
	public void setDistanceBetweenHotels(Integer distanceBetweenHotels) {
		this.distanceBetweenHotels = distanceBetweenHotels;
	}
	public Integer getIntervalStartStamp() {
		return intervalStartStamp;
	}
	public void setIntervalStartStamp(Integer intervalStartStamp) {
		this.intervalStartStamp = intervalStartStamp;
	}
	public Integer getIntervalEndStamp() {
		return intervalEndStamp;
	}
	public void setIntervalEndStamp(Integer intervalEndStamp) {
		this.intervalEndStamp = intervalEndStamp;
	}


	
	
	
}
