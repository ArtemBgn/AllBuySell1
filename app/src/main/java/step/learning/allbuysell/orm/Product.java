package step.learning.allbuysell.orm;

import android.view.View;

import org.json.JSONObject;

public class Product {
    private String id;
    private String category;
    private String condition;
    private String description;
    private String nameProduct;
    private String photoProduct;
    private String priceProduct;
    private String sellerId;

    public static Product fromJson(JSONObject jsonObject) throws IllegalArgumentException {
        try {
            String id = jsonObject.getString("id");
            String category = jsonObject.getString("productCategory");
            String condition = jsonObject.getString("productCondition");
            String description = jsonObject.getString("productDescription");
            String nameProduct = jsonObject.getString("productName");
            String photoProduct = jsonObject.getString("productPhoto");
            String priceProduct = jsonObject.getString("productPrice");
            String sellerId = jsonObject.getString("sellerId");

            Product product = new Product();
            product.setId( id );
            product.setCategory( category );
            product.setCondition( condition );
            product.setDescription( description );
            product.setNameProduct( nameProduct );
            product.setPhotoProduct( photoProduct );
            product.setPriceProduct( priceProduct );
            product.setSellerId( sellerId );
            return product;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException( ex.getMessage() );
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPhotoProduct() {
        return photoProduct;
    }

    public void setPhotoProduct(String photoProduct) {
        this.photoProduct = photoProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}