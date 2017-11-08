package dev.momo.library.core.data.recycler;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Recycler ViewHolder
 * that has item clickable action
 * <p>
 * Updated by Peng on 2017/10/26.
 */
public abstract class ObjectViewHolder<T> extends RecyclerView.ViewHolder {

    public static final String TAG = ObjectViewHolder.class.getSimpleName();

    private OnItemClickListener<T> clickListener;
    private OnItemLongClickListener<T> longClickListener;

    protected int index;
    protected T object;

    public ObjectViewHolder(final View itemView) {
        super(itemView);
        setClickableItem(itemView);
        setLongClickableItem(itemView);
    }

    /**
     * Override this method to setup when object binding
     *
     * @param position
     * @param object
     */
    @CallSuper
    protected void onBind(int position, T object) {
        this.index = position;
        this.object = object;
    }


    final protected View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (clickListener == null) return;
            clickListener.onItemClick(ObjectViewHolder.this, index, object);
        }
    };

    final protected View.OnLongClickListener longListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (longClickListener == null) return true;
            longClickListener.onItemLongClick(ObjectViewHolder.this, index, object);
            return false;
        }
    };


    /**
     * Use whole item view as default clickable range,
     * Override this to change clickable item view like this,
     * - customView.setOnClickListener(listener);
     * or set at viewHolder adapter
     * - viewHolder.setClickableItem(viewHolder.getView());
     * <p>
     * @param view clickable item view
     */
    public void setClickableItem(View view) {
        view.setOnClickListener(listener);
    }


    public void setLongClickableItem(View view) {
        view.setOnLongClickListener(longListener);
    }

    /**
     * Do the same thing as above.
     *
     * But it's strange to set ViewHolder.itemView to setOnClickListener will cause NPE,
     * so parse view inside method instead. Try to FIXME
     *
     * @return clickable view
     */
    @Deprecated
    protected View getClickableItemView() {
        return itemView;
    }

    @Deprecated
    public void setClickableItem() {
        getClickableItemView().setOnClickListener(listener);
    }

    @Deprecated
    public void setLongClickableItem() {
        getClickableItemView().setOnLongClickListener(longListener);
    }


    /**
     * Use this method to set ViewHolder to listen on item click or long click listener
     *
     * @param listener OnItemClickListener or OnItemLongClickListener
     * @return this
     */
    public ObjectViewHolder<T> listen(OnItemClickListener<T> listener) {
        this.clickListener = listener;
        return this;
    }

    public ObjectViewHolder<T> listen(OnItemLongClickListener<T> listener) {
        this.longClickListener = listener;
        return this;
    }


    /**
     * transition
     *
     * @return transaction target (null as default)
     */
    public
    @Nullable
    View getTransactionTarget() {
        return null;
    }

}
