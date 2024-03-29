package com.nightwolf.crm_test.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.adapter.FeedAdapter
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.bean.FeedBean
import com.nightwolf.crm_test.databinding.FragmentFeedLinelayoutBinding
import com.nightwolf.crm_test.ui.viewModel.FeedViewModel

/**
 * @我的界面
 */
class FeedFragment : BaseFragment<FeedViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_feed_linelayout
    }

    lateinit var binding: FragmentFeedLinelayoutBinding

    val viewModel by viewModels<FeedViewModel>()

    val list = mutableListOf<FeedBean.BodyBean.AtListBean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedLinelayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = FeedAdapter(list)

        viewModel.getFeedBack().observe(this, Observer {
            list.clear()
            //没有登录时
            list.addAll(it ?: listOf())
            binding.recyclerView.adapter!!.notifyDataSetChanged()
        })

    }

    override fun initData() {

    }

    override fun getViewModelClass(): Class<FeedViewModel> {
        return FeedViewModel::class.java
    }

}