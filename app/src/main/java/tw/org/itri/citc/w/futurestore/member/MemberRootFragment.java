package tw.org.itri.citc.w.futurestore.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.org.itri.citc.w.futurestore.R;

/**
 * Created on 2017/11/12.
 */

public class MemberRootFragment extends Fragment{
    private static final String TAG = "MemberRootFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_root_fragment, container, false);

        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.member_root_frame, new MemberLoginFragment());
        trans.commit();

        return view;
    }


}
