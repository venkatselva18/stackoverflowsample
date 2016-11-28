package realimage.stackoverflow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import realimage.stackoverflow.ui.fragment.QuestionFragment;

import static realimage.stackoverflow.helpers.Config.SORT_ACTIVITY;
import static realimage.stackoverflow.helpers.Config.SORT_CREATION;
import static realimage.stackoverflow.helpers.Config.SORT_HOT;
import static realimage.stackoverflow.helpers.Config.SORT_MONTH;
import static realimage.stackoverflow.helpers.Config.SORT_VOTES;
import static realimage.stackoverflow.helpers.Config.SORT_WEEK;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        String DEFAULT_SORT = SORT_ACTIVITY;
        switch (position) {
            case 0:
                DEFAULT_SORT = SORT_ACTIVITY;
                break;
            case 1:
                DEFAULT_SORT = SORT_VOTES;
                break;
            case 2:
                DEFAULT_SORT = SORT_HOT;
                break;
            case 3:
                DEFAULT_SORT = SORT_CREATION;
                break;
            case 4:
                DEFAULT_SORT = SORT_WEEK;
                break;
            case 5:
                DEFAULT_SORT = SORT_MONTH;
                break;
        }
        return QuestionFragment.newInstance(DEFAULT_SORT);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ACTIVITY";
            case 1:
                return "VOTES";
            case 2:
                return "HOT";
            case 3:
                return "CREATION";
            case 4:
                return "WEEK";
            case 5:
                return "MONTH";
        }
        return null;
    }
}