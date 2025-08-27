package exceptions

class InformationNotFoundException(endpoint: String) : Exception("Information not found at endpoint: $endpoint!")