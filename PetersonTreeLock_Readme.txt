Name of class for tree -------TreeLock
1) To run Test2 or Test
java <Test2 or Test> <Lockname> <ThreadCount>
eg:  	java Test2 TreeLock 128
If thread count is not passed Thread count is set to 2.

2) Class name L_EXCLUSION 
java <Test2 or Test> <Lockname> <ThreadCount> <Threadallowed>
eg 	java Test2 L_EXCLUSION 8 4
If thread allowed is no given it is taken as "threadcount/2"
To see the correctness......uncomment sleep section in shared counter class
******Imp
Two implementations of L_Exclusion have been provided
** Each level except (N-L) allow N-1 threads to the next level and (N-L)th level allow L threads in CS.
** Each level allows L threads to next level.
Default is 1st implementation. Uncomment the section to run which ever algorithm you want.
******

3) All other probes added for PartII Q2 have been commented. This is done so that print and sleep statements do not delay implementation of TreeLock