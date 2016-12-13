package locks;

import bench.ThreadId;
import locks.Peterson;

//import java.util.concurrent.TimeUnit;


public class TreeLock implements Lock{
//	private AtomicReferenced<Peterson>[][] tree;
	public static int THREAD_COUNT;
	public int height_of_tree;
	public volatile Peterson[][] tree; 
	
	public static int binlog( int bits ) // returns 0 for bits=0
	{
	    int log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
    
	public TreeLock(){
//		try {
//		    TimeUnit.NANOSECONDS.sleep(100);
//		} catch (InterruptedException e) {
//		    //Handle exception
//		}
		height_of_tree =  binlog(THREAD_COUNT) ;
		tree = new Peterson[(height_of_tree)][THREAD_COUNT/2];
		for(int i = 0; i < height_of_tree; i++){
			THREAD_COUNT = THREAD_COUNT/2;
			for (int m = 0; m < THREAD_COUNT; m++){
				tree[i][m] = new Peterson();
			}
		}
	}
	
	
	public void lock(){
//		try {
//	    TimeUnit.NANOSECONDS.sleep(100);
//	} catch (InterruptedException e) {
//	    //Handle exception((ThreadId)Thread.currentThread()).getThreadId()
//	}
		int i = ((ThreadId)Thread.currentThread()).getThreadId();
		for(int j=0; j < height_of_tree; j++)
		{
			i = (int) Math.floor(((ThreadId)Thread.currentThread()).getThreadId()/(Math.pow(2, j+1)));
			tree[j][i].lock(((ThreadId)Thread.currentThread()).getThreadId());
		}
//		try {
//	    TimeUnit.NANOSECONDS.sleep(100);
//	} catch (InterruptedException e) {
//	    //Handle exception
//	}
	}
	public void unlock()
	{
//		try {
//	    TimeUnit.NANOSECONDS.sleep(100);
//	} catch (InterruptedException e) {
//	    //Handle exception
//	}
		int i = ((ThreadId)Thread.currentThread()).getThreadId();
		for (int k = height_of_tree - 1 ; k>=0;k--)
		{
			i = (int) Math.floor(((ThreadId)Thread.currentThread()).getThreadId()/(Math.pow(2, (k + 1))));
			tree[k][i].unlock(((ThreadId)Thread.currentThread()).getThreadId());
		}
//		try {
//	    TimeUnit.NANOSECONDS.sleep(100);
//	} catch (InterruptedException e) {
//	    //Handle exception
//	}
	}
}