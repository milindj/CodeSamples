package record;
import java.util.concurrent.ArrayBlockingQueue;


public class DataProcessor {
	private ArrayBlockingQueue<HotelRecord> recordWriteQueue;
	private static Integer QUEUE_CAPACITY = 200;
	
	public DataProcessor(){
		this.recordWriteQueue = new ArrayBlockingQueue<HotelRecord>(QUEUE_CAPACITY);
	}
	
	public void addToQueue(HotelRecord hotelRecord){
		try {
			this.recordWriteQueue.put((hotelRecord));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
