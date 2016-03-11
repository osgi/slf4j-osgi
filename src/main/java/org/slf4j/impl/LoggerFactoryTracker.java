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
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * slf4j-osgi implementation OSGi Logger handler.
 * 
 * @author $Id$
 */
class LoggerFactoryTracker extends ServiceTracker<LoggerFactory,LoggerFactory> {

	LoggerFactoryTracker(BundleContext context) {
		super(context, LoggerFactory.class, null);
	}

	@Override
	public LoggerFactory addingService(ServiceReference<LoggerFactory> reference) {
		return super.addingService(reference);
	}

	@Override
	public void modifiedService(ServiceReference<LoggerFactory> reference, LoggerFactory service) {
		super.modifiedService(reference, service);
	}

	@Override
	public void removedService(ServiceReference<LoggerFactory> reference, LoggerFactory service) {
		super.removedService(reference, service);
	}

	void log(Bundle bundle, String name, LogLevel level, String message, Throwable t) {
		StringBuilder buf = new StringBuilder(32);
		buf.append('[');
		buf.append(Thread.currentThread().getName());
		buf.append("] ");

		buf.append(level.name());
		buf.append(' ');

		buf.append(name);
		buf.append(" - ");

		buf.append(message);

		System.err.println(buf.toString());
		if (t != null) {
			t.printStackTrace(System.err);
		}
		System.err.flush();
	}

	void formatAndLog(Bundle bundle, String name, LogLevel level, String format, Object... arguments) {
		FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
		log(bundle, name, level, tp.getMessage(), tp.getThrowable());
	}

	Logger getLogger(Bundle bundle, String name) {
		LoggerFactory factory = getService();
		if (factory != null) {
			return factory.getLogger(bundle, name, Logger.class);
		}
		return null;
	}
}
