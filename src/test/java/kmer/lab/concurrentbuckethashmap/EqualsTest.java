/*******************************************************************************
 * Copyright (C) 2016-2017 Dennis Cosgrove, Ben Choi
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
package kmer.lab.concurrentbuckethashmap;

import static edu.wustl.cse231s.v5.V5.launchApp;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import edu.wustl.cse231s.junit.JUnitUtils;
import kmer.lab.concurrentbuckethashmap.ConcurrentBucketHashMap;
import kmer.lab.rubric.KMerRubric;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 * 
 *         {@link ConcurrentBucketHashMap#put(Object, Object)}
 *         {@link ConcurrentBucketHashMap#get(Object)}
 */
@KMerRubric(KMerRubric.Category.BUCKET_MAP)
public class EqualsTest extends AbstractDictionaryTest {
	@Rule
	public TestRule timeout = JUnitUtils.createTimeoutRule();

	@Test
	public void testPrimitiveGet() {
		launchApp(() -> {
			ConcurrentBucketHashMap<Integer, String> map = createMap();
			map.put(1, "one");
			assertEquals("one", map.get(1));
		});
	}

	@Test
	public void testNonPrimitiveGet() {
		launchApp(() -> {
			ConcurrentBucketHashMap<BigDecimal, String> map = createMap();
			map.put(new BigDecimal(1), "one");
			assertEquals("one", map.get(new BigDecimal(1)));
		});

	}

	@Test
	public void testPrimitivePut() {
		launchApp(() -> {
			ConcurrentBucketHashMap<Integer, String> map = createMap();
			map.put(1, "one");
			map.put(1, "uno");
			assertEquals("uno", map.get(1));
		});
	}

	@Test
	public void testNonPrimitivePut() {
		launchApp(() -> {
			ConcurrentBucketHashMap<BigDecimal, String> map = createMap();
			map.put(new BigDecimal(1), "one");
			map.put(new BigDecimal(1), "uno");
			assertEquals("uno", map.get(new BigDecimal(1)));
		});
	}
}
