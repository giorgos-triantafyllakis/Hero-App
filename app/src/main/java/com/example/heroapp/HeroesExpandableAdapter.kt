package com.example.heroapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.lifecycle.LiveData
import com.example.heroapp.databinding.ExpandableListBinding
import com.example.heroapp.network.dataClasses.CharacterRestModel


const val CHILDREN_COUNT = 1 // Each parent item has only one child item

enum class ParentGroup(val parentName: String, val childType: ChildType) {
    COMICS("Comics", ChildType.COMIC),
    STORIES("Stories", ChildType.STORY),
    EVENTS("Events", ChildType.EVENT),
    SERIES("Series", ChildType.SERIES)
}

//enum class for the child
enum class ChildType {
    COMIC,
    STORY,
    EVENT,
    SERIES
}

class HeroesExpandableAdapter(
    private val context: Context,
    private val heroDetailsList: LiveData<List<CharacterRestModel>>
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return ParentGroup.values().size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return CHILDREN_COUNT
    }

    override fun getGroup(groupPosition: Int): Any {
        return when (groupPosition) {
            ParentGroup.COMICS.ordinal -> ParentGroup.COMICS.parentName
            ParentGroup.STORIES.ordinal -> ParentGroup.STORIES.parentName
            ParentGroup.EVENTS.ordinal -> ParentGroup.EVENTS.parentName
            ParentGroup.SERIES.ordinal -> ParentGroup.SERIES.parentName
            else -> ""
        }
    }


    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val heroDetails = heroDetailsList.value?.get(0)
        val group = ParentGroup.values()[groupPosition]
        return getChildString(heroDetails, group.childType)
    }

    private fun getChildString(heroDetails: CharacterRestModel?, childType: ChildType): String {
        return when (childType) {
            ChildType.COMIC -> heroDetails?.comics?.items?.joinToString(", ") { it.name } ?: ""
            ChildType.STORY -> heroDetails?.stories?.items?.joinToString(", ") { it.name } ?: ""
            ChildType.EVENT -> heroDetails?.events?.items?.joinToString(", ") { it.name } ?: ""
            ChildType.SERIES -> heroDetails?.series?.items?.joinToString(", ") { it.name } ?: ""
        }
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val parentBinding: ExpandableListBinding =
            ExpandableListBinding.inflate(LayoutInflater.from(context), parent, false)
        parentBinding.groupTitle.visibility = View.VISIBLE
        parentBinding.groupTitle.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Title)
        parentBinding.groupTitle.text = getGroup(groupPosition).toString()


        return parentBinding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val childBinding: ExpandableListBinding = if (convertView == null) {
            ExpandableListBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ExpandableListBinding.bind(convertView)
        }

        childBinding.groupTitle.visibility = View.VISIBLE
        childBinding.groupTitle.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1)
        childBinding.groupTitle.text = getChild(groupPosition, childPosition).toString()

        return childBinding.root
    }


}