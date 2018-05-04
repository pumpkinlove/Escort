package com.device;

public class Device {
	static {
		try {
			System.loadLibrary("Device");
		} catch (UnsatisfiedLinkError e) {
			System.out.println(e.getMessage());
		} 
	}

	public native static int getImage(int timeout, byte[] finger, byte[] message);
	public native static int getFinger(int timeout, byte[] finger, byte[] message);
	public native static int setParam(byte[] message);
	
	public native static int ImageToFeature(byte[] image, byte[] feature, byte[] message);
	public native static int FeatureToTemp(byte[] tz1, byte[] tz2, 
			byte[] tz3, byte[] mb, byte[] message);
	public native static int verifyFinger(String mbFinger, String tzFinger, int level);
//	public native static int verifyBinFinger(byte[] mbFinger, byte[] tzFinger, int level);

	public native static int getRfid(int timeout, byte[] epc, byte[] epcid, byte[] message);
	public native static int cancel();
	
	public native static int readIdCard(int timeout, byte[] textData, 
			byte[] photoData, byte[] message);

	public native static int openRfid(byte[] message);	
	public native static int closeRfid(byte[] message);

	public native static int openFinger(byte[] message);	
	public native static int closeFinger(byte[] message);
}
