/*
 * Copyright (c) OSGi Alliance (2016). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slf4j.impl;

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * slf4j-osgi implementation of org.slf4j.Logger.
 * 
 * @author $Id$
 */
class SLF4JLogger extends MarkerIgnoringBase {
	private static final long	serialVersionUID	= 1L;
	final Bundle				bundle;
	final LoggerFactoryTracker	tracker;
	private volatile Logger		stub;

	SLF4JLogger(Bundle bundle, String name) {
		this.name = name;
		this.bundle = bundle;
		tracker = SLF4JLoggerFactory.getTracker();
	}

	private Logger getLogger() {
		Logger logger = tracker.getLogger(bundle, name);
		if (logger == null) {
			logger = stub;
			if (logger == null) {
				stub = logger = new StubLogger();
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
	 * Used when no LoggerFactory is available.
	 */
	class StubLogger implements Logger {
		private static final boolean	TRACE_ENABLED	= false;
		private static final boolean	DEBUG_ENABLED	= false;
		private static final boolean	INFO_ENABLED	= false;
		private static final boolean	WARN_ENABLED	= true;
		private static final boolean	ERROR_ENABLED	= true;

		@Override
		public String getName() {
			return SLF4JLogger.this.getName();
		}

		@Override
		public boolean isTraceEnabled() {
			return TRACE_ENABLED;
		}

		@Override
		public void trace(String msg) {
			if (TRACE_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.TRACE, msg, null);
			}
		}

		@Override
		public void trace(String msg, Object t) {
			if (TRACE_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.TRACE, msg, (Throwable) t);
			}
		}

		@Override
		public void trace(String format, Object arg1, Object arg2) {
			if (TRACE_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void trace(String format, Object... arguments) {
			if (TRACE_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public boolean isDebugEnabled() {
			return DEBUG_ENABLED;
		}

		@Override
		public void debug(String msg) {
			if (DEBUG_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.DEBUG, msg, null);
			}
		}

		@Override
		public void debug(String msg, Object t) {
			if (DEBUG_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.DEBUG, msg, (Throwable) t);
			}
		}

		@Override
		public void debug(String format, Object arg1, Object arg2) {
			if (DEBUG_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void debug(String format, Object... arguments) {
			if (DEBUG_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public boolean isInfoEnabled() {
			return INFO_ENABLED;
		}

		@Override
		public void info(String msg) {
			if (INFO_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.INFO, msg, null);
			}
		}

		@Override
		public void info(String msg, Object t) {
			if (INFO_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.INFO, msg, (Throwable) t);
			}
		}

		@Override
		public void info(String format, Object arg1, Object arg2) {
			if (INFO_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void info(String format, Object... arguments) {
			if (INFO_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public boolean isWarnEnabled() {
			return WARN_ENABLED;
		}

		@Override
		public void warn(String msg) {
			if (WARN_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.WARN, msg, null);
			}
		}

		@Override
		public void warn(String msg, Object t) {
			if (WARN_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.WARN, msg, (Throwable) t);
			}
		}

		@Override
		public void warn(String format, Object arg1, Object arg2) {
			if (WARN_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void warn(String format, Object... arguments) {
			if (WARN_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public boolean isErrorEnabled() {
			return ERROR_ENABLED;
		}

		@Override
		public void error(String msg) {
			if (ERROR_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.ERROR, msg, null);
			}
		}

		@Override
		public void error(String msg, Object t) {
			if (ERROR_ENABLED) {
				tracker.log(bundle, getName(), LogLevel.ERROR, msg, (Throwable) t);
			}
		}

		@Override
		public void error(String format, Object arg1, Object arg2) {
			if (ERROR_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void error(String format, Object... arguments) {
			if (ERROR_ENABLED) {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public void audit(String message) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void audit(String format, Object arg) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void audit(String format, Object arg1, Object arg2) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void audit(String format, Object... arguments) {
			throw new UnsupportedOperationException();
		}
	}
}
