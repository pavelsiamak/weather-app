package com.pavelsemak.weatherapp.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private List<String> infoList = new ArrayList<>();

    private String[] labels;
    private String[] units;

    public InfoAdapter(String[] labels, String[] units) {
        this.labels = labels;
        this.units = units;
    }

    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutResource = viewType == 0 ? R.layout.item_info : R.layout.item_info2;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new InfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InfoAdapter.ViewHolder holder, int position) {
        String item = infoList.get(position);

        String label = "";
        if (position < labels.length) {
            label = labels[position];
        }

        String unit = "";
        if (position < units.length) {
            unit = units[position];
        }

        holder.label.setText(label);
        holder.value.setText(String.format(unit, item));
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void setData(List<String> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.info_label)
        TextView label;
        @BindView(R.id.info_value)
        TextView value;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
