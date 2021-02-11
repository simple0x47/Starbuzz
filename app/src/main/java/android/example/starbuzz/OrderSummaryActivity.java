package android.example.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class OrderSummaryActivity extends AppCompatActivity {

    private ListView consumablesList;
    private ConsumableCart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        cart = getIntent().getParcelableExtra(TopLevelActivity.EXTRA_CART);

        if (cart == null) {
            throw new IllegalStateException("Missing ConsumableCart from order summary.");
        }

        consumablesList = findViewById(R.id.consumables_summary_list);

        initializeConsumablesList(cart);
    }

    private void initializeConsumablesList(ConsumableCart cart) {
        ArrayAdapter<ConsumableCartItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cart.getItems());
        consumablesList.setAdapter(adapter);
    }

    public static void switchToSummary(Activity activity, ConsumableCart cart) {
        Intent intent = new Intent(activity.getApplicationContext(), OrderSummaryActivity.class);
        intent.putExtra(TopLevelActivity.EXTRA_CART, cart);

        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onBackPressed() {
        TopLevelActivity.backToRootActivity(this, cart);
    }
}