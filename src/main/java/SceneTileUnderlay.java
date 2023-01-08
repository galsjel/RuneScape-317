// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class SceneTileUnderlay {

    public final int southwestColor;
    public final int southeastColor;
    public final int northeastColor;
    public final int northwestColor;
    public final int textureID;
    public final int rgb;
    public boolean flat;

    public SceneTileUnderlay(int southwestColor, int southeastColor, int northeastColor, int northwestColor, int textureID, int rgb, boolean flat) {
        this.southwestColor = southwestColor;
        this.southeastColor = southeastColor;
        this.northeastColor = northeastColor;
        this.northwestColor = northwestColor;
        this.textureID = textureID;
        this.rgb = rgb;
        this.flat = flat;
    }

}
