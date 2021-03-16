public class DoublyLinkedList {

	public static class Node {

		public Node prev;
		public Node next;

		public void unlink() {
			if (next != null) {
				next.prev = prev;
				prev.next = next;
				prev = null;
				next = null;
			}
		}

	}

	public final Node head;
	public Node peeked;

	public DoublyLinkedList() {
		head = new Node();
		head.prev = head;
		head.next = head;
	}

	public void pushBack(Node node) {
		if (node.next != null) {
			node.unlink();
		}
		node.next = head.next;
		node.prev = head;
		node.next.prev = node;
		node.prev.next = node;
	}

	public void pushFront(Node node) {
		if (node.next != null) {
			node.unlink();
		}
		node.next = head;
		node.prev = head.prev;
		node.next.prev = node;
		node.prev.next = node;
	}

	public Node pollFront() {
		Node node = head.prev;
		if (node == head) {
			return null;
		} else {
			node.unlink();
			return node;
		}
	}

	public Node peekFront() {
		Node node = head.prev;
		if (node == head) {
			peeked = null;
			return null;
		} else {
			peeked = node.prev;
			return node;
		}
	}

	public Node peekBack() {
		Node node = head.next;
		if (node == head) {
			peeked = null;
			return null;
		} else {
			peeked = node.next;
			return node;
		}
	}

	public Node prev() {
		Node node = peeked;
		if (node == head) {
			peeked = null;
			return null;
		} else {
			peeked = node.prev;
			return node;
		}
	}

	public Node next() {
		Node node = peeked;
		if (node == head) {
			peeked = null;
			return null;
		}
		peeked = node.next;
		return node;
	}

	public void clear() {
		if (head.prev == head) {
			return;
		}
		do {
			Node node = head.prev;
			if (node == head) {
				return;
			}
			node.unlink();
		} while (true);
	}

}
