@@
     public void i() {
         if (selectedItem != null) {
             selectedItem.setSelection(false);
             selectedItem = null;
         }
         if (widgetSelectedListener != null) widgetSelectedListener.a(false, "");
+        // reflect deselection in hierarchy panel
+        try {
+            com.besome.sketch.editor.view.ViewTreePanel treePanel = findViewById(pro.sketchware.R.id.view_tree_panel);
+            if (treePanel != null) {
+                treePanel.setSelection("");
+            }
+        } catch (Exception ignored) {
+        }
     }
@@
     public ItemView createAndAddView(ViewBean viewBean) {
         View itemView = viewPane.createItemView(viewBean);
         viewPane.addViewAndUpdateIndex(itemView);
@@
         itemView.setOnTouchListener(this);
-        return (ItemView) itemView;
+        // notify tree about structural change
+        notifyTreeChanged();
+        return (ItemView) itemView;
     }
@@
     public ItemView a(ArrayList<ViewBean> arrayList, boolean z) {
@@
         for (ViewBean view : arrayList) {
             if (arrayList.indexOf(view) == 0) {
                 syVar = createAndAddView(view);
             } else {
                 createAndAddView(view);
             }
         }
-        return syVar;
+        notifyTreeChanged();
+        return syVar;
     }
@@
     public ItemView a(ViewBean viewBean, boolean isInHistory) {
@@
-        return createAndAddView(viewBean);
+        ItemView iv = createAndAddView(viewBean);
+        notifyTreeChanged();
+        return iv;
     }
@@
     public void b(ArrayList<ViewBean> arrayList, boolean z) {
@@
-        int size = arrayList.size();
+        int size = arrayList.size();
         while (true) {
             size--;
             if (size < 0) {
-                return;
+                // structural change completed
+                notifyTreeChanged();
+                return;
             }
             d(arrayList.get(size));
         }
     }
@@
     public void deleteWidget(ViewBean viewBean) {
         ArrayList<ViewBean> b2 = jC.a(a).b(b, viewBean);
         for (int size = b2.size() - 1; size >= 0; size--) {
             jC.a(a).a(projectFileBean, b2.get(size));
         }
         b(b2, true);
+        // notify already handled by b(...) call
     }
@@
     public void updateSelection(String tag) {
         ItemView syVar;
         ItemView itemView = viewPane.findItemViewByTag(tag);
         if (itemView == null || (syVar = selectedItem) == itemView) {
             return;
         }
         if (syVar != null) {
             syVar.setSelection(false);
         }
         itemView.setSelection(true);
         selectedItem = itemView;
+        // reflect selection in hierarchy panel
+        try {
+            com.besome.sketch.editor.view.ViewTreePanel treePanel = findViewById(pro.sketchware.R.id.view_tree_panel);
+            if (treePanel != null) {
+                treePanel.setSelection(tag);
+            }
+        } catch (Exception ignored) {
+        }
     }
+
+    private void notifyTreeChanged() {
+        try {
+            com.besome.sketch.editor.view.ViewTreePanel treePanel = findViewById(pro.sketchware.R.id.view_tree_panel);
+            if (treePanel != null && projectFileBean != null && a != null) {
+                java.util.List<ViewBean> views = a.a.a.jC.a(a).d(projectFileBean.getXmlName());
+                treePanel.setViews(views);
+            }
+        } catch (Exception ignored) {
+        }
+    }
