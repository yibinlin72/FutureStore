package tw.org.itri.citc.w.futurestore.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tw.org.itri.citc.w.futurestore.R;

/**
 * Created on 2017/11/12.
 */

public class MemberSignupFragment extends Fragment{
    private static final String TAG = "MemberSignupFragment";

    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_signup_fragment, container, false);
        btn = (Button) view.findViewById(R.id.btn_to_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.member_root_frame, new MemberLoginFragment());
                trans.commit();
            }
        });

        return view;
    }
}
