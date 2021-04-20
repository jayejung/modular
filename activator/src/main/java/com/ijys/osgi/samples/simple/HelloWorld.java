package com.ijys.osgi.samples.simple;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorld implements BundleActivator {
	public void start(BundleContext context) {
		System.out.println("Hello World");
	}

	public void stop(BundleContext context) {
		System.out.println("Goodbye World");
	}
}
