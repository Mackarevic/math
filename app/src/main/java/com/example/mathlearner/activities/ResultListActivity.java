package com.example.mathlearner.activities;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mathlearner.R;
import com.example.mathlearner.adapters.ResultsListViewAdapter;
import com.example.mathlearner.saves.SaveInfo;
import com.example.mathlearner.saves.SaveManager;
import com.example.mathlearner.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultListActivity extends AppCompatActivity {

    private List<SaveInfo> saveList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        try {
            saveList = SaveManager.loadSaves(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.result_list_view);
        listView.setAdapter(new ResultsListViewAdapter(ResultListActivity.this, saveList));

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            try {
                SaveManager.remove(getApplicationContext(), position);
                saveList.remove(position);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        });

        Button btnClear = findViewById(R.id.result_list_clear);

        btnClear.setOnClickListener(v -> {
            try {
                SaveManager.wipeSave(getApplicationContext());
                saveList.clear();
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
