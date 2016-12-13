package locks;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import bench.ThreadId;

public class Peterson implements Lock{

	public int a = 0;
	public int b = 10;
	public static int THREAD_COUNT;
	private AtomicBoolean flag[] = new AtomicBoolean[THREAD_COUNT+1];
	private AtomicInteger victim;
	public Peterson() {
		for (int i = 0; i <= THREAD_COUNT; i++){
			flag[i] = new AtomicBoolean();
			flag[i].set(false);
		}
		victim = new AtomicInteger();
	}
	
	@Override
	public void lock() {
		int i = ((ThreadId)Thread.currentThread()).getThreadId();
		int j = 1-i;
		flag[i].set(true);
		victim.set(i);
//		System.out.println("Thread " + i + " completed doorway execution");
		while(flag[j].get() && victim.get() == i);
		a++;
		if (a == b){
			//System.out.println("Number of locks Aquired " + a);
			b = b + 10;
		}
//		System.out.println("Thread " + i + " enters critical section");
	}

	@Override
	public void unlock() {
		int i = ((ThreadId)Thread.currentThread()).getThreadId();
		flag[i].set(false);
	}
	
	public void lock(int i) {
		i = ((ThreadId)Thread.currentThread()).getThreadId();
        boolean j = true;
        flag[i].set(true);
        victim.set(i);
        while (j && victim.get() == i){
        	int a = 0;
        	for (int k = 0; k < THREAD_COUNT ;k++){
	        	if (k != i && flag[k].get()){
	        		j = true;
	        		a++;
	        	}	
	        }
        	if (a == 0){
        		j = false;
        	}
        }
//      System.out.println("Thread " + i + " waiting");
    }
	
	public void unlock(int i){
		i = ((ThreadId)Thread.currentThread()).getThreadId();
        flag[i].set(false);  
//      System.out.println("Thread " + i + " released");
    }

}
