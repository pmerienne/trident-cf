/**
 * Copyright 2013-2015 Pierre Merienne
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pmerienne.trident.cf.state.query;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseQueryFunction;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

import com.github.pmerienne.trident.cf.state.SetMultiMapState;

public class PreferenceCountQuery extends BaseQueryFunction<SetMultiMapState<Long, Long>, Long> {

	private static final long serialVersionUID = -5694522390976713478L;

	@Override
	public List<Long> batchRetrieve(SetMultiMapState<Long, Long> state, List<TridentTuple> tuples) {
		List<Long> counts = new ArrayList<Long>(tuples.size());

		long user, count;
		for (TridentTuple tuple : tuples) {
			user = tuple.getLong(0);
			count = state.sizeOf(user);
			counts.add(count);
		}

		return counts;
	}

	@Override
	public void execute(TridentTuple tuple, Long count, TridentCollector collector) {
		collector.emit(new Values(count));
	}

}
