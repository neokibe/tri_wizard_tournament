package com.example.tri_wizard_tournament;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SpellsAdapter  extends ArrayAdapter<Object> {
    private final List<Object> spells;
    private final Context context;

    public SpellsAdapter(List<Object> spells, Context context) {
        super(context, R.layout.custom_spells, spells);
        this.spells = spells;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_spells, parent, false);

        TextView tvSpell = rowView.findViewById(R.id.tvSpell);
        tvSpell.setText(spells.get(position).toString());
        return rowView;
    }
}
