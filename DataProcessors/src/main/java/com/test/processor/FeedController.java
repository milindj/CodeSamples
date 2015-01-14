package com.test.processor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.test.processor.Observation;
import com.test.processor.ProcessFeed;

public class FeedController {

	ArrayBlockingQueue<Observation> feedQueue;
	ProcessFeed processFeed;
	
	public FeedController(Integer bufferQueueSize, ProcessFeed processFeed) {
		this.feedQueue = new ArrayBlockingQueue<Observation>(bufferQueueSize);
		this.processFeed = processFeed;
	}

	public void startController(){
		//BlockingQueue<Runnable> threadQueue = new ArrayBlockingQueue<Runnable>(100);
		LinkedBlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(20, 50, 20, TimeUnit.SECONDS, threadQueue);
		//tpe.execute();
	}
	
	
	
}
