package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.presentation.adapters.ShipmentsAdapter

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var _binding: FragmentShipmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ShipmentsAdapter
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefresh.isRefreshing = true
        viewModel.refreshData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shipment_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setData()
        setSwipeRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val onItemClickListener = View.OnClickListener { itemView ->
            val shipment = itemView.tag as ShipmentNetwork
            //findNavController().navigate(HomepageFragmentDirections.actionHomepageFragmentToDetailFragment(shipment)) //TODO
        }

        val onContextClickListener = View.OnContextClickListener { true }

        adapter = ShipmentsAdapter(onItemClickListener, onContextClickListener)
        binding.recyclerview.adapter = adapter
    }

    private fun setSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(onRefreshListener)
    }

    private fun setData() {
        viewModel.viewState.observe(requireActivity()) { shipments ->
            ((binding.recyclerview.adapter) as ShipmentsAdapter).submitList(shipments)
            binding.swipeRefresh.isRefreshing = false
        }

    }
}
