package me.test.spring.rmi.pojo;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.rmi.RmiBasedExporter;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;

public class RMIUtil {

	private static Map<Object, Remote> staticRefMap = new HashMap<Object, Remote>();

	@SuppressWarnings("unchecked")
	public static <E> E registerAndExport(Object target, Class<E> clazz) {
		AnonymousRmiServiceExporter a = new AnonymousRmiServiceExporter();

		a.setService(target);
		a.setServiceInterface(clazz);
		Remote remoteProxy = a.getObjectToExport();

		staticRefMap.put(target, remoteProxy);
		RmiInvocationHandler stub = null;
		try {
			stub = (RmiInvocationHandler) UnicastRemoteObject.exportObject(
					remoteProxy, 0);
			System.out.println(stub);
		} catch (RemoteException e1) {
			throw new RuntimeException(e1);
		}

		MethodInterceptor mi = new TargetInterfaceWrapper(stub);
		ProxyFactory pf = new ProxyFactory(clazz, mi);
		pf.addInterface(Serializable.class);
		return (E) pf.getProxy();
	}

	public static void unregister(Object target) {
		Remote remoteProxy = staticRefMap.remove(target);
		try {
			UnicastRemoteObject.unexportObject(remoteProxy, true);
		} catch (NoSuchObjectException e) {
			// do nothing.
		}
	}

	public static class AnonymousRmiServiceExporter extends RmiBasedExporter {
		public Remote getObjectToExport() {
			return super.getObjectToExport();
		}
	}

	public static class TargetInterfaceWrapper implements MethodInterceptor,
			Serializable {

		private static final long serialVersionUID = 1L;

		private static RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();

		private RmiInvocationHandler stub = null;

		public TargetInterfaceWrapper(RmiInvocationHandler stub) {
			this.stub = stub;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Object invoke(MethodInvocation invocation) throws Throwable {
			RemoteInvocation remoteInvocation = remoteInvocationFactory
					.createRemoteInvocation(invocation);
			try {
				return stub.invoke(remoteInvocation);
			} catch (Exception e) {
				Class<?>[] declaredExceptions = invocation.getMethod()
						.getExceptionTypes();
				for (Class exceptionClass : declaredExceptions) {
					if (exceptionClass.isAssignableFrom(e.getClass())) {
						throw e;
					}
				}
				throw new RemoteAccessException(e.getMessage(), e);
			}
		}
	}
}
