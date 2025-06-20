package com.example.donutapptest.utils.enums

enum class Screens(val route: String) {
    LOGIN("login"), REGISTER("register"), HOME("home"), FAVORITES("favorites"), CART("cart");

    fun createRoute(id: String?): String {
        return if (id != null) route.replace("{id}", id) else route
    }
}