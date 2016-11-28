package realimage.stackoverflow.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.scribejava.apis.StackExchangeApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import realimage.stackoverflow.R;
import realimage.stackoverflow.apiService.ServiceGenerator;
import realimage.stackoverflow.apiService.ServiceInterface;
import realimage.stackoverflow.helpers.Config;
import realimage.stackoverflow.helpers.PrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    Button btnLog;
    final OAuth20Service oAuthService = new ServiceBuilder()
            .apiKey(Config.CLIENT_ID)
            .callback(Config.OAUTH_CALLBACK_URL)
            .apiSecret(Config.CONSUMER_SECRET)
            .debug()
            .scope("no_expiry,read_inbox,private_info")
            .build(StackExchangeApi.instance());
    Button btnStackQuestions;
    Context context;
    PrefUtil prefUtil;
    boolean isAcessTokenAvailable = false;
    ProgressDialog progressDialog;
    MenuItem menuLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        prefUtil = new PrefUtil(context);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnLog = (Button) findViewById(R.id.btn_log);
        btnStackQuestions = (Button) findViewById(R.id.btn_stackoverflow_questions);

        if (!prefUtil.getStringPref(Config.PREF_ACCESS_TOKEN, Config.EMPTY_ACCESS_TOKEN).equals(Config.EMPTY_ACCESS_TOKEN)) {
            setLoggedIn();
        }

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAcessTokenAvailable) {
                    startActivity(new Intent(context, YourQuestionsActivity.class));
                } else {
                    createOAuthService();
                }

            }
        });
        btnStackQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StackOverflowQuestionActivity.class));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Config.IS_AVAILABLE_ACCESS_CODE_FROM_WEBVIEW)
            logInSuccess(Config.ACCESS_CODE_FROM_WEBVIEW.trim());

    }

    private void createOAuthService() {
        String authUrl = null;
        try {
            authUrl = new MainActivity.authUrl().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Intent webIntent = new Intent(MainActivity.this, LoadWebViewActivity.class);
        webIntent.putExtra(Config.TO_LOAD_URL, authUrl);
        startActivity(webIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuLogout = (MenuItem) menu.findItem(R.id.action_logout);
        if (!isAcessTokenAvailable) {
            menuOptions(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            if (isAcessTokenAvailable)
                userLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutSuccess() {
        prefUtil.clearAllSharedPreferences();
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();
        btnLog.setText("Login");
        isAcessTokenAvailable = false;
        menuOptions(false);
        Config.IS_AVAILABLE_ACCESS_CODE_FROM_WEBVIEW = false;
    }

    private void invalidateAccessTokenAPI() {
        progressDialog = ProgressDialog.show(context, "Please wait", "Logout");
        ServiceInterface serviceInterface = ServiceGenerator.createService(ServiceInterface.class);
        serviceInterface.invalidateAccessToken(prefUtil.getStringPref(Config.PREF_ACCESS_TOKEN, "")).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                logoutSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class authUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return oAuthService.getAuthorizationUrl();
        }

    }

    private void logInSuccess(String accessToken) {
        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
        prefUtil.setStringPref(Config.PREF_ACCESS_TOKEN, accessToken);
        Config.IS_AVAILABLE_ACCESS_CODE_FROM_WEBVIEW = false;
        prefUtil.commit();
        setLoggedIn();
    }

    public void setLoggedIn() {
        btnLog.setText("Your Questions");
        isAcessTokenAvailable = true;
        menuOptions(true);
    }

    private void userLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to logout?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                invalidateAccessTokenAPI();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void menuOptions(boolean visible) {
        if (menuLogout != null)
            menuLogout.setVisible(visible);
    }
}
