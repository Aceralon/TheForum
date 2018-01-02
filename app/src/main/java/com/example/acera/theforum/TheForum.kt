package com.example.acera.theforum

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.acera.theforum.Adapter.RecyclerAdapter
import com.example.acera.theforum.Adapter.ViewHolder
import com.example.acera.theforum.Model.Json
import kotlinx.android.synthetic.main.activity_the_forum.*
import kotlinx.android.synthetic.main.app_bar_the_forum.*
import kotlinx.android.synthetic.main.content_the_forum.*
import java.util.*

class TheForum : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener
{

    private val postList = LinkedList<Json.Post>()
    private var recyclerAdapter: RecyclerAdapter<Json.Post>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_forum)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initRecyclerView()
        initRefresh()
    }

    fun clickHeaderIcon(view: View)
    {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
    }

    private fun initRecyclerView()
    {
        recyclerAdapter = object : RecyclerAdapter<Json.Post>(this, R.layout.main_post_item, postList)
        {
            override fun convert(holder: ViewHolder, t: Json.Post)
            {
                holder.getView<TextView>(R.id.mainPostItemSender).text = t.username
                holder.getView<TextView>(R.id.mainPostItemTitle).text = t.p_title
                holder.getView<TextView>(R.id.mainPostItemContent).text = t.p_content
                holder.getView<TextView>(R.id.mainPostItemTime).text = t.p_datetime
                holder.getView<TextView>(R.id.mainPostItemReply).text = t.p_floor.toString()
            }
        }

        recyclerAdapter!!.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener
        {
            override fun onClick(view: View, position: Int)
            {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onLongClick(view: View, position: Int)
            {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        mainPostView.adapter = recyclerAdapter
        mainPostView.layoutManager = LinearLayoutManager(this)
        mainPostView.itemAnimator = DefaultItemAnimator()


        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))
        postList.add(Json.Post("aa", "bb", "CC", "DD", 0))

        recyclerAdapter!!.notifyItemRangeInserted(0, postList.size)
    }

    private fun initRefresh()
    {
        mainPostRefresh.setColorSchemeResources(
                R.color.material_light_blue_700,
                R.color.material_red_700,
                R.color.material_orange_700,
                R.color.material_light_green_700)
        mainPostRefresh.setOnRefreshListener(this)
    }


    override fun onRefresh()
    {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed()
    {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else
        {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.the_forum, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId)
        {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        // Handle navigation view item clicks here.
        when (item.itemId)
        {
            R.id.nav_camera ->
            {
                // Handle the camera action
            }
            R.id.nav_gallery ->
            {

            }
            R.id.nav_slideshow ->
            {

            }
            R.id.nav_manage ->
            {

            }
            R.id.nav_share ->
            {

            }
            R.id.nav_send ->
            {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
