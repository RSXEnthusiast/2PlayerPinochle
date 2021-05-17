package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Hand extends CardContainer {
    int deviceWidth;

    public Hand(Context context) {
        this(context, null, 0);
    }

    public Hand(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    public void initHand() {
        for (int i = 0; i < 15; i++) {
            addCard(i);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        int cardWidth = (getMeasuredWidth() / 8) - 1;
        int cardHeight = (int) (cardWidth * (7.0 / 5.0));
        if (cardHeight * 2 > getMeasuredHeight()) {
            cardHeight = (getMeasuredHeight() / 2) - 1;
            cardWidth = (int) (cardHeight * (5.0 / 7.0));
        }
        int numBottomRow = getMeasuredWidth() / cardWidth;
        int bottomRight = (getMeasuredWidth() / 2) + ((numBottomRow * cardWidth) / 2);
        int i = count - 1;
        int j = 0;
        for (; i >= numBottomRow-1; i--, j++) {
            System.out.println(i);
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                return;
            }

            bottom = getMeasuredHeight();
            top = getMeasuredHeight() - cardHeight;
            right = bottomRight - (j * cardWidth);
            left = right - cardWidth;
            child.layout(left, top, right, bottom);
        }
        int numTopRow = count - numBottomRow;
        int topRight = (getMeasuredWidth() / 2) + ((numTopRow * cardWidth) / 2);
        j = 0;
        for (; i >= 0; i--, j++) {
            System.out.println(i);
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                return;
            }

            bottom = getMeasuredHeight() - cardHeight;
            top = getMeasuredHeight() - (cardHeight * 2);
            right = topRight - (j * cardWidth);
            left = right - cardWidth;
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int mLeftWidth = 0;
        int rowCount = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            // Measure the child.
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
            mLeftWidth += child.getMeasuredWidth();

            if ((mLeftWidth / deviceWidth) > rowCount) {
                maxHeight += child.getMeasuredHeight();
                rowCount++;
            } else {
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }
}
