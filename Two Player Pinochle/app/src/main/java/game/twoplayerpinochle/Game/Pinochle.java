package game.twoplayerpinochle.Game;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import game.twoplayerpinochle.CustomViews.Deck;
import game.twoplayerpinochle.CustomViews.Hand;
import game.twoplayerpinochle.R;

public class Pinochle extends AppCompatActivity {
    private GameStage gameStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinochle);
        float textSize = ((TextView) findViewById(R.id.tricksText)).getTextSize();
        ((TextView) findViewById(R.id.scoreText)).setTextSize(textSize);
        ((TextView) findViewById(R.id.meldText)).setTextSize(textSize);
        ((TextView) findViewById(R.id.bidText)).setTextSize(textSize);
        String[] iconColors = {"black", "white", "red", "green", "blue", "yellow", "pink", "cyan"};
        Random rand = new Random();
        String iconColor = iconColors[rand.nextInt(iconColors.length)];
        ((ImageView) findViewById(R.id.opponentPicture)).setImageResource(getResources().getIdentifier("default_icon_" + iconColor, "drawable", getPackageName()));
        findViewById(R.id.highlightDiscard).setOnClickListener(v -> discardTouched());
        newGame();
    }


    private void newGame() {
        Hand[] hands = new Hand[2];
        hands[0] = findViewById(R.id.hand);
        hands[1] = findViewById(R.id.hand2);
        hands[0].removeAllViews();
        hands[1].removeAllViews();
        ((Deck) findViewById(R.id.deck)).deal(6, hands);
        hands[0].flipAll();
        gameStage = GameStage.drawing;
    }

    public static void cardTouched(int cardNum) {
    }

    public static void deckTouched() {
    }

    public static void discardTouched() {
    }
}
