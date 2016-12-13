package locks;

//import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import bench.ThreadId;

public class Filter implements Lock{
	private AtomicInteger[] level;
	private AtomicInteger[] victim;
	public static int THREAD_COUNT;
	public Filter() {
		this(THREAD_COUNT);
	}

	public Filter(int n){
		level = new AtomicInteger[n];
		victim = new AtomicInteger[n];
		for (int i = 0; i < n; i++) {
			level[i] = new AtomicInteger();
			victim[i] = new AtomicInteger();
		}
	}
	
	@Override
	public void lock() {
		int me = ((ThreadId)Thread.currentThread()).getThreadId();
		for(int i=1; i<level.length; i++){
			level[me].set(i);
			victim[i].set(me);
			boolean found = false;
//			if (me == 0){
//				try {
//			    TimeUnit.MICROSECONDS.sleep(10);
//			} catch (InterruptedException e) {
//			    //Handle exception
//			}
//			}
			do{
				for(int k=0; k<level.length; k++) {
					if(k!=me && (found = (level[k].get() >= i && victim[i].get() == me)))
						break;
				} 
			} while(found);
		}
	}

	@Override
	public void unlock() {
		int me = ((ThreadId)Thread.currentThread()).getThreadId();
		level[me].set(0);
	}
}
