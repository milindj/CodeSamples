package record;

public class UnitTimePeriodRecord {

	public enum PeakPeriodType{
		PEAK, NOT_PEAK, UNDETERMINED
	}
	
	//avg price
// count of hotels	
// peak boolean 
// distance btwn hotels  
	// period time start
	// period time end
	private double sumPrice;
	private Integer countHotels;
	private PeakPeriodType peakPeriod; 
	private double sumDistBtwnHotels;
	private Integer intervalStartStamp;
	private Integer intervalEndStamp;
	public Integer getIntervalEndStamp() {
		return intervalEndStamp;
	}

	private Integer previousCarEndStamp;
	private Integer interval;
	private Boolean capacityFull;
	
	
	public double getSumPrice() {
		return sumPrice;
	}

	public Integer getCountHotels() {
		return countHotels;
	}

	public PeakPeriodType getPeakPeriod() {
		return peakPeriod;
	}

	public double getSumDistBtwnHotels() {
		return sumDistBtwnHotels;
	}

	public Integer getIntervalStartStamp() {
		return intervalStartStamp;
	}

	public Integer getPreviousCarEndStamp() {
		return previousCarEndStamp;
	}

	public Integer getInterval() {
		return interval;
	}

	public Boolean getCapacityFull() {
		return capacityFull;
	}

	public void setCapacityFull(Boolean capacityFull) {
		this.capacityFull = capacityFull;
	}
	
	public UnitTimePeriodRecord(Integer recordStartStamp, Integer interval) {
		this.intervalStartStamp  = (int) (interval * Math.floor(recordStartStamp/interval));
		this.intervalEndStamp = intervalStartStamp + interval;
		this.interval = interval;
		this.sumPrice = 0;
		this.countHotels = 0;
		this.sumDistBtwnHotels = 0;
		this.capacityFull = false;
	}

	public boolean addData(HotelRecord hotelRecord) {
		if(hotelRecord.getFrontTimeStamp() - intervalStartStamp > interval) {
			// This record is marked full as period interval has exceeded. 
			this.capacityFull = true;
			//Return false to indicate data was not added.
			return false;
		}
		int front = hotelRecord.getFrontTimeStamp();
		int back = hotelRecord.getBackTimeStamp();
		double speed = hotelRecord.getSpeed();
		if (this.previousCarEndStamp != null && this.previousCarEndStamp != 0 ){
			this.sumDistBtwnHotels = this.sumDistBtwnHotels + (hotelRecord.getFrontTimeStamp() - this.previousCarEndStamp) * hotelRecord.getSpeed();
		}
		this.previousCarEndStamp = hotelRecord.getBackTimeStamp();
		this.sumPrice = this.sumPrice + hotelRecord.getSpeed();
		this.countHotels++;
		return true;
	}
	
}
