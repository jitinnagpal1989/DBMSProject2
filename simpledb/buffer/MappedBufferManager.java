package simpledb.buffer;

import java.util.LinkedHashMap;

import simpledb.file.*;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
class MappedBufferManager {
   private int capacity;
   private int numUnpinned;
   private LinkedHashMap<Block, Buffer> bufferpoolMap;
   /**
    * Creates a buffer manager having the specified number 
    * of buffer slots.
    * This constructor depends on both the {@link FileMgr} and
    * {@link simpledb.log.LogMgr LogMgr} objects 
    * that it gets from the class
    * {@link simpledb.server.SimpleDB}.
    * Those objects are created during system initialization.
    * Thus this constructor cannot be called until 
    * {@link simpledb.server.SimpleDB#initFileAndLogMgr(String)} or
    * is called first.
    * @param numbuffs the number of buffer slots to allocate
    */
   MappedBufferManager(int numbuffs) {
      capacity = numbuffs;
      numUnpinned=0;
      bufferpoolMap = new LinkedHashMap<Block, Buffer>();
   }
   
   /**
    * Flushes the dirty buffers modified by the specified transaction.
    * @param txnum the transaction's id number
    */
   synchronized void flushAll(int txnum) {
      for (Buffer buff : bufferpoolMap.values())
         if (buff.isModifiedBy(txnum))
        	 buff.flush();
   }
   
   /**
    * Pins a buffer to the specified block. 
    * If there is already a buffer assigned to that block
    * then that buffer is used;  
    * otherwise, an unpinned buffer from the pool is chosen.
    * Returns a null value if there are no available buffers.
    * @param blk a reference to a disk block
    * @return the pinned buffer
    */
   synchronized Buffer pin(Block blk) {
      Buffer buff = findExistingBuffer(blk);
      if (buff == null) {
    	  if (capacity > bufferpoolMap.size())
    		  buff =  new Buffer();
    	  else {
    		  buff = chooseUnpinnedBuffer();
    		  if (buff == null)
    	            return null;
    		  bufferpoolMap.remove(buff.block());
    		  numUnpinned--;
    	  }
    	  
    	  buff.assignToBlock(blk);
    	  bufferpoolMap.put(blk, buff);
      }
      buff.pin();
      return buff;
   }
   
   /**
    * Allocates a new block in the specified file, and
    * pins a buffer to it. 
    * Returns null (without allocating the block) if 
    * there are no available buffers.
    * @param filename the name of the file
    * @param fmtr a pageformatter object, used to format the new block
    * @return the pinned buffer
    */
   synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
	  Buffer buff;
	  if (capacity > bufferpoolMap.size())
 		  buff =  new Buffer();
 	  else {
		  buff = chooseUnpinnedBuffer();
		  if (buff == null)
	            return null;
		  bufferpoolMap.remove(buff.block());
		  numUnpinned--;
	  }
      buff.assignToNew(filename, fmtr);
      bufferpoolMap.put(buff.block(), buff);
      buff.pin();
      return buff;
   }
   
   /**
    * Unpins the specified buffer.
    * @param buff the buffer to be unpinned
    */
   synchronized void unpin(Buffer buff) {
      buff.unpin();
      if(!buff.isPinned())
    	  numUnpinned++;
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   int available() {
      return capacity + numUnpinned - bufferpoolMap.size() ;
   }
   
   private Buffer findExistingBuffer(Block blk) {
      return bufferpoolMap.get(blk);
   }
   
   private Buffer chooseUnpinnedBuffer() {
      for (Buffer buff : bufferpoolMap.values())
         if (!buff.isPinned())
        	 return buff;
      return null;
   }
}