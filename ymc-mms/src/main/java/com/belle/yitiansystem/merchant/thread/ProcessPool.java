package com.belle.yitiansystem.merchant.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessPool {

	public static ExecutorService processPool = Executors.newFixedThreadPool(5);
	
}
