package com.xxmassdeveloper.mpchartexample.Modulo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xxmassdeveloper.mpchartexample.R;

import java.util.List;

/**
 * Created by NativoLink on 16/11/16.
 */
public class AdapterModulos extends ArrayAdapter<Modulo> {

    private List<Modulo> modulos;

    public AdapterModulos(Context context, List<Modulo> moduls) {
        super(context, R.layout.list_modulos, moduls);
        modulos = moduls;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_modulos, parent, false);

        Button btn_modelo = (Button) customView.findViewById(R.id.btnModulo);
        btn_modelo.setText(modulos.get(position).getLocacion());

        btn_modelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), modulos.get(position).getLocacion() + "", Toast.LENGTH_LONG).show();
            }
        });


        return customView;

    }
}