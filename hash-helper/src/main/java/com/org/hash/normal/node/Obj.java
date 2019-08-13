package com.org.hash.normal.node;

public class Obj {
    private String key;

    public Obj(String key) {
        this.key = key;
    }

    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "Obj{" +
                "key='" + key + '\'' +
                '}';
    }
}
