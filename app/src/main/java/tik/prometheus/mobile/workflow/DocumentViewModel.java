package tik.prometheus.mobile.workflow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    private MutableLiveData<String[]> usernames;

    public DocumentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is document fragment");
        usernames = new MutableLiveData<>();
        usernames.setValue(new String[]{"VYNKK", "2", "3"});
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String[]> getUsername() {
        return usernames;
    }


}
