package io.zillion.universalcopypaste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.zillion.universalcopypaste.FailureCallback;
import io.zillion.universalcopypaste.FarziActivity;
import io.zillion.universalcopypaste.NetworkRequest;
import io.zillion.universalcopypaste.R;
import io.zillion.universalcopypaste.SuccessCallback;
import io.zillion.universalcopypaste.utils.User;
import io.zillion.universalcopypaste.utils.UserManager;

/**
 * Created by jafarnaqvi on 08/06/16.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_activity_email)
    EditText email;
    @BindView(R.id.login_activity_password)
    EditText password;
    UserManager userManager;


    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        userManager=new UserManager(getBaseContext());

        if(userManager.getUser()!=null) {
            startActivity(new Intent(getBaseContext(), FarziActivity.class));

            finish();


        }


    }

    @OnClick(R.id.login_activity_login_button)
    public void login(){

        HashMap<String,String> params=new HashMap<>();
        params.put("email",email.getText().toString());
        params.put("password",password.getText().toString());
        NetworkRequest networkRequest=new NetworkRequest(this, "user/login", params, new SuccessCallback() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    User user=User.getInstance();

                    JSONObject userobj=jsonObject.getJSONObject("user");

                    user.setToken(jsonObject.getString("token"));
                    user.setEmail(userobj.getString("email"));
                    user.setId(userobj.getString("id"));
                    user.setName(userobj.getString("name"));


                    userManager.saveUser(user);

                    startActivity(new Intent(getBaseContext(), FarziActivity.class));

                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new FailureCallback() {
            @Override
            public void onResponse() {

            }
        });

        networkRequest.setCache(false);
        networkRequest.setMethod(Request.Method.POST);
        networkRequest.execute();


    }
}
