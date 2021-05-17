import java.util.*;
import java.io.*;

public class IntList {
	private int[] intLst = new int[1];
	private int curPos = 0;
	private int curSize = 1;

	public void apnd (int s) {
		intLst[curPos++] = s;
		if (curPos == curSize) {
			curSize *= 2;
			intLst = Arrays.copyOf(intLst, curSize);
		}
	}

	public int inp(int s) {
		if (s < curPos) {
			return intLst[s];
		} else {
			return -1;
		}
	}

	public int size() {
		return curPos;
	}
}