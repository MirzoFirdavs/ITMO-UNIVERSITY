package info.kgeorgiy.ja.zaynidinov.implementor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class ComparableMethod {
    private final Method method;

    public ComparableMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Implementor.generateMethodNameAndParams(method));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ComparableMethod)) {
            return false;
        }
        ComparableMethod otherMethod = (ComparableMethod) other;
        Class<?>[] thisParams = method.getParameterTypes();
        Class<?>[] otherParams = otherMethod.method.getParameterTypes();
        return method.getName().equals(otherMethod.method.getName()) && Arrays.equals(otherParams, thisParams);
    }
}