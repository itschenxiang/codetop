package com.itschenxiang.leetcode.codetop;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private static class DLNode {
        private int key;
        private int value;
        private DLNode prev;
        private DLNode next;

        public DLNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public DLNode getPrev() {
            return prev;
        }

        public void setPrev(DLNode prev) {
            this.prev = prev;
        }

        public DLNode getNext() {
            return next;
        }

        public void setNext(DLNode next) {
            this.next = next;
        }
    }
    
    private int capacity;
    private int size;
    private DLNode head;
    private DLNode tail;
    private Map<Integer, DLNode> nodeMap;
    public LRUCache(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("the capacity must greater than 0");
        }
        this.capacity = capacity;
        this.size = 0;
        this.head = new DLNode(-1, -1);
        this.tail = new DLNode(-1, -1);
        this.head.next = tail;
        this.tail.prev = head;
        this.nodeMap = new HashMap<>();
    }

    // get
    // 查map 无返回 有放队头返回
    public int get(int key) {
        if(!nodeMap.containsKey(key)) {
            return -1;
        } else {
            DLNode targetNode = nodeMap.get(key);
            removeNode(targetNode);
            addHead(targetNode);
            return targetNode.getValue();
        }
    }

    // put
    // 查map是否存在
    // 存在则更新value，删除并放到队头
    // 不存在则新增节点，并放入map，检查队列容量，大于capacity则删除一个队尾元素并从map删除
    public void put(int key, int value) {
        if(nodeMap.containsKey(key)) {
            DLNode targetNode = nodeMap.get(key);
            removeNode(targetNode);
            targetNode.setValue(value);
            addHead(targetNode);
        } else {
            DLNode newNode = new DLNode(key, value);
            addHead(newNode);
            nodeMap.put(key, newNode);
            size++;
            if(size > capacity) {
                DLNode tailNode = removeTail();
                nodeMap.remove(tailNode.getKey());
                size--;
            }
        }
    }
    
    private void addHead(DLNode node) {
        DLNode headNext = head.next;
        head.next = node;
        node.prev = head;
        node.next = headNext;
        headNext.prev = node;
    }
    private DLNode removeTail() {
        DLNode tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }
    private void removeNode(DLNode node) {
        DLNode prev = node.prev;
        DLNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }
}
