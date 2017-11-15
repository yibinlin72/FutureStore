package tw.org.itri.citc.w.futurestore.member;

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

    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_root_fragment, container, false);


        FragmentTransaction trans = getFragmentManager().beginTransaction();
        if (user!=null) {
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
