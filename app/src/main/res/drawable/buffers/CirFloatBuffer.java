// CirFloatBuffer.java
// Alive Technologies
package drawable.buffers;

public class CirFloatBuffer {
	private int mCapacity;
	private int mCount;
	private int mMask;
	private float[] mBuffer;

	
	public CirFloatBuffer(int size) {
		// Make size of buffer a power of 2, to simplify indexing
		int sizeAsPowerOfTwo = ceilingNextPowerOfTwo(size);
		mMask = sizeAsPowerOfTwo - 1;
		mCapacity = sizeAsPowerOfTwo;
		mBuffer = new float[mCapacity];
		mCount=0;
	}
	
	private static int ceilingNextPowerOfTwo(final int x)
	{
		return 1 << (32 - Integer.numberOfLeadingZeros(x - 1));
	}
	public void resize(int size) {
		int newCapacity = ceilingNextPowerOfTwo(size);
		if(newCapacity!=mCapacity) {
			float[] newBuffer = new float[newCapacity];
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
	
	public void add(float val) {
		mBuffer[mCount & mMask] = val;
		mCount++;
	}
	public void add(float[] array, int len) {
		for(int i=0;i<len;i++) {
			mBuffer[mCount & mMask] = array[i];
			mCount++;
		}
	}
	public float get(int index) {
		return(mBuffer[index & mMask]);
	}
}