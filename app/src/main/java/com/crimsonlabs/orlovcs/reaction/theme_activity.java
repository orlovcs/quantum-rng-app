package com.crimsonlabs.orlovcs.reaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class theme_activity extends FragmentActivity {


    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 9;

    static boolean CURRENT_THEME_AVAILABLE = false;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }



    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        final int ORIGINAL = R.style.AppTheme_ThemeOne;
        final int TWO = R.style.AppTheme_ThemeOne;
        final int THREE = R.style.AppTheme_ThemeOne;
        final int FOUR = R.style.AppTheme_ThemeOne;
        final int FIVE = R.style.AppTheme_ThemeOne;
        final int SIX = R.style.AppTheme_ThemeOne;
        final int SEVEN = R.style.AppTheme_ThemeOne;
        final int EIGHT = R.style.AppTheme_ThemeOne;
        final int NINE = R.style.AppTheme_ThemeOne;
        final int TEN = R.style.AppTheme_ThemeOne;


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("Purchased", ORIGINAL);

        //  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(KEY_THEME, currentTheme).apply()

        final String KEY_THEME = "Theme";
        final int ORIGINAL = R.style.AppTheme_ThemeOne;
        int currentTheme = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(KEY_THEME, ORIGINAL);
        setTheme(currentTheme);


        final String payment_required = "$0.99";
        final String theme_available = "Set Theme";


        // Set Content View
        setContentView(R.layout.themes_layout);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.themePager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        final Button selectThemeButton  = findViewById(R.id.selectThemeButton);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                boolean isThemeAvailable = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("theme_"+position, false);
                if(isThemeAvailable){
                    selectThemeButton.setText(theme_available);
                    CURRENT_THEME_AVAILABLE = true;
                }else{
                    selectThemeButton.setText(payment_required);
                    CURRENT_THEME_AVAILABLE = false;
                }
                Log.i("PURCHASE PAGER", "PAGER"+position);

                selectThemeButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        int layout;
                        switch(mPager.getCurrentItem()) {
                            case 0:
                                layout = R.style.AppTheme_ThemeOne;
                                break;
                            case 1:
                                layout = R.style.AppTheme_ThemeTwo;
                                break;
                            case 2:
                                layout = R.style.AppTheme_ThemeThree;
                                break;
                            case 3:
                                layout = R.style.AppTheme_ThemeFour;
                                break;
                            case 4:
                                layout = R.style.AppTheme_ThemeFive;
                                break;
                            case 5:
                                layout = R.style.AppTheme_ThemeSix;
                                break;
                            case 6:
                                layout = R.style.AppTheme_ThemeSeven;
                                break;
                            case 7:
                                layout = R.style.AppTheme_ThemeEight;
                                break;
                            case 8:
                                layout = R.style.AppTheme_ThemeNine;
                                break;
                            default:
                                layout = R.style.AppTheme_ThemeOne;
                        }
                        Log.i("PAGE PRESSED", ":"+layout);

                        if (CURRENT_THEME_AVAILABLE){
                            //Set the bought theme
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(KEY_THEME, layout).apply();
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            //Buy the theme
                             Log.i("PURCHASE INIT BUY FOR", "amoledtheme");
//                             beginThemePurchase("amoledtheme");


                             //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("theme_"+position, true).commit();


                        }


                    }
                });

            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
