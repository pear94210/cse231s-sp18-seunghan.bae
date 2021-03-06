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
package mapreduce.apps.cholera.studio;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.apache.commons.lang3.mutable.MutableDouble;

import edu.wustl.cse231s.NotYetImplementedException;
import mapreduce.apps.cholera.core.CholeraDeath;
import mapreduce.apps.cholera.core.WaterPump;
import mapreduce.collector.intsum.studio.IntSumCollector;
import mapreduce.framework.core.Mapper;

/**
 * @author Seunghan Bae
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class CholeraApp {
	public static CholeraAppValueRepresentation getValueRepresentation() {
		return CholeraAppValueRepresentation.HIGH_NUMBERS_SUSPECT;
	}

	public static Mapper<CholeraDeath, WaterPump, Number> createMapper() {
		return new Mapper<CholeraDeath, WaterPump, Number>() {
			@Override
			public void map(CholeraDeath item, BiConsumer<WaterPump, Number> keyValuePairConsumer) {
				WaterPump ans = null;
				double ansDist = Double.MAX_VALUE;
				for (WaterPump wp : WaterPump.values()) {
					double dist = wp.getLocation().getDistanceTo(item.getLocation());
					if (ans == null || dist < ansDist) {
						ans = wp;
						ansDist = dist;
					}
				}
				keyValuePairConsumer.accept(ans, 1);
				return;
			}
		};
	}

	public static Collector<? extends Number, ?, ? extends Number> createCollector() {
		return new IntSumCollector();
	}
}
