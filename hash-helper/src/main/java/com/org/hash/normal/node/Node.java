package com.org.hash.normal.node;

import java.util.HashMap;
import java.util.Map;

public class Node {
    String name;
    Map<Integer, Obj> objMap = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public void putObject(Obj obj) {
        objMap.put(obj.hashCode(), obj);
    }

    public Obj getObject(Obj obj) {
        return objMap.get(obj.hashCode());
    }

    public int hashCode() {
        return name.hashCode();
    }
}
