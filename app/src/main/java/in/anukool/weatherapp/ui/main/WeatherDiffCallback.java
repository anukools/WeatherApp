package in.anukool.weatherapp.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;


import java.util.List;

import in.anukool.weatherapp.data.model.ApixuForecastResponse.*;

public class WeatherDiffCallback extends DiffUtil.Callback {

    private final List<ForecastDay> mOldList;
    private final List<ForecastDay> mNewList;

    public WeatherDiffCallback(List<ForecastDay> oldList, List<ForecastDay> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).date.equals(mNewList.get(newItemPosition).date);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ForecastDay oldItem = mOldList.get(oldItemPosition);
        final ForecastDay newItem = mNewList.get(newItemPosition);

        return oldItem.day.equals(newItem.day);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
