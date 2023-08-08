package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.model.ShipmentWrapper
import pl.inpost.recruitmenttask.presentation.adapters.ShipmentWrapperAdapter

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var _binding: FragmentShipmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ShipmentWrapperAdapter

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
        setSwipeRefresh()
        collectData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val onItemClickListener = View.OnClickListener { itemView ->
            val shipment = itemView.tag as ShipmentNetwork
            //findNavController().navigate(ShipmentListFragmentDirections.actionShipmentListFragmentToDetailFragment(shipment)) //TODO
        }

        val onContextClickListener = View.OnContextClickListener { true }

        adapter = ShipmentWrapperAdapter(onItemClickListener, onContextClickListener)
        binding.recyclerview.adapter = adapter
    }

    private fun setSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wrappedShipmentsStateFlow.collectLatest {
                    when (it) {
                        is ShipmentUiState.Loading -> displayIsLoading()
                        is ShipmentUiState.Error -> displayError(it.error)
                        is ShipmentUiState.Success -> displayData(it.wrappedShipments)
                    }
                }
            }
        }
    }

    private fun displayIsLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    private fun displayError(e : Exception) {
        binding.swipeRefresh.isRefreshing = false
        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
    }

    private fun displayData(data: List<ShipmentWrapper>) {
        binding.swipeRefresh.isRefreshing = false
        ((binding.recyclerview.adapter) as ShipmentWrapperAdapter).submitList(data)
    }
}
