/*******************************************************************************
 * Copyright (C) 2016-2018 Dennis Cosgrove
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package sort.fun.merge;

import static edu.wustl.cse231s.v5.V5.async;
import static edu.wustl.cse231s.v5.V5.finish;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import edu.wustl.cse231s.NotYetImplementedException;
import midpoint.assignment.MidpointUtils;
import sort.core.merge.Combiner;

/**
 * @author Seunghan Bae
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class ParallelCombiner implements Combiner {
	private final int[] buffer;
	private final int threshold;

	public ParallelCombiner(int bufferLength, int threshold) {
		this.buffer = new int[bufferLength];
		this.threshold = threshold;
	}

	private void sequentialCombine(int bufferIndex, int[] data, int aMin, int aMaxExclusive, int bMin,
			int bMaxExclusive) {
		int indexA = aMin;
		int indexB = bMin;
		while (indexA < aMaxExclusive && indexB < bMaxExclusive) {
			this.buffer[bufferIndex++] = (data[indexA] < data[indexB]) ? data[indexA++] : data[indexB++];
		}
		while (indexA < aMaxExclusive) {
			this.buffer[bufferIndex++] = data[indexA++];
		}
		while (indexB < bMaxExclusive) {
			this.buffer[bufferIndex++] = data[indexB++];
		}
	}

	private void parallelCombine(int bufferIndex, int[] data, int aMin, int aMaxExclusive, int bMin, int bMaxExclusive)
			throws InterruptedException, ExecutionException {
		/*boolean aIsLonger = (aMaxExclusive - aMin) >= (bMaxExclusive - bMin);
		if (aIsLonger) {
			if (aMaxExclusive - aMin < this.threshold) {
				sequentialCombine(bufferIndex, data, aMin, aMaxExclusive, bMin, bMaxExclusive);
			}
			else {
				int aMid = MidpointUtils.calculateMidpoint(aMin, aMaxExclusive);
				int bLoc = binarySearch(data, bMin, bMaxExclusive, aMid);
				//async(() -> parallelCombine(bufferIndex, ));
			}
		}
		else {
			
		}*/
		throw new NotYetImplementedException();
	}
	
	private int binarySearch(int[] data, int min, int maxExclusive, int index) {
		int pivot = MidpointUtils.calculateMidpoint(min, maxExclusive);
		if (data[index] < data[min]) return 0;
		else if (data[index] >= data[maxExclusive - 1]) return maxExclusive - min;
		else {
			if ((data[index] < data[pivot]) && (data[index] < data[pivot + 1])) {
				return binarySearch(data, min, pivot + 1, index);
			}
			else if ((data[index] >= data[pivot]) && (data[index] >= data[pivot + 1])) {
				return binarySearch(data, pivot + 1, maxExclusive, index);
			}
			else return pivot - min + 1;
		}
	}

	@Override
	public void combineRange(int[] data, int min, int mid, int maxExclusive)
			throws InterruptedException, ExecutionException {
		parallelCombine(min, data, min, mid, mid, maxExclusive);
		System.arraycopy(buffer, min, data, min, maxExclusive - min);
	}
}
