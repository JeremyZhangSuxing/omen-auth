package com.imooc.test;

public class ReverseList {


    public static void main(String[] args) {
        ListNode listNode5 = new ListNode(5, null);
        ListNode listNode4 = new ListNode(4, listNode5);
        ListNode listNode3 = new ListNode(3, listNode4);
        ListNode listNode2 = new ListNode(2, listNode3);
        ListNode listNode1 = new ListNode(1, listNode2);
//        ListNode listNode = iterate(listNode1);
//        System.out.println(listNode);
        ListNode recursion = recursion(listNode1);
        System.out.println(recursion);
    }

    static class ListNode {
        int value;
        private ListNode next;


        public ListNode(int value, ListNode listNode) {
            this.value = value;
            this.next = listNode;
        }
    }

    /**
     * 链表反转 简单实现
     * 思路
     * 第一个节点指向一个空节点 第二个节点指向 第一个节点 依次遍历
     * 1. 第一个节点断链之后 会找不到第二个节点 需要一个本地变量保存 初始节点的后继节点
     * 2.需要一个本地变量 保存反转后的前置节点
     */
    public static ListNode iterate(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode previous = null, next;
        ListNode current = head;
        while (current != null) {
            //保存当前节点的下一节点
            next = current.next;
            //当前节点的下一节点 指向前置节点
            current.next = previous;
            //当前节点作为 原始下一节点的前置节点 最后的一次指向的是 原始链表末尾的节点
            previous = current;
            current = next;
        }
        return previous;
    }

    /**
     * 递归实现链表反转
     * 1. 从第一个节点开始 每次反转两个节点 但是 考虑到单向链表 翻转后 出现的断链因此需要从后向前翻转
     * 2. head 节点的 下一节点的指针指向 head；head 自身的指针为null
     * 3. 要先找到最后一个节点；单节点链表或者空链表直接返回
     */
    public static ListNode recursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = recursion(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
