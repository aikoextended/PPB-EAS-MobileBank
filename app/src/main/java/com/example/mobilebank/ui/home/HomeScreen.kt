package com.example.mobilebank.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.mobilebank.utils.actions.setClipboard
import com.example.mobilebank.utils.component.CircleButton
import com.example.mobilebank.utils.component.CustomLineDotSlider
import com.example.mobilebank.utils.component.CustomOnTopToast
import com.example.mobilebank.utils.component.CustomTextTab
import com.example.mobilebank.utils.component.GrayLine
import com.example.mobilebank.utils.component.LoyaltyCard
import com.example.mobilebank.utils.component.ProfileImage
import com.example.mobilebank.utils.component.SavingCard
import com.example.mobilebank.utils.helper.BankingDataStore
import kotlinx.coroutines.launch
import com.example.mobilebank.R


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context
) {
    val textNumber = "9876543210"
    val textName = "greenSaving"
    val balance = "1.000.000.000"
    var isShowMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val isVisibleFlow = BankingDataStore.getIsVisible(context)
    val isVisible by isVisibleFlow.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f // Adjust the card width as needed (80% of screen width)
    val nextCardVisibleWidth = cardWidth / 4 // 1/3 of the card width
    val pageSpacing = 10.dp
    val (selected, setSelected) = remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ProfileImage(
                modifier = Modifier.padding(start = 10.dp),
                size = 48.dp,
                color = Color(0xffB5CFB7),
                name = "VP",
                fontSize = 20
            )
            Spacer(modifier = Modifier.width(10.dp))
            LoyaltyCard(
                modifier = Modifier,
                heightCard = 48.dp,
                color = Color(0xffF3F4F6),
                widthCard = 126.dp,
                textLoyalty = "greenPro",
                imageSize = 27.dp,
                fontSize = 15
            )
            Spacer(modifier = Modifier.weight(1f))
            CircleButton(
                modifier = Modifier.padding(end = 10.dp),
                onClick = {},
                iconImage = R.drawable.icon_messenger,
                sizeImage = 20.dp
            )
            CircleButton(
                modifier = Modifier.padding(end = 20.dp),
                onClick = {},
                iconImage = R.drawable.icon_notification,
                sizeImage = 20.dp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextTab(
            items = listOf("Akun", "Kartu"),
            selectedItemIndex = selected,
            onClick = setSelected,
        )
        Spacer(modifier = Modifier.height(1.dp))
        GrayLine(Modifier, Color(0xFFF3F4F6), 10f)
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                end = nextCardVisibleWidth
            ),
            pageSpacing = pageSpacing
        ) { page ->
            // Your page content here
            // Example: Text("Page $page")
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterStart
            ) {
//                        Text(
//                            text = "Page $page",
//                            fontSize = 24.sp,
//                            textAlign = TextAlign.Center
//                        )
                SavingCard(
                    modifier = Modifier.padding(
                        end = 10.dp,
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp
                    ),
                    textNumber = textNumber,
                    textName = textName,
                    balance = balance,
                    onClick = {
                        context.apply {
                            setClipboard("Account Number", textNumber)
                            isShowMessage = true
                            message = "Berhasil di-Copy!"
                        }
                    },
                    isVisible = isVisible,
                    eyeClick = {
                        coroutineScope.launch {
                            BankingDataStore.saveIsVisible(context, !isVisible)
                        }
                    })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomLineDotSlider(
            pagerState = pagerState,
            count = 4, // Must match the page count
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )
        if (isShowMessage) {
            CustomOnTopToast(
                message = message,
                onDismiss = { isShowMessage = false }
            )
        }
    }
}