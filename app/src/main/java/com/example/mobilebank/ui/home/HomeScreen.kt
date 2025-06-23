package com.example.mobilebank.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilebank.R
import com.example.mobilebank.utils.actions.setClipboard
import com.example.mobilebank.utils.component.*
import com.example.mobilebank.utils.helper.BankingDataStore
import com.example.mobilebank.utils.helper.SavingItem
import com.example.mobilebank.utils.helper.ThousandSeparatorTransformation
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context
) {
    var isShowMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val isVisibleFlow = BankingDataStore.getIsVisible(context)
    val isVisible by isVisibleFlow.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()
    val savingsList = remember {
        mutableStateListOf(
            SavingItem("HP Baru", 1_000_000),
            SavingItem("Dana Darurat", 500_000),
        )
    }

    val pagerState = rememberPagerState(pageCount = { savingsList.size })

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f
    val nextCardVisibleWidth = cardWidth / 4
    val pageSpacing = 10.dp
    val (selected, setSelected) = remember { mutableIntStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newBalance by remember { mutableStateOf("") }

    var mainBalance by remember { mutableStateOf(5000000) } // contoh 5 juta

    var newBalanceFormatted by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedSavingIndex by remember { mutableIntStateOf(-1) }
    var addAmountFormatted by remember { mutableStateOf("") }
    var addError by remember { mutableStateOf<String?>(null) }


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
                name = "KP",
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
            items = listOf("Tabungan", "Deposito"),
            selectedItemIndex = selected,
            onClick = setSelected,
        )
        Spacer(modifier = Modifier.height(1.dp))
        GrayLine(Modifier, Color(0xFFF3F4F6), 10f)
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 CARD BARU yang statis (di atas pager)
        SavingCard(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            textNumber = "1122334455",
            textName = "Keyo Putri",
            balance = "%,d".format(mainBalance).replace(',', '.'),
            onClick = {
                context.apply {
                    setClipboard("Account Number", "1122334455")
                    isShowMessage = true
                    message = "Berhasil di-Copy!"
                }
            },
            isVisible = isVisible,
            eyeClick = {
                coroutineScope.launch {
                    BankingDataStore.saveIsVisible(context, !isVisible)
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Split Save",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add), // contoh drawable
                    contentDescription = "Tambah Tabungan",
                    Modifier.size(32.dp)
                )
            }
        }

        // 🔸 HorizontalPager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = nextCardVisibleWidth),
            pageSpacing = pageSpacing
        ) { page ->
            val item = savingsList[page]
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            ) {
                // Card greenSaving dipindah ke bawah
                SplitSavingCard(
                    modifier = Modifier,
                    textName = item.name,
                    balance = "%,d".format(item.balance).replace(',', '.'),
                    isVisible = isVisible,
                    eyeClick = {
                        coroutineScope.launch {
                            BankingDataStore.saveIsVisible(context, !isVisible)
                        }
                    },
                    onAddBalance = {
                        selectedSavingIndex = page
                        showAddDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    newName = ""
                    newBalanceFormatted = ""
                    errorMessage = null
                },
                title = { Text(text = "Tambah Tabungan") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Nama Tabungan") },
                            isError = errorMessage != null && newName.isBlank()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newBalanceFormatted,
                            onValueChange = {
                                // Bersihkan titik dan hanya simpan angka murni di state
                                newBalanceFormatted = it.replace(".", "").filter { char -> char.isDigit() }
                            },
                            label = { Text("Nominal") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = ThousandSeparatorTransformation(),
                            isError = errorMessage != null && errorMessage != ""
                        )
                        if (errorMessage != null) {
                            Text(
                                text = errorMessage ?: "",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val cleaned = newBalanceFormatted.replace(".", "").filter { it.isDigit() }
                        val numeric = cleaned.toIntOrNull()

                        when {
                            newName.isBlank() -> {
                                errorMessage = "Nama tabungan tidak boleh kosong"
                            }
                            numeric == null || numeric <= 0 -> {
                                errorMessage = "Nominal tidak valid"
                            }
                            numeric > mainBalance -> {
                                errorMessage = "Saldo utama tidak mencukupi"
                            }
                            else -> {
                                // Lolos semua validasi
                                savingsList.add(SavingItem(newName, numeric))
                                mainBalance -= numeric
                                newName = ""
                                newBalanceFormatted = ""
                                errorMessage = null
                                showDialog = false
                            }
                        }
                    }) {
                        Text("Simpan")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        newName = ""
                        newBalanceFormatted = ""
                        errorMessage = null
                    }) {
                        Text("Batal")
                    }
                }
            )
        }
        if (showAddDialog && selectedSavingIndex != -1) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog = false
                    addAmountFormatted = ""
                    addError = null
                },
                title = { Text("Tambah Dana ke ${savingsList[selectedSavingIndex].name}") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = addAmountFormatted,
                            onValueChange = {
                                addAmountFormatted = it.filter { ch -> ch.isDigit() }
                            },
                            label = { Text("Nominal Tambahan") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = ThousandSeparatorTransformation(),
                            isError = addError != null
                        )
                        if (addError != null) {
                            Text(addError!!, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val cleaned = addAmountFormatted.replace(".", "")
                        val amount = cleaned.toIntOrNull() ?: 0

                        when {
                            amount <= 0 -> addError = "Nominal tidak valid"
                            amount > mainBalance -> addError = "Saldo utama tidak mencukupi"
                            else -> {
                                savingsList[selectedSavingIndex].balance += amount
                                mainBalance -= amount
                                showAddDialog = false
                                addAmountFormatted = ""
                                addError = null
                            }
                        }
                    }) {
                        Text("Simpan")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        addAmountFormatted = ""
                        addError = null
                    }) {
                        Text("Batal")
                    }
                }
            )
        }




        Spacer(modifier = Modifier.height(16.dp))
        CustomLineDotSlider(
            pagerState = pagerState,
            count = savingsList.size,
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
