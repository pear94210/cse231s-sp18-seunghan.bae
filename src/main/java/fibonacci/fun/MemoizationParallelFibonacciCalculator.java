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
package fibonacci.fun;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static edu.wustl.cse231s.v5.V5.future;
import static edu.wustl.cse231s.v5.V5.doWork;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.asymptoticanalysis.OrderOfGrowth;
import fibonacci.core.FibonacciCalculator;

/**
 * @author Seunghan Bae
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class MemoizationParallelFibonacciCalculator implements FibonacciCalculator {
	@Override
	public BigInteger fibonacci(int n) throws InterruptedException, ExecutionException {
		// TODO: implement fibonacci with Future<BigInteger> memos
			// check out PascalsTriangleWithFuture for an example
			// https://www.cs.rice.edu/~vs3/hjlib/code/course-materials/demo-files/PascalsTriangleWithFuture.java
		@SuppressWarnings("unchecked")
		Future<BigInteger>[] memos = new Future[n + 1];
		for (int i = 0; i < memos.length; i++) {
			final int index = i;
			memos[index] = future(() -> {
				if (index == 0) return BigInteger.ZERO;
				else if (index == 1) return BigInteger.ONE;
				else {
					Future<BigInteger> memo1 = memos[index - 1];
					Future<BigInteger> memo2 = memos[index - 2];
					
					BigInteger int1 = memo1.get();
					BigInteger int2 = memo2.get();
					return int1.add(int2);
				}
			});
		}
		
		BigInteger ans = memos[n].get();
		return ans;
	}

	@Override
	public OrderOfGrowth getOrderOfGrowth() {
		return OrderOfGrowth.LINEAR;
	}

	@Override
	public boolean isSusceptibleToStackOverflowError() {
		return false;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
