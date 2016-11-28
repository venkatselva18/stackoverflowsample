package realimage.stackoverflow.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;

import java.util.ArrayList;

import realimage.stackoverflow.R;
import realimage.stackoverflow.adapter.QuestionsAdapter;
import realimage.stackoverflow.helpers.Config;
import realimage.stackoverflow.helpers.PrefUtil;
import realimage.stackoverflow.model.QuestionItems;

import com.github.scribejava.apis.StackExchangeApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import realimage.stackoverflow.model.QuestionResponse;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class YourQuestionsActivity extends AppCompatActivity {
    Context context;
    RecyclerView rvQuestionsList;
    QuestionsAdapter questionAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    PrefUtil prefUtil;
    final OAuth20Service oAuthService = new ServiceBuilder()
            .apiKey(Config.CLIENT_ID)
            .callback(Config.OAUTH_CALLBACK_URL)
            .apiSecret(Config.CONSUMER_SECRET)
            .debug()
            .scope("no_expiry")
            .build(StackExchangeApi.instance());
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question_list);
        context = YourQuestionsActivity.this;
        prefUtil = new PrefUtil(context);
        rvQuestionsList = (RecyclerView) findViewById(R.id.rv_questions_list);
        mLayoutManager = new LinearLayoutManager(context);
        rvQuestionsList.setLayoutManager(mLayoutManager);
        progressDialog = ProgressDialog.show(context, "Please Wait", "getting your questions");
        new getQuestionsWithAuth().execute("");
    }

    private class getQuestionsWithAuth extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.stackexchange.com/2.2/me/questions?order=desc&filter=!)Q2ANGPVz)tSvZguWTzAF_jz&site=stackoverflow&key=" + Config.API_KEY, oAuthService);
            oAuthService.signRequest(new OAuth2AccessToken(prefUtil.getStringPref(Config.PREF_ACCESS_TOKEN, "")), request);
            final com.github.scribejava.core.model.Response response = request.send();
            try {
                progressDialog.dismiss();
                QuestionResponse questionResponse = new Gson().fromJson(response.getBody().toString(), QuestionResponse.class);
                if (questionResponse.getItems().size() == 0) {
                    showNoQuestionsToast();
                } else {
                    setQuestions(questionResponse.getItems());
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }
    }

    private void showNoQuestionsToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "You didn't asked questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setQuestions(final ArrayList<QuestionItems> listQuestion) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                questionAdapter = new QuestionsAdapter(context, listQuestion);
                rvQuestionsList.setAdapter(questionAdapter);
            }
        });
    }

}
