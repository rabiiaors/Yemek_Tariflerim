package com.example.easyfood

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Android cihazda çalışacak enstrümantasyon testi.
 * Testin belgelerine [testing documentation](http://d.android.com/tools/testing) adresinden erişilebilir.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /**
     * Uygulama bağlamına erişmek için [InstrumentationRegistry.getInstrumentation().targetContext] kullanılır.
     */
    @Test
    fun useAppContext() {
        // Test edilen uygulamanın bağlamı.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // Paket adının, Context nesnesinin paket adıyla eşleştiğini doğrular.
        assertEquals("com.example.easyfood", appContext.packageName)
    }
}
