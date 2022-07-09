package com.example.movieapp.core.infra

class NetworkUnavailableException(override val message: String? = "No hay conexión a internet"): Exception()
class NoDataException(override val message: String? = null) : Exception()
class BadRequestException(override val message: String? = null) : Exception()
class UnauthorizedException(override val message: String? = null) : Exception()
class UnknownStatusCodeException(override val message: String? = null) : Exception()
class NotFoundException(override  val message: String? = null) : Exception()