package xyz.bnayagrawal.android.newsreader.feature.sources;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import xyz.bnayagrawal.android.newsreader.data.model.Source;

public class SourcesArrayAdapter extends ArrayAdapter<Source> {

    public SourcesArrayAdapter(Context context, int resource, List<Source> sources) {
        super(context, resource, sources);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
