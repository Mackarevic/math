package com.example.mathlearner.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mathlearner.R;
import com.example.mathlearner.saves.SaveInfo;
import com.example.mathlearner.saves.SaveType;

import java.util.List;

public class ResultsListViewAdapter extends BaseAdapter {

    private final Activity activity;
    private final List<SaveInfo> saveInfoList;

    private final int color0 = 0xFF090909;
    private int currentColor = color0;

    public ResultsListViewAdapter(Activity activity, List<SaveInfo> saveInfoList) {
        super();
        this.activity = activity;
        this.saveInfoList = saveInfoList;
    }

    @Override
    public int getCount() {
        return saveInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return saveInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (view == null)
            view = inflater.inflate(R.layout.result_list_item, null);

        LinearLayout bg = view.findViewById(R.id.result_list_item_background);

        int color1 = 0xFF121212;
        if (currentColor == color0)
            currentColor = color1;
        else
            currentColor = color0;

        bg.setBackgroundColor(currentColor);

        TextView numbers = view.findViewById(R.id.result_list_item_numbers),
                correct = view.findViewById(R.id.result_list_item_output);

        SaveInfo saveInfo = saveInfoList.get(position);

        int num1 = saveInfo.getNumber1(),
                num2 = saveInfo.getNumber2(),
                result = saveInfo.getUserInput();

        SaveType saveType = saveInfo.getSaveType();

        String numString = num1 + " + " + num2;
        String answer;

        switch (saveType) {
            case CORRECT:
                numString += " = " + result;
                answer = activity.getString(R.string.results_list_item_correct);
                break;
            case INCORRECT:
                numString += " = " + result;
                answer = activity.getString(R.string.results_list_item_incorrect);
                break;
            case BLANK_FIELD:
                numString += " = " + activity.getString(R.string.common_undefined);
                answer = activity.getString(R.string.results_list_item_blank);
                break;
            case NEW_TASK:
                numString += " = " + activity.getString(R.string.common_undefined);
                answer = activity.getString(R.string.results_list_item_new_task);
                break;
            default:
                numString += " = " + activity.getString(R.string.common_undefined);
                answer = activity.getString(R.string.common_undefined);
                break;
        }

        numbers.setText(numString);
        correct.setText(answer);

        return view;
    }
}
