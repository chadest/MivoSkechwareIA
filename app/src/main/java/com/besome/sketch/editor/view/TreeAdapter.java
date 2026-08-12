package com.besome.sketch.editor.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ViewBean;
import pro.sketchware.R;

import java.util.ArrayList;
import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.Holder> {

    private final List<TreeNode> visible = new ArrayList<>();
    private final List<TreeNode> roots = new ArrayList<>();
    private String selectionId;
    private OnNodeClickListener onNodeClickListener;

    public interface OnNodeClickListener {
        void onClick(ViewBean bean);
    }

    public void setOnNodeClickListener(OnNodeClickListener l) {
        onNodeClickListener = l;
    }

    public void setRoots(List<TreeNode> rootNodes) {
        roots.clear();
        if (rootNodes != null) roots.addAll(rootNodes);
        rebuildVisible();
        notifyDataSetChanged();
    }

    public void setSelection(String id) {
        selectionId = id;
        notifyDataSetChanged();
    }

    private void rebuildVisible() {
        visible.clear();
        for (TreeNode node : roots) {
            traverse(node);
        }
    }

    private void traverse(TreeNode node) {
        visible.add(node);
        if (node.expanded) {
            for (TreeNode child : node.children) {
                child.depth = node.depth + 1;
                traverse(child);
            }
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_tree_node, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TreeNode node = visible.get(position);
        ViewBean bean = node.bean;
        holder.tvText.setText(bean.id != null ? bean.id : "(no id)");
        // indent
        int padding = node.depth * 24;
        holder.itemView.setPadding(padding, holder.itemView.getPaddingTop(), holder.itemView.getPaddingRight(), holder.itemView.getPaddingBottom());
        holder.imgExpand.setVisibility(node.children.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        holder.imgExpand.setRotation(node.expanded ? 180f : 0f);

        boolean selected = selectionId != null && selectionId.equals(bean.id);
        holder.itemView.setSelected(selected);

        holder.itemView.setOnClickListener(v -> {
            if (!node.children.isEmpty()) {
                node.expanded = !node.expanded;
                rebuildVisible();
                notifyDataSetChanged();
            }
            if (onNodeClickListener != null) onNodeClickListener.onClick(bean);
        });
    }

    @Override
    public int getItemCount() {
        return visible.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvText;
        ImageView imgExpand;

        Holder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            tvText = itemView.findViewById(R.id.tv_node_text);
            imgExpand = itemView.findViewById(R.id.img_expand);
        }
    }
}
