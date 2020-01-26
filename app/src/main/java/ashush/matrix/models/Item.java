package ashush.matrix.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Item {
    private String nativeName;
    private String name;
    private Double area;
    @SerializedName("flag")
    private String flagURL;
    private String[] borders;

    public Item(String nativeName, String name, Double area, String flagURL, String[] borders) {
        this.nativeName = nativeName;
        this.name = name;
        this.area = area;
        this.flagURL = flagURL;
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nativeName='" + nativeName + '\'' +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", flagURL='" + flagURL + '\'' +
                ", borders=" + Arrays.toString(borders) +
                '}';
    }
}
