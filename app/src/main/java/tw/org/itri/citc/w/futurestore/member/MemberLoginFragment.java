package tw.org.itri.citc.w.futurestore.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tw.org.itri.citc.w.futurestore.R;

public class MemberLoginFragment extends Fragment{
    private static final String TAG = "MemberLoginFragment";

    OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Button btn_login;
    private Button btn_to_signup;
    private EditText txt_email;
    private EditText txt_password;

    private SharedPreferences user_settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        user_settings = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_login_fragment, container, false);

        txt_email = (EditText) view.findViewById(R.id.txt_email);
        txt_password = (EditText) view.findViewById(R.id.txt_password);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                Log.d(TAG, email+"/"+password);
                signIn(email, password, FirebaseInstanceId.getInstance().getToken());
            }
        });

        btn_to_signup = (Button) view.findViewById(R.id.btn_to_signup);
        btn_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.member_root_frame, new MemberSignupFragment());
                trans.commit();
            }
        });

        return view;
    }

    private void signIn(final String email, String password, String fcmToken) {
        Log.d(TAG, "signIn with :" + email);

        final Gson gson = new Gson();

        String url = "http://140.96.178.1:8081/app/login/";
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount(email);
        loginRequest.setPassword(password);
        //TODO: 改回 fcmToken
        loginRequest.setFcmToken("fcmtoken12345");
//        loginRequest.setFcmToken(fcmToken);
        String jsonRequest = gson.toJson(loginRequest);
        Log.d(TAG, "Request = " + jsonRequest);

        RequestBody body = RequestBody.create(JSON, jsonRequest);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResponse = response.body().string();
                Log.d(TAG, "length of response=" + jsonResponse.length());
                Log.d(TAG, "Response = " + jsonResponse);

                final LoginResponse loginResponse = gson.fromJson(jsonResponse, new TypeToken<LoginResponse>(){}.getType());
                Log.d(TAG, "msg= " + loginResponse.getMsg());
                Log.d(TAG, "uuid= " + loginResponse.getUuid());
                String uuid = loginResponse.getUuid().trim();
                if (!uuid.equals("")) {
                    user_settings.edit()
                            .putString("UUID", loginResponse.getUuid())
                            .putString("EMAIL", email)
                            .commit();

                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.member_root_frame, new MemberQRCodeFragment());
                    trans.commit();
                } else {
                    //告知使用者認證失敗
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Login failed: " + loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, final IOException e) {
                //告知使用者連線失敗
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, e.getMessage());
            }
        });
    }

}
