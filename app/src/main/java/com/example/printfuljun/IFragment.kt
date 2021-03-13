package com.example.printfuljun

import android.view.View

interface IFragment {
    fun getBasicRes()
    fun initializeAddCont(view: View)
    fun initializeLisForAddCont()
    fun initializeCoreAddCont()
    fun initializeLisForCoreAddCont()
}