package locks;
import java.util.concurrent.atomic.AtomicInteger;
import bench.ThreadId;
public class L_EXCLUSION implements Lock { 
	private AtomicInteger[] level;
	private AtomicInteger In_CS;
	private AtomicInteger[] victim;
	public static int THREAD_COUNT;
	public static int THREAD_ALLOWED;
	public L_EXCLUSION() {
		this(THREAD_COUNT, THREAD_ALLOWED);
	}
	public L_EXCLUSION(int n, int l){
		level = new AtomicInteger[n+1]; 
		victim = new AtomicInteger[n-l+1]; 
		for (int i = 0; i < n; i++) { 
			level[i] = new AtomicInteger();
			level[i].set(0);
		} 
		for (int i = 0; i < n-l+1; i++) { 
			victim[i] = new AtomicInteger();
			victim[i].set(0);
		} 
		In_CS = new AtomicInteger();
		In_CS.set(0);
	}
	@Override
	public void lock() {
		int me = ((ThreadId)Thread.currentThread()).getThreadId(); 
		for (int i = 1; i < THREAD_COUNT-THREAD_ALLOWED+1; i++){
			level[me].set(i);
			victim[i].set(me);
//			Each level allows L threads to next level. Uncomment below.
			int higher = THREAD_ALLOWED+1;
			while (higher>THREAD_ALLOWED && victim[i].get() == me) { 
				higher = 0; 
				for (int k = 0; k < THREAD_COUNT; k++) { 
					if (level[k].get() >= i) 
						higher++; 
				} 
			}
//			Each level except (N-L) allow N-1 threads to the next level and (N-L)th level allow L threads in CS.
//			if (i < THREAD_COUNT-THREAD_ALLOWED - 1){
//				boolean found = false;
//				do{
//					for(int k=0; k<THREAD_COUNT; k++) {
//						if(k!=me && (found = (level[k].get() >= i && victim[i].get() == me)))
//							break;
//					} 
//				} while(found);
//			}
//			else{
//				int higher = THREAD_ALLOWED+1;
//				while (higher>THREAD_ALLOWED && victim[i].get() == me) { 
//					higher = 0; 
//					for (int k = 0; k < THREAD_COUNT; k++) { 
//						if (level[k].get() >= i) 
//							higher++; 
//					} 
//				}
//			}
			

		}
	}
	@Override
	public void unlock() { 
		int me = ((ThreadId)Thread.currentThread()).getThreadId();
		level[me].set(0);
		In_CS.set(In_CS.get()-1);
	}
}
