package android.example.starbuzz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ConsumableCart implements Parcelable {
    private ArrayList<ConsumableCartItem> items = new ArrayList<>();

    public ConsumableCart() {

    }

    protected ConsumableCart(Parcel in) {
        items = in.createTypedArrayList(ConsumableCartItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsumableCart> CREATOR = new Creator<ConsumableCart>() {
        @Override
        public ConsumableCart createFromParcel(Parcel in) {
            return new ConsumableCart(in);
        }

        @Override
        public ConsumableCart[] newArray(int size) {
            return new ConsumableCart[size];
        }
    };

    public void addItem(ConsumableCartItem item) {
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    public void removeItem(ConsumableCartItem item) {
        items.remove(item);
    }

    public ArrayList<ConsumableCartItem> getItems() {
        return items;
    }
}
