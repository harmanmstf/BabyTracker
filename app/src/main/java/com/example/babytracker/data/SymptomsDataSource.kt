package com.example.babytracker.data

import com.example.babytracker.R
import com.example.babytracker.model.SymptomsDetail

class SymptomsDataSource {
    fun loadSymptoms(): List<SymptomsDetail> {
        return listOf(
            SymptomsDetail(R.drawable.img_runny_nose, R.string.runny_nose),
            SymptomsDetail(R.drawable.img_heartburn, R.string.heartburn),
            SymptomsDetail(R.drawable.img_noappetite, R.string.no_appetite),
            SymptomsDetail(R.drawable.img_rush, R.string.rush),
            SymptomsDetail(R.drawable.img_lowenegy, R.string.low_energy),
            SymptomsDetail(R.drawable.img_nausea, R.string.nausea),
            SymptomsDetail(R.drawable.img_cough, R.string.cough),
            SymptomsDetail(R.drawable.img_fever, R.string.fever),
        )
    }
}