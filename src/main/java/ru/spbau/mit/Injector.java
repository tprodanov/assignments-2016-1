package ru.spbau.mit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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
        implementationClassNames = new ArrayList(implementationClassNames);
        implementationClassNames.add(rootClassName);
        constructImplementationClasses(implementationClassNames);

        Class<?> root = Class.forName(rootClassName);
        return buildObject(root);
    }


    private static void constructImplementationClasses(List<String> implementationClassNames)
            throws ClassNotFoundException {
        List<Class<?>> parents = new ArrayList<>();
        for (String className : implementationClassNames) {
            Class<?> currentClass = Class.forName(className);
            addClass(currentClass, parents);
        }

        for (int i = 0; i < parents.size(); ++i) {
            Class<?> currentClass = parents.get(i);
            addClass(currentClass, parents);
        }

        for (Map.Entry<Class<?>, GraphClass> entry : graphNodes.entrySet()) {
            if (entry.getValue().isFinalImplementation()) {
                entry.getValue().spreadImplementation();
            }
        }
    }

    private static void addClass(Class<?> currentClass, List<Class<?>> parents) {
        if (!graphNodes.containsKey(currentClass)) {
            graphNodes.put(currentClass, new GraphClass(currentClass));
        }
        GraphClass currentNode = graphNodes.get(currentClass);


        Class<?> superclass = currentClass.getSuperclass();
        addParent(currentNode, superclass);
        if (superclass != null && Modifier.isAbstract(superclass.getModifiers())) {
            parents.add(superclass);
        }

        for (Class<?> parentInterface : currentClass.getInterfaces()) {
            addParent(currentNode, parentInterface);
            parents.add(parentInterface);
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
        if (node == null || node.getImplementation() == null) {
            throw new ImplementationNotFoundException();
        }

        if (node.isAmbiguous()) {
            throw new AmbiguousImplementationException();
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
