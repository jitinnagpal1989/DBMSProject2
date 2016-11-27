-----------------------------
Unity IDs - apandey6, pbhanda2, sahuja3, tgoel
-----------------------------

List of Files Changed - 

1. BasicBufferMgr.java
	Changed the bufferpool array to a linkedhashmap bufferpoolmap with key as block and buffer as value. 
	The benefit is that searching whether a block is in buffer happens in O(1) time.
	Also, linkedhashmaps have property of maintaining order of keys in which they are inserted, hence giving us a FIFO implementation.
	Changed several iterations to work with new data structure.
	Note : We have not preserved the earlier data structure as we felt it was unnecessary.

2. LogMgr.java

3. LogIterator.java

4. Transaction.java

5. RecoveryMgr.java

6. SetIntRecord.java

7. LogRecordIterator.java

8. CheckpointRecord.java

9. CommitRecord.java

10. LogRecord.java

11. RollbackRecord.java

12. SetIntRecord.java

13. SetStringRecord.java

14. StartRecord.java

-----------------------------

Testing - 
