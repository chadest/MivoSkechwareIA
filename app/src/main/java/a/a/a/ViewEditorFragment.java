@@
     public void initialize(ProjectFileBean projectFileBean) {
         this.projectFileBean = projectFileBean;
         isFabEnabled = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB);
         viewEditor.initialize(sc_id, projectFileBean);
         viewEditor.h();
         viewProperty.a(sc_id, this.projectFileBean);
         e();
         i();
+        // Initialize hierarchy tree panel (if included in layout)
+        try {
+            com.besome.sketch.editor.view.ViewTreePanel treePanel = viewEditor.findViewById(pro.sketchware.R.id.view_tree_panel);
+            if (treePanel != null) {
+                java.util.List<com.besome.sketch.beans.ViewBean> views = a.a.a.jC.a(sc_id).d(projectFileBean.getXmlName());
+                treePanel.setViews(views);
+                treePanel.setOnNodeClickListener(new com.besome.sketch.editor.view.ViewTreePanel.OnNodeClickListener() {
+                    @Override
+                    public void onNodeClick(com.besome.sketch.beans.ViewBean bean) {
+                        if (bean != null) {
+                            // reuse existing selection flow
+                            c(bean);
+                            viewProperty.e();
+                        }
+                    }
+                });
+            }
+        } catch (Exception ex) {
+            ex.printStackTrace();
+        }
         invalidateOptionsMenu();
     }
@@
     public void i() {
         invalidateOptionsMenu();
         if (projectFileBean != null) {
             b(jC.a(sc_id).d(projectFileBean.getXmlName()));
             a(jC.a(sc_id).h(projectFileBean.getXmlName()));
+            // Refresh tree panel views when layout is (re)loaded
+            try {
+                com.besome.sketch.editor.view.ViewTreePanel treePanel = viewEditor.findViewById(pro.sketchware.R.id.view_tree_panel);
+                if (treePanel != null) {
+                    java.util.List<com.besome.sketch.beans.ViewBean> views = a.a.a.jC.a(sc_id).d(projectFileBean.getXmlName());
+                    treePanel.setViews(views);
+                }
+            } catch (Exception ex) {
+                // swallow
+            }
         }
     }
