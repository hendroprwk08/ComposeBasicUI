package com.hendro.composebasicui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.launch

//https://www.tutorialkart.com/android-jetpack-compose/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val context = LocalContext.current
    var textValue by remember { mutableStateOf("") }

    //1. dialog - state
    var openDialog by remember { mutableStateOf(false) }

    //1. dropdown - state
    var mExpanded by remember { mutableStateOf(false) }

    //1. snackbar - state
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    //2. dialog - dibuat terpisah dari onClick
    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = stringResource(id = androidx.compose.material3.R.string.dialog),
                        modifier = Modifier.padding(16.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.dialog_description),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        OutlinedButton(
                            onClick = { openDialog = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(id = R.string.cancel))
                        }
                        Button(
                            onClick = {
                                Toast.makeText(
                                    context, R.string.confirmed, Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        //2. snackbar host
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text(stringResource(id = R.string.app_name))
            }, actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.description)
                    )
                }
                IconButton(onClick = {
                    //2. dropdown - show
                    mExpanded = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(id = R.string.description)
                    )
                }

                //3. dropdown - objek
                DropdownMenu(
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false }
                ) {
                    // menu items
                    DropdownMenuItem(
                        text = {
                            Text("Edit")
                        },
                        onClick = {
                            Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                            mExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Settings")
                        },
                        onClick = {
                            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                            mExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = null
                            )
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Send Feedback")
                        },
                        onClick = {
                            Toast.makeText(context, "Send Feedback", Toast.LENGTH_SHORT).show()
                            mExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Email,
                                contentDescription = null
                            )
                        }
                    )
                }

            })
        },

        ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text(stringResource(id = R.string.ketik)) })
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    val bundle = Bundle()
                    bundle.putString("b_nama", textValue)

//                    if(textValue.equals("")){
//                        Toast.makeText(context, "Lengkapi data", Toast.LENGTH_SHORT).show()
//                    }else{
//                        val intent = Intent(context,KeduaActivity::class.java)
//                        startActivity(context, intent, bundle)
//                    }

                    val intent = Intent(context, KeduaActivity::class.java).apply {
                        putExtra("b_nama", textValue)
                        // ...
                    }
                    startActivity(context, intent, null)

                }) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.Start),
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = "test"
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.intent),
                            color = Color.Black
                        )
                        Image(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.End),
                            painter = painterResource(id = R.drawable.baseline_person_pin_24),
                            contentDescription = "test"
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start),
                        onClick = {
                            Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show()
                        }) {
                        Image(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(end = 8.dp),
                            colorFilter = ColorFilter.tint(Color.White),
                            painter = painterResource(id = R.drawable.baseline_person_pin_24),
                            contentDescription = null
                        )
                        Text(stringResource(R.string.toast))
                    }

                    Button(modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                        onClick = {
                            //3. snackbar - opening
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = context.resources.getString(R.string.snack_bar), //penggunaan string resource menggunakan context
                                        actionLabel = context.resources.getString(R.string.ok),
                                        withDismissAction = true, //tombol X

                                        // Defaults to SnackbarDuration.Short
                                        duration = SnackbarDuration.Indefinite
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        /* Handle snackbar action performed */
                                    }

                                    SnackbarResult.Dismissed -> {
                                        /* Handle snackbar dismissed */
                                    }
                                }
                            }
                        }) {
                        Text(stringResource(R.string.snack_bar))
                    }
                    Button(modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End),
                        onClick = {
                            openDialog = true
                        }) {
                        Text(stringResource(androidx.compose.material3.R.string.dialog))
                        Image(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(start = 8.dp),
                            colorFilter = ColorFilter.tint(Color.White),
                            painter = painterResource(id = R.drawable.baseline_contact_page_24),
                            contentDescription = "test"
                        )

                    }
                }
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = stringResource(id = R.string.lorem),
                )
            }
        }
    }
}

@Preview(
    showSystemUi = false, showBackground = true
)
@Composable
fun SimpleComposablePreview() {
    MyApp()
}