import bench.Counter;
import bench.SharedCounter;
import bench.TestThread;
import locks.*;
//import edu.vt.ece.util.*;
//import edu.vt.ece.util.PetersonTree;;

public class Test {

	public static int THREAD_COUNT;
//	private static final String LOCK_ONE = "LockOne";
//	private static final String LOCK_TWO = "LockTwo";
	private static final String PETERSON = "TASLock";
//	private static final String FILTER = "Filter";
	

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (args.length >= 2)
			THREAD_COUNT = Integer.parseInt(args[1]);
		else
			THREAD_COUNT = 10;
		Filter.THREAD_COUNT = THREAD_COUNT;
		Peterson.THREAD_COUNT = THREAD_COUNT;
		TreeLock.THREAD_COUNT = THREAD_COUNT;
		L_EXCLUSION.THREAD_COUNT = THREAD_COUNT;
		if (args.length == 3)
			L_EXCLUSION.THREAD_ALLOWED = Integer.parseInt(args[2]);
		else
			L_EXCLUSION.THREAD_ALLOWED = THREAD_COUNT/2;
		String lockClass = (args.length==0 ? PETERSON : args[0]);
		final Counter counter = new SharedCounter(0, (Lock)Class.forName("edu.vt.ece.locks." + lockClass).newInstance());
//		final Counter counter = new SharedCounter(0, (Lock)Class.forName("edu.vt.ece.locks." + "PetersonTree").newInstance());
		for(int t=0; t<THREAD_COUNT; t++)
			new TestThread(counter).start();
	}
}
