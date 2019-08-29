package com.lizhehan.wanandroid.ui.collect.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.data.bean.CollectBean;

import java.util.List;

public class CollectRecyclerViewAdapter extends BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder> {

    public CollectRecyclerViewAdapter(int layoutResId, @Nullable List<CollectBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectBean.DatasBean item) {
        helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        helper.setImageResource(R.id.image_collect, R.drawable.ic_star);
        helper.addOnClickListener(R.id.image_collect);
    }
}
//public class CollectRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipedListener {
//
//    private Context context;
//    private List<CollectBean.DatasBean> mItems;
//    private View parentView;
//
//    private final int TYPE_NORMAL = 1;
//    private final int TYPE_FOOTER = 2;
//    private final String FOOTER = "footer";
//
//    public CollectRecyclerViewAdapter(Context context) {
//        this.context = context;
//        mItems = new ArrayList<>();
//    }
//
//    public void setItems(List<CollectBean.DatasBean> data) {
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mItems, data), true);
//        diffResult.dispatchUpdatesTo(this);
//        mItems.clear();
//        mItems.addAll(data);
//    }
//
//    public void addItem(int position, CollectBean.DatasBean insertData) {
//        mItems.add(position, insertData);
//        notifyItemRangeInserted(position, 1);
//    }
//
//    public void addFooter() {
//        mItems.add(null);
//        notifyItemRangeInserted(mItems.size() - 1, 1);
//    }
//
//    public void removeFooter() {
//        mItems.remove(mItems.size() - 1);
//        notifyItemRangeRemoved(mItems.size() - 1, 1);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        parentView = parent;
//        if (viewType == TYPE_NORMAL) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
//            return new RecyclerViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
//            return new FooterViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if (holder instanceof RecyclerViewHolder) {
//            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//            CollectBean.DatasBean item = mItems.get(position);
//            recyclerViewHolder.mTvTag.setVisibility(View.GONE);
//            recyclerViewHolder.mTvContent.setText(item.getTitle());
//            recyclerViewHolder.mTvAuthor.setText(item.getAuthor());
//            recyclerViewHolder.mTvTime.setText(item.getNiceDate());
//            recyclerViewHolder.mTvType.setText(item.getChapterName());
////            recyclerViewHolder.mImageCollect.setImageResource(R.drawable.ic_star);
//            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(ConstantUtil.DETAIL_ID, item.getOriginId());
//                    bundle.putString(ConstantUtil.DETAIL_PATH, item.getLink());
//                    bundle.putString(ConstantUtil.DETAIL_TITLE, item.getTitle());
//                    bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, ConstantUtil.TRUE);
//                    bundle.putString("author", item.getAuthor());
//                    IntentUtil.startActivity(context, WebViewActivity.class, bundle);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//            Bundle payload = (Bundle) payloads.get(0);//取出在getChangePayload（）方法返回的bundle
////            CollectBean.DatasBean bean = mItems.get(position);//取出新数据源，（可以不用）
//            if (holder instanceof RecyclerViewHolder) {
//                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//                for (String key : payload.keySet()) {
//                    switch (key) {
//                        case "KEY_TITLE":
//                            //这里可以用payload里的数据，不过data也是新的 也可以用
//                            recyclerViewHolder.mTvContent.setText(payload.getString(key));
//                            break;
//                        case "KEY_AUTHOR":
//                            recyclerViewHolder.mTvAuthor.setText(payload.getString(key));
//                            break;
//                        case "KEY_NICE_DATE":
//                            recyclerViewHolder.mTvTime.setText(payload.getString(key));
//                            break;
//                        case "KEY_CHAPTER_NAME":
//                            recyclerViewHolder.mTvType.setText(payload.getString(key));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (mItems.get(position) == null) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_NORMAL;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
//
//    @Override
//    public void onItemDismiss(final int position) {
//        CollectBean.DatasBean removeData = mItems.get(position);
//        mItems.remove(position);
//        notifyItemRangeRemoved(position, 1);
//        Snackbar.make(parentView, context.getString(R.string.cancel_collect_success), Snackbar.LENGTH_SHORT)
//                .setAction(context.getString(R.string.undo), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        addItem(position, removeData);
//                    }
//                }).show();
//    }
//
//    class RecyclerViewHolder extends RecyclerView.ViewHolder {
//        private View mView;
//
//        @BindView(R.id.tv_author)
//        TextView mTvAuthor;
//        @BindView(R.id.tv_type)
//        TextView mTvType;
//        @BindView(R.id.tv_content)
//        TextView mTvContent;
//        @BindView(R.id.image_collect)
//        ImageView mImageCollect;
//        @BindView(R.id.tv_time)
//        TextView mTvTime;
//        @BindView(R.id.tv_tag)
//        TextView mTvTag;
//
//        private RecyclerViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    class FooterViewHolder extends RecyclerView.ViewHolder {
//        private ProgressBar progress_bar_load_more;
//
//        private FooterViewHolder(View itemView) {
//            super(itemView);
//            progress_bar_load_more = itemView.findViewById(R.id.progress_bar_load_more);
//        }
//    }
//}