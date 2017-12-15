package tw.org.itri.citc.w.futurestore;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import tw.org.itri.citc.w.futurestore.member.MemberRootFragment;
import tw.org.itri.citc.w.futurestore.shoppingcart.ShoppingCartFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Handle possible data accompanying notification message.
        // if "pick up" or "put back" action, show shopping cart tab
        if (getIntent().getExtras() != null) {
            String action = (String) getIntent().getExtras().get("Action");
            if (action != null) {
                if (action.equals("Pickup") || action.equals(("Putback"))) {
                    mViewPager.setCurrentItem(1);
                }
            }
        }

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MemberRootFragment(), "Member");
        adapter.addFragment(new ShoppingCartFragment(), "Shopping Cart");
        viewPager.setAdapter(adapter);
    }


}
