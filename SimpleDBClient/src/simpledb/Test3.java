package simpledb;

import simpledb.buffer.Buffer;
import simpledb.buffer.BufferMgr;
import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.recovery.LogRecordIterator;
import simpledb.tx.recovery.RecoveryMgr;

public class Test3 {
	public static void main(String[] args) {
		//Init a simpleDB Client.
		SimpleDB.init("simpleDB");
//		Creating a Block.
		Block blk1 = new Block("filename.txt", 1);
		Block blk2 = new Block("filename", 2);
//		Create a RecoveryManager with id = 123.
		RecoveryMgr rm1 = new RecoveryMgr(1);
		RecoveryMgr rm2 = new RecoveryMgr(2);
		RecoveryMgr rm3 = new RecoveryMgr(3);
//		Commit a transaction.
		//rm1.commit();
//		Recover a transaction.
		//rm1.recover();
//		Sample setInt
		BufferMgr buffmgr = new BufferMgr(8);
		Buffer buff = buffmgr.pin(blk1);
		Buffer buff2 = buffmgr.pin(blk2);
		
		int lsn = rm1.setInt(buff, 4, 5, 10);
		buff.setInt(4, 10, 1, lsn);
		
		rm1.commit();
		
		int lsn2 = rm2.setInt(buff2, 14, 5, 10);
		//buff.setInt(14, 10, 2, lsn2);
		int lsn3 = rm2.setString(buff2, 24, "Hello", "World");
		//buff2.setString(24, "World", 2, lsn3);
		rm1.recover();
		buff = buffmgr.pin(blk1);
		System.out.println(buff.getInt(4));
		
		rm2.recover();
		buff2 = buffmgr.pin(blk2);
		System.out.println(buff2.getInt(14));
		System.out.println(buff2.getString(24));
//		Flushing all transactions
		//buffmgr.flushAll(123);
//		Using Log Record Iterator to print records .
		
	}

}
