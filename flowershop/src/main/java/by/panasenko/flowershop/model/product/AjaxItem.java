package by.panasenko.flowershop.model.product;

public class AjaxItem {
    private int id;
    private String name;
    private double price;
    private String image;

    public AjaxItem(Object[] o) {
        this.setId((Integer) o[0]);
        this.setImage("image/flower/" + o[1]);
        this.setName((String) o[2]);
        this.setPrice((Double) o[3]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
