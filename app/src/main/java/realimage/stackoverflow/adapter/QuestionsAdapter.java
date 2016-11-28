package realimage.stackoverflow.adapter;

import android.content.Context;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import realimage.stackoverflow.R;
import realimage.stackoverflow.model.QuestionItems;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    Context context;
    ArrayList<QuestionItems> listQuestion;
    QuestionsTagsAdapter questionsTagsAdapter;
    Calendar cal;

    public QuestionsAdapter(Context context, ArrayList<QuestionItems> listQuestion) {
        this.context = context;
        this.listQuestion = listQuestion;
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("IST"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.indiv_questions, null);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvQueTitle.setText(listQuestion.get(position).getTitle());
        holder.tvUserName.setText(listQuestion.get(position).getOwner().getDisplayName());
        holder.tvUpvote.setText("" + listQuestion.get(position).getUpVoteCount());
        holder.tvDate.setText("" + convertTimeWithIstTimeZome(listQuestion.get(position).getCreationDate()));
        questionsTagsAdapter = new QuestionsTagsAdapter(context, listQuestion.get(position).getTags());
        holder.rvTags.setAdapter(questionsTagsAdapter);
    }

    @Override
    public long getItemId(int position) {
        return listQuestion.size();
    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUpvote;
        TextView tvQueTitle;
        TextView tvUserName;
        TextView tvDate;
        RecyclerView rvTags;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUpvote = (TextView) itemView.findViewById(R.id.tv_upvote);
            tvQueTitle = (TextView) itemView.findViewById(R.id.tv_que_title);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_que_date);
            rvTags = (RecyclerView) itemView.findViewById(R.id.rv_tags_list);
            rvTags.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed
        }
    }

    public String convertTimeWithIstTimeZome(long time) {   //Convert TimeStamp to IST
        cal.setTimeInMillis(time * 1000);
        return (cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + "  "
                + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)) + getAmPm(cal.get(Calendar.AM_PM));
    }

    public String getAmPm(int i) {
        if (i == 0) {
            return " AM";
        }
        return " PM";
    }

}
