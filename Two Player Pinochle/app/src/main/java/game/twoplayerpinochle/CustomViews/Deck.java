package game.twoplayerpinochle.CustomViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.Collections;

import game.twoplayerpinochle.Game.Pinochle;

public class Deck extends CardContainer implements View.OnClickListener {

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
        shuffle();
        flipAllFaceDown();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            System.out.println("onLayout");
            getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void deal(int numCards, Hand[] hands) {
        for (int i = 0; i < numCards; i++) {
            hands[0].addCard(removeCardByIndex(0));
            hands[1].addCard(removeCardByIndex(0));
        }
    }

    public void shuffle() {
        ArrayList<Card> temp = new ArrayList<Card>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            temp.add(removeCardByIndex(0));
        }
        Collections.shuffle(temp);
        for (int i = 0; i < count; i++) {
            addCard(temp.remove(0));
        }
    }

    public void flipAllFaceDown() {
        for (int i = 0; i < getChildCount(); i++) {
            ((Card) getChildAt(i)).setFaceUp(false);
        }
    }

    @Override
    public void onClick(View v) {
        Pinochle.deckTouched();
    }
}
