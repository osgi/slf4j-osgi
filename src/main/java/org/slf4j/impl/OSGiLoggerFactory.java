/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * An implementation of {@link ILoggerFactory} which always returns
 * {@link OSGiLogger} instances.
 */
public class OSGiLoggerFactory implements ILoggerFactory {
	static final Bundle thisbundle;
	
	static {
		thisbundle = FrameworkUtil.getBundle(OSGiLoggerFactory.class);
	}

	static class ClassContext extends SecurityManager {
		ClassContext() {}
		public Bundle getCallerBundle() {
			for (Class< ? > c : getClassContext()) {
				String name = c.getName();
				if (name.startsWith("org.slf4j."))
					continue;
				System.out.printf("%s\n", name);
				Bundle b = FrameworkUtil.getBundle(c);
				return b != null ? b : thisbundle;
			}
			return thisbundle;
		}
	}

	static final ClassContext classContext;
	static {
		classContext = AccessController
				.doPrivileged(new PrivilegedAction<ClassContext>() {
					@Override
					public ClassContext run() {
						return new ClassContext();
					}
				});

	}
    ConcurrentMap<String, Logger> loggerMap;

    public OSGiLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
        OSGiLogger.init();
    }

    /**
     * Return an appropriate {@link OSGiLogger} instance by name.
     */
    @Override
	public Logger getLogger(String name) {
		System.out.printf("Bundle %s\n", classContext.getCallerBundle());
        Logger simpleLogger = loggerMap.get(name);
        if (simpleLogger != null) {
            return simpleLogger;
        } else {
            Logger newInstance = new OSGiLogger(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }

    /**
     * Clear the internal logger cache.
     *
     * This method is intended to be called by classes (in the same package) for
     * testing purposes. This method is internal. It can be modified, renamed or
     * removed at any time without notice.
     *
     * You are strongly discouraged from calling this method in production code.
     */
    void reset() {
        loggerMap.clear();
    }
}
