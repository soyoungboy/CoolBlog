package com.xuie.coolblog.ui.fragment.blog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuie.coolblog.R;
import com.xuie.coolblog.utils.ImageLoaderUtil;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    Context context;
    private List<BlogBean> blogList;
    private boolean showFooter = true;

    private OnItemClickListener onItemClickListener;

    public BlogAdapter(Context context) {
        this.context = context;
    }

    public void setBlogList(List<BlogBean> blogList) {
        this.blogList = blogList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!showFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item, parent, false);
            return new ItemViewHolder(v);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            BlogBean blog = blogList.get(position);
            if (blog == null) {
                return;
            }

            ((ItemViewHolder) holder).mTitle.setText(blog.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(blog.getDescription());
            if (blog.getImgSrc() != null) {
                ImageLoaderUtil.display(context, ((ItemViewHolder) holder).mImg, blog.getImgSrc());
            } else {
                ((ItemViewHolder) holder).mImg.setImageResource(blog.getImgId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int begin = showFooter ? 1 : 0;
        if (blogList == null) {
            return begin;
        }
        return blogList.size() + begin;
    }

    public BlogBean getItem(int position) {
        return blogList == null ? null : blogList.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.showFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mDesc;
        public ImageView mImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc = (TextView) v.findViewById(R.id.desc);
            mImg = (ImageView) v.findViewById(R.id.img);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, this.getAdapterPosition());
            }
        }
    }
}
