package exo2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class TestBean {
// teste si une classe est un bean : public, un constructeur vide,Serializable, 
//		pas final et get/set standard pour chaque attribut
	
	public static void main(String[] args) {
		Class <?> classe = Personne.class;
		
		try {
			validationBean(classe);
			System.out.println("La classe "+classe.getName()+" est bien un bean conforme aux conventions");
		} catch (Exception e) {
			System.err.println(e.getMessage());;
		}
	}


	private static void validationBean(Class<?> classe) throws Exception {
		// à rajouter : éliminer ce qui n'est pas une classe
		int mod = classe.getModifiers();
		if (!Modifier.isPublic(mod)) throw new Exception ("la classe doit être publique");
		if (Modifier.isFinal(mod)) throw new Exception ("la classe ne doit pas être final");
		if (!Arrays.asList(classe.getInterfaces()).contains(java.io.Serializable.class)) 
			throw new Exception ("la classe doit implémenter java.io.Serializable");
		try {
			classe.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new Exception ("la classe doit poss&éder un constructeur vide");
		}
		Field[] fields = classe.getDeclaredFields();
		String getter, setter;
		Method get,set;
		for (Field field : fields){
			if (Modifier.isStatic(field.getModifiers())) continue;
			if (!Modifier.isPrivate(field.getModifiers())) 
				throw new Exception("l'attribut "+field.getName()+ " doit être private");
			String name = field.getName();
			name = (name.substring(0, 1)).toUpperCase() + name.substring(1);
			getter = "get"+name; setter = "set"+name;
			try {
				get = classe.getDeclaredMethod(getter);
				set = classe.getDeclaredMethod(setter, field.getType());
			} catch (NoSuchMethodException e) {
				throw new Exception ("il manque le getter ou setter de l'attribut "+field.getName());
			}
			if (!Modifier.isPublic(get.getModifiers())){
				throw new Exception ("le getter de l'attribut "+field.getName()+" doit être public");
			}if (!Modifier.isPublic(set.getModifiers())){
				throw new Exception ("le setter de l'attribut "+field.getName()+" doit être public");
			}
		}
	}
}
