package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

public abstract class CardContainer extends ViewGroup {
    int deviceWidth;

    public CardContainer(Context context) {
        this(context, null, 0);
    }

    public CardContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    public void addCard(int card) {
        addView(new Card(getContext(), card));
    }

    public void addCard(Card card) {
        addView(card);
    }

    public Card removeCardByNum(Card card) {
        removeView(card);
        return card;
    }

    public Card removeCardByIndex(int index) {
        Card temp = (Card) getChildAt(index);
        removeViewAt(index);
        return temp;
    }
}
