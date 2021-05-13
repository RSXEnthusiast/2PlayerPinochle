package game.twoplayerpinochle;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pinochle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinochle);
        float textSize = ((TextView) findViewById(R.id.tricksText)).getTextSize();
        ((TextView) findViewById(R.id.scoreText)).setTextSize(textSize);
        ((TextView) findViewById(R.id.meldText)).setTextSize(textSize);
        ((TextView) findViewById(R.id.bidText)).setTextSize(textSize);
    }

    public static void cardTouched(int cardNum) {
    }
}
