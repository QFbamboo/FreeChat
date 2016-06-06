package com.bamboo.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bamboo on 16-5-31.
 */
public abstract class MyAdapter<T> extends BaseAdapter {

    private static final List LIST=new ArrayList();

    enum TYPE {
        ARRAY, LIST
    }

    private TYPE type;
    private Context context;
    private T[] array;
    private List<T> list;

    public MyAdapter(Context context){
        this(context,LIST);
    }

    public MyAdapter(Context context, T[] array) {
        this.context = context;
        this.array = array;
        this.type = TYPE.ARRAY;
    }

    public MyAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        this.type = TYPE.LIST;
    }
    //设置传输过来的数据
    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        if (type == TYPE.ARRAY) {
            return array.length;
        } else if (type == TYPE.LIST) {
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (type == TYPE.ARRAY) {
            return array[position];
        } else if (type == TYPE.LIST) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    //ViewHolder以静态内部类的形式存在,(私有构造),通过getHolder来获取ViewHolder对象
    public static ViewHolder getHolder(Context context, View convertView, ViewGroup parent,
                                       int layoutId, int position) {
        if (convertView == null) {
            try {
                return new ViewHolder(LayoutInflater.from(context).inflate(layoutId,
                        parent, false), position);

            } catch (Exception e) {
                throw new RuntimeException("LayoutInflater inflater Error..." + e);
            }
        }
        return (ViewHolder) convertView.getTag();
    }

    public static class ViewHolder {
        private final int position;
        private View convertView;
        private SparseArray<View> myView = new SparseArray<View>();

        public ViewHolder(View convertView, int position) {
            this.convertView = convertView;
            this.position = position;
            convertView.setTag(this);
        }

        public View getConvertView() {
            return convertView;
        }

        public <T extends View> T getView(int viewId) {
            View view = myView.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                myView.put(viewId, view);
            }
            return (T) view;
        }

    }
}
