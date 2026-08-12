package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ViewBean;
import pro.sketchware.R;

import java.util.ArrayList;
import java.util.List;

public class ViewTreePanel extends RelativeLayout {

    private RecyclerView recyclerView;
    private TreeAdapter adapter;
    private TextView tvTitle;
    private ImageButton btnClose;

    public interface OnNodeClickListener {
        void onNodeClick(ViewBean bean);
    }

    private OnNodeClickListener onNodeClickListener;

    public ViewTreePanel(Context context) {
        super(context);
        init(context);
    }

    public ViewTreePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tree_panel, this, true);
        recyclerView = findViewById(R.id.recycler_tree);
        tvTitle = findViewById(R.id.tv_tree_title);
        btnClose = findViewById(R.id.btn_tree_close);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TreeAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnNodeClickListener(bean -> {
            if (onNodeClickListener != null) onNodeClickListener.onNodeClick(bean);
        });

        btnClose.setOnClickListener(v -> setVisibility(View.GONE));
    }

    public void setOnNodeClickListener(OnNodeClickListener l) {
        onNodeClickListener = l;
    }

    public void setViews(List<ViewBean> views) {
        List<TreeNode> roots = TreeBuilder.buildTree(views == null ? new ArrayList<>() : views);
        adapter.setRoots(roots);
    }

    public void setSelection(String id) {
        adapter.setSelection(id);
    }
}
