package com.polihov.restaurantshell

object RestaurantRepository {
    fun getRestaurants(): List<Restaurant> = listOf(
        Restaurant(
            id = 1,
            name = "Прожарим",
            url = "https://mantrova-studio.github.io/prozharim/",
            logoResId = R.drawable.logo_projarim
        ),
        Restaurant(
            id = 2,
            name = "Сушидза",
            url = "https://xn--80ahjc8bm9a.xn--p1ai/",
            logoResId = R.drawable.logo_sushidza
        ),
        Restaurant(
            id = 3,
            name = "Банзай",
            url = "https://xn----8sbac5acm3a5au9c.xn--p1ai/",
            logoResId = R.drawable.logo_banzai
        ),
        Restaurant(
            id = 4,
            name = "4Руки",
            url = "https://xn--4-ptbfxp.xn--p1ai/",
            logoResId = R.drawable.logo_rest4
        ),
        Restaurant(
            id = 5,
            name = "Чудо-Пицца",
            url = "https://xn----8sbkr2ahy3aac.xn--p1ai/",
            logoResId = R.drawable.logo_rest5
        ),
        Restaurant(
            id = 6,
            name = "Лососнем",
            url = "https://lososnem.ru/",
            logoResId = R.drawable.logo_rest6
        ),
        Restaurant(
            id = 7,
            name = "Самурай",
            url = "https://samuraj56.ru/",
            logoResId = R.drawable.logo_rest7
        ),
        Restaurant(
            id = 8,
            name = "Сытый Япоша",
            url = "https://yaposha56.ru/",
            logoResId = R.drawable.logo_rest8
        )
    )

    fun getRestaurantById(id: Int): Restaurant? {
        return getRestaurants().find { it.id == id }
    }
}
