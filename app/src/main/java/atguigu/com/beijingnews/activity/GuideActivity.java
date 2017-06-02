package atguigu.com.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import atguigu.com.beijingnews.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @InjectView(R.id.vp)
    ViewPager vp;
    @InjectView(R.id.btn_start_main)
    Button btnStartMain;
    @InjectView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @InjectView(R.id.activity_guide)
    RelativeLayout activityGuide;
    @InjectView(R.id.iv_point_rad)
    ImageView ivPointRad;
    private ArrayList<ImageView> imageViews;
    private int[] ins = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    //两个灰色点之间的间距
    private int leftMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        //添加数据
        initData();
        //设置适配器
        vp.setAdapter(new MyPagerAdapter());
        //Viewpager的位置监听
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        //计算两点的距离
        ivPointRad.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //得到两次数据，消耗性能，进来就移除，值得到一次
                ivPointRad.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //间距
                leftMargin =  llPointGroup.getChildAt(1).getLeft()-llPointGroup.getChildAt(0).getLeft();

            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当滑到的时候回调
         *
         * @param position             当前滑动的页面的下标位置
         * @param positionOffset       百分比
         * @param positionOffsetPixels 滑动的像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //红点移动的距离： 间距 = ViewPager滑动的百分比
            //红点移动的距离 = 间距*ViewPager滑动的百分比
            float left = leftMargin*positionOffset+leftMargin*position;
            //设置红点的移动，用参数
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivPointRad.getLayoutParams();
            params.leftMargin = (int) left;

            ivPointRad.setLayoutParams(params);

        }

        /**
         * 当选中某个页面的时候回调
         * 在这里设置按钮的隐藏显示
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size() - 1) {
                btnStartMain.setVisibility(View.VISIBLE);
            } else {
                btnStartMain.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < ins.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(ins[i]);
            imageViews.add(iv);

            //添加灰色点
            ImageView iv1 = new ImageView(this);
            iv1.setImageResource(R.drawable.point_nomal);
            //视图的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            //设置添加视图的参数
            iv1.setLayoutParams(params);
            //每个视图的距离
            if (i != 0) {
                params.leftMargin = 10;
            }
            //把视图添加到线性布局中
            llPointGroup.addView(iv1);
        }

    }

    @OnClick(R.id.btn_start_main)
    public void onViewClicked() {

    }
}
