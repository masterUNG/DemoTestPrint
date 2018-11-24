package masterung.androidthai.in.th.demotestprint;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zj.wfsdk.WifiCommunication;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //    Explicit
    private WifiCommunication wifiCommunication;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Connected Printer
        wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket("192.168.1.87", 9100);

    }   // Main Method

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tag = "24novV3";
            switch (msg.what) {

                case WifiCommunication.WFPRINTER_CONNECTED:
                    Log.d(tag, "Success Connected Printer");
                    break;
                case WifiCommunication.WFPRINTER_DISCONNECTED:
                    Log.d(tag, "Disconnected Printer");
                    break;
                default:
                    break;

            }   // switch
        }   // handleMessage
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
