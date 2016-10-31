package chinmay.simplecounter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(false);
        setVibrate(true);
        setFadeAnimation();

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_1),
                getString(R.string.intro_desc_1),
                R.drawable.simple_counter,
                Color.parseColor("#212121")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_2),
                getString(R.string.intro_desc_2),
                R.drawable.intro_1,
                Color.parseColor("#4A148C")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_3),
                getString(R.string.intro_desc_3),
                R.drawable.intro_2,
                Color.parseColor("#3A506B")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_4),
                getString(R.string.intro_desc_4),
                R.drawable.intro_3,
                Color.parseColor("#EE293b")));
    }

    private void loadCounter() {
        finish();
    }

    @Override
    public void onDonePressed() {
        loadCounter();
    }

    @Override
    public void onSkipPressed() {
        loadCounter();
    }
}
