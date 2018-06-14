package exo1;

import java.lang.reflect.Modifier;

public class AfficheEntete {
	// ce programme charge une charge � partir de son nom et affiche l'entete de cette classe
	// public abstract class etc...
	// le param�trage des types n'est pas affich�
	// � faire dans une future version : idem pour chaque m�thode

	
	public static void main(String[] args) {
		// � tester avec diff�rents types de java.util
		Class <?> classe = java.util.List.class;
		
		System.out.println(enTete(classe));
	}

	private static String enTete(Class<?> classe) {
		String affiche = "";		
		// avant le nom de la classe*****************************
		int modifiers = classe.getModifiers();
		// public ou rien (package)
		if (Modifier.isPublic(modifiers)) affiche += "public ";
		// class ou interface
				if (Modifier.isInterface(modifiers)) 
					affiche += "interface ";
				else {
				// abstract ou non
				if (Modifier.isAbstract(modifiers)) affiche += "abstract ";
				affiche += "class ";
				}
				
		// nom de la classe *************************************
		affiche = affiche +=classe.getSimpleName();
		
		// apr�s le nom de la classe*****************************				
		Class<?> supertype = classe.getSuperclass();
		if (supertype!=null) 
			affiche+= " extends "+ supertype.getSimpleName();
		Class<?>[] interfacesImplementees = classe.getInterfaces();
		if (interfacesImplementees.length!=0) {
			if (classe.isInterface()) 
				affiche += " extends " + interfacesImplementees[0].getSimpleName();
			else {
				affiche+= " implements ";
				for (Class<?> interfacee : interfacesImplementees)
					affiche += interfacee.getSimpleName()+",";
				affiche = affiche.substring(0, affiche.length()-1); // retirer le dernier ","
				}
		}
		return affiche;
	}

}
