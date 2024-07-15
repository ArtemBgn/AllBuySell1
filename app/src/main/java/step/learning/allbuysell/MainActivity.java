package step.learning.allbuysell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String PAGE_FREE_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=free";
    private static final String PAGE_KIDS_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=kids";
    private static final String PAGE_CLOTES_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=clothes";
    private static final String PAGE_HOME_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=housegarden";
    private static final String PAGE_CARS_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=auto";
    private static final String PAGE_ELECTRONIC_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=electronics";
    private static final String PAGE_ALL_URL = "https://courseazure20240223211026.azurewebsites.net/api/appmobile?category=all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.page_free_category).setOnClickListener(this::freeCategoryClick);
        findViewById(R.id.page_kids_category).setOnClickListener(this::kidsCategoryClick);
        findViewById(R.id.page_chlotes_category).setOnClickListener(this::chlotesCategoryClick);
        findViewById(R.id.page_home_category).setOnClickListener(this::homeCategoryClick);
        findViewById(R.id.page_cars_category).setOnClickListener(this::carsCategoryClick);
        findViewById(R.id.page_mobile_category).setOnClickListener(this::mobileCategoryClick);
        findViewById(R.id.page_all_category).setOnClickListener(this::allCategoryClick);
    }
    private void freeCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_FREE_URL);
        startActivity(intent);
    }
    private void kidsCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_KIDS_URL);
        startActivity(intent);
    }
    private void chlotesCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_CLOTES_URL);
        startActivity(intent);
    }
    private void homeCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_HOME_URL);
        startActivity(intent);
    }
    private void carsCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_CARS_URL);
        startActivity(intent);
    }
    private void mobileCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_ELECTRONIC_URL);
        startActivity(intent);
    }
    private void allCategoryClick(View view) {
        Intent intent = new Intent(this, FreeActivity.class);
        intent.putExtra("category", PAGE_ALL_URL);
        startActivity(intent);
    }
}