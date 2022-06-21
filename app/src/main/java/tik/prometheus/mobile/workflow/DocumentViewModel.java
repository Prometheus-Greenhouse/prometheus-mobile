package tik.prometheus.mobile.workflow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DocumentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is document fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



}
