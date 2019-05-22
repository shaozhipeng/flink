/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.io.network.metrics;

import org.apache.flink.metrics.Gauge;
import org.apache.flink.runtime.io.network.partition.consumer.SingleInputGate;

/**
 * Gauge metric measuring the input buffer pool usage gauge for {@link SingleInputGate}s.
 */
public class InputBufferPoolUsageGauge implements Gauge<Float> {

	private final SingleInputGate[] inputGates;

	public InputBufferPoolUsageGauge(SingleInputGate[] inputGates) {
		this.inputGates = inputGates;
	}

	@Override
	public Float getValue() {
		int usedBuffers = 0;
		int bufferPoolSize = 0;

		for (SingleInputGate inputGate : inputGates) {
			usedBuffers += inputGate.getBufferPool().bestEffortGetNumOfUsedBuffers();
			bufferPoolSize += inputGate.getBufferPool().getNumBuffers();
		}

		if (bufferPoolSize != 0) {
			return ((float) usedBuffers) / bufferPoolSize;
		} else {
			return 0.0f;
		}
	}
}
