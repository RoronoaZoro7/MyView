package com.myview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Author LI .
 * Time 2018/9/17
 * Description is BouncingMenu
 */
public class BouncingMenu {

    private  View root;
    private ViewGroup viewGroup;
    private final BouncingView bouncingView;
    private final ListView listView;

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    private final ArrayAdapter<String> adapter;

    public BouncingMenu(View view, int layout) {
        //渲染菜单布局View进来
        root = LayoutInflater.from(view.getContext()).inflate(layout, null, false);
        bouncingView = (BouncingView) root.findViewById(R.id.sv);
        listView = root.findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(
                view.getContext(), android.R.layout.simple_list_item_1, data);

        //找到FrameLayput用来装载View
         viewGroup =  findSuitableParent(view);
    }

    private ViewGroup findSuitableParent(View view) {
        do {
              if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return null;
    }


    public static BouncingMenu MakMenu(View view, int layout) {
       return new BouncingMenu(view,layout);
    }

    public void show() {
        //装载View  add
        if(viewGroup.getParent()!=null){
            viewGroup.removeView(root);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(root,layoutParams);
        //开启特效
        bouncingView.show(new jiekouhuidiao() {
            @Override
            public void istrue(boolean b) {
                if(b){
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    public  void remoview() {

        ObjectAnimator translationX = new ObjectAnimator().ofFloat(root,"translationX",0,800f);

        translationX.start();

        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(root);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });









    }
}
