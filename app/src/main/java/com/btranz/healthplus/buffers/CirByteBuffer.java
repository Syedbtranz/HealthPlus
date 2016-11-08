// CirByteBuffer.java
// Alive Technologies
package com.btranz.healthplus.buffers;


public class CirByteBuffer {
	private int mCapacity;
	private int mCount;
	private int mMask;
	private byte[] mBuffer;

	
	public CirByteBuffer(int size) {
		// Make size of buffer a power of 2, to simplify indexing
		int sizeAsPowerOfTwo = ceilingNextPowerOfTwo(size);
		mMask = sizeAsPowerOfTwo - 1;
		mCapacity = sizeAsPowerOfTwo;
		mBuffer = new byte[mCapacity];
		mCount=0;
	}
	
	private static int ceilingNextPowerOfTwo(final int x)
	{
		return 1 << (32 - Integer.numberOfLeadingZeros(x - 1));
	}
	public void resize(int size) {
		int newCapacity = ceilingNextPowerOfTwo(size);
		if(newCapacity!=mCapacity) {
			byte[] newBuffer = new byte[newCapacity];
			int oldestIndex;
			if(newCapacity>mCapacity) {
				oldestIndex = mCount - mCapacity; 
			}else {
				oldestIndex = mCount - newCapacity;
			}
			int newCount=0;
			for(int i=oldestIndex;i<mCount;i++) {
				newBuffer[newCount] = get(i);
				newCount++;
			}
			mBuffer = null; // Delete the old array and replace with the new
			mBuffer = newBuffer;
			mMask = newCapacity -1;
			mCount = newCount;
		}
	}
	
	public int getCapacity() {
		return(mCapacity);
	}
	
	public void reset() {
		mCount = 0;
	}
	
	public int getCount() {
		return(mCount);
	}
	
	public void add(byte val) {
		mBuffer[mCount & mMask] = val;
		mCount++;
	}
	public void add(byte[] array, int len) {
		for(int i=0;i<len;i++) {
			mBuffer[mCount & mMask] = array[i];
			mCount++;
		}
	}
	public byte get(int index) {
		return(mBuffer[index & mMask]);
	}
}