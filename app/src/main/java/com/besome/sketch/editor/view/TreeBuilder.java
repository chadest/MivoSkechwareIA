package com.besome.sketch.editor.view;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility to build a tree of TreeNode from a flat list of ViewBean
 */
public final class TreeBuilder {

    private TreeBuilder() {
    }

    public static List<TreeNode> buildTree(List<ViewBean> views) {
        Map<String, TreeNode> map = new HashMap<>();
        List<TreeNode> roots = new ArrayList<>();

        // create nodes
        for (ViewBean vb : views) {
            map.put(vb.id, new TreeNode(vb));
        }

        // link parent -> children
        for (ViewBean vb : views) {
            TreeNode node = map.get(vb.id);
            String parentId = vb.parent;
            if (parentId == null || parentId.isEmpty() || parentId.equals("root")) {
                node.depth = 0;
                roots.add(node);
            } else {
                TreeNode parent = map.get(parentId);
                if (parent != null) {
                    node.depth = parent.depth + 1;
                    parent.children.add(node);
                } else {
                    // fallback to root if parent not found
                    node.depth = 0;
                    roots.add(node);
                }
            }
        }

        return roots;
    }
}
