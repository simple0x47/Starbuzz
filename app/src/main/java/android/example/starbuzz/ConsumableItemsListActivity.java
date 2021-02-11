package android.example.starbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConsumableItemsListActivity extends AppCompatActivity {
    public static final String EXTRA_CONSUMABLES = "consumables";

    private ConsumableCart cart;
    private Consumable[] consumables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumable_items_list);

        Bundle dataPackage = getIntent().getExtras();
        initializeConsumablesAndCartFromBundle(dataPackage);

        Toolbar consumableBar = findViewById(R.id.consumable_bar);
        setSupportActionBar(consumableBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Creamos el adaptador para acceder a los datos de la clase Java Consumables
        ArrayAdapter<Consumable> listAdapter = new ArrayAdapter<>
                (this,
                        android.R.layout.simple_list_item_1,
                        consumables);

        //Asignamos el adaptador al elemento gráfico que lo va a usar
        ListView listConsumables = findViewById(R.id.list_consumables);
        listConsumables.setAdapter(listAdapter);

        //Creamos el listener sobre el listView
        AdapterView.OnItemClickListener itemClickListener =
                (listDrinks1, itemView, position, id) -> {

                    //Al hacer clic sobre algún elemento paso un intent
                    Intent intent = new Intent(ConsumableItemsListActivity.this,
                            ConsumableActivity.class);
                    intent.putExtra(ConsumableActivity.EXTRA_CONSUMABLE, consumables[(int) id]);
                    startActivity(intent);
                };

        //Asignamos el listener al elemento que queremos que lo use
        listConsumables.setOnItemClickListener(itemClickListener);
    }

    private void initializeConsumablesAndCartFromBundle(Bundle dataPackage) {
        if (dataPackage == null) {
            throw new IllegalStateException("Required a bundle containing the consumables.");
        }

        Parcelable[] uncheckedConsumables = dataPackage.getParcelableArray(EXTRA_CONSUMABLES);

        if (uncheckedConsumables != null) {
            consumables = new Consumable[uncheckedConsumables.length];

            for (int i = 0; i < uncheckedConsumables.length; i++) {
                consumables[i] = (Consumable) uncheckedConsumables[i];
            }
        } else {
            throw new IllegalStateException("Bundle does not contain expected consumables.");
        }

        Parcelable uncheckedCart = dataPackage.getParcelable(TopLevelActivity.EXTRA_CART);

        if (uncheckedCart == null) {
            throw new IllegalStateException("Bundle does not contain expected cart.");
        }

        cart = (ConsumableCart) uncheckedCart;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.consumable_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_consumable_to_cart:
                ConsumableOrderMakerActivity.switchToOrderMaker(this, cart, consumables);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        TopLevelActivity.backToRootActivity(this, cart);
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        TopLevelActivity.backToRootActivity(this, cart);
    }

    public static void switchTo(AppCompatActivity activity, ConsumableCart cart, Consumable[] consumables) {
        Intent nextPage = new Intent(activity.getApplicationContext(), ConsumableItemsListActivity.class);
        nextPage.putExtra(TopLevelActivity.EXTRA_CART, cart);
        nextPage.putExtra(EXTRA_CONSUMABLES, consumables);

        activity.startActivity(nextPage);
        activity.finish();
    }
}
