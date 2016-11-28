package realimage.stackoverflow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import realimage.stackoverflow.R;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class QuestionsTagsAdapter extends RecyclerView.Adapter<QuestionsTagsAdapter.ViewHolder> {
    Context context;
    ArrayList<String> listTags;

    public QuestionsTagsAdapter(Context context, ArrayList<String> listTags) {
        this.context = context;
        this.listTags = listTags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.indiv_tags, null);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTagName.setText("#" + listTags.get(position));
        holder.tvTagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, listTags.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return listTags.size();
    }

    @Override
    public int getItemCount() {
        return listTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTagName = (TextView) itemView.findViewById(R.id.tv_tag_name);
        }
    }

}
