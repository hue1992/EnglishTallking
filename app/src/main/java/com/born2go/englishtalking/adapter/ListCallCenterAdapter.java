package com.born2go.englishtalking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.born2go.englishtalking.MainActivity;
import com.born2go.englishtalking.R;
import com.born2go.englishtalking.entities.CallCenter;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Hue on 8/15/2016.
 */
public class ListCallCenterAdapter extends RecyclerView.Adapter<ListCallCenterAdapter.ListCallCenterAdapterViewHolder> {

    private final MainActivity mActivity;
    private final List<CallCenter> callCenters;
    List<String> images = Arrays.asList("http://images.triumphmotorcycles.co.uk/media-library/images/d%20series%20images/bonneville%20genre%20page/hero%20block/quick%20fix/streettwin_scrambler_accessoryinspirationkit_rs.jpg",
            "http://images.triumphmotorcycles.co.uk/media-library/images/d%20series%20images/bonneville%20t120%20family%20page/7%20blocks/du%20prestige.jpg?w=1500"
            , "http://i.ndtvimg.com/i/2016-03/2016-triumph-bonneville-t120_827x510_61457949670.jpg"
            , "http://kickstart.bikeexif.com/wp-content/uploads/2016/03/2a-triumph-t120-review-625x417.jpg"
            , "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQzxG2BzJhaaiJd1q4t1OIy4cCxdvVNJhyEDiwcPgxBcSsBUf2U");
    private boolean sort = false;

    public ListCallCenterAdapter(MainActivity thiz, List<CallCenter> callCenters) {
        this.mActivity = thiz;
        this.callCenters = callCenters;
    }


    @Override
    public ListCallCenterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_center_item, parent, false);
        ListCallCenterAdapterViewHolder listCallCenterAdapterViewHolder = new ListCallCenterAdapterViewHolder(view);
        return listCallCenterAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ListCallCenterAdapterViewHolder holder, int position) {
        View view = holder.itemView;
        final CallCenter callCenter = callCenters.get(position);
        ImageView imgWellcome = (ImageView) view.findViewById(R.id.avata);
        TextView callcenterName = (TextView) view.findViewById(R.id.callcenterName);
        TextView grade = (TextView) view.findViewById(R.id.grade);
        LinearLayout active = (LinearLayout) view.findViewById(R.id.active);

        //Load img well come
//        ESFSingleton.getInstance().getImageLoader().get(callCenter.getAvatar(), ImageLoader.getImageListener(imgWellcome, R.drawable.default_avatar_photo, R.drawable.default_avatar_photo));
//        imgWellcome.setImageUrl(callCenter.getAvatar(), ESFSingleton.getInstance().getImageLoader());
        Picasso.with(mActivity).load(callCenter.getAvatar()).error(R.drawable.default_avatar_photo).into(imgWellcome);

        //set name and grade
        callcenterName.setText(callCenter.getCcName());
        grade.setText(callCenter.getGrade());

        //set active call center
        active.removeAllViews();
        if (callCenter.getActive() == 0) {
            active.addView(LayoutInflater.from(view.getContext()).inflate(R.layout.view_busy, null, false));
            // active.setColorFilter(R.color.colorDeactivate);
        } else {
            // active.setColorFilter(R.color.colorActive);
            active.addView(LayoutInflater.from(view.getContext()).inflate(R.layout.view_available, null, false));
        }

        //
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.gotoDetails(callCenter);
            }
        });


    }

    @Override
    public int getItemCount() {
        return callCenters.size();
    }

    public void sort() {
        if (sort) {
            //des
            Collections.sort(callCenters, new Comparator<CallCenter>() {
                @Override
                public int compare(CallCenter lhs, CallCenter rhs) {
                    return lhs.getCcName().compareTo(rhs.getCcName());
                }
            });
            sort = false;

        } else {
            //asc
            Collections.sort(callCenters, new Comparator<CallCenter>() {
                @Override
                public int compare(CallCenter lhs, CallCenter rhs) {
                    return rhs.getCcName().compareTo(lhs.getCcName());
                }
            });
            sort = true;
        }
        notifyDataSetChanged();
    }

    public class ListCallCenterAdapterViewHolder extends RecyclerView.ViewHolder {
        public ListCallCenterAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
