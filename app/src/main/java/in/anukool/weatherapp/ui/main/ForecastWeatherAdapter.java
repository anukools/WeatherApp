package in.anukool.weatherapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.anukool.weatherapp.R;
import in.anukool.weatherapp.data.model.ApixuForecastResponse.ForecastDay;
import in.anukool.weatherapp.util.StringFormatterUtil;

public class ForecastWeatherAdapter extends RecyclerView.Adapter<ForecastWeatherAdapter.WeatherItemViewHolder> {

    private List<ForecastDay> mItems = new ArrayList<>();
    private OnWeatherItemClickListener clickListener;

    public ForecastWeatherAdapter(ArrayList<ForecastDay> items) {
        replaceData(items);
    }

    public void setWeatherCardListener(OnWeatherItemClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public WeatherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_card, parent, false);
        return new WeatherItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherItemViewHolder holder, int position) {
        ForecastDay data = mItems.get(position);

        holder.mDay.setText(StringFormatterUtil.getDayFromDate(data.date));
        holder.mTemperature.setText(StringFormatterUtil.getTemperatureStringwithC(data.day.avgTempC));
    }

    public void replaceData(List<ForecastDay> items) {
//        final WeatherDiffCallback diffCallback = new WeatherDiffCallback(mItems, items);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        diffResult.dispatchUpdatesTo(this);

        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class WeatherItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_day)
        public TextView mDay;
        @BindView(R.id.txt_temperature)
        public TextView mTemperature;

        public WeatherItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_layout)
        public void onItemClicked() {
            clickListener.onWeatherCardClicked(mItems.get(getAdapterPosition()));
        }


    }

}
