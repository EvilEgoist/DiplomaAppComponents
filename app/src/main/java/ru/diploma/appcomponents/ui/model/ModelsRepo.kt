package ru.diploma.appcomponents.ui.model


val Emerald = 0xFF5dbfa6
val Cyan = 0xFF5fbccb
val Orange = 0xFFFF7518
val yellow = 0xFFFFB300


object ModelsRepo {

    private val listOfCards = listOf<CardModel>(
        CardModel(
            EntertainmentModel(
                EntertainmentType.RESTAURANT,
                "13:30",
                "March 14",
                "Restaurant booking",
                "https://cdn.dribbble.com/users/3658/screenshots/3198819/reyes_800x600-01.png",
                "Retro kitchen",
                "Sennaya Square, h.4",
                "Крутой ресторант ретро-кухни"
            ),
            Cyan,
            Emerald
        ),
        CardModel(
            EntertainmentModel(
                EntertainmentType.MUSEUM,
                "15:30",
                "April 24",
                "Buy a ticket",
                "https://optic-dias.ru/wp-content/uploads/2018/09/news_miof2.png",
                "MIOF Московская международная оптическая выставка",
                "Kazanskaya Square, h.4",
                "Крутой ресторант ретро-кухни"
            ),
            Orange,
            yellow
        ),

        CardModel(
            EntertainmentModel(
                EntertainmentType.MUSEUM,
                "11:00",
                "April 2",
                "Museum visit",
                "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/a3577213779187.562781c3c496e.jpg",
                "The State Tretyakov Gallery",
                "Lavrushinsky Ln, 10, стр. 1",
                "Один из лучших музеев России"
            ),
            0xFFF44336,
            0xFF4CAF50
        ),
        CardModel(
            EntertainmentModel(
                EntertainmentType.PARK,
                "15:00",
                "May 7",
                "Park walk",
                "https://findquiz.ru/location_img/264/main.jpg",
                "Gorky Park",
                "Krymsky Val, 9",
                "Один из крупнейших парков Европы"
            ),
            0xFF673AB7,
            0xFF009688
        ),
        CardModel(
            EntertainmentModel(
                EntertainmentType.THEATER,
                "19:00",
                "June 20",
                "Theater performance",
                "https://i.pinimg.com/736x/b8/f1/c3/b8f1c3ee6febb5a55e939bef87b4de10--bolshoi-ballet-things-to-do.jpg",
                "Bolshoi Theater",
                "Theatre Square, 1",
                "Известнейший театр России"
            ),
            0xFF0097A7,
            0xFFFFC107
        ),
        CardModel(
            EntertainmentModel(
                EntertainmentType.OTHER,
                "14:00",
                "August 15",
                "Aquarium visit",
                "https://storage.theoryandpractice.ru/tnp/uploads/image_logo/000/038/590/image/1a9756208e.png",
                "Moscow Oceanarium",
                "Prospekt Mira, 119",
                "Один из крупнейших океанариумов России"
            ),
            0xFFFF9800,
            0xFF2196F3
        ),
    )

    fun getCards(): List<CardModel> = listOfCards
}