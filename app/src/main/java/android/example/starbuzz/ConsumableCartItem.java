package android.example.starbuzz;

import android.os.Parcel;
import android.os.Parcelable;

public class ConsumableCartItem implements Parcelable {
    public final Consumable consumable;
    public final int quantity;

    public ConsumableCartItem(Consumable consumable, int quantity) {
        this.consumable = consumable;
        this.quantity = quantity;
    }

    protected ConsumableCartItem(Parcel in) {
        consumable = in.readParcelable(Consumable.class.getClassLoader());
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(consumable, flags);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsumableCartItem> CREATOR = new Creator<ConsumableCartItem>() {
        @Override
        public ConsumableCartItem createFromParcel(Parcel in) {
            return new ConsumableCartItem(in);
        }

        @Override
        public ConsumableCartItem[] newArray(int size) {
            return new ConsumableCartItem[size];
        }
    };

    @Override
    public String toString() {
        return consumable + ": " + quantity;
    }
}
