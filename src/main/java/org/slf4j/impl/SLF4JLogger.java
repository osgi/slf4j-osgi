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

package org.slf4j.impl;

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * slf4j-osgi implementation of org.slf4j.Logger.
 *
 * @author $Id$
 */
class SLF4JLogger extends MarkerIgnoringBase {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Class<StubLogger> stubClass = StubLogger.class;
	final transient Bundle bundle;
	final transient LoggerFactoryTracker tracker;
	private transient volatile Logger stub;

	SLF4JLogger(Bundle bundle, String name, LoggerFactoryTracker tracker) {
		this.name = name;
		this.bundle = bundle;
		this.tracker = tracker;
	}

	private Logger getLogger() {
		Logger logger = tracker.getLogger(bundle, name);
		if (logger == null) {
			logger = stub;
			if (logger == null) {
				stub = logger = newStubLogger();
			}
		}
		return logger;
	}

	@Override
	public boolean isTraceEnabled() {
		Logger logger = getLogger();
		return logger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		Logger logger = getLogger();
		logger.trace(msg);
	}

	@Override
	public void trace(String msg, Throwable t) {
		Logger logger = getLogger();
		logger.trace(msg, t);
	}

	@Override
	public void trace(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger.isTraceEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1);
			logger.trace(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger.isTraceEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
			logger.trace(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger.isTraceEnabled()) {
			FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
			logger.trace(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public boolean isDebugEnabled() {
		Logger logger = getLogger();
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		Logger logger = getLogger();
		logger.debug(msg);
	}

	@Override
	public void debug(String msg, Throwable t) {
		Logger logger = getLogger();
		logger.debug(msg, t);
	}

	@Override
	public void debug(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1);
			logger.debug(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
			logger.debug(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
			logger.debug(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public boolean isInfoEnabled() {
		Logger logger = getLogger();
		return logger.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		Logger logger = getLogger();
		logger.info(msg);
	}

	@Override
	public void info(String msg, Throwable t) {
		Logger logger = getLogger();
		logger.info(msg, t);
	}

	@Override
	public void info(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1);
			logger.info(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
			logger.info(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void info(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
			logger.info(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public boolean isWarnEnabled() {
		Logger logger = getLogger();
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		Logger logger = getLogger();
		logger.warn(msg);
	}

	@Override
	public void warn(String msg, Throwable t) {
		Logger logger = getLogger();
		logger.warn(msg, t);
	}

	@Override
	public void warn(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger.isWarnEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1);
			logger.warn(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger.isWarnEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
			logger.warn(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger.isWarnEnabled()) {
			FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
			logger.warn(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public boolean isErrorEnabled() {
		Logger logger = getLogger();
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		Logger logger = getLogger();
		logger.error(msg);
	}

	@Override
	public void error(String msg, Throwable t) {
		Logger logger = getLogger();
		logger.error(msg, t);
	}

	@Override
	public void error(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger.isErrorEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1);
			logger.error(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger.isErrorEnabled()) {
			FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
			logger.error(tp.getMessage(), tp.getThrowable());
		}
	}

	@Override
	public void error(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger.isErrorEnabled()) {
			FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
			logger.error(tp.getMessage(), tp.getThrowable());
		}
	}

	/**
	 * Make a new StubLogger for when no LoggerFactory is available.
	 * <p>
	 * Uses Proxy and reflection to avoid directly implementing org.osgi.service.log.Logger
	 * and thus using a narrow provider import version range for the org.osgi.service.log package.
	 */
	Logger newStubLogger() {
		final StubLogger delegate = new StubLogger();
		final Class<?> delegateClass = delegate.getClass();
		return (Logger) Proxy.newProxyInstance(delegateClass.getClassLoader(), new Class<?>[] {Logger.class}, (proxy, method, args) -> {
			Method delegateMethod;
			try {
				delegateMethod = delegateClass.getMethod(method.getName(), method.getParameterTypes());
			} catch (NoSuchMethodException e) {
				throw new UnsupportedOperationException(e);
			}
			try {
				return delegateMethod.invoke(delegate, args);
			} catch (InvocationTargetException e) {
				Throwable t = e;
				for (Throwable cause; (t instanceof InvocationTargetException) && ((cause = t.getCause()) != null); ) {
					t = cause;
				}
				throw t;
			}
		});
	}

	/**
	 * Used when no LoggerFactory is available.
	 */
	@SuppressWarnings("unused")
	class StubLogger {
		private static final boolean TRACE_ENABLED = false;
		private static final boolean DEBUG_ENABLED = false;
		private static final boolean INFO_ENABLED = false;
		private static final boolean WARN_ENABLED = true;
		private static final boolean ERROR_ENABLED = true;

		StubLogger() {}

		public String getName() {
			return SLF4JLogger.this.getName();
		}

		public boolean isTraceEnabled() {
			return TRACE_ENABLED;
		}

		public void trace(String msg) {
			if (TRACE_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.TRACE, msg, null);
			}
		}

		public void trace(String msg, Object t) {
			if (TRACE_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.TRACE, msg, (Throwable) t);
			}
		}

		public boolean isDebugEnabled() {
			return DEBUG_ENABLED;
		}

		public void debug(String msg) {
			if (DEBUG_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.DEBUG, msg, null);
			}
		}

		public void debug(String msg, Object t) {
			if (DEBUG_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.DEBUG, msg, (Throwable) t);
			}
		}

		public boolean isInfoEnabled() {
			return INFO_ENABLED;
		}

		public void info(String msg) {
			if (INFO_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.INFO, msg, null);
			}
		}

		public void info(String msg, Object t) {
			if (INFO_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.INFO, msg, (Throwable) t);
			}
		}

		public boolean isWarnEnabled() {
			return WARN_ENABLED;
		}

		public void warn(String msg) {
			if (WARN_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.WARN, msg, null);
			}
		}

		public void warn(String msg, Object t) {
			if (WARN_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.WARN, msg, (Throwable) t);
			}
		}

		public boolean isErrorEnabled() {
			return ERROR_ENABLED;
		}

		public void error(String msg) {
			if (ERROR_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.ERROR, msg, null);
			}
		}

		public void error(String msg, Object t) {
			if (ERROR_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.ERROR, msg, (Throwable) t);
			}
		}

		@Override
		public String toString() {
			return getClass().getName() + "(" + getName() + ")";
		}
	}
}
