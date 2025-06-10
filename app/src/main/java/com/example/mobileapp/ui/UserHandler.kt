package com.example.mobileapp.ui

interface UserHandler<data> {
    fun handleChange(details: data)
}