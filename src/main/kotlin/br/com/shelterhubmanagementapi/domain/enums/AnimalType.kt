package br.com.shelterhubmanagementapi.domain.enums

enum class AnimalType {
    Canine,
    Feline,
    Bird,
    Reptile,
    OtherMammal,
    Unknown
    ;

    companion object {
        fun getOrDefault(value: String): AnimalType {
            val animalType =
                AnimalType.entries.find {
                    it.toString() == value
                }
            return animalType ?: Unknown
        }
    }
}
