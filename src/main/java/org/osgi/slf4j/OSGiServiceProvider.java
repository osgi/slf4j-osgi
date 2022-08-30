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

package org.osgi.slf4j;

import org.osgi.annotation.bundle.Capability;
import org.osgi.annotation.bundle.Requirement;
import org.osgi.namespace.extender.ExtenderNamespace;
import org.osgi.service.serviceloader.ServiceLoaderNamespace;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * slf4j-osgi implementation of org.slf4j.spi.SLF4JServiceProvider.
 */
@Requirement(namespace = ExtenderNamespace.EXTENDER_NAMESPACE, //
	name = ServiceLoaderNamespace.SERVICELOADER_NAMESPACE + ".registrar")
@Capability(namespace = ServiceLoaderNamespace.SERVICELOADER_NAMESPACE, //
	name = "org.slf4j.spi.SLF4JServiceProvider", //
	attribute = ServiceLoaderNamespace.CAPABILITY_REGISTER_DIRECTIVE + ":=org.osgi.slf4j.OSGiServiceProvider", //
	uses = {SLF4JServiceProvider.class, ILoggerFactory.class})
public class OSGiServiceProvider implements SLF4JServiceProvider {
	public String getRequestedApiVersion() {
		return "2.0.0";
	}

	public ILoggerFactory getLoggerFactory() {
		return SLF4JLoggerFactory.getSLF4JLoggerFactory();
	}

	private IMarkerFactory markerFactory;
	private MDCAdapter mdcAdapter;

	public void initialize() {
		markerFactory = new BasicMarkerFactory();
		mdcAdapter = new NOPMDCAdapter();
	}

	public IMarkerFactory getMarkerFactory() {
		return markerFactory;
	}

	public MDCAdapter getMDCAdapter() {
		return mdcAdapter;
	}
}
