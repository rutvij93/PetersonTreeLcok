package bench;
import locks.Lock;

public class SharedCounter extends Counter{
	private Lock lock;
	public SharedCounter(int c, Lock lock) {
		super(c);
		this.lock = lock;
	}
	
	@Override
	public int getAndIncrement() {
		lock.lock();
//		Uncomment the section below to make all threads sleep for 1 sec.
		
//		System.out.println("Thread " + ((ThreadId)Thread.currentThread()).getThreadId() + " sleeping inside CS");
//		try {
//		    TimeUnit.MILLISECONDS.sleep(1000);
//		} catch (InterruptedException e) {
//		    //Handle exception
//		}
//		System.out.println("Thread " + ((ThreadId)Thread.currentThread()).getThreadId() + " completed sleep and exiting CS");
		
		
		int temp = -1;
		try {
			//System.out.println("Thread " + ((ThreadId)Thread.currentThread()).getThreadId() + " inside CS");
			temp = super.getAndIncrement();
		} finally {
			lock.unlock();
		}
		return temp;
	}
}
