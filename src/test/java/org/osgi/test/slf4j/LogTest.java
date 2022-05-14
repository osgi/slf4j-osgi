/*******************************************************************************
 * Copyright (c) Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *******************************************************************************/

package org.osgi.test.slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.LogReaderService;
import org.osgi.test.assertj.log.logentry.LogEntryAssert;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTest {
	private String testName;
	private Logger logger;

	@BeforeEach
	void setUp(TestInfo info) {
		testName = info.getTestClass()
			.map(Class::getName)
			.get() + "." + info.getTestMethod()
			.map(Method::getName)
			.get();
		logger = LoggerFactory.getLogger(testName);
		assertThat(logger).isNotNull();
	}

	@Test
	void log_test(@InjectService LogReaderService lr, @InjectBundleContext BundleContext bc) {
		Exception exception = new Exception();
		logger.warn("Test {} from {}", "message", testName, exception);
		Optional<LogEntry> first = Collections.list(lr.getLog())
			.stream()
			.filter(logEntry -> Objects.equals(logEntry.getLoggerName(), testName))
			.findFirst();
		assertThat(first).isNotEmpty();
		LogEntryAssert logEntryAssert = LogEntryAssert.assertThat(first.get())
			.hasLogLevel(LogLevel.WARN)
			.hasLoggerName(testName)
			.hasBundle(bc.getBundle())
			.hasServiceReference(null);
		logEntryAssert.hasExceptionThat()
			.isNotNull();
		logEntryAssert.hasMessageThat()
			.startsWith("Test")
			.contains("message")
			.contains(testName);
	}
}
