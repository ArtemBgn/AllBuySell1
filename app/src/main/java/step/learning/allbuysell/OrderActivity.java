package step.learning.allbuysell;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import step.learning.allbuysell.orm.ForPageProducts;
import step.learning.allbuysell.orm.Product;

public class OrderActivity extends AppCompatActivity {

    private static final String PHOTO_URL = "https://courseazure20240223211026.azurewebsites.net/photoProducts/";
    private final byte[] buffer = new byte[8096];
    private String nameProduct;
    private String descriptionProduct;
    private String conditionProduct;
    private String priceProduct;
    private String photoProduct;
    private String idProduct;
    private String sellerIdProduct;
    private LinearLayout container;
    private EditText etNameCustomer;
    private EditText etDelivery;
    private String nameCustomer;
    private String delivery;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        nameProduct = intent.getStringExtra("name product");
        descriptionProduct = intent.getStringExtra("description product");
        conditionProduct = intent.getStringExtra("condition product");
        priceProduct = intent.getStringExtra("price product");
        photoProduct = intent.getStringExtra("photo product");
        sellerIdProduct = intent.getStringExtra("sellerId product");
        idProduct = intent.getStringExtra("id product");

        intent.removeExtra("name product");
        intent.removeExtra("description product");
        intent.removeExtra("condition product");
        intent.removeExtra("price product");
        intent.removeExtra("photo product");
        intent.removeExtra("sellerId product");
        intent.removeExtra("id product");

        container = findViewById(R.id.layout_texttest_container);

        testView();
    }
    private void testView() {
        TextView tvOpus = new TextView(this);
        tvOpus.setText( idProduct + "\n" + nameProduct + "\n" + descriptionProduct + "\n" + conditionProduct + "\n" + priceProduct + "\n" + sellerIdProduct + "\n" + idProduct );

        ImageView iv = new ImageView(this);
        container.addView(iv);

        String urlImage = PHOTO_URL + photoProduct;
        container.post( () -> urlToImageView( urlImage, iv ));

        container.addView(tvOpus);
        TextView tvName = new TextView(this);
        tvName.setText("Ви дійсно бажаєте замовити товар " + nameProduct + " ?" );
        tvName.setPadding(0, 20, 0, 0);
        TextView tvNameCustumer = new TextView(this);
        tvNameCustumer.setText("Введітть Ваше ім'я: " );
        etNameCustomer = new EditText(this);
        TextView tvDelivery = new TextView(this);
        tvDelivery.setText("Введітть Вашу адресу: " );
        etDelivery = new EditText(this);
        container.addView(tvName);
        container.addView(tvNameCustumer);
        container.addView(etNameCustomer);
        container.addView(tvDelivery);
        container.addView(etDelivery);
        Button send = new Button(this);
        send.setText("SEND");
        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { updatePage(); }
                }
        );
        container.addView(send);
    }

    private void sendClick(String jsonString) {
        try {
            JSONObject root = new JSONObject( jsonString );
            int status = root.getInt("status");
            if(status == 1) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Замовлення передано в обробку!", Toast.LENGTH_SHORT).show();
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                });
            }
            else {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Oups!.. щось пішло не так!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (Exception ex) {
            throw new IllegalArgumentException( ex.getMessage() );
        }
    };

    private void updatePage() {
        nameCustomer = etNameCustomer.getText().toString();
        delivery = etDelivery.getText().toString();
        if( nameCustomer.isEmpty() ) {
            Toast.makeText(this, "Заповніть поле 'Введіть Ваше ім'я'", Toast.LENGTH_SHORT).show();
            return;
        }
        if( delivery.isEmpty() ) {
            Toast.makeText(this, "Заповніть поле 'Введіть Вашу адресу'", Toast.LENGTH_SHORT).show();
            return;
        }
        if( executorService.isShutdown() ) return;
        CompletableFuture
                .supplyAsync( this::loadPage, executorService )
                .thenAcceptAsync( this::sendClick );
    }
    private String loadPage() {
        String URL_ORDER = "https://courseazure20240223211026.azurewebsites.net/api/appmobile2?productId="
                +idProduct+"&sellerId="+sellerIdProduct+"&productName="+nameProduct+"&productPrice="
                +priceProduct+"&customerName="+nameCustomer+"&customerDelivery="+delivery;
        try ( InputStream pageAllStream = new URL( URL_ORDER ).openStream() ) {
            return readString( pageAllStream );
        }
        catch ( Exception ex ) {
            Log.e("OrderActivity::loadPage()",
                    ex.getMessage() == null ? ex.getClass().getName() : ex.getMessage() );
        };
        return null;
    }

    private String readString( InputStream stream ) throws IOException {
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        int len;
        while ( (len = stream.read( buffer )) != -1 ) {
            byteBuilder.write( buffer, 0, len);
        }
        String res = byteBuilder.toString();
        byteBuilder.close();
        return res;
    }
    private void urlToImageView(String url, ImageView imageView) {
        CompletableFuture
                .supplyAsync( () -> {
                    try ( java.io.InputStream is = new URL(url).openConnection().getInputStream() ) {
                        return BitmapFactory.decodeStream( is );
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, executorService )
                .thenAccept( (b) -> runOnUiThread( () -> imageView.setImageBitmap(b) ));
    }
}