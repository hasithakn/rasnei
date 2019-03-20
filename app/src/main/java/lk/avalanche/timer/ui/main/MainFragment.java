package lk.avalanche.timer.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lk.avalanche.timer.R;
import lk.avalanche.timer.databinding.MainFragmentBinding;
import lk.avalanche.timer.db.Entity.Data;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final MainViewModel.Model model = new MainViewModel.Model();
        binding.setModel(model);
        mViewModel.liveData.observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                mViewModel.changeSetting(data);
            }
        });
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.startTimer();
            }
        });
        binding.btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.pauseTimer();
            }
        });
        binding.btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.resetTimer();
            }
        });
        mViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String[] split = s.split(":");
                model.setMin(split[1]);
                model.setSec(split[2]);
                model.setMilSec(split[3]);
                model.setCurrent_round(split[4]);
                binding.setModel(model);
            }
        });
    }

}
