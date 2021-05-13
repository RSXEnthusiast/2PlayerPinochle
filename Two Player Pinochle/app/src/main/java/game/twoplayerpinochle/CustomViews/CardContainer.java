package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class CardContainer extends ViewGroup {
    ArrayList<Card> cards = new ArrayList<Card>();

    public CardContainer(Context context) {
        super(context);
    }
}
