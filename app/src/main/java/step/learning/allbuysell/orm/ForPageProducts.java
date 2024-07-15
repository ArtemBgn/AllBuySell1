package step.learning.allbuysell.orm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForPageProducts {
    private int status;
    private List<Product> data;

    public static ForPageProducts fromJsonString(String jsonString) throws IllegalArgumentException {
        try {
            JSONObject root = new JSONObject( jsonString );
            int status = root.getInt("status");
            JSONArray arr = root.getJSONArray("data");

            List<Product> data = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                data.add( Product.fromJson( arr.getJSONObject(i) ) ) ;
            }

            ForPageProducts res = new ForPageProducts();
            res.setStatus(status);
            res.setData(data);
            return res;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException( ex.getMessage() );
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
