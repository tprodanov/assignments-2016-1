package ru.spbau.mit;

import java.lang.reflect.Constructor;
import java.util.*;


public final class Injector {

    private static Map<Class<?>, GraphClass> graphNodes = new HashMap<>();
    private static Map<Class<?>, Object> implementations = new HashMap<>();
    private static Set<Class<?>> visited = new HashSet<>();

    private Injector() {
    }

    /**
     * Create and initialize object of `rootClassName` class using classes from
     * `implementationClassNames` for concrete dependencies.
     */
    public static Object initialize(String rootClassName, List<String> implementationClassNames) throws Exception {
        Class<?> root = Class.forName(rootClassName);
        graphNodes.put(root, new GraphClass(root));
        constructImplementationClasses(implementationClassNames);
        return buildObject(root);
    }


    private static void constructImplementationClasses(List<String> implementationClassNames)
            throws ClassNotFoundException {
        for (String className : implementationClassNames) {
            Class<?> currentClass = Class.forName(className);

            if (!graphNodes.containsKey(currentClass)) {
                graphNodes.put(currentClass, new GraphClass(currentClass));
            }
            GraphClass currentNode = graphNodes.get(currentClass);

            addParent(currentNode, currentClass.getSuperclass());
            for (Class<?> parentInterface : currentClass.getInterfaces()) {
                addParent(currentNode, parentInterface);
            }
        }

        for (Map.Entry<Class<?>, GraphClass> entry : graphNodes.entrySet()) {
            if (entry.getValue().isFinalImplementation()) {
                entry.getValue().spreadImplementation();
            }
        }
    }

    private static void addParent(GraphClass currentNode, Class<?> parent) {
        if (parent == null) {
            return;
        }
        if (!graphNodes.containsKey(parent)) {
            graphNodes.put(parent, new GraphClass(parent));
        }
        GraphClass superNode = graphNodes.get(parent);
        currentNode.addParent(superNode);
    }

    private static Object buildObject(Class<?> clazz) throws Exception {
        if (implementations.containsKey(clazz)) {
            return implementations.get(clazz);
        }

        GraphClass node = graphNodes.get(clazz);
        if (node.isAmbiguous()) {
            throw new AmbiguousImplementationException();
        }

        if (node.getImplementation() == null) {
            throw new ImplementationNotFoundException();
        }

        if (visited.contains(clazz)) {
            throw new InjectionCycleException();
        }
        visited.add(clazz);

        Class<?> implementationClass = node.getImplementation();
        Constructor<?> constructor = implementationClass.getConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        int i = 0;
        for (Class<?> type : parameterTypes) {
            parameters[i] = buildObject(type);
            i += 1;
        }

        Object newObj = constructor.newInstance(parameters);
        implementations.put(clazz, newObj);
        return newObj;
    }

}
