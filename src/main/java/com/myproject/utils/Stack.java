package com.myproject.utils;

import com.myproject.utils.data.AdminCloseAccountMessages;
import com.myproject.utils.data.RequestResets;

public class Stack {
	public Node top;
	public int size;

	public class Node{
		public Object data;
		public Node next;

		Node(Object data){
			this.data = data;
			this.next = null;
		}
	}

    public void push(Object data){
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public void pop(){
        if(top == null){
            return;
        }
        top = top.next;
        size--;
    }

    public void popByAccountNumber(String acctNum){
        Node curr = top;
        Node prev = null;

        while(curr != null){
            boolean match = false;

            if (curr.data instanceof RequestResets) {
                RequestResets rr = (RequestResets) curr.data;

                if (rr.getAccountNumber().equals(acctNum)) {
                    match = true;
                }
            }

            if(curr.data instanceof AdminCloseAccountMessages){
                AdminCloseAccountMessages zz = (AdminCloseAccountMessages) curr.data;

                if(zz.getSenderAccountNumber().equals(acctNum)){
                    match = true;
                }
            }

            if(match){
                if(prev == null){
                    top = curr.next;
                }else{
                    prev.next = curr.next;
                }

                size--;
                return;
            }

            prev = curr;
            curr = curr.next;
        }
    }

    public Object peek(){
        return top.data;
    }

    public boolean empty(){
        return (top == null);  
    }
}