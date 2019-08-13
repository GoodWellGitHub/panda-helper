package com.org.hash.normal.node;

public class NodeMain {
    public static void main(String[] args) {
        NodeArray nodeArray = new NodeArray();

        Node[] nodes = {
                new Node("Node--> 1"),
                new Node("Node--> 2"),
                new Node("Node--> 3")
        };

        for (Node node : nodes) {
            nodeArray.addNode(node);
        }

        Obj[] objs = {
                new Obj("1"),
                new Obj("2"),
                new Obj("3"),
                new Obj("4"),
                new Obj("5")
        };

        for (Obj obj : objs) {
            nodeArray.putObj(obj);
        }

        validate(nodeArray, objs);
    }

    private static void validate(NodeArray nodeArray, Obj[] objs) {
        for (Obj obj : objs) {
            System.out.println(nodeArray.getObj(obj));
        }

        nodeArray.addNode(new Node("anything1"));
        nodeArray.addNode(new Node("anything2"));

        System.out.println("========== after  =============");

        for (Obj obj : objs) {
            System.out.println(nodeArray.getObj(obj));
        }
    }
}
