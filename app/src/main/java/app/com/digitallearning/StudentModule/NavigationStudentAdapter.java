package app.com.digitallearning.StudentModule;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.NavigationItem;
import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class NavigationStudentAdapter extends RecyclerView.Adapter<NavigationStudentAdapter.ViewHolder> {

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationStudentAdapter(List<NavigationItem> data) {
        mData = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewholder = new ViewHolder(v);
//        viewholder.itemView.setOnTouchListener(new View.OnTouchListener() {
//                                                   @Override
//                                                   public boolean onTouch(View v, MotionEvent event) {
//
//                                                       switch (event.getAction()) {
//                                                           case MotionEvent.ACTION_DOWN:
//                                                               touchPosition(viewholder.getAdapterPosition());
//                                                               return false;
//                                                           case MotionEvent.ACTION_CANCEL:
//                                                               touchPosition(-1);
//                                                               return false;
//                                                           case MotionEvent.ACTION_MOVE:
//                                                               return false;
//                                                           case MotionEvent.ACTION_UP:
//                                                               touchPosition(-1);
//                                                               return false;
//                                                       }
//                                                       return true;
//                                                   }
//                                               }
//        );
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (mNavigationDrawerCallbacks != null) {

                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(viewholder.getAdapterPosition());
                                                       }
                                                       else {


                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(viewholder.getAdapterPosition());
                                                       }
                                                   }

                                               }
        );
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mData.get(i).getText());
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(i).getDrawable(), null, null, null);

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.itemView.setBackgroundResource(R.drawable.transparent_hover);
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;


        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)

            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }


}
