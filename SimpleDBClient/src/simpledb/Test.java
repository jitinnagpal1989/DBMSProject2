package simpledb;


import simpledb.buffer.Buffer;
import simpledb.buffer.BufferAbortException;
import simpledb.buffer.BufferMgr;
import simpledb.file.Block;
import simpledb.server.SimpleDB;


public class Test {
	
	public static void main(String args[]) {
		SimpleDB.init("simpleDB");
		//Creating a Block -
		Block blk1 = new Block("filename1", 1);
		Block blk2 = new Block("filename2", 2);
		Block blk3 = new Block("filename3", 3);
		Block blk4 = new Block("filename4", 4);
		Block blk5 = new Block("filename5", 5);
		Block blk6 = new Block("filename6", 6);
		Block blk7 = new Block("filename7", 7);
		Block blk8 = new Block("filename8", 8);
		Block blk9 = new Block("filename9", 9);
		Block blk10 = new Block("filename10", 10);
		Block blk11 = new Block("filename11", 11);
		Block blk12 = new Block("filename12", 12);
		//Creating a basicBufferMgr
		BufferMgr basicBufferMgr = SimpleDB.bufferMgr() ;
		//Pin a Block
		Buffer buf1=basicBufferMgr.pin(blk1);
		Buffer buf2=basicBufferMgr.pin(blk2);
		Buffer buf3=basicBufferMgr.pin(blk3);
		Buffer buf4=basicBufferMgr.pin(blk4);
		Buffer buf5=basicBufferMgr.pin(blk5);
		Buffer buf6=basicBufferMgr.pin(blk6);
		Buffer buf7=basicBufferMgr.pin(blk7);
		Buffer buf8=basicBufferMgr.pin(blk8);
		basicBufferMgr.unpin(buf4);
		basicBufferMgr.unpin(buf5);
		basicBufferMgr.unpin(buf3);
		basicBufferMgr.unpin(buf2);
		Buffer buf9=basicBufferMgr.pin(blk9);
		Buffer buf10=basicBufferMgr.pin(blk10);
		Buffer buf11=basicBufferMgr.pin(blk11);
		Buffer buf12=basicBufferMgr.pin(blk12);
		
		
		//basicBufferMgr.pin(blk9);
		//Catching Buffer Exception
		try {
		//basicBufferMgr.pin(blk9);
		}
		catch (BufferAbortException e) {
			System.out.println(e);
		}
		
		System.out.println("Working correctly!");
		
	}
}
