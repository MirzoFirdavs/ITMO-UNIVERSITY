package info.kgeorgiy.ja.zaynidinov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implementor implements Impler {
    private static final String TAB = "\t";
    private static final String SPACE = " ";
    private static final String ROUND_OPEN_BRACKET = "(";
    private static final String ROUND_CLOSE_BRACKET = ")";
    private static final String CURLY_OPEN_BRACKET = "{";
    private static final String CURLY_CLOSE_BRACKET = "}";
    private static final String COMMA = ",";
    private static final String RETURN = "return";
    private static final String SEMICOLON = ";";
    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";
    private static final String FALSE = "false";
    private static final String NULL = "null";
    private static final String IMPL = "Impl";
    private static final String IMPLEMENTS = "implements";
    private static final String EXTENDS = "extends";
    private static final String CLASS = "class";
    private static final String PACKAGE = "package";
    private static final String OVERRIDE = "@Override";
    private static final String SUPER = "super";
    private static final String NEW_LINE = "\n";
    private static final String PARAM = "param";
    private static final String PUBLIC = "public";
    private static final String PRIVATE = "private";
    private static final String PROTECTED = "protected";
    private static final String STATIC = "static";
    private static final String FINAL = "final";
    private static final String VOLATILE = "volatile";
    private static final String TRANSIENT = "transient";
    private static final String NATIVE = "native";
    private static final String INTERFACE = "interface";
    private static final String ABSTRACT = "abstract";
    private static final String DOT_JAVA = ".java";
    private static final String THROWS = "throws";

    private static final Map<Integer, String> modifierIdToName = Map.of(
            1, PUBLIC,
            2, PRIVATE,
            3, PROTECTED,
            4, STATIC,
            5, FINAL,
            6, VOLATILE,
            7, TRANSIENT,
            8, NATIVE,
            9, INTERFACE,
            10, ABSTRACT
    );

    private static final Set<String> MODIFIERS_TO_SKIP = Set.of(NATIVE, INTERFACE, VOLATILE, ABSTRACT);

    private static int paramsCnt = 0;

    private static void cutFromEnd(StringBuilder builder, int cutCnt) {
        if (builder.length() >= cutCnt) {
            builder.setLength(builder.length() - cutCnt);
        }
    }

    private static void appendWithTabs(StringBuilder builder, Integer tabs, String data) {
        builder.append(TAB.repeat(tabs));
        builder.append(data);
        builder.append(NEW_LINE);
    }

    private static boolean shouldSkipModifier(String modifier) {
        return MODIFIERS_TO_SKIP.contains(modifier);
    }

    private static String generateModifiers(Method method) {
        StringBuilder modifiers = new StringBuilder();
        int modifiersValue = method.getModifiers(); // 10001010
        for (int i = 0; i < 10; i++) {
            if (((modifiersValue >> i) & 1) == 0) {
                continue;
            }
            String modifier = modifierIdToName.get(i + 1);
            if (shouldSkipModifier(modifier)) {
                continue;
            }
            modifiers.append(modifier);
            modifiers.append(SPACE);
        }
        return modifiers.toString();
    }

    private static void addParam(StringBuilder builder, int i) {
        builder.append(PARAM).append(i + 1);
        builder.append(COMMA);
        builder.append(SPACE);
    }

    private static String generateThrowException(Executable executable) {
        if (executable.getExceptionTypes().length == 0) {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(THROWS);
        for (Class<?> e : executable.getExceptionTypes()) {
            builder.append(SPACE).append(e.getCanonicalName());
        }
        builder.append(SPACE);
        return builder.toString();
    }

    private static String generateParameters(Executable executable) { // for Method's and Constructor's
        StringBuilder builder = new StringBuilder();
        paramsCnt = 0;
        for (var parameter : executable.getParameters()) {
            builder.append(parameter.getType().getCanonicalName());
            builder.append(SPACE);
            addParam(builder, paramsCnt);

            paramsCnt += 1;
        }
        cutFromEnd(builder, 2);
        return builder.toString();
    }

    static String generateMethodNameAndParams(Method method) {
        return method.getName() +
                ROUND_OPEN_BRACKET +
                generateParameters(method) +
                ROUND_CLOSE_BRACKET;
    }

    private static String getMethodSignature(Method method) {
        return generateModifiers(method) +
                method.getReturnType().getCanonicalName() +
                SPACE +
                generateMethodNameAndParams(method) + // name + parameters
                SPACE +
                generateThrowException(method) +
                CURLY_OPEN_BRACKET;
    }

    private static String generateDefaultValue(Method method) {
        if (method.getReturnType().equals(void.class)) {
            return EMPTY_STRING;
        } else if (!method.getReturnType().isPrimitive()) {
            return SPACE + NULL;
        } else if (method.getReturnType().equals(boolean.class)) {
            return SPACE + FALSE;
        } else {
            return SPACE + ZERO;
        }
    }

    private static String generateMethodBody(Method method) {
        return RETURN +
                generateDefaultValue(method) +
                SEMICOLON;
    }

    private static String generatePackage(Class<?> token) {
        if (token.getPackageName().isEmpty()) {
            return EMPTY_STRING;
        }
        return PACKAGE +
                SPACE +
                token.getPackageName() +
                SEMICOLON +
                NEW_LINE +
                NEW_LINE;
    }

    private static String implementsOrExtends(Class<?> token) {
        return Modifier.isInterface(token.getModifiers())
                ? SPACE + IMPLEMENTS + SPACE
                : SPACE + EXTENDS + SPACE;
    }

    private static String generateHeader(Class<?> token) {
        return generatePackage(token) +
                PUBLIC +
                SPACE +
                CLASS +
                SPACE +
                token.getSimpleName() +
                IMPL +
                implementsOrExtends(token) +
                token.getCanonicalName() +
                SPACE +
                CURLY_OPEN_BRACKET +
                NEW_LINE;
    }

    private static String generateConstructorSignature(Constructor<?> constructor, String className) {
        return PUBLIC +
                SPACE +
                className +
                IMPL +
                ROUND_OPEN_BRACKET +
                generateParameters(constructor) +
                ROUND_CLOSE_BRACKET +
                SPACE +
                generateThrowException(constructor) +
                CURLY_OPEN_BRACKET;
    }

    private static String generateConstructorSuperParams() {
        if (paramsCnt == 0) {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < paramsCnt; ++i) {
            addParam(builder, i);
        }
        cutFromEnd(builder, 2);
        return builder.toString();
    }

    private static String generateConstructorBody() {
        return SUPER +
                ROUND_OPEN_BRACKET +
                generateConstructorSuperParams() +
                ROUND_CLOSE_BRACKET +
                SEMICOLON;
    }

    private static void appendConstructor(StringBuilder builder, Constructor<?> constructor, Class<?> token) {
        appendWithTabs(builder, 1, generateConstructorSignature(constructor, token.getSimpleName()));
        appendWithTabs(builder, 2, generateConstructorBody());
        appendWithTabs(builder, 1, CURLY_CLOSE_BRACKET);
        builder.append(NEW_LINE);
    }

    private static String generateConstructors(Class<?> token) throws ImplerException {
        StringBuilder builder = new StringBuilder();
        int constructors = 0;
        for (Constructor<?> constructor : token.getDeclaredConstructors()) {
            if (!Modifier.isPrivate(constructor.getModifiers())) {
                appendConstructor(builder, constructor, token);
                constructors++;
            }
        }
        if (constructors == 0) {
            if (token.isInterface()) {
                return EMPTY_STRING;
            }
            throw new ImplerException("Class token should have at least 1 constructor");
        }
        cutFromEnd(builder, 1);
        return builder.toString();
    }

    private static Set<ComparableMethod> getAllMethods(Class<?> token) {
        Set<ComparableMethod> allMethods = new HashSet<>();
        for (Method method : token.getMethods()) {
            allMethods.add(new ComparableMethod(method));
        }
        if (token.isInterface()) {
            return allMethods;
        }
        while (token != null) {
            for (Method method : token.getDeclaredMethods()) {
                allMethods.add(new ComparableMethod(method));
            }
            token = token.getSuperclass();
        }
        return allMethods;
    }

    private static void appendMethod(StringBuilder builder, Method method) {
        appendWithTabs(builder, 1, OVERRIDE);
        appendWithTabs(builder, 1, getMethodSignature(method));
        appendWithTabs(builder, 2, generateMethodBody(method));
        appendWithTabs(builder, 1, CURLY_CLOSE_BRACKET);
        builder.append(NEW_LINE);
    }

    private static String generateMethods(Class<?> token) {
        Set<ComparableMethod> methods = getAllMethods(token);
        StringBuilder builder = new StringBuilder();
        for (ComparableMethod comparableMethod : methods) {
            Method method = comparableMethod.getMethod();
            if (Modifier.isAbstract(method.getModifiers())) {
                appendMethod(builder, method);
            }
        }
        cutFromEnd(builder, 1);
        return builder.toString();
    }

    private Path createParentDirectories(Class<?> token, Path path) throws ImplerException {
        try {
            path = path
                    .resolve(token.getPackageName().replace('.', File.separatorChar))
                    .resolve(token.getSimpleName() + IMPL + DOT_JAVA);
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            return path;
        } catch (IOException e) {
            throw new ImplerException("Can't directory create for implementor class.");
        }
    }

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token.isPrimitive()
                || token == Enum.class
                || Modifier.isPrivate(token.getModifiers())
                || Modifier.isFinal(token.getModifiers())) {
            throw new ImplerException("Error: provided primitive or Enum class");
        }
        Path path = createParentDirectories(token, root);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(generateHeader(token) +
                    generateConstructors(token) +
                    generateMethods(token) +
                    CURLY_CLOSE_BRACKET
            );
        } catch (IOException e) {
            throw new ImplerException("Error occurred on creating file or parent directories");
        }
    }
}