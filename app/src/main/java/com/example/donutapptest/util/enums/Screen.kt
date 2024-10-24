package com.example.donutapptest.util.enums

enum class Screen(val route: String) {
    LOGIN("login"),
    REGISTER("register"),
    HOME("home");

    fun createRoute(id: String?): String {
        return if (id != null) route.replace("{id}", id) else route
    }
}