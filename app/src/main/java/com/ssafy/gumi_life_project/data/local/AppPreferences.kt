package com.ssafy.gumi_life_project.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import java.util.*

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val SHUTTLE_BUS_INFO_PREFERENCE = "shuttle_bus_info_preference"

    const val APP_RUN_STATE = "app_run_state"
    private const val SHUTTLE_BUS_INFO = "shuttle_bus_info"
    private const val SHUTTLE_BUS_STOP_MARKED = "shuttle_bus_stop_marked"

    private lateinit var preferences: SharedPreferences
    private val gson = GsonBuilder().create()

    fun openSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun openShuttleBusSharedPreference(context: Context): SharedPreferences {
        preferences =
            context.getSharedPreferences(SHUTTLE_BUS_INFO_PREFERENCE, Context.MODE_PRIVATE)
        return preferences
    }

    fun updateAppRunState() {//앱 최초 실행이 아님을 표시
        preferences.edit().putBoolean(APP_RUN_STATE, false).commit()
    }

    fun initShuttleBusInfo() {//셔틀 정보 초기화
        val busLineList = mutableListOf<ShuttleBusLine>()

        val gumiLine = mutableListOf<ShuttleBusStop>()
        gumiLine.add(
            ShuttleBusStop(
                "구미역(파리바게트 앞)",
                36.128713,
                128.331697,
                "07:50",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "오성예식장 앞",
                36.121197,
                128.351761,
                "07:55",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "구미상공회의소 건너 승강장",
                36.117810,
                128.351508,
                "07:59",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "형곡동 파리바게트 앞",
                36.113729,
                128.339023,
                "08:03",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "사곡 보성1차(쪽쪽갈비 앞)",
                36.093881,
                128.355490,
                "08:15",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "우방신세계 2차(상모우방2단지 정류장)",
                36.083475,
                128.357088,
                "08:20",
                false
            )
        )
        gumiLine.add(
            ShuttleBusStop(
                "코오롱하늘채(정류장 지나 건널목)",
                36.086592,
                128.365786,
                "08:23",
                false
            )
        )
        busLineList.add(ShuttleBusLine("구미 노선", gumiLine))

        val daeguJungbuLine = mutableListOf<ShuttleBusStop>()
        daeguJungbuLine.add(
            ShuttleBusStop(
                "범어역 1번출구(설명한의원 앞)",
                35.859618,
                128.622844,
                "07:10",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "반월당역 18번출구(현대백화점 앞)",
                35.866139,
                128.590855,
                "07:15",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "콘서트하우스 좌회전 후 고가 전 책방(수석) 앞 펜스 뚫린 곳",
                35.875665,
                128.595076,
                "07:22",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "침산코오롱하늘채 건너(정류장)",
                35.885306,
                128.597676,
                "07:26",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "구평영무예다음2차(버스정류장)",
                36.092826,
                128.446125,
                "08:16",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "인동고등학교(입구건너 정류장)",
                36.094488,
                128.433352,
                "08:20",
                false
            )
        )
        daeguJungbuLine.add(
            ShuttleBusStop(
                "청구아파트 버스정류장(진평동)",
                36.098667,
                128.426789,
                "08:22",
                false
            )
        )
        busLineList.add(ShuttleBusLine("대구 중부 노선", daeguJungbuLine))

        val daeguSeobuLine = mutableListOf<ShuttleBusStop>()
        daeguSeobuLine.add(
            ShuttleBusStop(
                "진천역 2번출구(입구 계단 앞)",
                35.813655,
                128.522329,
                "07:10",
                false
            )
        )
        daeguSeobuLine.add(
            ShuttleBusStop(
                "상인역 8번 출구(미미관 마라탕 앞)",
                35.819721,
                128.537256,
                "07:15",
                false
            )
        )
        daeguSeobuLine.add(
            ShuttleBusStop(
                "본리네거리(덕인초등학교 건너 버스정류장 앞)",
                35.838872,
                128.537226,
                "07:25",
                false
            )
        )
        daeguSeobuLine.add(
            ShuttleBusStop(
                "죽전네거리(등교:광장갈비앞, 하교:죽전네거리 버스정류장)",
                35.851443,
                128.537527,
                "07:31",
                false
            )
        )
        daeguSeobuLine.add(
            ShuttleBusStop(
                "갑을네거리(타이어뱅크)",
                35.868880,
                128.537872,
                "07:40",
                false
            )
        )
        busLineList.add(ShuttleBusLine("대구 서부 노선", daeguSeobuLine))

        val shijiChilgokLine = mutableListOf<ShuttleBusStop>()
        shijiChilgokLine.add(
            ShuttleBusStop(
                "신매역 1번 출구",
                35.841125,
                128.704643,
                "06:50",
                false
            )
        )
        shijiChilgokLine.add(
            ShuttleBusStop(
                "동대구역(신세계백화점 앞)",
                35.877213,
                128.627977,
                "07:05",
                false
            )
        )
        shijiChilgokLine.add(
            ShuttleBusStop(
                "칠곡 운암역(1번 출구 대각선건너 국토정보공사 앞)",
                35.932376,
                128.555401,
                "07:39",
                false
            )
        )
        shijiChilgokLine.add(
            ShuttleBusStop(
                "홈플러스 칠곡점 건너(버스정류장 지나 인도)",
                35.945756,
                128.555990,
                "07:44",
                false
            )
        )
        busLineList.add(ShuttleBusLine("시지 칠곡 노선", shijiChilgokLine))

        val shuttleBusInfo = gson.toJson(busLineList, ArrayList::class.java)
        preferences.edit().putString(SHUTTLE_BUS_INFO, shuttleBusInfo).commit()
        updateShuttleBusStopMark(ShuttleBusStop("", 0.0, 0.0, "", false))
    }

    fun getShuttleBusInfo(): ArrayList<ShuttleBusLine> {
        val jsonData = preferences.getString(SHUTTLE_BUS_INFO, "")
        val lineListType = object : TypeToken<ArrayList<ShuttleBusLine>>() {}.type

        return gson.fromJson(jsonData, lineListType)
    }

    fun updateShuttleBusInfo(busLineList: MutableList<ShuttleBusLine>) {
        preferences.edit().remove(SHUTTLE_BUS_INFO)
        val newShuttleBusInfo = gson.toJson(busLineList, MutableList::class.java)
        preferences.edit().putString(SHUTTLE_BUS_INFO, newShuttleBusInfo).commit()
    }

    fun updateShuttleBusStopMark(shuttleBusStopMark: ShuttleBusStop) {
        val shuttleBusStopMarkJson = gson.toJson(shuttleBusStopMark, ShuttleBusStop::class.java)
        preferences.edit().putString(SHUTTLE_BUS_STOP_MARKED, shuttleBusStopMarkJson).commit()
    }

    fun getShuttleBusStopMark(): ShuttleBusStop {
        val jsonData = preferences.getString(SHUTTLE_BUS_STOP_MARKED, "")
        val shuttleBusStopType = object : TypeToken<ShuttleBusStop>() {}.type

        return gson.fromJson(jsonData, shuttleBusStopType)
    }

}