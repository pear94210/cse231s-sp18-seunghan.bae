/*******************************************************************************
 * Copyright (C) 2016-2017 Dennis Cosgrove
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
package kmer.lab.intarray;

import java.util.List;

import edu.wustl.cse231s.NotYetImplementedException;
import kmer.core.KMerCount;
import kmer.core.KMerCounter;
import kmer.core.KMerUtils;
import kmer.core.array.IntArrayKMerCount;

/**
 * A sequential implementation of {@link KMerCounter} that uses an int array,
 * where each index represents a k-mer and the value at that index represents
 * the count.
 * 
 * @author Seunghan Bae
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class IntArrayKMerCounter implements KMerCounter {

	@Override
	public KMerCount parse(List<byte[]> sequences, int k) {
		long possible = KMerUtils.calculatePossibleKMers(k);
		int arrayLength = KMerUtils.toArrayLength(possible);
		//int arrayLength = KMerUtils.calculateSumOfAllKMers(sequences, k);
		int[] array = new int[arrayLength];
		int count = 0;
		for (byte[] sequence : sequences) {
			for (int i = 0; i < sequence.length - k + 1; i++) {
				array[count] = KMerUtils.toPackedInt(sequence, i, k);
				count++;
			}
		}
		return new IntArrayKMerCount(k, array);
	}

}
