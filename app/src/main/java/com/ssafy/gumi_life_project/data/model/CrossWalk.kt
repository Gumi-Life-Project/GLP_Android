package com.ssafy.gumi_life_project.data.model

import com.ssafy.gumi_life_project.R

data class CrossWalk(val time: String)

enum class SignalLight(val titleResId: Int, val contentResId: Int) {
    SIGNAL_LIGHT_1(R.string.cross_walk_title1, R.string.cross_walk_explain1),
    SIGNAL_LIGHT_2(R.string.cross_walk_title2, R.string.cross_walk_explain2),
    SIGNAL_LIGHT_3(R.string.cross_walk_title3, R.string.cross_walk_explain3)
}