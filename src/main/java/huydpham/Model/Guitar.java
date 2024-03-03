package huydpham.Model;

public class Guitar {
    String serialNumber;
    double price;
    Builder builder;
    String model;
    Type type;
    Wood backWood;
    Wood topWood;

    public Guitar(String sNum, double price, Builder builder, String model, Type type, Wood backWood, Wood topWood) {
        this.serialNumber = sNum;
        this.price = price;
        setBuilder(builder);
        this.model = model;
        setType(type);
        setBackWood(backWood);
        setTopWood(topWood);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Builder getBuilder() {
        return builder;
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Wood getBackWood() {
        return backWood;
    }

    public void setBackWood(Wood backWood) {
        this.backWood = backWood;
    }

    public Wood getTopWood() {
        return topWood;
    }

    public void setTopWood(Wood topWood) {
        this.topWood = topWood;
    }
}
