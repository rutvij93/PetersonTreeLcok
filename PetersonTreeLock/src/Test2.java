import bench.Counter;
import bench.SharedCounter;
import bench.TestThread2;
import locks.*;

public class Test2 {
	//private static final String LOCK_ONE = "LockOne";
	//private static final String LOCK_TWO = "LockTwo";
	private static final String PETERSON = "Peterson";
	//private static final String FILTER = "Filter";
	private static int THREAD_COUNT;
	private static final int TOTAL_ITERS = 640;
	private static int ITERS;
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		if (args.length >= 2)
			THREAD_COUNT = Integer.parseInt(args[1]);
		else
			THREAD_COUNT = 2;
		ITERS = TOTAL_ITERS/THREAD_COUNT;
		Filter.THREAD_COUNT = THREAD_COUNT;
		Peterson.THREAD_COUNT = THREAD_COUNT;
		TreeLock.THREAD_COUNT = THREAD_COUNT;
		L_EXCLUSION.THREAD_COUNT = THREAD_COUNT;
		if (args.length == 3)
			L_EXCLUSION.THREAD_ALLOWED = Integer.parseInt(args[2]);
		else
			L_EXCLUSION.THREAD_ALLOWED = THREAD_COUNT/2;
		String lockClass = (args.length==0 ? PETERSON : args[0]);
		final Counter counter = new SharedCounter(0, (Lock)Class.forName("locks." + lockClass).newInstance());

		final TestThread2[] threads = new TestThread2[THREAD_COUNT];

		for(int t=0; t<THREAD_COUNT; t++) {
			threads[t] = new TestThread2(counter, ITERS);
		}

		for(int t=0; t<THREAD_COUNT; t++) {
			threads[t].start();
		}

		long totalTime = 0;
		for(int t=0; t<THREAD_COUNT; t++) {
			threads[t].join();
			totalTime += threads[t].getElapsedTime();
		}

		System.out.println("Average time per thread is " + totalTime/THREAD_COUNT + "ms");

	}
}
