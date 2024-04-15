package com.example.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Adapter.TodoAdapter;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private TodoAdapter adapter;
    public RecyclerItemTouchHelper(TodoAdapter adapter){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder,int direction){
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            adapter.editItem(position);
        }
    }
    @Override
    public void onChildDraw(Canvas c,RecyclerView rc, RecyclerView.ViewHolder vh, float dX, float dY, int actionState, boolean isCurrentActive){
        super.onChildDraw(c,rc,vh,dX,dY,actionState,isCurrentActive);
        ColorDrawable background;
        View itemView = vh.itemView;
        int backgroundCornerOffset =20;

        if(dX>0){
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(),R.color.colorPrimaryDark));
        }else{
            background = new ColorDrawable(Color.RED);
        }

        if(dX>0){
            background.setBounds(itemView.getLeft(),itemView.getTop(),
                    itemView.getLeft()+((int)dX)+backgroundCornerOffset,itemView.getBottom());
        }else if(dX<0){
            background.setBounds(itemView.getRight()+((int)dX)-backgroundCornerOffset,itemView.getTop(),
                    itemView.getRight(),itemView.getBottom());
        }
        else{
            background.setBounds(0,0,0,0);
        }
        background.draw(c);

    }
}
