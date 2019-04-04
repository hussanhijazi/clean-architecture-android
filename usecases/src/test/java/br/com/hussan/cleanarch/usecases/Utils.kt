package br.com.hussan.cleanarch.usecases

import org.mockito.Mockito

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
