package com.besome.sketch.editor.view;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public ViewBean bean;
    public final List<TreeNode> children = new ArrayList<>();
    public boolean expanded = true;
    public int depth = 0;

    public TreeNode(ViewBean bean) {
        this.bean = bean;
    }
}
