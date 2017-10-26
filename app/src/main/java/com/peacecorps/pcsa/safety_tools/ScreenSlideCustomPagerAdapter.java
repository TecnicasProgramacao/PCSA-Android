package com.peacecorps.pcsa.safety_tools;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 * @author rohan
 * @since 08-07-2016.
 */
public class ScreenSlideCustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] steps;
    int numberOfPages;

    public ScreenSlideCustomPagerAdapter(final Context context,
                                         final int[] steps,
                                         final int numberOfPages) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.steps = steps;
        this.numberOfPages = numberOfPages;
    }
    @Override
    public final int getCount() {
        return numberOfPages;
    }

    @Override
    public final boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public final Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_viewpager, container, false);
        TextView textView = (TextView) itemView.findViewById(R.id.text_to_show);
        textView.setText(Html.fromHtml(mContext.getString(steps[position])));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public final void destroyItem(final ViewGroup container,
                                  final int position,
                                  final Object object) {
        container.removeView((View) object);
    }
}
