package game.twoplayerpinochle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        if (sp.getString("frontStyle", "notDefined").equals("notDefined")) {
            sp.edit().putString("frontStyle", "temp").apply();
        }
        if (sp.getString("backStyle", "notDefined").equals("notDefined")) {
            sp.edit().putString("backStyle", "temp").apply();
        }
        setContentView(R.layout.main);
        findViewById(R.id.newGameButton).setOnClickListener(v -> newGame());
        findViewById(R.id.settingsButton).setOnClickListener(v -> mainSettingsMenu());
        findViewById(R.id.continueButton).setOnClickListener(v -> continueGame());
        findViewById(R.id.onlineButton).setOnClickListener(v -> playOnline());
        findViewById(R.id.statsButton).setOnClickListener(v -> stats());
    }

    private void continueGame() {
        //Not Implemented Yet
    }

    private void newGame() {
        startActivity(new Intent(this, Pinochle.class));
    }

    private void playOnline() {
        //Not Implemented Yet
    }

    private void mainSettingsMenu() {
        startActivity(new Intent(this, AllSettings.class));
    }

    private void stats() {
        //Not Implemented Yet
    }
}
