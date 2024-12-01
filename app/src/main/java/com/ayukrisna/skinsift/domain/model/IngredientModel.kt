package com.ayukrisna.skinsift.domain.model

data class IngredientModel (
    val id: Int,
    val name: String,
    val rating: String,
    val description: String,
    val benefit: String,
    val category: String,
    val key: String,
)

fun getIngredientDummy() : List<IngredientModel>{
    val dummyDictionaryList = listOf(
        IngredientModel(
            id = 1,
            name = "Apple",
            rating = "Terbaik",
            description = "A delicious and healthy fruit.",
            benefit = "Rich in fiber and vitamins.",
            category = "Fruit",
            key = "A1"
        ),
        IngredientModel(
            id = 2,
            name = "Banana",
            rating = "Baik",
            description = "A quick source of energy.",
            benefit = "High in potassium.",
            category = "Fruit",
            key = "B1"
        ),
        IngredientModel(
            id = 3,
            name = "Carrot",
            rating = "Rata-Rata",
            description = "A crunchy and sweet vegetable.",
            benefit = "Good for eye health.",
            category = "Vegetable",
            key = "C1"
        ),
        IngredientModel(
            id = 4,
            name = "Donut",
            rating = "Buruk",
            description = "A tasty but sugary snack.",
            benefit = "Provides instant energy.",
            category = "Snack",
            key = "D1"
        ),
        IngredientModel(
            id = 5,
            name = "Eggplant",
            rating = "Terburuk",
            description = "A versatile vegetable.",
            benefit = "Contains antioxidants.",
            category = "Vegetable",
            key = "E1"
        ),
        IngredientModel(
            id = 6,
            name = "Fig",
            rating = "Terbaik",
            description = "A sweet and nutritious fruit.",
            benefit = "High in calcium and fiber.",
            category = "Fruit",
            key = "F1"
        ),
        IngredientModel(
            id = 7,
            name = "Grapes",
            rating = "Baik",
            description = "A juicy and delicious fruit.",
            benefit = "Rich in antioxidants.",
            category = "Fruit",
            key = "G1"
        ),
        IngredientModel(
            id = 8,
            name = "Honey",
            rating = "Rata-Rata",
            description = "A natural sweetener.",
            benefit = "Has antibacterial properties.",
            category = "Sweetener",
            key = "H1"
        ),
        IngredientModel(
            id = 9,
            name = "Ice Cream",
            rating = "Buruk",
            description = "A cold and creamy dessert.",
            benefit = "Tastes great on a hot day.",
            category = "Dessert",
            key = "I1"
        ),
        IngredientModel(
            id = 10,
            name = "Jackfruit",
            rating = "Terburuk",
            description = "A large and tropical fruit.",
            benefit = "High in vitamin C.",
            category = "Fruit",
            key = "J1"
        )
    )

    return dummyDictionaryList
}