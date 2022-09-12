package tik.prometheus.rest.models


class Greenhouse(
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String? = null,
    var area: Float? = null,
    var height: Float? = null,
    var width: Float? = null,
    var length: Float? = null,
    var cultivationArea: Float? = null,
) {
}