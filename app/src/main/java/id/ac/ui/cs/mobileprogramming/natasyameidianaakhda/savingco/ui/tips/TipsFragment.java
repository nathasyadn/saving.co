package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.tips;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.ConnectionReceiver;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;

public class TipsFragment extends Fragment {

    private TipsViewModel tipsViewModel;
    private ConnectionReceiver receiver;
    private IntentFilter intentFilter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tipsViewModel =
                ViewModelProviders.of(this).get(TipsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tips, container, false);

        final TextView textBitcoin = (TextView) root.findViewById(R.id.bitcoinText);
        String definition = textBitcoin.getText() + getString(R.string.textBitcoinAPI) + tipsViewModel.getBitcoinIndexAPI();
        textBitcoin.setText(definition);

        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter(SavingcoConstant.BROADCAST_CONNECTION_INTENT_FILTER_NAME);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}