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
		servicesClasses = new Vector<Class<? extends Service>>();
	}
	private static List<Class<? extends Service>> servicesClasses;

// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService(Class<?> c) {
		// vérifier la conformité par introspection
		try {
			checkBRI(c);
			servicesClasses.add((Class<? extends Service>) c);
			//System.out.println(c.getSimpleName()+"Has been added :-)");
		
			
		} catch (Exception e) {
			System.out.println(c.getName()+"Is Not BRI compliant, please make sure that the class :");
			if (!implementBRIService(c)) {
				System.out.println("implements BRI.Service");
			} 
			else if (!isNotAbstract(c)) {
				System.out.println("Is not abstract");
			}
			else if (!isPublic(c)) {
				System.out.println("Is Public");
			}
			else if (!hasAPublicSocketConstructor(c)) {
				System.out.println("As a publicconstructor (Socket) without any exception");
			}
			else if (!hasAPrivateFinalSocketAttribute(c)) {
				System.out.println("As a private final socket attribute");
			}
			else if (!hasAToStringMethode(c)) {
				System.out.println("As a public Static String toString() method");
			}
		}
}
	
private static boolean checkBRI(Class<?> c) {
	if (implementBRIService(c)&&isNotAbstract(c) && isPublic(c) && hasAPublicSocketConstructor(c)
			&& hasAPrivateFinalSocketAttribute(c) && hasAToStringMethode(c) ) {
	return true;
	} 
	else {return false;
	}
	
}

private static boolean hasAToStringMethode(Class<?> c) {
	Method[] methodTab = c.getMethods();
	for (Method method : methodTab) {
		if (method.getName().equals("toStringue")) {
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
	
	Class<?>[] interfacesImplementees = c.getInterfaces();
	for (Class<?> class1 : interfacesImplementees) {
		if (class1.getName().equals("bri.Service")) {
			return true;
			}
	}
	return false;
		
}

// renvoie la classe de service (numService -1)	
	public static Class<? extends Service> getServiceClass(int numService) {
		
		return servicesClasses.get(numService);
		
	}
	public static void removeClass (String classeName){
		for (Class<? extends Service> class1 : servicesClasses) {
			if (class1.getName() == classeName) {
			
				servicesClasses.remove(class1);
			}
		}
	}
	
// liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes :##";
		for (int i = 0; i<servicesClasses.size(); i++) {
			int serviceNumber = i+1;
			result += getServiceClass(i).getName()+"service numéro : "+serviceNumber+"##";
			
		}
		return result;
	}

}
