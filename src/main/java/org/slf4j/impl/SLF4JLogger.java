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
	private static final long			serialVersionUID	= 1L;
	private static final boolean		TRACE_ENABLED		= false;
	private static final boolean		DEBUG_ENABLED		= false;
	private static final boolean		INFO_ENABLED		= false;
	private static final boolean		WARN_ENABLED		= true;
	private static final boolean		ERROR_ENABLED		= true;
	private final Bundle				bundle;
	private final LoggerFactoryTracker	tracker;

	SLF4JLogger(Bundle bundle, String name) {
		this.name = name;
		this.bundle = bundle;
		tracker = SLF4JLoggerFactory.getTracker();
	}

	private Logger getLogger() {
		return tracker.getLogger(bundle, name);
	}

	@Override
	public boolean isTraceEnabled() {
		Logger logger = getLogger();
		if (logger != null) {
			return logger.isTraceEnabled();
		}
		return TRACE_ENABLED;
	}

	@Override
	public void trace(String msg) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.trace(msg);
			return;
		}
		if (TRACE_ENABLED) {
			tracker.log(bundle, name, LogLevel.TRACE, msg, null);
		}
	}

	@Override
	public void trace(String msg, Throwable t) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.trace(msg, t);
			return;
		}
		if (TRACE_ENABLED) {
			tracker.log(bundle, name, LogLevel.TRACE, msg, t);
		}
	}

	@Override
	public void trace(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isTraceEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1);
				logger.trace(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (TRACE_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.TRACE, format, arg1);
		}
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isTraceEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
				logger.trace(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (TRACE_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.TRACE, format, arg1, arg2);
		}
	}

	@Override
	public void trace(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isTraceEnabled()) {
				FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
				logger.trace(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (TRACE_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.TRACE, format, argArray);
		}
	}

	@Override
	public boolean isDebugEnabled() {
		Logger logger = getLogger();
		if (logger != null) {
			return logger.isDebugEnabled();
		}
		return DEBUG_ENABLED;
	}

	@Override
	public void debug(String msg) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.debug(msg);
			return;
		}
		if (DEBUG_ENABLED) {
			tracker.log(bundle, name, LogLevel.DEBUG, msg, null);
		}
	}

	@Override
	public void debug(String msg, Throwable t) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.debug(msg, t);
			return;
		}
		if (DEBUG_ENABLED) {
			tracker.log(bundle, name, LogLevel.DEBUG, msg, t);
		}
	}

	@Override
	public void debug(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isDebugEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1);
				logger.debug(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (DEBUG_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.DEBUG, format, arg1);
		}
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isDebugEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
				logger.debug(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (DEBUG_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.DEBUG, format, arg1, arg2);
		}
	}

	@Override
	public void debug(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isDebugEnabled()) {
				FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
				logger.debug(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (DEBUG_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.DEBUG, format, argArray);
		}
	}

	@Override
	public boolean isInfoEnabled() {
		Logger logger = getLogger();
		if (logger != null) {
			return logger.isInfoEnabled();
		}
		return INFO_ENABLED;
	}

	@Override
	public void info(String msg) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.info(msg);
			return;
		}
		if (INFO_ENABLED) {
			tracker.log(bundle, name, LogLevel.INFO, msg, null);
		}
	}

	@Override
	public void info(String msg, Throwable t) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.info(msg, t);
			return;
		}
		if (INFO_ENABLED) {
			tracker.log(bundle, name, LogLevel.INFO, msg, t);
		}
	}

	@Override
	public void info(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isInfoEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1);
				logger.info(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (INFO_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.INFO, format, arg1);
		}
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isInfoEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
				logger.info(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (INFO_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.INFO, format, arg1, arg2);
		}
	}

	@Override
	public void info(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isInfoEnabled()) {
				FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
				logger.info(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (INFO_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.INFO, format, argArray);
		}
	}

	@Override
	public boolean isWarnEnabled() {
		Logger logger = getLogger();
		if (logger != null) {
			return logger.isWarnEnabled();
		}
		return WARN_ENABLED;
	}

	@Override
	public void warn(String msg) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.warn(msg);
			return;
		}
		if (WARN_ENABLED) {
			tracker.log(bundle, name, LogLevel.WARN, msg, null);
		}
	}

	@Override
	public void warn(String msg, Throwable t) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.warn(msg, t);
			return;
		}
		if (WARN_ENABLED) {
			tracker.log(bundle, name, LogLevel.WARN, msg, t);
		}
	}

	@Override
	public void warn(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isWarnEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1);
				logger.warn(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (WARN_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.WARN, format, arg1);
		}
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isWarnEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
				logger.warn(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (WARN_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.WARN, format, arg1, arg2);
		}
	}

	@Override
	public void warn(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isWarnEnabled()) {
				FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
				logger.warn(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (WARN_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.WARN, format, argArray);
		}
	}

	@Override
	public boolean isErrorEnabled() {
		Logger logger = getLogger();
		if (logger != null) {
			return logger.isErrorEnabled();
		}
		return ERROR_ENABLED;
	}

	@Override
	public void error(String msg) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.error(msg);
			return;
		}
		if (ERROR_ENABLED) {
			tracker.log(bundle, name, LogLevel.ERROR, msg, null);
		}
	}

	@Override
	public void error(String msg, Throwable t) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.error(msg, t);
			return;
		}
		if (ERROR_ENABLED) {
			tracker.log(bundle, name, LogLevel.ERROR, msg, t);
		}
	}

	@Override
	public void error(String format, Object arg1) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isErrorEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1);
				logger.error(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (ERROR_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.ERROR, format, arg1);
		}
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isErrorEnabled()) {
				FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
				logger.error(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (ERROR_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.ERROR, format, arg1, arg2);
		}
	}

	@Override
	public void error(String format, Object... argArray) {
		Logger logger = getLogger();
		if (logger != null) {
			if (logger.isErrorEnabled()) {
				FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
				logger.error(tp.getMessage(), tp.getThrowable());
			}
			return;
		}
		if (ERROR_ENABLED) {
			tracker.formatAndLog(bundle, name, LogLevel.ERROR, format, argArray);
		}
	}
}
