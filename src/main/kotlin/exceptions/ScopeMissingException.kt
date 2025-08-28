package exceptions

import enums.ScopesEnum

class ScopeMissingException(scopes: List<ScopesEnum>) : Exception("A required scope is missing! Current scopes: $scopes")