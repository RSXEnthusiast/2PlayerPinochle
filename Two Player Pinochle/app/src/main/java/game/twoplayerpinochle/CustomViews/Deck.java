package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

public class Deck extends CardContainer {

    public Deck(Context context) {
        this(context, null, 0);
    }

    public Deck(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Deck(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
        for (int i = 0; i < 48; i++) {
            addCard(i);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            System.out.println("onLayout");
            getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
    }
}
