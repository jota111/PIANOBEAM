package com.test.test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.test.test.SelectGenreActivity.genre;
import static com.test.test.SelectGenreActivity.classic_selected;
import static com.test.test.SelectGenreActivity.kpop_selected;
import static com.test.test.SelectGenreActivity.popsong_selected;
import static com.test.test.SelectGenreActivity.ost_selected;
import static com.test.test.SelectGenreActivity.childrensong_selected;
import static com.test.test.LoginActivity.music_selected;

public class SelectMusicPagerAdapter extends PagerAdapter {
    public static final int elise = 0;
    public static final int nowhere = 1;
    public static final int star = 2;
    public static int music;

    private Context context;
    private Integer[] musics;

    public SelectMusicPagerAdapter(Context context) {
        switch(genre) {
            case classic_selected: // 클래식
                musics = new Integer[]{R.drawable.elise};
                break;
            case kpop_selected: // 가요
                musics = new Integer[]{R.drawable.nowhere};
                break;
            case popsong_selected: // 팝송
                musics = new Integer[]{};
                break;
            case ost_selected: // ost
                musics = new Integer[]{};
                break;
            case childrensong_selected: // 동요
                musics = new Integer[]{R.drawable.star, R.drawable.rabbit};
                break;
        }

        this.context = context;
    }

    @Override
    public int getCount() {
        return musics.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_select_music, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setBackgroundResource(musics[position]);
        ViewPager vp = (ViewPager)container;
        vp.addView(view, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(genre) {
                    case classic_selected:
                        switch(position) {
                            case elise:
                                //imageView.setBackgroundResource(R.drawable.nowhere_entered);
                                music = elise;
                                v.getContext().startActivity(new Intent(v.getContext(), GameActivity.class));
                                break;
                        }
                        break;
                    case childrensong_selected:
                        switch(position) {
                            case star:
                                //imageView.setBackgroundResource(R.drawable.star_entered);
                                music = star;
                                v.getContext().startActivity(new Intent(v.getContext(), GameActivity.class));
                                break;
                        }
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View)object;
        vp.removeView(view);
    }
}