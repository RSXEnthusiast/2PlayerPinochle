package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        sortBySuit();
        final int count = getChildCount();

        int cardWidth = (getMeasuredWidth() / 8);
        int cardHeight = (int) (cardWidth * (7.0 / 5.0));
        if (cardHeight * 2 > getMeasuredHeight()) {
            cardHeight = (getMeasuredHeight() / 2);
            cardWidth = (int) (cardHeight * (5.0 / 7.0));
        }
        int numBottomRow = getMeasuredWidth() / cardWidth;
        System.out.println(numBottomRow);
        if (count < numBottomRow) {
            numBottomRow = count;
        }
        int numTopRow = count - numBottomRow;
        int cardsRightBottom = (getMeasuredWidth() / 2) + ((numBottomRow * cardWidth) / 2);
        int cardsRightTop = (getMeasuredWidth() / 2) + ((numTopRow * cardWidth) / 2);
        for (int i = count - 1, j = 0; i >= 0; i--, j++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                return;
            }
            if (j < numBottomRow) {
                bottom = getMeasuredHeight();
                right = cardsRightBottom - (j * cardWidth);
            } else {
                bottom = getMeasuredHeight() - cardHeight;
                right = cardsRightTop - (j * cardWidth) + getMeasuredWidth();
            }
            top = bottom - cardHeight;
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

    public void flipAll() {
        for (int i = 0; i < getChildCount(); i++) {
            ((Card) getChildAt(i)).flipCard();
        }
    }

    public void flipAllFaceUp() {
        for (int i = 0; i < getChildCount(); i++) {
            ((Card) getChildAt(i)).setFaceUp(true);
        }
    }

    public void flipAllFaceDown() {
        for (int i = 0; i < getChildCount(); i++) {
            ((Card) getChildAt(i)).setFaceUp(false);
        }
    }

    public void sortBySuit() {
        Card[] temp = new Card[getChildCount()];
        System.out.println(temp.length);
        for (int i = 0; i < temp.length; i++) {
            temp[i] = removeCardByIndex(0);
        }
        sort(temp, 0, temp.length - 1);
        for (int i = 0; i < temp.length; i++) {
            addCard(temp[i]);
        }
    }

    void merge(Card arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        Card L[] = new Card[n1];
        Card R[] = new Card[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getCardNum() >= R[j].getCardNum()) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    void sort(Card arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
}
