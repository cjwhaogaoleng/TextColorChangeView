package com.example.textcolorchange;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    List<Fragment> list;

    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list!=null ? list.get(position):null;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


}
