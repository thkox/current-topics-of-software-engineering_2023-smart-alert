package eu.tkacas.smartalert.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.data.home.HomeViewModel
import kotlinx.coroutines.launch
import eu.tkacas.smartalert.components.NavigationDrawerHeader
import eu.tkacas.smartalert.components.NavigationDrawerBody
import eu.tkacas.smartalert.components.AppToolbar
//import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import eu.tkacas.smartalert.components.HeadingTextComponent

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = "Home")

        }
    }
}
//
//@Composable
//fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
//
//    val scaffoldState = rememberScaffoldState()
//    val coroutineScope = rememberCoroutineScope()
//
//    homeViewModel.getUserData()
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            AppToolbar(toolbarTitle = stringResource(id = R.string.home),
//                logoutButtonClicked = {
//                    homeViewModel.logout()
//                },
//                navigationIconClicked = {
//                    coroutineScope.launch {
//                        scaffoldState.drawerState.open()
//                    }
//                }
//            )
//        },
//        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
//        drawerContent = {
//            NavigationDrawerHeader(homeViewModel.emailId.value)
//            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
//                onNavigationItemClicked = {
//                    Log.d("ComingHere","inside_NavigationItemClicked")
//                    Log.d("ComingHere","${it.itemId} ${it.title}")
//                })
//        }
//
//    ) { paddingValues ->
//
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(paddingValues)
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//
//
//            }
//
//        }
//    }
//}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

//
//@Composable
//fun rememberScaffoldState(
//    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
//    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
//): ScaffoldState = remember {
//    ScaffoldState(drawerState, snackbarHostState)
//}
//
//@Stable
//class ScaffoldState(
//    val drawerState: DrawerState,
//    val snackbarHostState: SnackbarHostState
//)