package com.veontomo.fiestatime.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;

import java.util.List;

/**
 * An adapter for visualizing events. It displays event name, event next occurrence
 * and the quantity of days until the event.
 */
public class DetailedAdapter<T extends Event> extends BaseAdapter {

    private final Context mContext;
    /**
     * List of items that this adapter is supposed to display
     */
    List<T> items;

    /**
     * number of items in {@link #items}
     */
    int size = 0;
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return size;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public DetailedAdapter(final Context context){
        this.mContext = context;
    }



    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public T getItem(int position) {
        return (items == null) ? null :  items.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    public void load(List<T> items){
        this.items = items;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getClass().getSimpleName().equals("SingleEvent") ? 0 : 1;
    }




    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 0:
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.detailed_event_row, parent, false);
                    WeekEventHolder eventHolder = new WeekEventHolder();
                    eventHolder.text = (TextView) row.findViewById(R.id.layout_event_row_name);
                    row.setTag(eventHolder);
                }
                WeekEventHolder holder = (WeekEventHolder) row.getTag();
                holder.text.setText(getItem(position).getName());
                break;
            case 1:
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.detailed_event_row, parent, false);
                    SingleEventHolder proverbHolder = new SingleEventHolder();
                    proverbHolder.text = (TextView) row.findViewById(R.id.layout_event_row_name);
                    row.setTag(proverbHolder);
                }
                SingleEventHolder holder2 = (SingleEventHolder) row.getTag();
                holder2.text.setText(getItem(position).getName());
                break;
            default:
                Logger.log("unknown item type");

        }
        return row;
    }


    private static class SingleEventHolder {
        public TextView text;
    }

    private static class WeekEventHolder {
        public TextView text;
    }

}
