@@
     @Override
     public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
         super.onCreateOptionsMenu(menu, menuInflater);
         menuInflater.inflate(R.menu.design_view_menu, menu);
         menu.findItem(R.id.menu_view_redo).setEnabled(false);
         menu.findItem(R.id.menu_view_undo).setEnabled(false);
         if (projectFileBean != null) {
             menu.findItem(R.id.menu_view_redo).setEnabled(cC.c(sc_id).f(projectFileBean.getXmlName()));
             menu.findItem(R.id.menu_view_undo).setEnabled(cC.c(sc_id).g(projectFileBean.getXmlName()));
         }
+        // adjust hierarchy toggle checked state if panel exists
+        try {
+            View tree = viewEditor.findViewById(pro.sketchware.R.id.view_tree_panel);
+            if (tree != null) {
+                MenuItem item = menu.findItem(R.id.menu_view_hierarchy);
+                if (item != null) item.setChecked(tree.getVisibility() == View.VISIBLE);
+            }
+        } catch (Exception ignored) {
+        }
     }
@@
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int itemId = item.getItemId();
         if (itemId == R.id.menu_view_redo) {
             onRedo();
         } else if (itemId == R.id.menu_view_undo) {
             onUndo();
+        } else if (itemId == R.id.menu_view_hierarchy) {
+            // toggle hierarchy panel
+            try {
+                View tree = viewEditor.findViewById(pro.sketchware.R.id.view_tree_panel);
+                if (tree != null) {
+                    int newVis = tree.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
+                    tree.setVisibility(newVis);
+                    item.setChecked(newVis == View.VISIBLE);
+                    if (newVis == View.VISIBLE && projectFileBean != null) {
+                        java.util.List<com.besome.sketch.beans.ViewBean> views = a.a.a.jC.a(sc_id).d(projectFileBean.getXmlName());
+                        ((com.besome.sketch.editor.view.ViewTreePanel) tree).setViews(views);
+                    }
+                }
+            } catch (Exception ignored) {
+            }
         }
         return true;
     }
