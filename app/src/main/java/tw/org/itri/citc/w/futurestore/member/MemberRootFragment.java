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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tw.org.itri.citc.w.futurestore.R;


public class MemberRootFragment extends Fragment{
    private static final String TAG = "MemberRootFragment";

//    private FirebaseUser user;
    private SharedPreferences user_settings;
    private String uuid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_settings = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        uuid = user_settings.getString("UUID", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_root_fragment, container, false);

        FragmentTransaction trans = getFragmentManager().beginTransaction();
        if (!uuid.equals("")) {
            Log.d(TAG, "user logged in");
            trans.replace(R.id.member_root_frame, new MemberQRCodeFragment());
        } else {
            Log.d(TAG, "user not logged in yet");
            trans.replace(R.id.member_root_frame, new MemberLoginFragment());
        }
        trans.commit();

        return view;
    }

}
