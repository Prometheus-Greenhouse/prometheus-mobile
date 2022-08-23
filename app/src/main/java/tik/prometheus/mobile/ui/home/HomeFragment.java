package tik.prometheus.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import tik.prometheus.mobile.R;
import tik.prometheus.mobile.databinding.FragmentHomeBinding;
import tik.prometheus.mobile.services.HTTPConnector;
import tik.prometheus.mobile.utils.Utils;
import tik.prometheus.mobile.workflow.WorkflowRepos;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private WorkflowRepos workflowRepos;
    LineChart mpLinechart;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        workflowRepos = HTTPConnector.createService(WorkflowRepos.class, "VYNKK", "1");
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initChart(binding);
        LinearLayout sensorDashboard = binding.sensorDashboard;
        for (int i = 0; i <= 10; i++) {
            CardView card = new CardView(getContext());

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(200, 200);
            layout.setMargins(5, 5, 5, 5);
            card.setLayoutParams(layout);

            int color = Utils.Companion.themeColor(getContext(), R.attr.colorError);
            card.setCardBackgroundColor(color);

            sensorDashboard.addView(card);
        }
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return binding.getRoot();
    }

    List<Entry> dataValues() {
        List<Entry> dataVals = new ArrayList<>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 10));
        dataVals.add(new Entry(3, 30));
        dataVals.add(new Entry(9, 20));
        dataVals.add(new Entry(7, 10));
        return dataVals;
    }

    private void initChart(FragmentHomeBinding binding) {
        mpLinechart = binding.lineChart;
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(
                new LineDataSet(dataValues(), "Data set 1")
        );
        LineData data = new LineData(dataSets);
        mpLinechart.setData(data);
        mpLinechart.invalidate();
        int color = Utils.Companion.themeColor(getContext(), R.attr.colorOnBackground);
        mpLinechart.getAxisLeft().setTextColor(color);
        mpLinechart.getAxisRight().setTextColor(color);
        mpLinechart.getLegend().setTextColor(color);
        mpLinechart.getLineData().setValueTextColor(color);
        mpLinechart.getDescription().setTextColor(color);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}