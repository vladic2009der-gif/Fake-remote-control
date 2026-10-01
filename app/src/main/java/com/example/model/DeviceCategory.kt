package com.example.model

enum class RemoteType {
    TV,
    AC,
    PROJECTOR,
    AUDIO,
    GENERIC
}

data class DeviceCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val remoteType: RemoteType,
    val supportedProtocols: List<String>,
    val popularBrands: List<String>,
    val deviceCountApprox: String
)

object DeviceCategories {
    val allCategories = listOf(
        DeviceCategory(
            id = "tv",
            title = "Телевизоры (TV)",
            subtitle = "Smart TV, OLED, QLED, LED панели",
            iconName = "tv",
            remoteType = RemoteType.TV,
            supportedProtocols = listOf("NEC", "RC-5", "Sony SIRC", "Samsung"),
            popularBrands = listOf("Samsung", "LG", "Sony", "Xiaomi", "TCL", "Philips", "Hisense", "Panasonic", "Sharp", "Haier"),
            deviceCountApprox = "2,400+ моделей"
        ),
        DeviceCategory(
            id = "ac",
            title = "Кондиционеры (AC)",
            subtitle = "Сплит-системы и климат-контроль",
            iconName = "ac",
            remoteType = RemoteType.AC,
            supportedProtocols = listOf("Daikin", "Gree", "Midea", "NEC"),
            popularBrands = listOf("Daikin", "Mitsubishi", "Gree", "Midea", "Ballu", "Haier", "Panasonic", "Electrolux", "Toshiba"),
            deviceCountApprox = "1,850+ моделей"
        ),
        DeviceCategory(
            id = "projector",
            title = "Проекторы",
            subtitle = "Лазерные, DLP, 3LCD и домашние кинотеатры",
            iconName = "projector",
            remoteType = RemoteType.PROJECTOR,
            supportedProtocols = listOf("NEC", "RC-6", "Epson-IR"),
            popularBrands = listOf("Epson", "BenQ", "Optoma", "ViewSonic", "XGIMI", "Sony", "Acer", "Dangbei"),
            deviceCountApprox = "640+ моделей"
        ),
        DeviceCategory(
            id = "audio",
            title = "Аудио & Саундбары",
            subtitle = "Ресиверы, Hi-Fi усилители и акустика",
            iconName = "audio",
            remoteType = RemoteType.AUDIO,
            supportedProtocols = listOf("NEC", "RC-5", "Denon IR"),
            popularBrands = listOf("Yamaha", "Pioneer", "Denon", "JBL", "Bose", "Sony", "Harman Kardon", "Marantz", "Samsung"),
            deviceCountApprox = "980+ моделей"
        ),
        DeviceCategory(
            id = "stb",
            title = "ТВ-Приставки & Спутник",
            subtitle = "IPTV, DVB-T2 ресиверы и медиаплееры",
            iconName = "stb",
            remoteType = RemoteType.TV,
            supportedProtocols = listOf("NEC", "RC-5", "RC-MM"),
            popularBrands = listOf("Xiaomi Mi Box", "Apple TV", "Триколор ТВ", "Ростелеком", "Dune HD", "Amazon Fire", "Ugoos"),
            deviceCountApprox = "1,200+ моделей"
        ),
        DeviceCategory(
            id = "fan",
            title = "Вентиляторы & Очистители",
            subtitle = "Климатическая техника и воздухоочистители",
            iconName = "fan",
            remoteType = RemoteType.GENERIC,
            supportedProtocols = listOf("NEC", "Custom IR"),
            popularBrands = listOf("Dyson", "Xiaomi", "Philips", "Tefal", "Boneco", "Polaris", "Electrolux"),
            deviceCountApprox = "420+ моделей"
        ),
        DeviceCategory(
            id = "rgb_light",
            title = "Умный свет (RGB IR)",
            subtitle = "Светодиодные ленты, люстры и прожекторы",
            iconName = "light",
            remoteType = RemoteType.GENERIC,
            supportedProtocols = listOf("NEC 24/44 Key"),
            popularBrands = listOf("Yeelight", "Magic Home", "Philips Hue IR", "RGB Strip 44-Key", "Paulmann"),
            deviceCountApprox = "310+ моделей"
        ),
        DeviceCategory(
            id = "dvd",
            title = "DVD & Blu-ray",
            subtitle = "Дисковые проигрыватели и плееры",
            iconName = "dvd",
            remoteType = RemoteType.TV,
            supportedProtocols = listOf("Sony SIRC", "NEC", "Panasonic"),
            popularBrands = listOf("Sony", "Panasonic", "Pioneer", "LG", "Samsung", "Philips"),
            deviceCountApprox = "560+ моделей"
        )
    )
}
