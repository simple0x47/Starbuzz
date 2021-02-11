package android.example.starbuzz;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConsumableActivity extends AppCompatActivity {
    public static final String EXTRA_CONSUMABLE = "consumable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumable);

        // Obtenemos el 'consumible' desde el intent.
        Consumable consumable = getIntent().getExtras().getParcelable(EXTRA_CONSUMABLE);

        //Rellenar los elementos del layout con el contenido del objeto Consumable
        //Primero los referencio para usarlos
        ImageView photo = findViewById(R.id.photo);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);

        //Llenamos los elementos
        photo.setImageResource(consumable.getImageResourceId());
        photo.setContentDescription(consumable.getName());

        name.setText(consumable.getName());
        description.setText(consumable.getDescription());
    }
}