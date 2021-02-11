package android.example.starbuzz;

import android.os.Parcel;
import android.os.Parcelable;

public class Consumable implements Parcelable {
    private String category;
    private String name;
    private String description;
    private int imageResourceId;

    public static final Consumable[] drinks = {
            new Consumable("Drink", "Latte","A couple of espresso shots with steamed milk",
                    R.drawable.latte),
            new Consumable("Drink", "Cappuccino","Espresso, hot milk, and a steamed milk foam",
                    R.drawable.cappuccino),
            new Consumable("Drink", "Filter","Highest quality beans roasted and brewed fresh",
                    R.drawable.filter)
    };

    public static final Consumable[] food = {
            new Consumable("Food", "HotDog", "German's state of art Frankfurt sausage",
                    R.drawable.hotdog),
            new Consumable("Food", "Hamburger", "Fresh bovine meat, with same day harvested lettuce",
                    R.drawable.hamburger),
            new Consumable("Food", "Kebab", "Turkey's universal food symbol with chicken",
                    R.drawable.kebab)
    };

    public Consumable(String category, String name, String description, int imageResourceId) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    protected Consumable(Parcel in) {
        category = in.readString();
        name = in.readString();
        description = in.readString();
        imageResourceId = in.readInt();
    }

    public static final Creator<Consumable> CREATOR = new Creator<Consumable>() {
        @Override
        public Consumable createFromParcel(Parcel in) {
            return new Consumable(in);
        }

        @Override
        public Consumable[] newArray(int size) {
            return new Consumable[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(imageResourceId);
    }
}
