package com.androidapp.kidospartners.beans;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

import com.androidapp.kidospartners.BR;

public class KidosPartnersAgeCriteriaBean extends BaseObservable {
    private int from;
    private int to;

    @Bindable
    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
        notifyPropertyChanged(BR.from);
    }

    @Bindable
    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
        notifyPropertyChanged(BR.to);
    }


    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        System.out.println("******** Inside binding Adapter "+value);
        view.setText(Integer.toString(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        System.out.println("******** Inside inversebinding Adapter "+view.getText());
        if("".equals(view.getText().toString()))
            return 0;
        else
            return Integer.parseInt(view.getText().toString());
    }

    @Override
    public String toString() {
        return "KidosPartnersAgeCriteriaBean{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
