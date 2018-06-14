package loadjar;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class RunnableManager {
	// cette classe g�re des objets Runnable et les espions associ�s qui
	// comptent le bombre d'utilisations

	static {
		runnables = new ArrayList<Runnable>();
		espions = new ArrayList<RunnableInvocationCpt>();
	}
	private static List<Runnable> runnables;
	private static List<RunnableInvocationCpt> espions;

	public static void addRunnable(Class<? extends Runnable> runnableClass) {
		try {
			RunnableInvocationCpt espion = new RunnableInvocationCpt(runnableClass.newInstance());
			if (espions.contains(espion)) {
				System.out.println("doublon");
				return;}
			Runnable r = 
					(Runnable) Proxy.newProxyInstance (
							runnableClass.getClassLoader(), 
							runnableClass.getInterfaces(), 
							espion
							);
			synchronized (RunnableManager.class) {
				// ce verrou rend cette classe thread-safe
				runnables.add(r);
				espions.add(espion);
			}
		} catch (Exception e) {}

	}

	public static void lance(int numRunnable) {
		Runnable r = null;
		synchronized (RunnableManager.class) {
			r = runnables.get(numRunnable);
		}
		new Thread(r).start();

	}
	
// toStringue liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes :\n";
		int i = 1;
			for (Runnable r : runnables) {
				result = result + i + " " + r.toString()+"\n";
				i++;
			}
		return result;
	}
	
// bilan liste le nombre d'utilisations de chaque activit�
	public static String bilan() {
		String result = "Bilan des activit�s :\n";
			for (RunnableInvocationCpt espion : espions)
				result += espion.toString()+"\n";
		return result;
	}

}
