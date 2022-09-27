package tik.prometheus.mobile.ui.screen.farm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FarmViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FarmViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}