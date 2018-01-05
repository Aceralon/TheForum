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
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.acera.theforum.Adapter.RecyclerAdapter
import com.example.acera.theforum.Adapter.ViewHolder
import com.example.acera.theforum.Model.Json
import kotlinx.android.synthetic.main.activity_the_forum.*
import kotlinx.android.synthetic.main.app_bar_the_forum.*
import kotlinx.android.synthetic.main.content_the_forum.*
import java.lang.Exception
import java.util.*

class TheForum : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener
{

    private val postList = LinkedList<Json.Post>()
    private var recyclerAdapter: RecyclerAdapter<Json.Post>? = null
    private val layoutManager = LinearLayoutManager(this)
    private var startItem: MenuItem? = null

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

        navView.setNavigationItemSelectedListener(this)

        initRecyclerView()
        initRefresh()

        startItem = navView.menu.add(0, Menu.FIRST, 100, "Start")
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
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                //goto the post detail
                val myIntent = Intent(this@TheForum, PostActivity::class.java)
                myIntent.putExtra(getString(R.string.goto_post_detail), postList[position])
                startActivity(myIntent)
            }

            override fun onLongClick(view: View, position: Int)
            {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                //see if need long click or just do nothing
            }
        })

        mainPostView.adapter = recyclerAdapter
        mainPostView.layoutManager = layoutManager
        mainPostView.itemAnimator = DefaultItemAnimator()
//        mainPostView.scroll
        mainPostView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int)
            {
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if (!recyclerView!!.canScrollVertically(1))
                    {
                        Toast.makeText(this@TheForum, "End to Load", Toast.LENGTH_SHORT).show()
                        //加载更多功能的代码
                        //TODO(Load more posts)
                        //loadTopic(currentPage + 1, false)
                    }
                    //获取最后一个完全显示的ItemPosition
//                    val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
//                    val totalItemCount = layoutManager.getItemCount()
//                    // 判断是否滚动到底部，并且是向右滚动
//                    if (lastVisibleItem >= totalItemCount - 5)
//                    {//&& enableScrollListener && currentPage < totalPage
//
//                    }
                }
                //修正ripple效果
                try
                {
                    val firstPosition = layoutManager.findFirstVisibleItemPosition()
                    mainPostRefresh.isEnabled = (firstPosition == 0)
                    Log.i("refresh", "State - $newState : firstVisiblePosition$firstPosition")
                    Log.i("refresh", "enable: ${mainPostRefresh.isEnabled}")
                } catch (e: Exception)
                {
                    e.printStackTrace()
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })




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
        //TODO("not implemented")
        // To change body of created functions use File | Settings | File Templates.
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
//        menuInflater.inflate(R.menu.the_forum, menu)
        val item = menu.add(0, Menu.FIRST, 100, "Start")

//        menu.add(0, Menu.FIRST, 10, "Hi")
        item.icon = getDrawable(R.drawable.ic_post_person)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId)
        {
            R.id.action_settings ->
            {
                val myIntent = Intent(this, SettingsActivity::class.java)
                startActivity(myIntent)
                true
            }
            R.id.action_test ->
            {
                Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
                Toast.makeText(this, "camera", Toast.LENGTH_LONG).show()
            }
            R.id.nav_gallery ->
            {
                Toast.makeText(this, "gallery", Toast.LENGTH_LONG).show()
            }
            R.id.nav_slideshow ->
            {
                Toast.makeText(this, "slideshow", Toast.LENGTH_LONG).show()
            }
            R.id.nav_manage ->
            {
                Toast.makeText(this, "manage", Toast.LENGTH_LONG).show()

            }
            R.id.nav_register ->
            {
                val myIntent = Intent(this, RegisterActivity::class.java)
                startActivity(myIntent)
                Toast.makeText(this, "register", Toast.LENGTH_LONG).show()
            }
            R.id.nav_login ->
            {
                Toast.makeText(this, "login", Toast.LENGTH_LONG).show()
                val myIntent = Intent(this@TheForum, LoginActivity::class.java)
                startActivityForResult(myIntent, resources.getInteger(R.integer.request_login))
            }
            startItem!!.itemId ->
            {
                Toast.makeText(this, "start", Toast.LENGTH_LONG).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        when (requestCode)
        {
            resources.getInteger(R.integer.request_login) ->
            {
                when (resultCode)
                {
                    resources.getInteger(R.integer.login_sucess) ->
                    {

                    }
                    resources.getInteger(R.integer.login_failed) ->
                    {

                    }
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}
