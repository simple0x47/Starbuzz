package android.example.starbuzz;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class ConsumableOrderMakerActivity extends AppCompatActivity {
    private Consumable selectedConsumable = null;
    private int quantityOfConsumable = 0;

    private TextView consumableCategory;
    private TextView quantityIndicator;
    private ImageButton quantityDecrementer;
    private ImageButton quantityIncrementer;
    private Button orderButton;

    private ConsumableCart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumable_order_maker);

        Toolbar toolbar = findViewById(R.id.order_maker_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        consumableCategory = findViewById(R.id.consumable_category);
        quantityIndicator = findViewById(R.id.consumable_quantity);
        quantityIncrementer = findViewById(R.id.incrementer_consumable_quantity);
        quantityDecrementer = findViewById(R.id.decrementer_consumable_quantity);
        orderButton = findViewById(R.id.order_button);

        Bundle orderPreData = getIntent().getExtras();
        updateActivityAccordinglyToOrderPreData(orderPreData);
    }

    private void updateActivityAccordinglyToOrderPreData(Bundle orderPreData) {
        if (orderPreData == null) {
            throw new IllegalStateException("'orderPreData' must be valid.");
        }

        Parcelable[] uncheckedConsumableItems = orderPreData
                .getParcelableArray(ConsumableItemsListActivity.EXTRA_CONSUMABLES);

        if (uncheckedConsumableItems == null) {
            throw new IllegalStateException("'orderPreData' does not contain expected consumables items.");
        }


        Consumable[] consumables = new Consumable[uncheckedConsumableItems.length];

        for (int i = 0; i < uncheckedConsumableItems.length; i++) {
            consumables[i] = (Consumable) uncheckedConsumableItems[i];

            if (consumables[i] == null) {
                throw new IllegalStateException("Invalid consumables.");
            }
        }

        Parcelable uncheckedCart = orderPreData.getParcelable(TopLevelActivity.EXTRA_CART);

        if (uncheckedCart == null) {
            throw new IllegalStateException("'uncheckedCart' does not contain expected consumables cart.");
        }

        cart = (ConsumableCart) uncheckedCart;

        consumableCategory.setText(consumables[0].getCategory());

        initializeConsumableSelector(consumables);
        initializeQuantityModifiers();
        initializeOrderButton(cart);
    }

    private void initializeConsumableSelector(Consumable[] consumables) {
        Spinner consumableSelector = findViewById(R.id.consumable_selector);
        ArrayAdapter<Consumable> consumablesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, consumables);
        consumablesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consumableSelector.setAdapter(consumablesAdapter);

        consumableSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConsumable = (Consumable) parent.getItemAtPosition(position);
                updateQuantityOfConsumable(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateQuantityOfConsumable(int quantity) {
        this.quantityOfConsumable = quantity;
        quantityIndicator.setText(String.format("%d", quantity));

        updateOrderButtonState();
        if (quantityOfConsumable > 0) {
            quantityDecrementer.setEnabled(true);
        } else {
            quantityDecrementer.setEnabled(false);
        }
    }

    private void updateOrderButtonState() {
        if ((selectedConsumable != null) && (quantityOfConsumable > 0)) {
            orderButton.setEnabled(true);
        } else {
            orderButton.setEnabled(false);
        }
    }

    private void initializeQuantityModifiers() {
        quantityIncrementer.setOnClickListener(v -> updateQuantityOfConsumable(quantityOfConsumable + 1));
        quantityDecrementer.setOnClickListener(v -> updateQuantityOfConsumable(Math.max(0, quantityOfConsumable - 1)));
    }

    private void initializeOrderButton(ConsumableCart cart) {
        orderButton.setOnClickListener(v -> {
            ConsumableCartItem item = new ConsumableCartItem(selectedConsumable, quantityOfConsumable);
            cart.addItem(item);
            OrderSummaryActivity.switchToSummary(this, cart);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        TopLevelActivity.backToRootActivity(this, cart);
        return true;
    }

    @Override
    public void onBackPressed() {
        TopLevelActivity.backToRootActivity(this, cart);
    }

    public static void switchToOrderMaker(AppCompatActivity activity, ConsumableCart cart, Consumable[] consumables) {
        Intent intent = new Intent(activity.getApplicationContext(), ConsumableOrderMakerActivity.class);
        intent.putExtra(TopLevelActivity.EXTRA_CART, cart);
        intent.putExtra(ConsumableItemsListActivity.EXTRA_CONSUMABLES, consumables);

        activity.startActivity(intent);
        activity.finish();
    }
}