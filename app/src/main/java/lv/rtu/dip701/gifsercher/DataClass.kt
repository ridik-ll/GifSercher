package lv.rtu.dip701.gifsercher

data class GiphyResponse(
    val data: List<Gif>,
    val pagination: Pagination
)

data class Gif(
    val type: String,
    val id: String,
    val url: String,
    val images: Images
)

data class Images(
    val original: OriginalImage
)

data class OriginalImage(
    val url: String
)

data class Pagination(
    val total_count: Int,
    val count: Int,
    val offset: Int
)
