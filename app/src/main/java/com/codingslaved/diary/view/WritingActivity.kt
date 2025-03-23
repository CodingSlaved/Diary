package com.codingslaved.diary.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingslaved.diary.ui.theme.DiaryTheme
import java.util.Calendar

class WritingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

@Composable
fun Head(modifier: Modifier = Modifier) {
    val date = Calendar.getInstance()
    val today = "${date.get(Calendar.YEAR)}년 ${date.get(Calendar.MONTH) + 1}월 ${date.get(Calendar.DATE)}일"

    Row (
        modifier = Modifier.fillMaxWidth().height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
            Text(text = today)
        }
        Button(
            onClick = {},
            shape = RectangleShape,
            modifier = modifier.fillMaxHeight(),
            contentPadding = PaddingValues.Absolute(3.dp, 0.dp, 3.dp, 0.dp)
        ) {
            Text(text = "아이콘 선택")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun WritingPagePreview() {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    DiaryTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 2.dp, top = 5.dp, end = 2.dp, bottom = 5.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold { innerPadding ->
                Column (modifier = Modifier.padding(innerPadding)) {
                    Head(Modifier.padding(innerPadding))
                    HorizontalDivider(modifier = Modifier.padding(2.dp), thickness = 2.dp)
                    TextField(
                        value = value,
                        onValueChange = { value = it},
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    )
                }
            }
        }
    }
}