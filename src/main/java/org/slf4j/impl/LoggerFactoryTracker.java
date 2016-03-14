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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;
import org.osgi.util.tracker.ServiceTracker;

/**
 * slf4j-osgi implementation OSGi Logger handler.
 * 
 * @author $Id$
 */
class LoggerFactoryTracker extends ServiceTracker<Object,Object> {
	static class Entry {
		private final Bundle	bundle;
		private final String	name;
		private final LogLevel	level;
		private final String	message;
		private final Throwable	t;

		Entry(Bundle bundle, String name, LogLevel level, String message, Throwable t) {
			this.bundle = bundle;
			this.name = name;
			this.level = level;
			this.message = message;
			this.t = t;
		}

		@Override
		public String toString() {
			StringWriter buf = new StringWriter(32);
			buf.append(level.name());
			buf.append(' ');
			buf.append(name);
			buf.append(" - ");
			buf.append(message);
			if (t != null) {
				t.printStackTrace(new PrintWriter(buf));
			}
			return buf.toString();
		}

		void log(LoggerFactory factory) {
			Logger logger = factory.getLogger(bundle, name, Logger.class);
			switch (level) {
				case TRACE :
					logger.trace(message, t);
					break;
				case DEBUG :
					logger.debug(message, t);
					break;
				case INFO :
					logger.info(message, t);
					break;
				case WARN :
					logger.warn(message, t);
					break;
				case ERROR :
					logger.error(message, t);
					break;
				default :
					logger.audit(message, t);
					break;
			}
		}
	}

	private static final int			QUEUE_SIZE		= 200;
	private final BlockingQueue<Entry>	queue;
	private volatile LoggerFactory		firstFactory	= null;

	LoggerFactoryTracker(BundleContext context) {
		super(context, LoggerFactory.class.getName(), null);
		queue = new LinkedBlockingQueue<>(QUEUE_SIZE);
	}

	@Override
	public Object addingService(ServiceReference<Object> reference) {
		// We use system bundle context and track all
		Object service = context.getService(reference);
		if (service instanceof LoggerFactory) {
			if (isEmpty()) {
				LoggerFactory factory = (LoggerFactory) service;
				drain(factory);
				firstFactory = factory;
			}
			return service;
		}
		// service is not typesafe for us
		if (service != null) {
			context.ungetService(reference);
		}
		return null;
	}

	@Override
	public void removedService(ServiceReference<Object> reference, Object service) {
		firstFactory = null;
		context.ungetService(reference);
	}

	private void drain(LoggerFactory factory) {
		if ((factory == null) || queue.isEmpty()) {
			return;
		}
		List<Entry> entries = new ArrayList<>(QUEUE_SIZE);
		queue.drainTo(entries);
		for (Entry entry : entries) {
			entry.log(factory);
		}
	}

	void log(Bundle bundle, String name, LogLevel level, String message, Throwable t) {
		Entry e = new Entry(bundle, name, level, message, t);
		while (!queue.offer(e)) {
			// queue is full! Dump queue to System.err.
			List<Entry> entries = new ArrayList<>(QUEUE_SIZE);
			queue.drainTo(entries);
			for (Entry entry : entries) {
				System.err.println(entry);
				System.err.flush();
			}
		}
	}

	Logger getLogger(Bundle bundle, String name) {
		LoggerFactory factory = (LoggerFactory) getService();
		if (factory == null) {
			factory = firstFactory;
		}
		if (factory != null) {
			drain(factory);
			return factory.getLogger(bundle, name, Logger.class);
		}
		return null;
	}
}
