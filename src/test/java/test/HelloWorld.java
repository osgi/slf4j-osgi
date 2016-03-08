package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import junit.framework.TestCase;

public class HelloWorld  extends TestCase {
	public void testHelloWorld() {
		Logger logger = LoggerFactory.getLogger(HelloWorld.class);
		logger.info("Hello World");
		logger.info("LoggerFactory class name: {}",
				StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr());
		logger.info("LoggerFactory: {}",
				StaticLoggerBinder.getSingleton()
						.getLoggerFactory()
						.getClass());
	}
}
