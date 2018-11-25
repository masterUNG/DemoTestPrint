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
import android.widget.Button;
import android.widget.Toast;

import com.zj.wfsdk.WifiCommunication;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //    Explicit
    private WifiCommunication wifiCommunication;
    private boolean aBoolean = false;
    private boolean communicationABoolean = true;   //true ==> Can Print, false ==> Disable Print
    private Button button, printAgainButton;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Connected Printer
        createCommuicationPrinter();

//        Print Controller
        button = getView().findViewById(R.id.btnPrint);
        printAgainButton = getView().findViewById(R.id.btnPrintAgain);
        printAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCommuicationPrinter();
                communicationABoolean = true;
            }
        });


    }   // Main Method

    private void createCommuicationPrinter() {
        wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket("192.168.1.87", 9100);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tag = "24novV3";
            switch (msg.what) {

                case WifiCommunication.WFPRINTER_CONNECTED:
                    Log.d(tag, "Success Connected Printer");
                    button.setText("Test Print");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (communicationABoolean) {

                                String printString = "MasterUNG";
                                Log.d("24novV3", "You Click Test Print ==> " + printString);

                                byte[] bytes = new byte[]{0x10, 0x04, 0x04};
                                wifiCommunication.sndByte(bytes);
                                wifiCommunication.sendMsg(printString, "gbk");

                                byte[] bytes1 = new byte[]{0x0A, 0x0D};
                                wifiCommunication.sndByte(bytes1);

                                wifiCommunication.close();

                                communicationABoolean = false;

                            } else {
                                Toast.makeText(getActivity(), "Disable Printer Please Press Click Again", Toast.LENGTH_SHORT).show();
                            }

                        }   // onClick

                    });

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
