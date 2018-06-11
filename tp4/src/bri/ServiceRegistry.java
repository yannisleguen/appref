package bri;

import java.util.List;
import java.util.Vector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<?>>();
	}
	private static List<Class<?>> servicesClasses;

// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService(Class<?> c) {
		// vérifier la conformité par introspection
		if (checkBRI(c)) {
			// si conforme, ajout au vector
			servicesClasses.add(c);
		}else {
			// si non conforme --> exception avec message clair
			System.out.println("No BRI compliance, please make sure that "+c.getName()+": ");
			System.out.println("implments BRI.Service");
			System.out.println("Is not abstract");
			System.out.println("Is Public");
			System.out.println("As a publicconstructor (Socket) without any exception");
			System.out.println("As an private final socket attribute");
			System.out.println("As a public Static String toString() method");
		}
		
	}
	
private static boolean checkBRI(Class<?> c) {
	if (implementBRIService(c) && isNotAbstract(c) && isPublic(c) && hasAPublicSocketConstructor(c)
			&& hasAPrivateFinalSocketAttribute(c) && hasAToStringMethode(c) ) {
	return true;
	} 
	else {return false;
	}
	
}

private static boolean hasAToStringMethode(Class<?> c) {
	Method[] methodTab = c.getMethods();
	for (Method method : methodTab) {
		if (method.getName().equals("toString")) {
			return true;
		}
	}
	return false;
}

private static boolean hasAPrivateFinalSocketAttribute(Class<?> c) {
	Field[] f = c.getDeclaredFields();
	for (Field field : f) {
		if (field.getType().equals(java.net.Socket.class)) {
			if(Modifier.isFinal(field.getModifiers())&&
					Modifier.isPrivate(field.getModifiers())) {
				return true;
			}
		}
	}
	return false;
}

private static boolean hasAPublicSocketConstructor(Class<?> c) {
	try {
		//Without exceptions and constructor with a socket
		if (c.getConstructor(java.net.Socket.class).getExceptionTypes().length==0)  { 
			int modifier= c.getConstructor(java.net.Socket.class).getModifiers();
			if (Modifier.isPublic(modifier)) {
				return true;
			}
		}
	
	}catch (NoSuchMethodException e) {
		e.printStackTrace();
	}
	return false;
}

private static boolean isPublic(Class<?> c) {
	if(Modifier.isPublic(c.getModifiers())){
	return true;
	}else
	return false;
}

private static boolean isNotAbstract(Class<?> c) {
	if (!Modifier.isAbstract(c.getModifiers())) {
		return true;
	}else
	return false;
}

private static boolean implementBRIService(Class<?> c) {
	
	if (c.getSuperclass().isInterface() && c.getSuperclass().getSimpleName().equals("ServiceBRI")) {
		return true;
	}
	return false;
}

// renvoie la classe de service (numService -1)	
	public static Class<?> getServiceClass(int numService) {
		return servicesClasses.get(numService);
		
	}
	
// liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes :##\n";
		for (Class<?> class1 : servicesClasses) {
			result += class1.getSimpleName() +"\n";
		}
		return result;
	}

}
