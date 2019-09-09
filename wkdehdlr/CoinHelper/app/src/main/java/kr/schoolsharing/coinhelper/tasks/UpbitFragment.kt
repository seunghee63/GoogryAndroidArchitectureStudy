package kr.schoolsharing.coinhelper.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.schoolsharing.coinhelper.R
import kr.schoolsharing.coinhelper.data.Repository
import kr.schoolsharing.coinhelper.databinding.FragmentCoinrecyclerBinding
import kr.schoolsharing.coinhelper.model.UpbitItem

class UpbitFragment : Fragment(), UpbitContract.View {

    override lateinit var presenter: UpbitContract.Presenter
    private lateinit var binding: FragmentCoinrecyclerBinding

    private val rVAdapter = UpbitRVAdapter()

    override fun showAddTask(tickerList: List<UpbitItem>) {
        rVAdapter.setTickerList(tickerList)
    }

    override fun showErrorToast(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_coinrecycler,
            container,
            false
        )
        return binding.root
    }
    

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.rvCoin) {
            adapter = rVAdapter
            layoutManager = LinearLayoutManager(context!!)
            setHasFixedSize(true)
        }

        //TODO : MARKET_NAME enum으로 빼자
        val marketName = arguments?.get("MARKET_NAME").toString()
        presenter = UpbitPresenter(Repository, this)
        presenter.start(marketName)
    }

    companion object {
        fun newInstance(marketName: String): UpbitFragment {
            val fragment = UpbitFragment()
            val bundle = Bundle()

            bundle.putString("MARKET_NAME", marketName)
            fragment.arguments = bundle
            return fragment
        }
    }

}