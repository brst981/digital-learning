package app.com.digitallearning.TeacherModule.Students;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by ${ShalviSharma} on 7/30/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    //int image[]={R.drawable.icn,R.drawable.icn};


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        //this.image = image;

    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        Log.e("",""+position);
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            ArchivedFragment tab1 = new ArchivedFragment();

            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            EnrolledFragment tab2 = new EnrolledFragment();
            return tab2;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return NumbOfTabs;
    }



}
