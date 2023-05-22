import com.google.gson.annotations.SerializedName;

public class Drawable extends DoublyLinkedList.Node {

    @SerializedName("normals")
    public VertexNormal[] normals;
    @SerializedName("min_y")
    public int minY = 1000;

    public Drawable() {
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
