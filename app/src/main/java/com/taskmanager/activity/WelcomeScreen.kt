package com.taskmanager.activity

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskmanager.activity.viewmodel.WelcomeViewModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = Constants.WELCOMESCREEN.WELCOMETEXT,
                fontSize = 30.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Box(
                modifier = Modifier
                    .height(400.dp)
                    .width(350.dp)
                    .padding(20.dp)
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.DarkGray
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Constants.WELCOMESCREEN.PHRASE,
                    fontSize = 23.sp,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(textAlign = TextAlign.Center)
                )
            }



            Row {
                Button(onClick = { viewModel.navigate(Routes.CreateAccount.route) }) {
                    Text(text = Constants.WELCOMESCREEN.REGISTER)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { viewModel.navigate(Routes.LoginScreen.route) }) {
                    Text(text = Constants.WELCOMESCREEN.LOGIN)
                }
            }
        }
    }
}
