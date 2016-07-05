package test.alrotic;

import java.util.Stack;

public class PrintListReverse {

	
	public static void main(String[] args) {
	
	}
	
	public static void printListReverse(ListNode headNode){
		
		Stack<ListNode> stack=new Stack<>();
		while(headNode!=null){
			stack.push(headNode);
			headNode=headNode.next;
		}
	}
}


class ListNode{
	ListNode next;
	ListNode before;
	public ListNode getNext() {
		return next;
	}
	public void setNext(ListNode next) {
		this.next = next;
	}
	public ListNode getBefore() {
		return before;
	}
	public void setBefore(ListNode before) {
		this.before = before;
	}
	
	
}
