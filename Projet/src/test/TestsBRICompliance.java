package test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import bri.ServiceRegistry;

class TestsBRICompliance {

	@Test
	void test() {
		
		String classeName = "examples.ServiceInversion";
		
		String fileNameURL = "ftp://localhost:2121/classes/";
		URLClassLoader urlcl = null;
		try {
			urlcl = URLClassLoader.newInstance(new URL[]{new URL(fileNameURL)});
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Class<?> c = null;
		try {
			c = urlcl.loadClass(classeName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceRegistry.addService(c);
		System.out.println(ServiceRegistry.toStringue());
	}

}
