package util;

import bench.*;
import bench.SharedCounter;
import bench.TestThread2;
import locks.Filter;
import locks.*;

public class Tree<T extends Comparable<T>> implements Cloneable{
	private T data;
	public static int count;
	private Tree<T> right;
	private Tree<T> left;
	private static final String PETERSON = "Peterson";
	//private static final String FILTER = "Filter";
	private static final int THREAD_COUNT = 128;
	private static final int TOTAL_ITERS = 64000;
	private static final int ITERS = TOTAL_ITERS/THREAD_COUNT;
	
	public Tree(T root) {
		this.data = root;
	}

	public T getData() {
		return data;
	}

	public Tree<T> getRightChild(){
		return right;
	}

	public Tree<T> getLeftChild(){
		return left;
	}

	public void add(T node){
		if(data.compareTo(node)<0){
			if(right==null)
				right = new Tree<T>(node);
			else
				right.add(node);
		}
		else{
			if(left==null)
				left = new Tree<T>(node);
			else
				left.add(node);
		}
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		Filter.THREAD_COUNT = THREAD_COUNT;
		TreeLock.THREAD_COUNT = THREAD_COUNT;
		String lockClass = (args.length==0 ? PETERSON : args[0]);
		final Counter counter = new SharedCounter(0, (Lock)Class.forName("edu.vt.ece.locks." + lockClass).newInstance());

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
		Tree<Integer> tree = new Tree<Integer>(0);;
		for (int p = 1; p<THREAD_COUNT - 1; p++){
			tree.add(p);
		}
		
		// print left branch from the root till the left-most leaf
		Tree<Integer> itr = tree;
		do{
			if (itr.getData() == 0){
				
			}
			//System.out.println(itr.getData());
		}while((itr = itr.getLeftChild())!=null);
		// print right branch from the root till the right-most leaf
		itr = tree;
		do{
			//System.out.println(itr.getData());
		}while((itr = itr.getRightChild())!=null);
	}
}