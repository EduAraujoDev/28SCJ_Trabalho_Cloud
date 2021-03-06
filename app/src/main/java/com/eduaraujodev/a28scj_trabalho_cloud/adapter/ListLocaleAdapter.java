package com.eduaraujodev.a28scj_trabalho_cloud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduaraujodev.a28scj_trabalho_cloud.R;
import com.eduaraujodev.a28scj_trabalho_cloud.domain.Locale;

import java.util.List;

public class ListLocaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Locale> locales;
    private LocaleOnClickListener localeOnClickListener;

    public ListLocaleAdapter(Context context, List<Locale> locales, LocaleOnClickListener localeOnClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.locales = locales;
        this.localeOnClickListener = localeOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.list_locale_adapter, parent, false);

        return new LocaleItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Locale locale = locales.get(position);

        LocaleItemHolder localeItemHolder = (LocaleItemHolder) holder;
        localeItemHolder.tvDataHora.setText(locale.dataHora);
        localeItemHolder.tvLog.setText(String.valueOf(locale.log));
        localeItemHolder.tvLat.setText(String.valueOf(locale.lat));

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                localeOnClickListener.onClickLocale(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locales.size();
    }

    private class LocaleItemHolder extends RecyclerView.ViewHolder {

        TextView tvDataHora;
        TextView tvLog;
        TextView tvLat;

        public LocaleItemHolder(View itemView) {
            super(itemView);

            tvDataHora = (TextView) itemView.findViewById(R.id.tvDataHora);
            tvLog = (TextView) itemView.findViewById(R.id.tvLog);
            tvLat = (TextView) itemView.findViewById(R.id.tvLat);
        }
    }

    public interface LocaleOnClickListener {
        void onClickLocale(View view, int posicao);
    }
}