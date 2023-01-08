// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTileOverlay {

    public static final int[][] SHAPE_POINTS = {
            {1, 3, 5, 7},
            {1, 3, 5, 7},
            {1, 3, 5, 7},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 2, 6},
            {1, 3, 5, 7, 2, 8},
            {1, 3, 5, 7, 2, 8},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 13, 14},
    };
    public static final int[][] SHAPE_PATHS = {
            {0, 1, 2, 3, 0, 0, 1, 3},
            {1, 1, 2, 3, 1, 0, 1, 3},
            {0, 1, 2, 3, 1, 0, 1, 3},
            {0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3},
            {0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4},
            {0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4},
            {0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3},
            {0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3},
            {0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5},
            {0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5},
            {0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3},
            {1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3},
            {1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5},
    };

    public static final int[] tmpScreenX = new int[6];
    public static final int[] tmpScreenY = new int[6];
    public static final int[] tmpViewspaceX = new int[6];
    public static final int[] tmpViewspaceY = new int[6];
    public static final int[] tmpViewspaceZ = new int[6];
    public final int[] vertexX;
    public final int[] vertexY;
    public final int[] vertexZ;
    public final int[] triangleColorA;
    public final int[] triangleColorB;
    public final int[] triangleColorC;
    public final int[] triangleVertexA;
    public final int[] triangleVertexB;
    public final int[] triangleVertexC;
    public final boolean flat;
    public final int shape;
    public final int rotation;
    public final int backgroundRGB;
    public final int foregroundRGB;
    public int[] triangleTextureIDs;

    public SceneTileOverlay(int tileZ, int southwestColor2, int northwestColor1, int northeastY, int textureID, int northeastColor2, int rotation, int southwestColor1, int backgroundRGB, int northeastColor1, int northwestY, int southeastY, int southwestY, int shape, int northwestColor2, int southeastColor2, int southeastColor1, int tileX, int foregroundRGB) {
        flat = (southwestY == southeastY) && (southwestY == northeastY) && (southwestY == northwestY);
        this.shape = shape;
        this.rotation = rotation;
        this.backgroundRGB = backgroundRGB;
        this.foregroundRGB = foregroundRGB;

        final int ONE = 128;
        final int HALF = ONE / 2;
        final int QUARTER = ONE / 4;
        final int THREE_QUARTER = (ONE * 3) / 4;

        int[] points = SHAPE_POINTS[shape];
        int vertexCount = points.length;
        vertexX = new int[vertexCount];
        vertexY = new int[vertexCount];
        vertexZ = new int[vertexCount];
        int[] primaryColors = new int[vertexCount];
        int[] secondaryColors = new int[vertexCount];

        int sceneX = tileX * ONE;
        int sceneZ = tileZ * ONE;

        for (int v = 0; v < vertexCount; v++) {
            int type = points[v];

            if (((type & 1) == 0) && (type <= 8)) {
                type = ((type - rotation - rotation - 1) & 7) + 1;
            }

            if ((type > 8) && (type <= 12)) {
                type = ((type - 9 - rotation) & 3) + 9;
            }

            if ((type > 12) && (type <= 16)) {
                type = ((type - 13 - rotation) & 3) + 13;
            }

            int x;
            int z;
            int y;
            int color1;
            int color2;
            if (type == 1) {
                x = sceneX;
                z = sceneZ;
                y = southwestY;
                color1 = southwestColor1;
                color2 = southwestColor2;
            } else if (type == 2) {
                x = sceneX + HALF;
                z = sceneZ;
                y = (southwestY + southeastY) >> 1;
                color1 = (southwestColor1 + southeastColor1) >> 1;
                color2 = (southwestColor2 + southeastColor2) >> 1;
            } else if (type == 3) {
                x = sceneX + ONE;
                z = sceneZ;
                y = southeastY;
                color1 = southeastColor1;
                color2 = southeastColor2;
            } else if (type == 4) {
                x = sceneX + ONE;
                z = sceneZ + HALF;
                y = (southeastY + northeastY) >> 1;
                color1 = (southeastColor1 + northeastColor1) >> 1;
                color2 = (southeastColor2 + northeastColor2) >> 1;
            } else if (type == 5) {
                x = sceneX + ONE;
                z = sceneZ + ONE;
                y = northeastY;
                color1 = northeastColor1;
                color2 = northeastColor2;
            } else if (type == 6) {
                x = sceneX + HALF;
                z = sceneZ + ONE;
                y = (northeastY + northwestY) >> 1;
                color1 = (northeastColor1 + northwestColor1) >> 1;
                color2 = (northeastColor2 + northwestColor2) >> 1;
            } else if (type == 7) {
                x = sceneX;
                z = sceneZ + ONE;
                y = northwestY;
                color1 = northwestColor1;
                color2 = northwestColor2;
            } else if (type == 8) {
                x = sceneX;
                z = sceneZ + HALF;
                y = (northwestY + southwestY) >> 1;
                color1 = (northwestColor1 + southwestColor1) >> 1;
                color2 = (northwestColor2 + southwestColor2) >> 1;
            } else if (type == 9) {
                x = sceneX + HALF;
                z = sceneZ + QUARTER;
                y = (southwestY + southeastY) >> 1;
                color1 = (southwestColor1 + southeastColor1) >> 1;
                color2 = (southwestColor2 + southeastColor2) >> 1;
            } else if (type == 10) {
                x = sceneX + THREE_QUARTER;
                z = sceneZ + HALF;
                y = (southeastY + northeastY) >> 1;
                color1 = (southeastColor1 + northeastColor1) >> 1;
                color2 = (southeastColor2 + northeastColor2) >> 1;
            } else if (type == 11) {
                x = sceneX + HALF;
                z = sceneZ + THREE_QUARTER;
                y = (northeastY + northwestY) >> 1;
                color1 = (northeastColor1 + northwestColor1) >> 1;
                color2 = (northeastColor2 + northwestColor2) >> 1;
            } else if (type == 12) {
                x = sceneX + QUARTER;
                z = sceneZ + HALF;
                y = (northwestY + southwestY) >> 1;
                color1 = (northwestColor1 + southwestColor1) >> 1;
                color2 = (northwestColor2 + southwestColor2) >> 1;
            } else if (type == 13) {
                x = sceneX + QUARTER;
                z = sceneZ + QUARTER;
                y = southwestY;
                color1 = southwestColor1;
                color2 = southwestColor2;
            } else if (type == 14) {
                x = sceneX + THREE_QUARTER;
                z = sceneZ + QUARTER;
                y = southeastY;
                color1 = southeastColor1;
                color2 = southeastColor2;
            } else if (type == 15) {
                x = sceneX + THREE_QUARTER;
                z = sceneZ + THREE_QUARTER;
                y = northeastY;
                color1 = northeastColor1;
                color2 = northeastColor2;
            } else {
                x = sceneX + QUARTER;
                z = sceneZ + THREE_QUARTER;
                y = northwestY;
                color1 = northwestColor1;
                color2 = northwestColor2;
            }
            vertexX[v] = x;
            vertexY[v] = y;
            vertexZ[v] = z;
            primaryColors[v] = color1;
            secondaryColors[v] = color2;
        }

        int[] paths = SHAPE_PATHS[shape];
        int triangleCount = paths.length / 4;
        triangleVertexA = new int[triangleCount];
        triangleVertexB = new int[triangleCount];
        triangleVertexC = new int[triangleCount];
        triangleColorA = new int[triangleCount];
        triangleColorB = new int[triangleCount];
        triangleColorC = new int[triangleCount];

        if (textureID != -1) {
            triangleTextureIDs = new int[triangleCount];
        }

        int index = 0;
        for (int i = 0; i < triangleCount; i++) {
            int color = paths[index];
            int a = paths[index + 1];
            int b = paths[index + 2];
            int c = paths[index + 3];

            index += 4;

            if (a < 4) {
                a = (a - rotation) & 3;
            }

            if (b < 4) {
                b = (b - rotation) & 3;
            }

            if (c < 4) {
                c = (c - rotation) & 3;
            }

            triangleVertexA[i] = a;
            triangleVertexB[i] = b;
            triangleVertexC[i] = c;

            if (color == 0) {
                triangleColorA[i] = primaryColors[a];
                triangleColorB[i] = primaryColors[b];
                triangleColorC[i] = primaryColors[c];
                if (triangleTextureIDs != null) {
                    triangleTextureIDs[i] = -1;
                }
            } else {
                triangleColorA[i] = secondaryColors[a];
                triangleColorB[i] = secondaryColors[b];
                triangleColorC[i] = secondaryColors[c];
                if (triangleTextureIDs != null) {
                    triangleTextureIDs[i] = textureID;
                }
            }
        }
    }

}
