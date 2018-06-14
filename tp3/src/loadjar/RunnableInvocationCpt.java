package loadjar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RunnableInvocationCpt implements InvocationHandler {
	
	private Runnable runnable;
	private Integer nbUtisalisations;
	
	public RunnableInvocationCpt(Runnable r) {
		this.runnable = r;
		this.nbUtisalisations = 0;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().equals("run")) // test à compléter...public void run()
			this.nbUtisalisations++;
		return method.invoke(runnable, args);
	}

	@Override
	public String toString() {
		return runnable.getClass().getSimpleName() + "  " + this.nbUtisalisations.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		RunnableInvocationCpt other = (RunnableInvocationCpt) obj;

		return (this.runnable.getClass().getName().equals(other.runnable.getClass().getName()));
	}
}
