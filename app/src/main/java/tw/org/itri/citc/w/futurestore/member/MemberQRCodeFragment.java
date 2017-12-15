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
import android.widget.ImageView;
import android.widget.TextView;

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

    private SharedPreferences user_settings;
    private String uuid;
    private String email;

    private TextView lbl_account_info;
    private ImageView iv_qrcode;
    private Button btn_logout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_settings = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        uuid = user_settings.getString("UUID", "");
        email = user_settings.getString("EMAIL", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_qrcode_fragment, container, false);

        lbl_account_info = (TextView) view.findViewById(R.id.lbl_account_info);
        lbl_account_info.setText("Hello, " + email);

        iv_qrcode = (ImageView) view.findViewById(R.id.iv_qrcode);
        generateQRCode(iv_qrcode, uuid, 250, 250);

        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_settings.edit()
                        .putString("UUID", "")
                        .putString("EMAIL", "")
                        .commit();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.member_root_frame, new MemberLoginFragment());
                trans.commit();
            }
        });

        return view;
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
            e.printStackTrace();
        }
    }

}
