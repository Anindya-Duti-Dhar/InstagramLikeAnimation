package anindya.sample.InstagramLikeAnimation;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class MainActivity extends AppCompatActivity {
    SpaceTabLayout tabLayout;
    String TAG = getClass().getName();
    ViewPager mViewPager;
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add the fragments you want to display in a List
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentA());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentC());

        final CoordinatorLayout ParentLayout = (CoordinatorLayout) findViewById(R.id.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getString(R.string.chats_toolbar));

        tabLayout.initialize(mViewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);

        tabLayout.setTabOneOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getCurrentPosition() == 0) {
                    Log.d(TAG, "TabOnclick " + " Contacts");
                }
            }
        });

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getCurrentPosition() == 0) {
                    Log.d(TAG, "Onclick " + " Contacts");
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mToolbarTitle.setText(getString(R.string.contacts_toolbar));
                }
                if (position == 1) {
                    mToolbarTitle.setText(getString(R.string.chats_toolbar));
                }
                if (position == 2) {
                    mToolbarTitle.setText(getString(R.string.profile_toolbar));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
