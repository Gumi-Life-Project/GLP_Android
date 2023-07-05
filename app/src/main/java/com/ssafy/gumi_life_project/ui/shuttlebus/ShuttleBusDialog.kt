package com.ssafy.gumi_life_project.ui.shuttlebus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.databinding.DialogShuttleBusBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView


class ShuttleBusDialog(context: Context, private val shuttleBusStop: ShuttleBusStop) :
    DialogFragment() {
    private val viewModel by activityViewModels<ShuttleBusViewModel>()
    private lateinit var binding: DialogShuttleBusBinding
    private lateinit var mapView: MapView
    private val MARKER_NAME = "탑승위치"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogShuttleBusBinding.inflate(inflater, container, false)
        binding.shuttleBusStop = shuttleBusStop
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        try {
            val parentWidth = resources.displayMetrics.widthPixels
            val size = parentWidth - (parentWidth / 6)
            dialog?.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_all_corners)
            setMap()
            setListener()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun setMap() {
        mapView = MapView(context)
        mapView.setMapCenterPoint(
            mapPointWithGeoCoord(
                shuttleBusStop.latitude,
                shuttleBusStop.longitude
            ), true
        )
        mapView.setZoomLevel(1, true)

        val marker = MapPOIItem()
        marker.apply {
            itemName = MARKER_NAME
            tag = 0
            mapPoint = mapPointWithGeoCoord(shuttleBusStop.latitude, shuttleBusStop.longitude)
            markerType = MapPOIItem.MarkerType.RedPin
        }

        mapView.addPOIItem(marker)

        binding.mapView.addView(mapView)
    }

    private fun setListener() {
        binding.buttonMark.setOnClickListener {
            viewModel.updateShuttleBusLineList(shuttleBusStop)
        }
    }
}