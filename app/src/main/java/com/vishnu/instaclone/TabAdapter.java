package com.vishnu.instaclone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new profiletab();
            case 1:
                return new userstab();
            case 2:
                return new sharepic();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int pos){
        switch(pos){
            case 0:
                return "profile";
                case 1:
                    return "users";
            case 2:
                return "shareimg";
        }
        return null;
    }
}
