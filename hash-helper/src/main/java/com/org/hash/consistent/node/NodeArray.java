package com.org.hash.consistent.node;

import com.org.hash.normal.node.Node;
import com.org.hash.normal.node.Obj;

import java.util.SortedMap;
import java.util.TreeMap;

public class NodeArray {
    TreeMap<Integer, Node> nodeTreeMap = new TreeMap<>();

    public void addNode(Node node) {
        nodeTreeMap.put(node.hashCode(), node);
    }

    public void put(Obj obj) {
        int objHashCode = obj.hashCode();
        Node node = nodeTreeMap.get(objHashCode);
        if (node != null) {
            node.putObject(obj);
            return;
        }
        // 找到比给定 key 大的集合
        SortedMap<Integer, Node> sortedMap = nodeTreeMap.tailMap(objHashCode);

        int firstHashCode = sortedMap.isEmpty() ? nodeTreeMap.firstKey() : sortedMap.firstKey();

        nodeTreeMap.get(firstHashCode).putObject(obj);
    }

    public Obj get(Obj obj) {
        Node node = nodeTreeMap.get(obj.hashCode());
        if (node != null) {
            return node.getObject(obj);
        }

        // 找到比给定 key 大的集合
        SortedMap<Integer, Node> tailMap = nodeTreeMap.tailMap(obj.hashCode());
        // 找到最小的节点
        int nodeHashcode = tailMap.isEmpty() ? nodeTreeMap.firstKey() : tailMap.firstKey();
        return nodeTreeMap.get(nodeHashcode).getObject(obj);
    }
}
