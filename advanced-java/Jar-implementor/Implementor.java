package info.kgeorgiy.ja.zaynidinov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * @author Mirzofirdavs Zaynidinov.
 *
 * <p>
 * Class implements {@link JarImpler} and {@link Impler} interfaces.
 * </p>
 */
public class Implementor implements Impler, JarImpler {
    /**
     * Tab for generated code.
     */
    private static final String TAB = "\t";
    /**
     * Whitespace for generated code.
     */
    private static final String SPACE = " ";
    /**
     * Round open bracket for generated code.
     */
    private static final String ROUND_OPEN_BRACKET = "(";
    /**
     * Round close bracket for generated code.
     */
    private static final String ROUND_CLOSE_BRACKET = ")";
    /**
     * Curly open bracket for generated code.
     */
    private static final String CURLY_OPEN_BRACKET = "{";
    /**
     * Curly close bracket for generated code.
     */
    private static final String CURLY_CLOSE_BRACKET = "}";
    /**
     * Comma for generated code.
     */
    private static final String COMMA = ",";
    /**
     * Return - for generated code.
     */
    private static final String RETURN = "return";
    /**
     * Semicolon for generated code.
     */
    private static final String SEMICOLON = ";";
    /**
     * Empty string for generated code;
     */
    private static final String EMPTY_STRING = "";
    /**
     * Number zero is default value of some methods.
     */
    private static final String ZERO = "0";
    /**
     * Boolean value false is default value of some methods/
     */
    private static final String FALSE = "false";
    /**
     * Null value is default value of some methods.
     */
    private static final String NULL = "null";
    /**
     * Suffix name for generated classes.
     */
    private static final String IMPL = "Impl";
    /**
     * Implements - used for generated code.
     */
    private static final String IMPLEMENTS = "implements";
    /**
     * Extends - used for generated code.
     */
    private static final String EXTENDS = "extends";
    /**
     * Class - used for generated code.
     */
    private static final String CLASS = "class";
    /**
     * Package - used for generated code.
     */
    private static final String PACKAGE = "package";
    /**
     * Override - used for generated code.
     */
    private static final String OVERRIDE = "@Override";
    /**
     * Super - used for generated code.
     */
    private static final String SUPER = "super";
    /**
     * New line for generated code.
     */
    private static final String NEW_LINE = "\n";
    /**
     * Prefix param for name of parameters of methods.
     */
    private static final String PARAM = "param";
    /**
     * Modifier public for generated code.
     */
    private static final String PUBLIC = "public";
    /**
     * Modifier private for generated code.
     */
    private static final String PRIVATE = "private";
    /**
     * Modifier protected for generated code.
     */
    private static final String PROTECTED = "protected";
    /**
     * Modifier static for generated code.
     */
    private static final String STATIC = "static";
    /**
     * Modifier final for generated code.
     */
    private static final String FINAL = "final";
    /**
     * Modifier volatile for generated code.
     */
    private static final String VOLATILE = "volatile";
    /**
     * Modifier transient for generated code.
     */
    private static final String TRANSIENT = "transient";
    /**
     * Modifier native for generated code.
     */
    private static final String NATIVE = "native";
    /**
     * Modifier interface for generated code.
     */
    private static final String INTERFACE = "interface";
    /**
     * Modifier abstract for generated code.
     */
    private static final String ABSTRACT = "abstract";
    /**
     * Java for generate code.
     */
    private static final String JAVA = "java";
    /**
     * Intended for throws exception generated code.
     */
    private static final String THROWS = "throws";

    /**
     * Dot for generated code.
     */
    private static final String DOT = ".";

    /**
     * Slash for generated code.
     */
    private static final String SLASH = "/";

    /**
     * Manifest version for generated code.
     */
    private static final String MANIFEST_VERSION = "1.0";

    /**
     * Encoding for generated code.
     */
    private static final String ENCODING = "-encoding";

    /**
     * UTF format for generated code.
     */
    private static final String UTF_FORMAT = "UTF-8";

