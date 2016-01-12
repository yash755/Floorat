package com.floorat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.floorat.ImageUtils.ImageLoader;
import com.floorat.Objects.Noticelist;
import com.floorat.R;

import java.util.ArrayList;

public class Noticeboardlistadapter extends BaseAdapter implements Filterable {

    private ArrayList<Noticelist> mOriginalValues; // Original Values
    private ArrayList<Noticelist> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;
    private Activity activity;
    public ImageLoader imageLoader;

    public Noticeboardlistadapter(Context context, ArrayList<Noticelist> nlist) {
        this.mOriginalValues = nlist;
        this.mDisplayedValues = nlist;
        inflater = LayoutInflater.from(context);
    }

    public Noticeboardlistadapter(Activity a,ArrayList<Noticelist> nlist ) {
        activity = a;
        this.mOriginalValues = nlist;
        this.mDisplayedValues = nlist;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.noticeboard_rowview, null);

        TextView text=(TextView)vi.findViewById(R.id.heading);
        text.setText(mDisplayedValues.get(position).heading);

        ImageView image=(ImageView)vi.findViewById(R.id.image);
        imageLoader.DisplayImage(mDisplayedValues.get(position).url, image);
        return vi;

    }

    

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Noticelist>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Noticelist> FilteredArrList = new ArrayList<Noticelist>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Noticelist>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).heading;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Noticelist(mOriginalValues.get(i).heading,mOriginalValues.get(i).url));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}