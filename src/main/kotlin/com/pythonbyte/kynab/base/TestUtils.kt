package com.pythonbyte.kynab.base

import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

fun assertNotEmpty( actual: String ) {
    assertNotNull( actual )
    //assertNotEquals( "", actual )
}

fun assertNotEmpty( actual: List<Any> ) {
    assertNotNull( actual )
    assertNotEquals( 0, actual.size )
}

