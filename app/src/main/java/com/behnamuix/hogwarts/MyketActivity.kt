package com.behnamuix.hogwarts

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.avacast.ui.theme.VazirFont
import com.behnamuix.avacast.ui.theme.VazirFontBold
import com.behnamuix.hogwarts.myket.util.IabHelper
import com.behnamuix.hogwarts.ui.theme.MvpComposeTheme
import com.behnamuix.tenserpingx.AndroidWraper.DeviceInfo
import ir.myket.billingclient.util.Purchase
import ir.myket.billingclient.util.Security
import java.text.SimpleDateFormat
import java.util.*

class MyketActivity : ComponentActivity() {
    var url_image_bg_second_letter =
        "https://uploadkon.ir/uploads/fba322_25file-000000006008620aa33cd8d51dcd98ce.png"
    private val skuPremium = "letter_hogwarts"
    private val rcRequest = 1003
    private val keyPremiumPurchased = "first_launch"

    private lateinit var mHelper: IabHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("my", MODE_PRIVATE)
        enableEdgeToEdge()

        mHelper = IabHelper(this, BuildConfig.IAB_PUBLIC_KEY).apply {
            enableDebugLogging(true)
        }

        setContent {
            MvpComposeTheme {
                Column(
                    modifier = Modifier
                        .paint(
                            painterResource(R.drawable.bg2),
                            contentScale = ContentScale.Crop
                        )
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(

                            modifier = Modifier
                                .size(300.dp),
                            trackColor = Color.Red

                        )
                        Box(
                            modifier = Modifier
                                .size(300.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                color = Color.White,
                                text = "در حال بارگزاری",
                                fontFamily = VazirFontBold,
                                fontSize = 32.sp)
                        }
                    }

                }
                if (isPremiumPurchased()) {
                    goToMain()
                } else {
                    payConfig()
                }
            }
        }
    }

    private fun isPremiumPurchased(): Boolean {
        return sharedPreferences.getBoolean(keyPremiumPurchased, false)
    }

    private fun setPremiumPurchased(isPurchased: Boolean) {
        sharedPreferences.edit().putBoolean(keyPremiumPurchased, isPurchased).apply()
    }

    private fun goToMain() {
        downloadAndSaveImage(this, url_image_bg_second_letter)
        finish()
    }

    private fun payConfig() {
        mHelper.startSetup { result ->
            if (result.isSuccess) {
                Toast.makeText(
                    this,
                    "در حال ارتباط با سرور های مایکت هستیم اندکی صبر کنید",
                    Toast.LENGTH_SHORT
                ).show()
                payIntent()
            } else {
                Log.e("Myket", "Setup failed: ${result.message}")
                Toast.makeText(this, "خطا در راه‌اندازی: ${result.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun payIntent() {
        mHelper.queryInventoryAsync { result, inventory ->
            if (result?.isFailure == true) {
                Log.e("Myket", "Inventory query failed: $result")
                return@queryInventoryAsync
            }

            val purchase = inventory?.getPurchase(skuPremium)
            if (purchase != null && isPurchaseValid(purchase)) {
                Log.d("Myket", "User already owns item")
                setPremiumPurchased(true)
                goToMain()
            } else {
                startPurchaseFlow()
            }
        }
    }

    private fun isPurchaseValid(purchase: Purchase): Boolean {
        return try {
            val isValid = Security.verifyPurchase(
                BuildConfig.IAB_PUBLIC_KEY,
                purchase.originalJson,
                purchase.signature
            )
            Log.d("Myket", "Purchase verification result: $isValid")
            isValid
        } catch (e: Exception) {
            Log.e("Myket", "Purchase verification error", e)
            false
        }
    }

    private fun startPurchaseFlow() {
        mHelper.launchPurchaseFlow(this, skuPremium, rcRequest, { result, purchase ->
            if (result?.isFailure == true) {
                Log.e("Myket", "Purchase failed: $result")
                return@launchPurchaseFlow
            }

            if (purchase != null && isPurchaseValid(purchase)) {
                handleSuccessfulPurchase(purchase)
            } else {
                Log.e("Myket", "Invalid purchase or verification failed")
            }
        }, "")
    }

    private fun handleSuccessfulPurchase(purchase: Purchase) {
        Log.d("Myket", "Purchase successful for $skuPremium")
        Toast.makeText(this, "با تشکر از خرید شما!", Toast.LENGTH_LONG).show()
        setPremiumPurchased(true)
        goToMain()

        // Consumption (if needed)
        mHelper.consumeAsync(purchase) { _, result ->
            if (result?.isFailure == true) {
                Log.e("Myket", "Consumption failed: $result")
            } else {
                Log.d("Myket", "Consumption successful")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        if (::mHelper.isInitialized) {
            mHelper.dispose()
        }
        super.onDestroy()
    }

    private fun formatPurchaseTime(purchaseTime: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("Asia/Tehran")
        return sdf.format(Date(purchaseTime))
    }
}
