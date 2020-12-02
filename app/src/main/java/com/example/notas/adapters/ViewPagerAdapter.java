package com.example.notas.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.notas.fragmentos.FragmentoNotas;
import com.example.notas.fragmentos.FragmentoTareas;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {

                new FragmentoNotas(),
                new FragmentoTareas()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = getItem(position).getClass().getName();
        title =(String) title.subSequence(title.lastIndexOf(".") + 1, title.length());
        switch (title){

            case "FragmentoNotas":
                return "Notas";
            case "FragmentoTareas":
                return "Tareas";
            default:
                return "Titulo";
        }
    }
}
