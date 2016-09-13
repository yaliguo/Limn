package my.phoenix.limn.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import my.phoenix.limn.R;
import pojo.Gank;
import view.MeiImage;

/**
 * by phoneix
 */
public class HomeContentAdapter  extends RecyclerView.Adapter<HomeContentAdapter.HomeContetHolder>{
    private List<Gank> mDatas;
    private Context mContext;
    public HomeContentAdapter(Context context,List<Gank> mDatas) {
        this.mDatas = mDatas;
        this.mContext=context;
    }

    @Override
    public HomeContetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new HomeContetHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_content, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeContetHolder holder, int position) {
        holder.mContentText.setText(mDatas.get(position).desc!=null?mDatas.get(position).desc:"");
        Glide
                .with(mContext)
                .load(mDatas.get(position).url)
                .centerCrop()
                .crossFade()
                .into(holder.mContentImg)
        .getSize(new SizeReadyCallback() {
            @Override
            public void onSizeReady(int width, int height) {
//                if (!holder.card.isShown()) {
//                    holder.card.setVisibility(View.VISIBLE);
//                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class HomeContetHolder extends RecyclerView.ViewHolder{
        View card;

        private final MeiImage mContentImg;
        private final TextView mContentText;

        public HomeContetHolder(View itemView) {
            super(itemView);
            card = itemView;
            mContentImg = (MeiImage) itemView.findViewById(R.id.item_home_img);
            mContentText = (TextView) itemView.findViewById(R.id.item_home_text);
            mContentImg.setOriginalSize(50,(int)(50+Math.random()*10));

        }
    }
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

        }
    }
}
