package ru.otus.lO14;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IoC {

    static TestLoggingInterface createMyClass() {
        return (TestLoggingInterface) Proxy.newProxyInstance(TestLoggingImpl.class.getClassLoader(),
                TestLoggingImpl.class.getInterfaces(), new DemoInvocationHandler(new TestLoggingImpl()));
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("executed method: " + method.getName() + ", param:" + args[0]);
            }
            return method.invoke(myClass, args);
        }
    }
}
