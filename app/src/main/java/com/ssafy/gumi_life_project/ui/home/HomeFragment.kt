package com.ssafy.gumi_life_project.ui.home

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.databinding.FragmentHomeBinding
import com.ssafy.gumi_life_project.ui.home.crosswalk.CrossWalkBottomSheet
import com.ssafy.gumi_life_project.ui.main.LoadingDialog
import com.ssafy.gumi_life_project.ui.main.MainViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    private val viewModel by viewModels<HomeViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()
    private lateinit var randomTip: Tip
    private lateinit var adapter: SimpleBoardAdapter
    private val mealAdapter = MealAdapter()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@HomeFragment.viewModel
            mainViewModel = this@HomeFragment.activityViewModel
        }
    }

    override fun init() {
        initIcons()
        observeData()
        initRecyclerView()
        viewModel.loadAndSetTriggerTimes()

        bindingNonNull.linearlayoutTip.setOnClickListener {
            if(::randomTip.isInitialized) {
                val bottomSheetDialogFragment =
                    TipBottomSheet(randomTip.subject, randomTip.description)
                bottomSheetDialogFragment.show(childFragmentManager, "TipBottomSheet")
            }
        }

        bindingNonNull.linearlayoutSimpleBoard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_boardListFragment)
        }
    }

    private fun initRecyclerView() {
        viewModel.getSimpleBoard()

        adapter = SimpleBoardAdapter()
        adapter.onItemClickListener = object : SimpleBoardAdapter.OnItemClickListener {
            override fun onItemClick(boardItem: BoardItem) {
                findNavController().navigate(R.id.action_homeFragment_to_boardListFragment)
            }
        }
        bindingNonNull.recyclerviewSimple.adapter = adapter
        bindingNonNull.viewPager.adapter = mealAdapter
    }

    private fun initIcons() {
        bindingNonNull.toolBar.imageviewUserIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mypageFragment)
        }
    }

    private fun observeData() {
        with(viewModel) {
            val dialog = LoadingDialog(requireContext())
            isLoading.observe(viewLifecycleOwner) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }

            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }


            showBottomSheetEvent.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { signalLight ->
                    val titleResId = signalLight.titleResId
                    val contentResId = signalLight.contentResId

                    val title = getString(titleResId)
                    val content = getString(contentResId)

                    val bottomSheetDialogFragment =
                        CrossWalkBottomSheet(signalLight, title, content)
                    bottomSheetDialogFragment.show(childFragmentManager, "CrossWorkBottomSheet")
                }
            }

            isRefresh.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    val animatorRotate = ObjectAnimator.ofFloat(bindingNonNull.imageRefresh, "rotation", 0f, 360f)
                    animatorRotate.duration = 1000
                    animatorRotate.start()}
            }

            simpleBoard.observe(viewLifecycleOwner) {
                adapter.setBoardList(it)
            }
        }

        with(activityViewModel) {
            tip.observe(viewLifecycleOwner) { tip ->
                randomTip = getRandomTip(tip)
                bindingNonNull.textviewTipContent.text = randomTip.subject
            }

            weather.observe(viewLifecycleOwner) { weather ->
                bindingNonNull.textviewTodayWeatherTemperature.text = weather.data.temperature + "º"
                makeWeatherIcon(weather.data.precipitationType)

            }

            meal.observe(viewLifecycleOwner) { meal ->
                if(meal.message == "success") {
                    mealAdapter.setMealList(meal.data)
                    bindingNonNull.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            bindingNonNull.textviewPage.text = "(${position + 1}/${meal.data.size})"
                        }
                    })
                }
            }

            shuttleBusStopMark.observe(viewLifecycleOwner) { shuttleBusStopMark ->
                if (shuttleBusStopMark.stopName == "") {
                    bindingNonNull.textviewShuttleBusStopName.visibility = View.GONE
                    bindingNonNull.textviewShuttleBusStopTime.visibility = View.GONE
                    bindingNonNull.textviewShuttleNoMark.visibility = View.VISIBLE
                } else {
                    bindingNonNull.textviewShuttleBusStopName.visibility = View.VISIBLE
                    bindingNonNull.textviewShuttleBusStopTime.visibility = View.VISIBLE
                    bindingNonNull.textviewShuttleNoMark.visibility = View.GONE
                    bindingNonNull.textviewShuttleBusStopName.text = shuttleBusStopMark.stopName
                    bindingNonNull.textviewShuttleBusStopTime.text = shuttleBusStopMark.arrivalTime
                }
                bindingNonNull.linearlayoutShuttleBus.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_shuttleBusFragment)
                }
            }
        }
    }

    private fun getRandomTip(tips: List<Tip>): Tip {
        return if (tips.isNotEmpty()) tips[Random.nextInt(tips.size)] else Tip()
    }
    
    private fun makeWeatherIcon(type: String) {
        when (type) {
            "없음" -> bindingNonNull.imageviewTodayWeatherImg.setImageResource(R.drawable.icon_sunny)
            "비" -> bindingNonNull.imageviewTodayWeatherImg.setImageResource(R.drawable.icon_rainy)
            "눈" -> bindingNonNull.imageviewTodayWeatherImg.setImageResource(R.drawable.icon_snow)
        }
    }
}