    /**
     * Cp for generated code.
     */
    private static final String CP = "-cp";

    /**
     * Prefix temp for directories.
     */
    private static final String TEMP = "temp";

    /**
     * {@link Map} Map for mapping modifiers ID to Name.
     */
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

    /**
     * {@link Set} Set for modifiers to skip.
     */
    private static final Set<String> MODIFIERS_TO_SKIP = Set.of(NATIVE, INTERFACE, VOLATILE, ABSTRACT);

    /**
     * Variable for counting the number of parameters.
     */
    private static int paramsCnt;

    /**
     * {@link Void} Cuts the chars with a given number from the end of StringBuilder.
     *
     * @param builder {@link StringBuilder} for cut.
     * @param cutCnt  {@link Integer} the number of chars which will cut from the end.
     */
    private static void cutFromEnd(StringBuilder builder, int cutCnt) {
        if (builder.length() >= cutCnt) {
            builder.setLength(builder.length() - cutCnt);
        }
    }

    /**
     * {@link Void} Adds data with the specified number of tabs to the StringBuilder.
     *
     * @param builder {@link StringBuilder} container for adding data.
     * @param tabs    {@link Integer} the number of tabs for adding.
     * @param data    {@link String} the string, which we want to add.
     */
    private static void appendWithTabs(StringBuilder builder, Integer tabs, String data) {
        builder.append(TAB.repeat(tabs));
        builder.append(data);
        builder.append(NEW_LINE);
    }

    /**
     * Check the modifier and skip it, if it contains in set.
     *
     * @param modifier {@link String} the name of modifier which we received.
     * @return {@link Boolean} says is there a modifier in the set
     */
    private static boolean shouldSkipModifier(String modifier) {
        return MODIFIERS_TO_SKIP.contains(modifier);
    }

