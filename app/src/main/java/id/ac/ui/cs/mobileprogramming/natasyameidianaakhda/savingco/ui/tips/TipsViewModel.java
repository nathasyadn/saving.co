package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.tips;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.BitcoinRepository;

public class TipsViewModel extends AndroidViewModel {

    private BitcoinRepository bitcoinRepository;

    public TipsViewModel(Application application) {
        super(application);
        bitcoinRepository = new BitcoinRepository();
    }

    public String getBitcoinIndexAPI() {
        return bitcoinRepository.getBitcoinValue();
    }
}