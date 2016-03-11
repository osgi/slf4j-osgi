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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * slf4j-osgi implementation of org.slf4j.ILoggerFactory.
 * 
 * @author $Id$
 */
class SLF4JLoggerFactory implements org.slf4j.ILoggerFactory {
	static class Central extends SecurityManager implements SynchronousBundleListener {
		private static final Bundle										self;
		private static final BundleContext								systemBundleContext;
		private static final LoggerFactoryTracker						tracker;
		private static final Central									context;
		private static final ConcurrentMap<Bundle,SLF4JLoggerFactory>	factories;
		static {
			self = FrameworkUtil.getBundle(SLF4JLoggerFactory.class);
			assert self != null;
			systemBundleContext = getSystemBundleContext(self);
			assert systemBundleContext != null;
			tracker = new LoggerFactoryTracker(systemBundleContext);
			factories = new ConcurrentHashMap<>();
			context = AccessController.doPrivileged(new PrivilegedAction<Central>() {
				@Override
				public Central run() {
					Central c = new Central();
					systemBundleContext.addBundleListener(c);
					tracker.open();
					return c;
				}
			});
		}

		private static BundleContext getSystemBundleContext(Bundle bundle) {
			BundleContext bc = bundle.getBundleContext();
			if (bc != null) {
				return bc.getBundle(Constants.SYSTEM_BUNDLE_LOCATION).getBundleContext();
			}
			BundleWiring wiring = bundle.adapt(BundleWiring.class);
			List<BundleWire> wires = wiring.getRequiredWires(PackageNamespace.PACKAGE_NAMESPACE);
			for (BundleWire wire : wires) {
				if ("org.osgi.framework"
						.equals(wire.getCapability().getAttributes().get(PackageNamespace.PACKAGE_NAMESPACE))) {
					return wire.getProviderWiring().getBundle().getBundleContext();
				}
			}
			return null;
		}

		private static Bundle getCallerBundle() {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			boolean foundGetLogger = false;
			for (int i = 5; i < stack.length; i++) {
				StackTraceElement trace = stack[i];
				if (trace.getClassName().equals("org.slf4j.LoggerFactory")
						&& trace.getMethodName().equals("getLogger")) {
					foundGetLogger = true;
					continue;
				}
				if (foundGetLogger) {
					Class< ? >[] callers = context.getClassContext();
					Class< ? > caller = callers[i - 1];
					Bundle b = FrameworkUtil.getBundle(caller);
					return b != null ? b : self;
				}
			}
			return self;
		}

		static SLF4JLoggerFactory getSLF4JLoggerFactory() {
			Bundle callerBundle = getCallerBundle();
			SLF4JLoggerFactory factory = factories.get(callerBundle);
			if (factory != null) {
				return factory;
			}
			SLF4JLoggerFactory newFactory = new SLF4JLoggerFactory(callerBundle);
			factory = factories.putIfAbsent(callerBundle, newFactory);
			return (factory != null) ? factory : newFactory;
		}

		static LoggerFactoryTracker getTracker() {
			return tracker;
		}

		private Central() {}

		@Override
		public void bundleChanged(BundleEvent event) {
			switch (event.getType()) {
				case BundleEvent.UNRESOLVED :
					Bundle b = event.getBundle();
					if (b.equals(self)) {
						factories.clear();
						AccessController.doPrivileged(new PrivilegedAction<Void>() {
							@Override
							public Void run() {
								systemBundleContext.removeBundleListener(Central.this);
								tracker.close();
								return null;
							}
						});
					} else {
						factories.remove(b);
					}
					break;
			}
		}
	}

	static SLF4JLoggerFactory getSLF4JLoggerFactory() {
		return Central.getSLF4JLoggerFactory();
	}

	static LoggerFactoryTracker getTracker() {
		return Central.getTracker();
	}

	private final ConcurrentMap<String,SLF4JLogger>	loggers;
	private final Bundle							bundle;

	SLF4JLoggerFactory(Bundle bundle) {
		loggers = new ConcurrentHashMap<>();
		this.bundle = bundle;
	}

	/**
	 * Return a named Logger.
	 * 
	 * @return A Logger for the specified name.
	 */
	@Override
	public org.slf4j.Logger getLogger(String name) {
		SLF4JLogger logger = loggers.get(name);
		if (logger != null) {
			return logger;
		}
		SLF4JLogger newLogger = new SLF4JLogger(bundle, name);
		logger = loggers.putIfAbsent(name, newLogger);
		return (logger != null) ? logger : newLogger;
	}
}