    /**
     * Generated correct {@link String} modifier of {@link Method} method, which receive.
     *
     * @param method {@link Method} which we find modifier.
     * @return {@link String} generated modifier for method.
     */
    private static String generateModifiers(Method method) {
        StringBuilder modifiers = new StringBuilder();
        int modifiersValue = method.getModifiers();
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

    /**
     * {@link Void} Add the name of parameters for methods.
     *
     * @param builder {@link StringBuilder} generated StringBuilder for parameters methods.
     * @param i       {@link Integer} the number of the current name of parameter.
     */
    private static void addParam(StringBuilder builder, int i) {
        builder.append(PARAM).append(i + 1);
        builder.append(COMMA);
        builder.append(SPACE);
    }

    /**
     * Generated correct java code {@link String} of exceptions the specified {@link Executable}.
     *
     * <p>
     * If specified {@link Executable} don't have exceptions then java code implemented this exceptions
     * is <code>""</code>. Else exceptions separated by a comma, after world <i>throws</i>.
     * </p>
     *
     * @param executable {@link Executable} gives his exceptions.
     * @return {@link String} of exceptions specified {@link Executable}.
     */
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

    /**
     * Generated correct {@link String} java code of type name and parameters for Methods and Constructors.
     *
     * <p>
     * Method takes the {@link Executable} parameter (Method or Constructor) and build the name for all parameters, which it contains.
     * </p>
     *
     * @param executable {@link Executable} the method or constructor for which we should generate the code.
     * @return {@link String} generated parameters for input.
     */
    private static String generateParameters(Executable executable) {
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

    /**
     * Generated correct java code of {@link String} name and parameters for taken {@link Method} method.
     *
     * <p>
     * Method takes the {@link Method} method, and generate the {@link String} which contains the name of method and his parameters.
     * </p>
     *
     * @param method {@link Method} for which we will generate the name and parameters.
     * @return generated {@link String} with name and parameters for taken method.
     */
    static String generateMethodNameAndParams(Method method) {
        return method.getName() +
                ROUND_OPEN_BRACKET +
                generateParameters(method) +
                ROUND_CLOSE_BRACKET;
    }

    /**
     * Generated correct java code of {@link String} header for taken {@link Method} method.
     *
     * <p>
     * Method takes the {@link Method} method, and generate the {@link String} header for this method.
     * </p>
     *
     * @param method {@link Method} for which we will generate header.
     * @return generated {@link String} with header for taken method
     */
    private static String getMethodSignature(Method method) {
        return generateModifiers(method) +
                method.getReturnType().getCanonicalName() +
                SPACE +
                generateMethodNameAndParams(method) + // name + parameters
                SPACE +
                generateThrowException(method) +
                CURLY_OPEN_BRACKET;
    }

    /**
     * Generated correct java code {@link String} which returns default value for {@link Method} method.
     *
     * <p>
     * For taken {@link Method} method read his type.
     * If the return type is equals to {@link Void}, it returns the empty string.
     * Else if the return type is not equals to Primitive, it returns the null.
     * Else if the return type is equals to {@link Boolean}, it returns the false.
     * Else it returns zero.
     * </p>
     *
     * @param method {@link Method} for which return default value.
     * @return generated {@link String} with default value for {@link Method} method.
     */
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

    /**
     * Generated correct {@link String} java code of body for {@link Method} method.
     *
     * @param method {@link Method} for which generated the body.
     * @return generated {@link String} with the body for {@link Method} method.
     */
    private static String generateMethodBody(Method method) {
        return RETURN +
                generateDefaultValue(method) +
                SEMICOLON;
    }

    /**
     * Generated correct {@link String} java code of the name of the package which contains {@link Class} token.
     *
     * <p>
     * Fist will be check token's package name.
     * If the package is empty, returns empty string.
     * Else, return the generated {@link String} the package name of {@link Class} token.
     * </p>
     *
     * @param token {@link Class} for which generated the package name.
     * @return {@link String} generated package name for {@link Class} token.
     */
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

    /**
     * Generated correct {@link String} java code Implements or Extends {@link Class} token.
     *
     * <p>
     * First, the method checks the modifier of {@link Class} token.
     * If the modifier is Interface, then it returns the Implements.
     * Else it returns Extends.
     * </p>
     *
     * @param token {@link Class} Input class.
     * @return {@link String} generated implements or extends class.
     */
    private static String implementsOrExtends(Class<?> token) {
        return Modifier.isInterface(token.getModifiers())
                ? SPACE + IMPLEMENTS + SPACE
                : SPACE + EXTENDS + SPACE;
    }

    /**
     * Generated correct java code {@link String}, which generate the header of the taken {@link Class} token.
     *
     * <p>
     * The structure of the header will look like: public class + the name of {@link Class} token +
     * Implement or Extend + get Canonical name of {@link Class} token.
     * </p>
     *
     * @param token the {@link Class} for which generate the header.
     * @return the {@link String} generated header for taken {@link Class} token.
     */
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

    /**
     * Generated correct java code {@link String} of constructor signature, for {@link Constructor} constructor.
     *
     * @param constructor {@link Constructor} for which generated signature.
     * @param className   {@link String} the name of the constructor's class.
     * @return {@link String} constructor signature for {@link Constructor} constructor.
     */
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

    /**
     * Generated correct java code {@link String} if super parameters for Constructors.
     *
     * @return {@link String} the super parameters for Constructors.
     */
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

    /**
     * Generated correct java code {@link String} of Constructor's body.
     *
     * @return {@link String} generated constructor body.
     */
    private static String generateConstructorBody() {
        return SUPER +
                ROUND_OPEN_BRACKET +
                generateConstructorSuperParams() +
                ROUND_CLOSE_BRACKET +
                SEMICOLON;
    }

    /**
     * {@link Void} Add all parts of {@link Constructor} constructor to the structure of {@link Class} token, with tabulations.
     *
     * @param builder     {@link StringBuilder} container for generated code of the {@link Class} token.
     * @param constructor {@link Constructor} current constructor of the {@link Class} token.
     * @param token       {@link Class} taken class, for which adding the constructor.
     */
    private static void appendConstructor(StringBuilder builder, Constructor<?> constructor, Class<?> token) {
        appendWithTabs(builder, 1, generateConstructorSignature(constructor, token.getSimpleName()));
        appendWithTabs(builder, 2, generateConstructorBody());
        appendWithTabs(builder, 1, CURLY_CLOSE_BRACKET);
        builder.append(NEW_LINE);
    }

    /**
     * Generated correct java code {@link String} which generate all {@link Constructor} constructor.
     *
     * <p>
     * If {@link Class} <i>token == Interface</i> then java code implemented constructors
     * is <code>""</code>. Else all not private constructors implemented.
     * and append in {@link StringBuilder}.
     * </p>
     *
     * @param token is {@link Class} for which, we generate constructors.
     * @return {@link String} generate all constructors for {@link Class} token.
     * @throws ImplerException when don't find {@link Constructor} at the specified {@link Class} token.
     */
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

    /**
     * Add all methods from {@link Class} token and all methods of his ancestors to the {@link HashSet} set and returns it.
     *
     * <p>
     * All methods <i>class token</i> and his ancestors put in HashSet wrappers
     * over {@link Method} for correct compare of method. Not public methods get
     * climbing the parse tree and calling {@link Class#getDeclaredMethods()}.
     * </p>
     *
     * @param token {@link Class} is interface or class to implement.
     * @return {@link HashSet} the set of all valid methods.
     */
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

    /**
     * {@link Void} Add all parts of {@link Method} method to the structure of {@link Class} token, with tabulations.
     *
     * @param builder {@link StringBuilder} the container of adding all the part of generating method.
     * @param method  {@link Method} current method which we add now to the container.
     */
    private static void appendMethod(StringBuilder builder, Method method) {
        appendWithTabs(builder, 1, OVERRIDE);
        appendWithTabs(builder, 1, getMethodSignature(method));
        appendWithTabs(builder, 2, generateMethodBody(method));
        appendWithTabs(builder, 1, CURLY_CLOSE_BRACKET);
        builder.append(NEW_LINE);
    }

    /**
     * Static class used for correct representing {@link Method}.
     */
    public static class ComparableMethod {
        /**
         * Wrapped instance of {@link Method}.
         */
        private final Method method;

        /**
         * Constructor of the wrapper class over the specified method.
         *
         * @param method for wrapper.
         */
        public ComparableMethod(Method method) {
            this.method = method;
        }

        /**
         * getter of the current {@link Class}.
         *
         * @return {@link Method} method.
         */
        public Method getMethod() {
            return method;
        }

        /**
         * Calculates the correct hash of the wrapped {@link Method}.
         *
         * @return correct hash of {@link Method}.
         */
        @Override
        public int hashCode() {
            return Objects.hash(Implementor.generateMethodNameAndParams(method));
        }

        /**
         * Correct equals of wrapped methods.
         *
         * <p>
         * If specified <code>object != wrapper</code> return false.
         * Comparison first by {@link Class} of parameters <i>tokens</i> and
         * compare {@link Method#getName()}.
         * </p>
         *
         * @param other is {@link Object} to compare.
         * @return comparison result this with specified {@link Object}.
         */
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

    /**
     * Generated correct java code {@link String}, that generated all valid methods.
     *
     * <p>
     * First of all it iterate for all methods in set of methods, and generate only the methods, which modifiers is abstract.
     * </p>
     *
     * @param token {@link Class} taken class, for which generate valid methods.
     * @return {@link String} generating methods of {@link Class} token.
     */
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

    /**
     * Changes the given path into a correct one. Replacing with correct separator, appends Impl.java.
     *
     * @param root  {@link Path} the given path.
     * @param token the {@link Class} which for generate path.
     * @return the correct path.
     */
    private Path getFullPath(Path root, Class<?> token) {
        return root.resolve(token
                        .getPackageName()
                        .replace('.', File.separatorChar))
                .resolve(token.getSimpleName() + IMPL + DOT + JAVA);
    }

    /**
     * Creates all parent directories of a given root.
     *
     * @param token the {@link Class} token of implemented class.
     * @param root  the given path.
     * @return the encoded string.
     * @throws ImplerException if an error occurs while creating parent dirs.
     */
    private Path createParentDirectories(Class<?> token, Path root) throws ImplerException {
        Path path = getFullPath(root, token);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new ImplerException("Can't directory create for implementor class.");
        }
        return path;
    }

    /**
     * Encodes the provided string.
     *
     * @param s a {@link String} to encode.
     * @return the encoded string.
     */
    private String encode(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) {
            builder.append("\\u").append(String.format("%04x", (int) c));
        }
        return builder.toString();
    }

    /**
     * Generated java file implements the specified <i>token</i> and puts in the {@link Path} with name is {@link Class#getSimpleName()} + Impl.
     *
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws ImplerException when:
     *                         <ul>
     *                             <li><i>token == null or root == null</i></li>
     *                             <li><i>token == Primitive</i></li>
     *                             <li><i>token == Final class</i></li>
     *                             <li><i>token == Private class or interface</i></li>
     *                             <li><i>token == Enum.class</i></li>
     *                         </ul>
     */
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
            bufferedWriter.write(encode(generateHeader(token) +
                    generateConstructors(token) +
                    generateMethods(token) +
                    CURLY_CLOSE_BRACKET)
            );
        } catch (IOException e) {
            throw new ImplerException("Error occurred on creating file or parent directories");
        }
    }

    /**
     * Invoked in case the program was run with -jar key.
     * Creates the temporary directory and invokes methods for implementing, compiling, creating jar file.
     *
     * @param token   the {@link Class} token of implemented class.
     * @param jarFile {@link Path} name of jar file to be generated
     * @throws ImplerException if an error occurs while creating parent path for jar or temporary directory for source.
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        Path parentPath = jarFile.toAbsolutePath().normalize().getParent();
        Path TempPath;
        try {
            TempPath = Files.createTempDirectory(parentPath, TEMP);
        } catch (IOException e) {
            throw new ImplerException("Can not create temp directory");
        }
        implement(token, TempPath);
        compile(token, TempPath);
        generateManifest(token, TempPath, jarFile);
    }

    /**
     * Compiles the solution.
     *
     * @param token      the {@link Class} token of implemented class.
     * @param sourcePath the temp directory with classes.
     * @throws ImplerException if an error occurs while compiling the result or searching for classpath.
     */
    private void compile(Class<?> token, Path sourcePath) throws ImplerException {
        Path tokenClassPath;
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        try {
            String path =
                    token.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath();
            path = !path.startsWith(SLASH) ? path : path.substring(1);
            tokenClassPath = Path.of(path);
        } catch (InvalidPathException e) {
            throw new ImplerException("Cannot find class path", e);
        }
        String[] arguments = {
                ENCODING
                , UTF_FORMAT
                , CP
                , sourcePath.toString() + File.pathSeparator + tokenClassPath
                , getFullPath(sourcePath, token).toString()
        };
        final int code = javaCompiler.run(null, null, null, arguments);
        if (code != 0) {
            throw new ImplerException("Error compiling classes!");
        }
    }

    /**
     * Generates manifest by given token, temporary path and jar path.
     *
     * @param token   {@link Class} given token.
     * @param path    {@link Path} given path.
     * @param jarFile {@link Path} jar path.
     * @throws ImplerException if an error occurs while creating jar file.
     */
    private void generateManifest(Class<?> token, Path path, Path jarFile) throws ImplerException {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, MANIFEST_VERSION);
        String className = token.getPackageName().replace(DOT, SLASH)
                + SLASH
                + token.getSimpleName()
                + IMPL
                + DOT
                + CLASS;
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            jarOutputStream.putNextEntry(new JarEntry(className));
            Files.copy(path.resolve(className), jarOutputStream);
        } catch (IOException e) {
            throw new ImplerException("Can not creating jar-file.");
        }
    }
}