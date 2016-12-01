package test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lxl on 2016/12/1.
 */
public class MyScrollView extends ScrollView  {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface  OnScrollChangeListener{
        public void onScrollChange(float Y);
    }

    private OnScrollChangeListener scrollChangeListener;

    public void setScrollChangeListener(OnScrollChangeListener listener){
        this.scrollChangeListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollChangeListener.onScrollChange(t);
    }
}
