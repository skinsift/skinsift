package com.ayukrisna.skinsift.view.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme
import com.ayukrisna.skinsift.util.UiText
import com.ayukrisna.skinsift.util.isNumber

@Composable
fun CustomSearchBar(

) {
    CustomTextField(
        title = ""
    )
}

@Composable
fun CustomTextField(
    title: String,
    text: String = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier,
    errorMessage: UiText? = null,
    isError: Boolean = false,
    isVisible: Boolean = false,
    leadingIcon: Painter? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLine: Int = 1,
    ) {
    //Variables
    val isKeyboardTypeNumber =
        keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    val colorBorder =
        if (isError) {
            MaterialTheme.colorScheme.error
        } else if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        }
    //Layout
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Box () {
            BasicTextField(
                value = if (isKeyboardTypeNumber) {
                    if (isNumber(text)) text else ""
                } else text,
                onValueChange = {
                    if (isKeyboardTypeNumber) {
                        if (isNumber(it)) onValueChange(it)
                    } else onValueChange(it)
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                maxLines = maxLine,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation =
                if (keyboardType == KeyboardType.Password) {
                    if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .height(48.dp)
                    .border(1.dp, colorBorder, RoundedCornerShape(20.dp))
                    .padding(8.dp)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (leadingIcon != null) {
                            Icon(
                                painter = leadingIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .requiredSize(48.dp).padding(2.dp, 16.dp, 4.dp, 16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = if (leadingIcon != null) 48.dp else 0.dp)
                                .align(Alignment.CenterStart)
                        ) {
                            innerTextField()
                        }
                        if (trailingIcon != null) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                            ) {
                                trailingIcon()
                            }
                        }
                    }
                },
            )
            if(text.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = if (leadingIcon != null) 56.dp else 8.dp)
                    )
                }
            }
        }
        Text(
            text = errorMessage?.asString(context).orEmpty(),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmail() {
    SkinSiftTheme {
        var text by remember { mutableStateOf("") }
        
        CustomTextField(
            title = "Email",
            text = text,
            placeholder = "emailkamu@example.com",
            onValueChange = { newText -> text = newText },
            leadingIcon = painterResource(id = R.drawable.ic_email),
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            isError = false,
            isVisible = false,
            errorMessage = null,
            singleLine = true,
            maxLine = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewText() {
    SkinSiftTheme {
        var text by remember { mutableStateOf("") }

        CustomTextField(
            title = "Username",
            text = text,
            placeholder = "Enter text...",
            onValueChange = { newText -> text = newText },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            isError = false,
            isVisible = false,
            errorMessage = null,
            singleLine = true,
            maxLine = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPassword() {
    SkinSiftTheme {
        var text by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        CustomTextField(
            title = "Password",
            text = text,
            placeholder = "Beri password keren",
            onValueChange = { newText -> text = newText },
            leadingIcon = painterResource(id = R.drawable.ic_lock),
            trailingIcon = {
                Box(
                    modifier = Modifier) {
                    IconButton(
                        onClick =
                        {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(
                                id = R.drawable.ic_visibility_off
                            ) else painterResource(
                                id = R.drawable.ic_visibility
                            ),
                            contentDescription = "Visible",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .requiredSize(48.dp).padding(16.dp)
                        )
                    }
                }
            },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isError = false,
            isVisible = isPasswordVisible,
            errorMessage = null,
            singleLine = true,
            maxLine = 1
        )
    }
}






//@Preview(showBackground = true)
//@Composable
//fun PreviewSearchBar() {
//    SkinSiftTheme{
//        SearchBarSample()
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchBarSample() {
//    var query by rememberSaveable { mutableStateOf("") }
//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//    Box(Modifier.fillMaxSize().semantics { isTraversalGroup = true }) {
//        SearchBar(
//            modifier = Modifier.align(Alignment.TopCenter).semantics { traversalIndex = 0f },
//            inputField = {
//                SearchBarDefaults.InputField(
//                    query = query,
//                    onQueryChange = { query = it },
//                    onSearch = { expanded = false },
//                    expanded = expanded,
//                    onExpandedChange = { expanded = it },
//                    placeholder = { Text("Hinted search text") },
//                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
//                )
//            },
//            expanded = expanded,
//            onExpandedChange = { expanded = it },
//        ) {
//            Column(Modifier.verticalScroll(rememberScrollState())) {
//                repeat(4) { idx ->
//                    val resultText = "Suggestion $idx"
//                    ListItem(
//                        headlineContent = { Text(resultText) },
//                        supportingContent = { Text("Additional info") },
//                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
//                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
//                        modifier =
//                        Modifier.clickable {
//                            query = resultText
//                            expanded = false
//                        }
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        }
//
//        LazyColumn(
//            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier.semantics { traversalIndex = 1f },
//        ) {
//            val list = List(100) { "Text $it" }
//            items(count = list.size) {
//                Text(
//                    text = list[it],
//                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//                )
//            }
//        }
//    }
//}