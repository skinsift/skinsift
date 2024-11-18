package com.ayukrisna.dicodingstory.application.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import com.ayukrisna.dicodingstory.util.UiText
import com.ayukrisna.dicodingstory.util.isNumber

@Composable
fun CustomTextField(
    title: String,
    text: String = "",
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
                .border(0.dp, colorBorder, RoundedCornerShape(20.dp))
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
                                .requiredSize(48.dp).padding(16.dp)
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
    DicodingStoryTheme {
        var text by remember { mutableStateOf("") }
        
        CustomTextField(
            title = "Email",
            text = text,
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
    DicodingStoryTheme {
        var text by remember { mutableStateOf("") }

        CustomTextField(
            title = "Username",
            text = text,
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
    DicodingStoryTheme {
        var text by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        CustomTextField(
            title = "Password",
            text = text,
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