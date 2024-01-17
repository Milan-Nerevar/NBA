package com.nerevar.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.stack.StackScreen
import com.github.terrakok.modo.stack.back
import com.nerevar.nba.core.compose.NBATheme
import com.nerevar.nba.core.compose.stringResource
import com.nerevar.nba.core.compose.value
import com.nerevar.nba.core.navigation.RootScreen
import com.nerevar.nba.feature.player_list.presentation.PlayerListScreen

class MainActivity : ComponentActivity() {

    private var rootScreen: StackScreen? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootScreen = Modo.init(savedInstanceState, rootScreen) {
            RootScreen(PlayerListScreen())
        }
        setContent {
            NBATheme {
                val isRootScreenVisible by remember {
                    derivedStateOf {
                        (rootScreen?.navigationState?.stack?.size ?: 0) <= 1
                    }
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                if (isRootScreenVisible) {
                                    Text(text = stringResource(R.string.app_name).value)
                                }
                            },
                            navigationIcon = {
                                if (!isRootScreenVisible) {
                                    IconButton(onClick = { rootScreen?.back() }) {
                                        Icon(Icons.Filled.ArrowBack, null)
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        )
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        rootScreen?.Content()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Modo.save(outState, rootScreen)
        super.onSaveInstanceState(outState)
    }
}


