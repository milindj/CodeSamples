package com.test.processor;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.test.processor.Observation;
import com.test.processor.ProcessFeed;

/**
 * Receives input from a livefeed(Observations) into a Queue, 
 * which is then further taken up by a pool of threads to process and store it in a more coarse form.
 * @author Milind
 */
public class FeedController {

	ArrayBlockingQueue<Observation> feedQueue;
	PersistenceStore persistenceStore;
	
	public FeedController(Integer bufferQueueSize, PersistenceStore persistenceStore) {
		this.feedQueue = new ArrayBlockingQueue<Observation>(bufferQueueSize);
		this.persistenceStore = persistenceStore;
	}

	public void startController() {
		//BlockingQueue<Runnable> threadQueue = new ArrayBlockingQueue<Runnable>(100);
		LinkedBlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(20, 50, 20, TimeUnit.SECONDS, threadQueue);
		
		//Poll the feed queue and create tasks to be processed.
		Observation observation = null;
		try {
			while((observation = this.feedQueue.poll(3, TimeUnit.MINUTES)) != null){				
				
				//Create a local buffer to process upto say 30 observations.
				ArrayList<Observation> localBuffer = new ArrayList<Observation>();
				localBuffer.add(observation);
				this.feedQueue.drainTo(localBuffer, 30);
				
				//Create a new task/thread and add to execute it.
				ProcessFeed processor = new ProcessFeed(localBuffer, persistenceStore);
				tpe.execute(processor);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receive an observation to be processed. 
	 * This operation returns an acknowledgment of false/true.
	 * @param observation
	 * @return
	 */
	public boolean addToFeed(Observation observation){
		return this.feedQueue.offer(observation);		
	}
}
