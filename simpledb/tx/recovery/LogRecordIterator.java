package simpledb.tx.recovery;

import static simpledb.tx.recovery.LogRecord.*;
import java.util.ListIterator;

import simpledb.log.BasicLogRecord;
import simpledb.server.SimpleDB;

/**
 * A class that provides the ability to read records
 * from the log in reverse order.
 * Unlike the similar class 
 * {@link simpledb.log.LogIterator LogIterator},  
 * this class understands the meaning of the log records.
 * @author Edward Sciore
 */
public class LogRecordIterator implements ListIterator<LogRecord> {
   private ListIterator<BasicLogRecord> iter = SimpleDB.logMgr().iterator();
   
   public boolean hasNext() {
      return iter.hasNext();
   }
   
   /**
    * Constructs a log record from the values in the 
    * current basic log record.
    * The method first reads an integer, which denotes
    * the type of the log record.  Based on that type,
    * the method calls the appropriate LogRecord constructor
    * to read the remaining values.
    * @return the next log record, or null if no more records
    */
   public LogRecord next() {
      BasicLogRecord rec = iter.next();
      int op = rec.nextInt();
      switch (op) {
         case CHECKPOINT:
            return new CheckpointRecord(rec);
         case START:
            return new StartRecord(rec);
         case COMMIT:
            return new CommitRecord(rec);
         case ROLLBACK:
            return new RollbackRecord(rec);
         case SETINT:
            return new SetIntRecord(rec);
         case SETSTRING:
            return new SetStringRecord(rec);
         default:
            return null;
      }
   } 
   
   public void remove() {
      throw new UnsupportedOperationException();
   }

	@Override
	public boolean hasPrevious() {
		return iter.hasPrevious();
	}
	
	@Override
	public LogRecord previous() {
		BasicLogRecord rec = iter.previous();
	      int op = rec.nextInt();
	      switch (op) {
	         case CHECKPOINT:
	            return new CheckpointRecord(rec);
	         case START:
	            return new StartRecord(rec);
	         case COMMIT:
	            return new CommitRecord(rec);
	         case ROLLBACK:
	            return new RollbackRecord(rec);
	         case SETINT:
	            return new SetIntRecord(rec);
	         case SETSTRING:
	            return new SetStringRecord(rec);
	         default:
	            return null;
	      }
	}
	
	@Override
	public int nextIndex() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int previousIndex() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void set(LogRecord e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(LogRecord e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
