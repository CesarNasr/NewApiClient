package com.example.newapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.example.newapiclient.databinding.FragmentNewsBinding
import com.example.newapiclient.presentation.adapters.NewsAdapter
import com.example.newapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        newsViewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }
        initRecyclerView()
        viewNewsList()

        setSearchView()
    }

    private fun initRecyclerView() {
//        newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }


    }


    private fun viewNewsList() {
        newsViewModel.getNewsHeadlines(country, page)
        newsViewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is com.example.newapiclient.data.util.Resource.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())

                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = (it.totalResults / 20) + 1
                        }
                        isLastPage = page == pages
                    }

                }
                is com.example.newapiclient.data.util.Resource.Error -> {
                    hideProgressbar()
                    response.message.let {
                        Toast.makeText(activity, "Error occured : $it", Toast.LENGTH_LONG).show()
                    }
                }

                is com.example.newapiclient.data.util.Resource.Loading -> {
                    viewProgressbar()
                }
            }
        })
    }

    private fun hideProgressbar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = INVISIBLE
    }

    private fun viewProgressbar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = VISIBLE
    }


    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList

            val shoudPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shoudPaginate) {
                page++
                newsViewModel.getNewsHeadlines(country, page)
                isScrolling = false
            }


        }
    }


    //search
    private fun setSearchView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                newsViewModel.getSearchedNews("us", query.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    newsViewModel.getSearchedNews("us", newText.toString(), page)
                    viewSearchedNews()
                }

                return false

            }

        })



        fragmentNewsBinding.svNews.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                initRecyclerView()
                viewNewsList()
                return false
            }
        })
    }


    fun viewSearchedNews() {
        newsViewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is com.example.newapiclient.data.util.Resource.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())

                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = (it.totalResults / 20) + 1
                        }
                        isLastPage = page == pages
                    }

                }
                is com.example.newapiclient.data.util.Resource.Error -> {
                    hideProgressbar()
                    response.message.let {
                        Toast.makeText(activity, "Error occured : $it", Toast.LENGTH_LONG).show()
                    }
                }

                is com.example.newapiclient.data.util.Resource.Loading -> {
                    viewProgressbar()
                }
            }
        })

    }
}