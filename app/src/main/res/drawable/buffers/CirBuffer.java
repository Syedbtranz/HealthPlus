// CirBuffer.java
// Alive Technologies
package drawable.buffers;

public class CirBuffer {
	private byte[] mBuffer;
	private int mWriteCount;
	private int mReadCount;
	private int mSize;
	private int mUsedCount;
	private int mTotalCount;
	
	public CirBuffer() {
		mBuffer = null;
		mUsedCount = 0;
		mTotalCount = 0;
	}

	boolean create(int size)
	{
		if(size!=mSize)
		{
			if(mBuffer!=null)
			{
				mBuffer = null;
			}
			mSize = size;
			mBuffer = new byte[mSize];
			if(mBuffer==null)
			{
				return(false);
			}
			mUsedCount = 0;
			mTotalCount = 0;
			mWriteCount = mReadCount = 0;
		}
		return(true);
	}
	void empty()
	{
		mUsedCount = 0;
		mTotalCount = 0;
		mWriteCount = mReadCount = 0;
	}
	
	void write(byte[] buffer, int startIndex, int len)
	{
		mTotalCount+=len;

		int nTrailingSpace = mSize - mWriteCount;
		
		if(len <= nTrailingSpace)
		{
			System.arraycopy(buffer, startIndex, mBuffer, mWriteCount, len);
			mWriteCount += len;
		}
		else
		{
			System.arraycopy(buffer, startIndex, mBuffer, mWriteCount, nTrailingSpace); // Write end block
			mWriteCount = 0; // Wrap pointer to start
			
			int nRemaining = len - nTrailingSpace;
			System.arraycopy(buffer, startIndex + nTrailingSpace, mBuffer, mWriteCount, nRemaining);
			mWriteCount += nRemaining;
		}
		mUsedCount += len;
	}

	void write(byte value)
	{
		mTotalCount++;
		if(mWriteCount == mSize)
		{
			mWriteCount = 0;
		}
		
		if(mUsedCount<mSize)
		{
			mUsedCount++;
		}
		else
		{
			// Overwriting values that haven't been read
			mReadCount++;
			if(mReadCount == mSize)
			{
				mReadCount = 0;
			}
		}
		mBuffer[mWriteCount] = value;
		mWriteCount+=1;
	}
	
	byte read()
	{
		if(mReadCount == mSize)
		{
			mReadCount = 0;
		}
		byte value = mBuffer[mReadCount];
		mUsedCount--;
		mReadCount++;
		return(value);
	}
	
	int read(byte[] buffer, int len)
	{
		int n = len;
		int ncount=0;
		while(mUsedCount>0 && n>0)
		{
			buffer[ncount] = mBuffer[mReadCount];
			if(mReadCount == mSize)
			{
				mReadCount = 0;
			}
			mUsedCount--;
			n--;
			ncount++;
			mReadCount++;
		}
		return(ncount);
	}
	
	// Rewind read pos back to start of buffer history
	void readRewind()
	{
		if(mTotalCount<mSize){
			mReadCount = 0;
			mUsedCount = mTotalCount;
		}else {
			mReadCount = mWriteCount;
			if(mReadCount == mSize)
			{
				mReadCount = 0;
			}
			mReadCount++;
			mUsedCount = mSize-1;
		}
	}

	// Remove from buffer
	int discard(int count)
	{
		if(count>mUsedCount) count = mUsedCount;
		
		int nEndCount = (mSize - mReadCount);
		if(count <= nEndCount)
		{
			mReadCount += count;
		}
		else
		{
			int nRemainingCount = count - nEndCount;
			mReadCount = nRemainingCount;
		}
		mUsedCount -= count;
		return count;
		
	}
	int getCountTotal()
	{
		return(mTotalCount);
	}
	int getCountHistory()
	{
		if(mTotalCount>mSize)
			return(mSize-1);
		else
			return(mTotalCount);
	}
	int getCountUsed()
	{
		return(mUsedCount);
	}
	int getCountFree()
	{
		return(mSize - mUsedCount);
	}
}
