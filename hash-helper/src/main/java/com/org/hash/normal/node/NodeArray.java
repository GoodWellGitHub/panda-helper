package com.org.hash.normal.node;

public class NodeArray {
    Node[] nodes = new Node[1024];

    int size = 0;

    public void addNode(Node node) {
        nodes[size++] = node;
    }

    public Obj getObj(Obj obj) {
        int index = obj.hashCode() % size;
        return nodes[index].getObject(obj);
    }

    public void putObj(Obj obj) {
        int index = obj.hashCode() % size;
        nodes[index].putObject(obj);
    }
}
