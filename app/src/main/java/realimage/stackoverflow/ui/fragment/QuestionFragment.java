package realimage.stackoverflow.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import realimage.stackoverflow.R;
import realimage.stackoverflow.adapter.QuestionsAdapter;
import realimage.stackoverflow.apiService.ServiceGenerator;
import realimage.stackoverflow.apiService.ServiceInterface;
import realimage.stackoverflow.helpers.Config;
import realimage.stackoverflow.model.QuestionItems;
import realimage.stackoverflow.model.QuestionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static realimage.stackoverflow.helpers.Config.CURRENT_SORT;
import static realimage.stackoverflow.helpers.Config.DESC;
import static realimage.stackoverflow.helpers.Config.SORT_ACTIVITY;
import static realimage.stackoverflow.helpers.Config.STACKOVERFLOW;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class QuestionFragment extends Fragment {
    Context context;
    RecyclerView rvQuestionsList;
    QuestionsAdapter questionAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<QuestionItems> listQuestion = new ArrayList<>();
    View rootView;
    boolean isSortActivty = false;

    public static QuestionFragment newInstance(String sortName) {
        QuestionFragment helpFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CURRENT_SORT, sortName);
        helpFragment.setArguments(bundle);
        return helpFragment;
    }

    ServiceInterface serviceInterface;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question_list, container, false);
        context = getActivity();

        rvQuestionsList = (RecyclerView) rootView.findViewById(R.id.rv_questions_list);
        mLayoutManager = new LinearLayoutManager(context);
        rvQuestionsList.setLayoutManager(mLayoutManager);

        serviceInterface = ServiceGenerator.createService(ServiceInterface.class);
        if (getArguments().getString(CURRENT_SORT, Config.SORT_ACTIVITY).equals(SORT_ACTIVITY)) {
            isSortActivty = true;
        }
        loadQuetionsApi(getArguments().getString(CURRENT_SORT, Config.SORT_ACTIVITY));   //Default is sort= activity
        return rootView;
    }

    private void loadQuetionsApi(String sortName) {
        ProgressDialog progressDialog = null;
        if (isSortActivty) {
            progressDialog = ProgressDialog.show(context, "Please wait", "Getting data");
        }
        final ProgressDialog finalProgressDialog = progressDialog;
        serviceInterface.getQuetions(DESC, sortName, Config.API_FILTER_FOR_QUESTIONS, STACKOVERFLOW, Config.API_KEY)
                .enqueue(new Callback<QuestionResponse>() {
                    @Override
                    public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                        if (isSortActivty && finalProgressDialog != null) {
                            finalProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            setQuestions(response.body().getItems());
                        } else {
                            Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionResponse> call, Throwable t) {
                        if (isSortActivty && finalProgressDialog != null) {
                            finalProgressDialog.dismiss();
                        }
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setQuestions(ArrayList<QuestionItems> listQuestion) {
        questionAdapter = new QuestionsAdapter(context, listQuestion);
        rvQuestionsList.setAdapter(questionAdapter);
    }

}
