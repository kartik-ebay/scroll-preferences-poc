package com.krishnanand.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainFragment: Fragment() {

    lateinit var recyclerView: RecyclerView

    lateinit var recyclerAdapter: MainRecyclerAdapter

    lateinit var placeViewModel: PlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        val layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = MainRecyclerAdapter(requireContext(), placeViewModel)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            adapter = recyclerAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, layoutManager.orientation))
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placeViewModel.placesLiveData.observe(requireActivity(), Observer {
            recyclerAdapter.notifyDataSetChanged()
        })
        placeViewModel.fetchPlacesFromJsonFile(requireContext())
    }
}