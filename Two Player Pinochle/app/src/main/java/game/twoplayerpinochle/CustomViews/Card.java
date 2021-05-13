package game.twoplayerpinochle.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import game.twoplayerpinochle.Pinochle;

public class Card extends View implements View.OnTouchListener {
    private int imageId;
    private int cardNum;
    private boolean faceUp;

    public Card(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(imageId);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Pinochle.cardTouched(cardNum);
        return false;
    }

    public void updateImage(int cardNum) {
        this.cardNum = cardNum;
        if (faceUp) {
            imageId = getImageId(getCardResourceName(cardNum));
        }
        this.setOnTouchListener(this);
        this.invalidate();
    }

    public int getImageId(String imageName) {
        return getResources().getIdentifier(imageName, "drawable", this.getContext().getPackageName());
    }

    private String getCardResourceName(int cardNum) {
        String stringBuilder = "";
        if (cardNum == -1) {
            stringBuilder += getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE).getString("backStyle", "cardStyle not found");
            stringBuilder += "back";
        } else {
            stringBuilder += getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE).getString("cardStyle", "cardStyle not found");
            stringBuilder += getCardImageFileName(cardNum);
        }
        return stringBuilder;
    }

    public static String getCardImageFileName(int num) {
        return getCardValue(num) + "_" + getCardSuit(num);
    }

    private static String getCardValue(int num) {
        switch (num % 13) {
            case 1:
                return "ace";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
            case 10:
                return "ten";
            case 11:
                return "jack";
            case 12:
                return "queen";
            case 0:
                return "king";
        }
        return "-1";
    }

    public static String getCardSuit(int num) {
        switch ((num - 1) / 13) {
            case 0:
                return "h";
            case 1:
                return "c";
            case 2:
                return "d";
            case 3:
                return "s";
        }
        return "-1";
    }

    public void setFaceUp(boolean b) {
        faceUp = b;
        if (!faceUp) {
            imageId = getImageId(getCardResourceName(-1));
        } else {
            imageId = getImageId(getCardResourceName(cardNum));
        }
        invalidate();
    }

    public void flipCard() {
        if (faceUp) {
            imageId = getImageId(getCardResourceName(-1));
            this.faceUp = false;
        } else {
            imageId = getImageId(getCardResourceName(cardNum));
            this.faceUp = true;
        }
        this.invalidate();
    }
}
