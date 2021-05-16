package com.put.pt.poltext.navigator

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.put.pt.poltext.R

class NavigationRVAdapter(private var items: ArrayList<NavigationItemModel>, private var currentPos: Int) :
    RecyclerView.Adapter<NavigationRVAdapter.NavigationItemViewHolder>() {

    private lateinit var context: Context

    class NavigationItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconView = view.findViewById(R.id.navigation_icon) as ImageView
        val titleText = view.findViewById(R.id.navigation_title) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        context = parent.context
        val navItem = LayoutInflater.from(parent.context).inflate(R.layout.row_nav_drawer, parent, false)
        return NavigationItemViewHolder(navItem)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        if (position == currentPos) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.baby_blue))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }

        holder.iconView.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
        holder.titleText.setTextColor(Color.DKGRAY)
        holder.titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.toFloat())
        holder.titleText.text = items[position].title
        holder.iconView.setImageResource(items[position].icon)
    }
}