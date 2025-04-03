package com.codingslaved.diary.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codingslaved.diary.ui.theme.DiaryTheme
import com.codingslaved.diary.viewmodel.DiaryUiState
import java.util.Calendar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codingslaved.diary.viewmodel.DiaryDetails
import com.codingslaved.diary.viewmodel.DiaryEntryViewModel
import com.codingslaved.diary.viewmodel.DiaryProvider
import kotlinx.coroutines.launch

@Composable
fun WritingScreen(
    viewModel: DiaryEntryViewModel = viewModel(factory = DiaryProvider.Factory)
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Head(Modifier.padding(innerPadding))
            HorizontalDivider(modifier = Modifier.padding(2.dp), thickness = 2.dp)
            TextField(
                viewModel,
                onValueChange = viewModel::updateUiState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun Head(modifier: Modifier = Modifier) {
    val selectedIcon = remember { mutableStateOf(Icons.Default.Face) }
    val showDialog = remember { mutableStateOf(false) }
    val date = Calendar.getInstance()
    val today =
        "${date.get(Calendar.YEAR)}년 ${date.get(Calendar.MONTH) + 1}월 ${date.get(Calendar.DATE)}일"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
            Text(text = today)
        }
        IconButton(onClick = {
            showDialog.value = !showDialog.value
        }, modifier = modifier.fillMaxHeight()) {
            Icon(
                imageVector = selectedIcon.value,
                contentDescription = "선택한 아이콘",
                modifier = Modifier.fillMaxSize()
            )
        }
        if (showDialog.value) {
            IconPicker(showDialog, selectedIcon)
        }
    }
}

@Composable
fun IconPicker(showDialog: MutableState<Boolean>, selectedIcon: MutableState<ImageVector>) {
    val icons = arrayOf(Icons.Default.Add, Icons.Default.Face, Icons.Default.AddCircle)
    Dialog(onDismissRequest = { showDialog.value = false }) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.background(Color.Red)
        ) {
            items(icons.size) { icon ->
                IconButton(onClick = {
                    selectedIcon.value = icons[icon]
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = icons[icon],
                        contentDescription = icons[icon].name,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun TextField(
    viewModel: DiaryEntryViewModel,
    onValueChange: (DiaryDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val diaryUiState: DiaryUiState = viewModel.diaryUiState
    val coroutineScope = rememberCoroutineScope()

    TextField(
        value = diaryUiState.diaryDetails.text,
        onValueChange = { onValueChange(diaryUiState.diaryDetails.copy(text = it)) },
        modifier = modifier
            .focusRequester(focusRequester)
            .focusable()
            .onFocusChanged {
                if (!it.hasFocus) {
                    coroutineScope.launch {
                        viewModel.saveDiary()
                    }
                }

            },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WritingPagePreview() {

    DiaryTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 2.dp, top = 5.dp, end = 2.dp, bottom = 5.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            WritingScreen()
        }
    }
}