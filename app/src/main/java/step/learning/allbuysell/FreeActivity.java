package step.learning.allbuysell;

import static android.provider.CalendarContract.CalendarCache.URI;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.health.connect.datatypes.WeightRecord;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import step.learning.allbuysell.orm.ForPageProducts;
import step.learning.allbuysell.orm.Product;

public class FreeActivity extends AppCompatActivity {
    private static final String PHOTO_URL = "https://courseazure20240223211026.azurewebsites.net/photoProducts/";
    private String PAGE_URL;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private LinearLayout container;
    private final List<Product> products = new ArrayList<>();
    private final byte[] buffer = new byte[4048];
    public ScrollView mainScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_free);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        PAGE_URL = intent.getStringExtra( "category" ) ;
        intent.removeExtra("category");
        mainScroller = findViewById( R.id.scroller_json_container ) ;
        container = findViewById( R.id.layout_json_container ) ;
        updatePage() ;
    }
    private void displayChatMessages( String response ) {
        try {
            ForPageProducts forPageProducts = ForPageProducts.fromJsonString( response );
            for( Product product : forPageProducts.getData() ) {
                this.products.add( product );
            }
        }
        catch ( IllegalArgumentException ex ) {
            Log.e("ChatActivity::loadChat()",
                    ex.getMessage() == null ? ex.getClass().getName() : ex.getMessage() );
        }

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnParams.setMargins(2, 2, 2, 2);
        btnParams.gravity = Gravity.END;

//Фон для всього блока ( productALLLayout )
        Drawable myBackground = AppCompatResources.getDrawable(
                getApplicationContext(),
                R.drawable.fon_for_product);

//Параметри для всього блоку ( productALLLayout )
        LinearLayout.LayoutParams viewAllParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        viewAllParams.height = 600;
        //viewAllParams.weight = 300;
        viewAllParams.setMargins(10, 15, 10, 15);
        viewAllParams.gravity = Gravity.START;

//Параметри LinearLayout ( productPhotoLayout ) з ImageView - не работает
        /*
        LinearLayout.LayoutParams viewPhotoParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        //viewPhotoParams.height = 300;
        //viewPhotoParams.weight = 400;
        viewPhotoParams.setMargins(0, 0, 0, 0);
        viewPhotoParams.gravity = Gravity.START;
        //*/

//Параметри LinearLayout ( productLayout ) з набором TextView та Button
        LinearLayout.LayoutParams viewTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        viewTextParams.setMargins(10, 15, 10, 15);
        viewTextParams.gravity = Gravity.END;

//Параметри-Обмеження для ImageView ( iv )
        LinearLayout.LayoutParams viewImageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        viewImageParams.height = 300;
        viewImageParams.weight = 300;
        viewImageParams.setMargins(5, 5, 5, 5);
        viewImageParams.gravity = Gravity.CENTER;

        runOnUiThread( ()
                -> {
            for( Product product : this.products ) {
                TextView tvName = new TextView(this);
                tvName.setText( product.getNameProduct() );
                tvName.setGravity( Gravity.END );
                TextView tvDescription = new TextView(this);
                tvDescription.setText( product.getDescription() );
                tvDescription.setGravity( Gravity.END );
                TextView tvCondition = new TextView(this);
                tvCondition.setText( "Стан: " + product.getCondition() );
                tvCondition.setGravity( Gravity.END );
                TextView tvPrice = new TextView(this);
                tvPrice.setText( product.getPriceProduct() );
                tvPrice.setGravity( Gravity.END );

                Button button = new Button(this);
                button.setText("КУПИТИ");
                button.setLayoutParams(btnParams);
                button.setBackgroundColor(getResources().getColor(R.color._fon_header));
                button.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FreeActivity.this, OrderActivity.class);
                                intent.putExtra("name product", product.getNameProduct());
                                intent.putExtra("description product", product.getDescription());
                                intent.putExtra("condition product", product.getCondition());
                                intent.putExtra("price product", product.getPriceProduct());
                                intent.putExtra("photo product", product.getPhotoProduct());
                                intent.putExtra("id product", product.getId());
                                intent.putExtra("sellerId product", product.getSellerId());
                                startActivity(intent);
                            }
                        }
                );


//Створення ImageView ( iv ), створення LinearLayout ( productPhotoLayout ), додавання ( iv ) до productPhotoLayout
                ImageView iv = new ImageView(this);
                iv.setLayoutParams( viewImageParams );
                LinearLayout productPhotoLayout = new LinearLayout(this);
                productPhotoLayout.setOrientation( LinearLayout.HORIZONTAL );
                productPhotoLayout.addView( iv );
                productPhotoLayout.setGravity(Gravity.CENTER);

                iv.setLayoutParams( viewImageParams );

//Створення ( productLayout ), додавання до нього textView та Button
                LinearLayout productLayout = new LinearLayout(this);
                productLayout.setOrientation( LinearLayout.VERTICAL );

                productLayout.setLayoutParams( viewTextParams );
                productLayout.setPadding(15, 15, 15, 1);
                productLayout.setMinimumWidth( mainScroller.getWidth() / 2 );

                productLayout.addView(tvName);
                productLayout.addView(tvDescription);
                productLayout.addView(tvCondition);
                productLayout.addView(tvPrice);
                productLayout.addView(button);

//Створення ( productALLLayout ), та додавання до нього двох Layout-ів
                LinearLayout productALLLayout = new LinearLayout(this);
                productALLLayout.setOrientation( LinearLayout.HORIZONTAL );

                productALLLayout.setBackground( myBackground );
                productALLLayout.setPadding(60, 30, 30, 30);

                productALLLayout.addView( productPhotoLayout );
                productALLLayout.addView( productLayout );

                String urlImage = PHOTO_URL + product.getPhotoProduct();
                container.post( () -> urlToImageView( urlImage, iv ));

                productALLLayout.setLayoutParams( viewAllParams );
                productALLLayout.setGravity( Gravity.CENTER );
                container.addView( productALLLayout );
            }
        } );
    }
    private void updatePage() {
        if( executorService.isShutdown() ) return;
        CompletableFuture
                .supplyAsync( this::loadPage, executorService )
                .thenAcceptAsync( this::displayChatMessages );
    }
    private String loadPage() {
        try ( InputStream pageAllStream = new URL( PAGE_URL ).openStream() ) {
            return readString( pageAllStream );
        }
        catch ( Exception ex ) {
            Log.e("Activity::loadPage()",
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