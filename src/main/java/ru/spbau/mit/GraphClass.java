package ru.spbau.mit;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class GraphClass {

    private final Class<?> clazz;
    private Class<?> implementation = null;
    private List<GraphClass> parents = new ArrayList<>();
    private boolean ambiguous = false;
    private boolean hasChildren = false;

    public GraphClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void addParent(GraphClass newParent) {
        newParent.hasChildren = true;
        parents.add(newParent);
    }

    private void corrupt() {
        ambiguous = true;
        for (GraphClass parent : parents) {
            parent.corrupt();
        }
    }

    public boolean isAmbiguous() {
        return ambiguous;
    }

    public boolean isFinalImplementation() {
        return !(hasChildren || clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()));
    }

    public void spreadImplementation() {
        implementation = clazz;
        for (GraphClass parent : parents) {
            parent.spreadImplementation(clazz);
        }
    }

    public Class<?> getImplementation() {
        return implementation;
    }

    private void spreadImplementation(Class<?> implementation) {
        if ((!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) ||
                this.implementation != null) {
            corrupt();
            return;
        }

        this.implementation = implementation;
        for (GraphClass parent : parents) {
            parent.spreadImplementation(clazz);
        }
    }

}
