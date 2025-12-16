package com.myproject.utils;

import com.myproject.utils.data.AdminCloseAccountMessages;
import com.myproject.utils.data.RequestResets;
import com.myproject.utils.data.UserInfo;

public class LinkedList {
	public Node head;
    public Node tail;
    int size;

    public class Node{
        public Object data;
        public Node next;

        Node(Object data){
            this.data = data;
            this.next = null;
        }
    }

    public void addFirst(Object data){
        Node node = new Node(data);
        node.next = head;
        head = node;
        if(tail == null){
            tail = head;
        }
        size++;
    }

    public void addLast(Object data){
        if(head == null){
            addFirst(data);
            return;
        }
        Node node = new Node(data);
        tail.next = node;
        tail = node;
        size++;
    }

    public void deleteByAccountNumber(String acctNum) {
        Node temp = head;
        Node prev = null;

        while (temp != null) {
            boolean match = false;

            if (temp.data instanceof RequestResets) {
                RequestResets rr = (RequestResets) temp.data;

                if (rr.getAccountNumber().equals(acctNum)) {
                    match = true;
                }
            }

            if(temp.data instanceof AdminCloseAccountMessages){
                AdminCloseAccountMessages abc = (AdminCloseAccountMessages) temp.data;

                if(abc.getSenderAccountNumber().equals(acctNum)){
                    match = true;
                }
            }

            if(match){
                if (prev == null) {
                    head = temp.next;

                    if (head == null) {
                        tail = null;
                    }
                }else {
                    prev.next = temp.next;
                    if (temp == tail){
                        tail = prev;
                    }
                }

                size--;
                return;
            }

            prev = temp;
            temp = temp.next;
        }
    }

    public void sort(){
        if(this.size <= 1){
            return;
        }

        Node current, index;
        UserInfo temp;

        for(current = this.head; current != null; current = current.next){
            for(index = current.next; index != null; index = index.next){
                UserInfo info1 = (UserInfo) current.data;
                UserInfo info2 = (UserInfo) index.data;

                if((info1.getAcctName()).compareToIgnoreCase(info2.getAcctName()) > 0){
                    temp = info1;
                    current.data = info2;
                    index.data = temp;
                }
            }
        }
    }

    public void clear(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}