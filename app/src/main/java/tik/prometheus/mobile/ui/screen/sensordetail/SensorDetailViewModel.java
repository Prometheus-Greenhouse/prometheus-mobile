package tik.prometheus.mobile.ui.screen.sensordetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SensorDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SensorDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}