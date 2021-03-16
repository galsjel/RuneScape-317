import java.util.Arrays;

public class OnDemandRequest extends DoublyLinkedList.Node {

	public int store;
	public int file;
	public boolean important;
	public int cycle;
	public byte[] data;

	public OnDemandRequest() {
		important = true;
	}

	@Override
	public String toString() {
		return "OnDemandRequest{" + "store=" + store + ", file=" + file + ", priority=" + important + ", cycle=" + cycle + ", data=" + Arrays.toString(data) + '}';
	}

}
