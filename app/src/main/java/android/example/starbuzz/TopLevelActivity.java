package android.example.starbuzz;

import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends AppCompatActivity {
    public static final String EXTRA_CART = "cart";

    private ConsumableCart cart = new ConsumableCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        Parcelable uncheckedCart = getIntent().getParcelableExtra(EXTRA_CART);
        if (uncheckedCart != null) {
            cart = (ConsumableCart) uncheckedCart;
        }

        //Creación del listener
        AppCompatActivity thisActivity = this;
        AdapterView.OnItemClickListener itemClickListener =
                (listView, itemView, position, id) -> {
                    if (position == 0) {
                        ConsumableItemsListActivity.switchTo(thisActivity, cart, Consumable.drinks);
                    } else if (position == 1) {
                        ConsumableItemsListActivity.switchTo(thisActivity, cart, Consumable.food);
                    }
                };

        //Asignamos el listener creado a la listView
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    public static void backToRootActivity(AppCompatActivity activity, ConsumableCart cart) {
        Intent intent = new Intent(activity.getApplicationContext(), TopLevelActivity.class);
        intent.putExtra(EXTRA_CART, cart);

        activity.startActivity(intent);
        activity.finish();
    }
}