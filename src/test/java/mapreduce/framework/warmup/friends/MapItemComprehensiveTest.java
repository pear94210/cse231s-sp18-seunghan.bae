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
package mapreduce.framework.warmup.friends;

import java.util.Collection;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.wustl.cse231s.junit.JUnitUtils;
import edu.wustl.cse231s.util.OrderedPair;
import mapreduce.apps.friends.core.Account;
import mapreduce.apps.friends.core.AccountId;
import mapreduce.apps.friends.util.AccountDatabase;
import mapreduce.apps.wordcount.core.TextSection;
import mapreduce.core.TestApplication;
import mapreduce.framework.warmup.friends.AccessWarmUpMutualFriendsUtils;
import mapreduce.framework.warmup.friends.MutualFriendsConcreteStaticMapReduce;
import mapreduce.warmup.AbstractMapItemComprehensiveTest;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 * 
 *         {@link MutualFriendsConcreteStaticMapReduce#map(TextSection, java.util.function.BiConsumer)}
 */
@RunWith(Parameterized.class)
public class MapItemComprehensiveTest
		extends AbstractMapItemComprehensiveTest<Account, OrderedPair<AccountId>, Set<AccountId>> {
	public MapItemComprehensiveTest(AccountDatabase accountDatabase) {
		super(accountDatabase.getAccounts(), TestApplication.MUTUAL_FRIENDS, AccessWarmUpMutualFriendsUtils::map);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> getConstructorArguments() {
		return JUnitUtils.toParameterizedArguments(AccountDatabase.values());
	}
}