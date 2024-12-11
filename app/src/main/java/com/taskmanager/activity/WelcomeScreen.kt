package com.taskmanager.activity

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskmanager.R
import com.taskmanager.activity.viewmodel.WelcomeViewModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {

    val transition = rememberInfiniteTransition(label = "")

    val buttonColor = transition.animateColor(
        initialValue = Color.Yellow,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(800, easing = FastOutLinearInEasing)
        ),
        label = ""
    )
    val callToActionPhrases = listOf(
        "Notas salvas Facilmente",
        "Organize suas ideias, onde e quando quiser!",
        "Nunca perca suas ideias: sincronize na nuvem!",
        "Crie, salve e acesse suas notas de qualquer lugar."
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.studying_banner),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                alpha = 0.2f,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = Constants.WELCOMESCREEN.WELCOMETEXT,
                fontSize = 26.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = Constants.WELCOMESCREEN.PHRASE,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Center)
            )
            LazyRow(horizontalArrangement = Arrangement.Center) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Yellow,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .width(250.dp),
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.Center,
                            ) {
                                item { SequentialWriteTexts(callToActionPhrases) }
                            }
                        }
                    }
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { viewModel.navigate(Routes.CreateAccount.route) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 3.dp, color = buttonColor.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = Constants.WELCOMESCREEN.REGISTER)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { viewModel.navigate(Routes.LoginScreen.route) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor.value,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = Constants.WELCOMESCREEN.LOGIN)
                }
            }
        }
    }
}

@Composable
fun SequentialWriteTexts(phrases: List<String>) {
    var currIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(currIndex) {
        if (currIndex == phrases.size - 1) currIndex = 0
    }

    if (currIndex < phrases.size) {
        WriteText(
            value = phrases[currIndex]
        ) {
            if (currIndex + 1 < phrases.size) {
                currIndex++
            }
        }
    }
}

@Composable
private fun WriteText(value: String, finishedWrite: () -> Unit) {
    var displayedText by remember { mutableStateOf("") }
    LaunchedEffect(value) { // Reexecuta quando a frase muda
        displayedText = "" // Reinicia o texto
        value.forEach { c ->
            displayedText += c.toString()
            delay(80) // Delay para o efeito de escrita
        }
        delay(500)
        finishedWrite() // Notifica que a escrita terminou
    }
    Text(
        displayedText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        fontWeight = FontWeight.SemiBold,
        textDecoration = TextDecoration.Underline,
        fontSize = 26.sp,
        style = TextStyle(fontFamily = FontFamily(Font(R.font.pinyonfont_regular)))
    )
}