public class Entity extends DoublyLinkedList.Node {

	public VertexNormal[] vertexNormal;
	public int minY = 1000;

	public Entity() {
	}

	public void draw(int yaw, int sinEyePitch, int cosEyePitch, int sinEyeYaw, int cosEyeYaw, int relativeX, int relativeY, int relativeZ, int bitset) {
		Model model = getModel();

		if (model != null) {
			minY = model.minY;
			model.draw(yaw, sinEyePitch, cosEyePitch, sinEyeYaw, cosEyeYaw, relativeX, relativeY, relativeZ, bitset);
		}
	}

	public Model getModel() {
		return null;
	}

}
