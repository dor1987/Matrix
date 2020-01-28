package ashush.matrix.models;


import java.util.Arrays;

import static ashush.matrix.utils.Constants.COUNTRY_TYPE;

public class Item {
    private String nativeName;
    private String name;
    private Double area;
    private String[] borders;
    private String alpha3Code;
    private int type;


    public Item(String nativeName, String name, Double area, String[] borders, String alpha3Code) {
        this.nativeName = nativeName;
        this.name = name;
        this.area = area;
        this.borders = borders;
        this.alpha3Code = alpha3Code;
        this.type = COUNTRY_TYPE;
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
        if(area == null)
            return 0.0;
        return area;
    }

    public void setArea(Double area) {
        this.area = area;

    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }


    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nativeName='" + nativeName + '\'' +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", borders=" + Arrays.toString(borders) +
                ", alpha3Code='" + alpha3Code + '\'' +
                ", type=" + type +
                '}';
    }
}
