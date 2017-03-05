package chinmay.simplecounter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
                Color.parseColor("#263238")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_2),
                getString(R.string.intro_desc_2),
                R.drawable.intro_1,
                Color.parseColor("#689F38")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_3),
                getString(R.string.intro_desc_3),
                R.drawable.intro_2,
                Color.parseColor("#AB47BC")));

        addSlide(AppIntro2Fragment.newInstance(getString(R.string.intro_title_4),
                getString(R.string.intro_desc_4),
                R.drawable.intro_3,
                Color.parseColor("#FF7043")));
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
