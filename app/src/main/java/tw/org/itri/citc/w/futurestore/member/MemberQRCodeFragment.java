package tw.org.itri.citc.w.futurestore.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

import tw.org.itri.citc.w.futurestore.R;

public class MemberQRCodeFragment extends Fragment {
    private static final String TAG = "MemberQRCodeFragment";

    private TextView lbl_account_info;
    private ImageView iv_qrcode;
    private Button btn_logout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.member_root_frame, new MemberLoginFragment());
                    trans.commit();
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_qrcode_fragment, container, false);

        lbl_account_info = (TextView) view.findViewById(R.id.lbl_account_info);
        lbl_account_info.setText("Hello, " + user.getEmail());

        iv_qrcode = (ImageView) view.findViewById(R.id.iv_qrcode);
        generateQRCode(iv_qrcode, user.getEmail(), 250, 250);

        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void generateQRCode(ImageView imageView, String QRCodeContent, int QRCodeWidth, int QRCodeHeight) {

        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix result = writer.encode(QRCodeContent, BarcodeFormat.QR_CODE, QRCodeWidth, QRCodeHeight, hints);
            Bitmap bitmap = Bitmap.createBitmap(QRCodeWidth, QRCodeHeight, Bitmap.Config.ARGB_8888);
            for (int y = 0; y<QRCodeHeight; y++)
            {
                for (int x = 0;x<QRCodeWidth; x++)
                {
                    bitmap.setPixel(x, y, result.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            imageView.setImageBitmap(bitmap);
        }
        catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
