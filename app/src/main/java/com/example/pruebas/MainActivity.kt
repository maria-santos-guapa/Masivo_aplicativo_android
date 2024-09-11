@file:Suppress("DEPRECATION")

package com.example.pruebas
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import android.content.Context
import android.content.Intent
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.material.icons.filled.Add
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import viewmodels.DatosGuardadosViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val isLoggedIn = rememberSaveable { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()
            val viewModel: DatosGuardadosViewModel = viewModel(
                factory = DatosGuardadosViewModelFactory(applicationContext)
            )
            NavHost(navController, startDestination = "LoginScreen") {
                composable("startForms/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        startForms(navController = navController, viewModel = viewModel,context = applicationContext,username = muestreoValue)
                    }

                }
                composable("startFormsVerificacionCosecha/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        startFormsVerificacionCosecha(navController = navController, viewModel = viewModel,context = applicationContext,username = muestreoValue)
                    }

                }
                composable("startFormsVerificacion/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        startFormsVerificacion(navController = navController, viewModel = viewModel,context = applicationContext,username = muestreoValue)
                    }

                }
                composable("loginScreen") {
                    LoginScreen(navController = navController, viewModel = viewModel,context = applicationContext)
                }
                composable(
                    "formulario/{muestreo}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("muestreo") { type = NavType.IntType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val muestreo = backStackEntry.arguments?.getInt("muestreo")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    muestreo?.let { muestreoValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formulario(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
                composable(
                    "formularioCosecha/{muestreo}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("muestreo") { type = NavType.IntType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val muestreo = backStackEntry.arguments?.getInt("muestreo")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    muestreo?.let { muestreoValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formularioCosecha(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
                composable(
                    "formularioVerificacion/{muestreo}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("muestreo") { type = NavType.IntType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val muestreo = backStackEntry.arguments?.getInt("muestreo")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    muestreo?.let { muestreoValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formularioVerificacion(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
                composable("IngresarBloque/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        IngresarBloqueDialog(
                            navController = navController,
                            viewModel = viewModel,
                            username = muestreoValue
                        )
                    }
                }
                composable("startScreen/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        startScreen(navController = navController, viewModel = viewModel,context = applicationContext,username = muestreoValue)
                    }
                }

                composable("panelControl/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        panelControl(navController = navController,viewModel = viewModel,context = applicationContext, username = muestreoValue)
                    }
                }
                composable("searchBlock/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                    backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        searchBlock(
                            navController = navController,
                            viewModel = viewModel,
                            context = applicationContext,
                            username = muestreoValue
                        )
                    }
                }
                composable("searchBlockVerificacion/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        searchBlockVerificacion(
                            navController = navController,
                            viewModel = viewModel,
                            context = applicationContext,
                            username = muestreoValue
                        )
                    }
                }
                composable("searchBlockCosecha/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )) {
                        backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username")
                    username?.let { muestreoValue ->
                        searchBlockCosecha(
                            navController = navController,
                            viewModel = viewModel,
                            context = applicationContext,
                            username = muestreoValue
                        )
                    }
                }
                composable(
                    "formularioEditar/{fecha}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("fecha") { type = NavType.StringType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val fecha = backStackEntry.arguments?.getString("fecha")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    fecha?.let { fechaValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formularioEditar(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    fecha = fechaValue,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
                composable(
                    "formularioVerificacionEditar/{fecha}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("fecha") { type = NavType.StringType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val fecha = backStackEntry.arguments?.getString("fecha")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    fecha?.let { fechaValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formularioVerificacionEditar(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    fecha = fechaValue,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
                composable(
                    "formularioCosechaEditar/{fecha}/{bloque}/{username}",
                    arguments = listOf(
                        navArgument("fecha") { type = NavType.StringType },
                        navArgument("bloque") { type = NavType.StringType },
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val fecha = backStackEntry.arguments?.getString("fecha")
                    val bloque = backStackEntry.arguments?.getString("bloque")
                    val usuario = backStackEntry.arguments?.getString("username")
                    fecha?.let { fechaValue ->
                        bloque?.let { bloqueValue ->
                            usuario?.let { usuarioValue ->
                                formularioCosechaEditar(
                                    navController = navController,
                                    bloque = bloqueValue,
                                    viewModel = viewModel,
                                    fecha = fechaValue,
                                    username = usuarioValue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



class DatosGuardadosViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatosGuardadosViewModel::class.java)) {
            return DatosGuardadosViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LocalUserStore(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("local_user_store", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getUsers(): MutableList<User> {
        val usersJson = sharedPreferences.getString("users", null)
        return if (usersJson != null) {
            val type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(usersJson, type)
        } else {
            mutableListOf()
        }
    }

    fun saveUsers(users: MutableList<User>) {
        val usersJson = gson.toJson(users)
        sharedPreferences.edit().putString("users", usersJson).apply()
    }

    fun saveUser(user: User) {
        val users = getUsers()
        users.removeAll { it.username == user.username }  // Remove any existing user with the same username
        users.add(user)
        saveUsers(users)
    }

    fun getUser(username: String): User? {
        return getUsers().find { it.username == username }
    }

    data class User(val username: String, val password: String)
}

@Composable
fun IngresarBloqueDialog(
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
) {
    var lote by remember { mutableStateOf("") }
    var block by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Registrar Nuevo Bloque",
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ingrese los siguientes datos:",
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            var dropdownDesarrollo = remember { mutableStateOf(false) }
            val desarrolloOptions = listOf("PC","SC")
            Spacer(modifier = Modifier.height(10.dp))
            // Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        dropdownDesarrollo.value = true
                    })
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .height(52.dp)
            ) {
                Text(
                    text = "Desarrollo: $selectedOption",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                DropdownMenu(
                    expanded = dropdownDesarrollo.value,
                    onDismissRequest = { dropdownDesarrollo.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    desarrolloOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            selectedOption = option
                            dropdownDesarrollo.value = false
                        }) {
                            Text(
                                text = option,
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // TextFields
            OutlinedTextField(
                value = lote,
                onValueChange = { lote = it.take(2) },
                label = { Text("Lote") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = block,
                onValueChange = { block = it.take(2) },
                label = { Text("Bloque") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = year,
                onValueChange = { year = it.take(2) },
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Botón Guardar
            Button(
                onClick = {
                    if (selectedOption.isEmpty() || lote.isEmpty() || block.isEmpty() || year.isEmpty()) {
                        errorMessage = "Todos los campos son obligatorios"
                    } else {
                        errorMessage = ""
                        val nuevoDato = mapOf(
                            "Origen" to "Web",
                            "bloque" to "$selectedOption$lote$block$year",
                            "peso" to 0,
                            "sistemaRadicularAlto" to "",
                            "sistemaRadicularMedio" to "",
                            "sistemaRadicularBajo" to "",
                            "fusarium" to ""
                        )
                        viewModel.agregarDato(nuevoDato)
                        var i = 1
                        var selectedBlock = "$selectedOption$lote$block$year"
                        navController.navigate("formulario/$i/${selectedBlock.trim().uppercase()}/$username")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            // Botón Cancelar
            Button(
                onClick = {
                    navController.navigate("startForms/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                // Imagen
                Image(
                    painter = painterResource(id = R.drawable.logi),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
            // Texto de pie de página
            Text(
                text = "Powered by Guapa \n Versión 2.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            // Texto de error
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}
@Composable
fun LoginScreen(navController: NavController, viewModel: DatosGuardadosViewModel, context: Context) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Agricola Guapa SAS\n Aplicativo Móvil\nLogin",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Ingrese Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Ingrese Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    loginUser(context, user, password) { success ->
                        if (success) {
                            navController.navigate("startScreen/$user")
                        } else {
                            errorMessage = when {
                                user.isEmpty() && password.isEmpty() -> "Ingrese usuario y contraseña"
                                user.isEmpty() -> "Ingrese usuario"
                                password.isEmpty() -> "Ingrese contraseña"
                                else -> "Usuario o contraseña incorrectos"
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Ingresar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.logi),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = "Powered by Guapa\nVersión 2.0",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun startScreen(navController: NavController, viewModel: DatosGuardadosViewModel, context: Context,username: String) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val datos = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
    var showDialog by remember { mutableStateOf(false) }
    var showDialogNew by remember { mutableStateOf(false) }
    var showDialogMuestras by remember { mutableStateOf(false) }
    if (showDialogMuestras) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user clicks outside of it or presses the back button
                showDialogMuestras = false
            },
            title = {
                Text(text = "¿Está seguro de eliminar todas las muestras?")
                //TODO
                // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialogMuestras = false
                        viewModel.borrarTodosLosDatosGuardadosNoWeb()
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialogMuestras = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user clicks outside of it or presses the back button
                showDialog = false
            },
            title = {
                Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                //TODO
                // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        showDialogNew = true
                        coroutineScope.launch {
                            sendData(viewModel = viewModel, context = context){ success ->
                                showDialogNew = !success
                                println(success)
                                if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                            }
                        }
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    if (showDialogNew) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Se están enviando los datos. Por favor, espere...") },
            confirmButton = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Agricola Guapa SAS \n Aplicativo Móvil",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                navController.navigate("startFormsVerificacionCosecha/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Verificación Observaciones")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("startFormsVerificacion/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Verificación Cosecha")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("startforms/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Enfermedades y Plagas en Fruta")
        }
        var showDialogData by remember { mutableStateOf(false) }
        Button(
            onClick = {
                // Show the dialog when the button is clicked
                showDialogMuestras = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Borrar Todas las Muestras")
        }
        Button(
            onClick = {
                val url = "https://drive.google.com/file/d/1WuoSP4yLLgVgZSTnZzNWayW1xHf-q_Bw/view?usp=drive_link"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Manual de uso aplicativo móvil",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp))
        {
            Button(
                onClick = {
                    showDialogData = true
                    updateBlocks(viewModel) { success ->
                        showDialogData = !success
                        println(success)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .padding(top = 16.dp)
            ) { Text("Actualizar Datos") }

            if (showDialogData) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text(text = "Se están actualizando los datos. Por favor, espere...") },
                    confirmButton = {}
                )
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
            ) { Text("Enviar Datos") }
        }
        val datos = viewModel.datosGuardados.filter { it["estatus"] == "enviado" }
        val datosVerificación = datos.filter { it["aplicacion"] == "verificacion" }
        val datosCosecha = datos.filter { it["aplicacion"] == "cosecha" }
        val datosEnfermedades = datos.filter { it["aplicacion"] == "enfermedades" }

        // Función para obtener la fecha del primer registro, o un mensaje si no hay datos
        fun obtenerFecha(datos: List<Map<String, Any?>>): String {
            return if (datos.isNotEmpty()) {
                datos.first()["fecha"] as? String ?: "Fecha desconocida"
            } else {
                "No se han enviado datos"
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logi),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = "Powered by Guapa\nVersión 2.0",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun startForms(navController: NavController, viewModel: DatosGuardadosViewModel, context: Context,username: String) {
    var success: Boolean = false
    var selectedOption by remember { mutableStateOf("") }
    var sampleCountText by remember { mutableStateOf("") }
    val progress = viewModel.getProgress()
    val datos = viewModel.datosGuardados.filter { it["aplicacion"] == "enfermedades" }
    val progressVisible = viewModel.isProgressVisible()
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var showDialogNew by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user clicks outside of it or presses the back button
                showDialog = false
            },
            title = {
                Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                //TODO
                // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        showDialogNew = true
                        coroutineScope.launch {
                            sendData(viewModel = viewModel, context = context){ success ->
                                showDialogNew = !success
                                println(success)
                                if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                            }
                        }
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    if (showDialogNew) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Se están enviando los datos. Por favor, espere...") },
            confirmButton = {}
        )
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Enfermedades y Plagas \n en Fruta",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val fecha_actualizacion = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .mapNotNull { it["Fecha_Cargue"] as? String }
            .firstOrNull()


        Text(
            text = "Última Actualización",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_actualizacion}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val otrafecha = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] != "Web" && it["aplicacion"] == "enfermedades" }
            .mapNotNull { it["fecha_muestreo"] as? String } // Asegúrate de que es un String o descártalo
            .maxOrNull() ?: "" // Si no hay ninguna fecha, devuelve una


        Text(
            text = "Fecha Última muestra : $otrafecha",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Usuario: $username",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        var selectedBlock by remember { mutableStateOf("") }
        val blocks = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .map { it["bloque"] as? String }
            .filterNotNull()

        TextField(
            value = selectedBlock,
            onValueChange = { selectedBlock = it },
            label = { Text("Ingrese el bloque") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
            singleLine = true
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Text("Bloques disponibles:", fontWeight = FontWeight.Bold)
            if (selectedBlock.isNotBlank()) {
                Row {
                    blocks.filter { it.contains(selectedBlock, ignoreCase = true) }.take(4).forEach { block ->
                        ClickableText(
                            text = AnnotatedString(block),
                            onClick = { selectedBlock = block },
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                        )
                    }
                }
            }
        }

        Text(
            text = "*En caso de no tener registrado su bloque, por favor registrar dando clic en botón: Registrar Bloque",
            fontSize = 11.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Left
        )

        val i = 1
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Button(
                onClick = {
                    val blockExists = blocks.contains(selectedBlock.trim().uppercase())
                    if (blockExists) {
                        navController.navigate("formulario/$i/${selectedBlock.trim().uppercase()}/$username")
                    } else {
                        println("Bloque no registrado. El bloque ingresado no está registrado.")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) { Text("Registrar Hallazgos") }
            Button(
                onClick = {
                    selectedOption = ""
                    sampleCountText = ""
                    navController.navigate("ingresarBloque/$username")
                },
                modifier = Modifier.weight(1f)
            ) { Text("Registrar Bloque") }
        }
        Button(
            onClick = { navController.navigate("panelControl/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Hallazgos Individuales") }

        Button(
            onClick = { navController.navigate("searchBlock/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Hallazgos por Bloque") }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val file = generateExcelFile(viewModel, context, aplicacion = "enfermedades")
                    file?.let {
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar archivo Excel").apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Descargar Datos Guardados en Excel")
        }

        Button(
            onClick = { navController.navigate("startScreen/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Panel Principal") }
        Spacer(modifier = Modifier.height(10.dp))

        val bloquesUnicosNoWeb = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" && it["aplicacion"] == "enfermedades" }
            .mapNotNull { it["bloque"] as? String } // Utiliza mapNotNull para eliminar elementos nulos después de mapear
            .distinct()

        Text(
            text = "Cantidad de bloques únicos registrados: ${bloquesUnicosNoWeb.size}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        val cantidadPlantasNoWeb = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" && it["aplicacion"] == "enfermedades" }
            .mapNotNull { it["terraza"] as? String } // Utiliza mapNotNull para eliminar elementos nulos después de mapear
            .distinct()
        Text(
            text = "Cantidad de terrazas (muestras): ${cantidadPlantasNoWeb.size}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        val cantidadBloquesWeb = viewModel.datosGuardados.count { it["Origen"] == "Web" }
        Text(
            text = "Cantidad de bloques cargados desde Web: $cantidadBloquesWeb",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 2.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun startFormsVerificacionCosecha(navController: NavController, viewModel: DatosGuardadosViewModel, context: Context,username: String) {
    var success: Boolean = false
    var selectedOption by remember { mutableStateOf("") }
    var sampleCountText by remember { mutableStateOf("") }
    val progress = viewModel.getProgress()
    val datos = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
    val progressVisible = viewModel.isProgressVisible()
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var showDialogNew by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user clicks outside of it or presses the back button
                showDialog = false
            },
            title = {
                Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                //TODO
                // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        showDialogNew = true
                        coroutineScope.launch {
                            sendData(viewModel = viewModel, context = context){ success ->
                                showDialogNew = !success
                                println(success)
                                if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                            }
                        }
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    if (showDialogNew) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Se están enviando los datos. Por favor, espere...") },
            confirmButton = {}
        )
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Verificación Observaciones",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val fecha_actualizacion = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .mapNotNull { it["Fecha_Cargue"] as? String }
            .firstOrNull()

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Última Actualización",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_actualizacion}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val otrafecha = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] != "Web" && it["aplicacion"] == "verificacion" }
            .mapNotNull { it["fecha_muestreo"] as? String } // Asegúrate de que es un String o descártalo
            .maxOrNull() ?: "" // Si no hay ninguna fecha, devuelve una


        Text(
            text = "Fecha Última muestra : $otrafecha",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Usuario: $username",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        var selectedBlock by remember { mutableStateOf("") }
        val blocks = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .map { it["bloque"] as? String }
            .filterNotNull()

        TextField(
            value = selectedBlock,
            onValueChange = { selectedBlock = it },
            label = { Text("Ingrese el bloque") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
            singleLine = true
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Text("Bloques disponibles:", fontWeight = FontWeight.Bold)
            if (selectedBlock.isNotBlank()) {
                Row {
                    blocks.filter { it.contains(selectedBlock, ignoreCase = true) }.take(4).forEach { block ->
                        ClickableText(
                            text = AnnotatedString(block),
                            onClick = { selectedBlock = block },
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                        )
                    }
                }
            }
        }

        Text(
            text = "*En caso de no tener registrado su bloque, por favor registrar dando clic en botón: Registrar Bloque",
            fontSize = 11.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Left
        )

        val i = 1
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Button(
                onClick = {
                    val blockExists = blocks.contains(selectedBlock.trim().uppercase())
                    if (blockExists) {
                        navController.navigate("formularioVerificacion/$i/${selectedBlock.trim().uppercase()}/$username")
                    } else {
                        println("Bloque no registrado. El bloque ingresado no está registrado.")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) { Text("Registrar Hallazgos") }
            Button(
                onClick = {
                    selectedOption = ""
                    sampleCountText = ""
                    navController.navigate("ingresarBloque/$username")
                },
                modifier = Modifier.weight(1f)
            ) { Text("Registrar Bloque") }
        }
        Button(
            onClick = { navController.navigate("searchBlockVerificacion/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Hallazgos por Bloque") }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val file = generateExcelFile(viewModel, context, aplicacion = "verificacion")
                    file?.let {
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar archivo Excel").apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Descargar Datos Guardados en Excel")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("startScreen/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Panel Principal") }

        Spacer(modifier = Modifier.height(10.dp))
        val bloquesUnicosNoWeb = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" }
            .filter { it["aplicacion"] == "verificacion" }
            .map { it["bloque"] as? String }
            .distinct()


        Text(
            text = "Cantidad de bloques únicos con factores: ${bloquesUnicosNoWeb.size}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )


        val cantidadBloquesWeb = viewModel.datosGuardados.count { it["Origen"] == "Web" }
        Text(
            text = "Cantidad de bloques cargados desde Web: $cantidadBloquesWeb",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 2.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun startFormsVerificacion(navController: NavController, viewModel: DatosGuardadosViewModel, context: Context,username: String) {
    var success: Boolean = false
    var selectedOption by remember { mutableStateOf("") }
    var sampleCountText by remember { mutableStateOf("") }
    val progress = viewModel.getProgress()
    val datos = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
    val progressVisible = viewModel.isProgressVisible()
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var showDialogNew by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user clicks outside of it or presses the back button
                showDialog = false
            },
            title = {
                Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                //TODO
                // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        showDialogNew = true
                        coroutineScope.launch {
                            sendData(viewModel = viewModel, context = context){ success ->
                                showDialogNew = !success
                                println(success)
                                if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                            }
                        }
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    if (showDialogNew) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Se están enviando los datos. Por favor, espere...") },
            confirmButton = {}
        )
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Verificación Cosecha",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val fecha_actualizacion = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .mapNotNull { it["Fecha_Cargue"] as? String }
            .firstOrNull()

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Última Actualización",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_actualizacion}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        val otrafecha = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] != "Web" && it["aplicacion"] == "cosecha" }
            .mapNotNull { it["fecha_muestreo"] as? String } // Asegúrate de que es un String o descártalo
            .maxOrNull() ?: "" // Si no hay ninguna fecha, devuelve una



        Text(
            text = "Fecha Última muestra : $otrafecha",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Usuario: $username",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        var selectedBlock by remember { mutableStateOf("") }
        val blocks = viewModel.obtenerDatosGuardados()
            .filter { it["Origen"] == "Web" }
            .map { it["bloque"] as? String }
            .filterNotNull()

        TextField(
            value = selectedBlock,
            onValueChange = { selectedBlock = it },
            label = { Text("Ingrese el bloque") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
            singleLine = true
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Text("Bloques disponibles:", fontWeight = FontWeight.Bold)
            if (selectedBlock.isNotBlank()) {
                Row {
                    blocks.filter { it.contains(selectedBlock, ignoreCase = true) }.take(4).forEach { block ->
                        ClickableText(
                            text = AnnotatedString(block),
                            onClick = { selectedBlock = block },
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                        )
                    }
                }
            }
        }

        Text(
            text = "*En caso de no tener registrado su bloque, por favor registrar dando clic en botón: Registrar Bloque",
            fontSize = 11.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Left
        )

        val i = 1
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Button(
                onClick = {
                    val blockExists = blocks.contains(selectedBlock.trim().uppercase())
                    if (blockExists) {
                        navController.navigate("formularioCosecha/$i/${selectedBlock.trim().uppercase()}/$username")
                    } else {
                        println("Bloque no registrado. El bloque ingresado no está registrado.")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) { Text("Registrar Hallazgos") }
            Button(
                onClick = {
                    selectedOption = ""
                    sampleCountText = ""
                    navController.navigate("ingresarBloque/$username")
                },
                modifier = Modifier.weight(1f)
            ) { Text("Registrar Bloque") }
        }
        Button(
            onClick = { navController.navigate("searchBlockCosecha/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Observaciones por Bloque") }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val file = generateExcelFile(viewModel, context, aplicacion = "cosecha")
                    file?.let {
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar archivo Excel").apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Descargar Datos Guardados en Excel")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate("startScreen/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text("Panel Principal") }
        Spacer(modifier = Modifier.height(10.dp))

        val bloquesUnicosNoWeb = viewModel.datosGuardados
            .filter { it["aplicacion"] == "cosecha" }
            .mapNotNull { it["Bloque"] as? String }  // Use safe cast to String and filter out nulls
            .distinct()



        Text(
            text = "Cantidad de bloques únicos con observaciones: ${bloquesUnicosNoWeb.size}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )


        val cantidadBloquesWeb = viewModel.datosGuardados.count { it["Origen"] == "Web" }
        Text(
            text = "Cantidad de bloques cargados desde Web: $cantidadBloquesWeb",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 2.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
            )
            .then(modifier)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides if (enabled) ContentAlpha.high else ContentAlpha.disabled) {
            Row(
                modifier = Modifier.padding(contentPadding)
            ) {
                ProvideTextStyle(TextStyle.Default) {
                    content()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formulario(
    bloque: String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }
    var nuevaenfermedadSelected by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    val muestreosActuales = viewModel.obtenerDatosGuardados().filter { it["Origen"] != "Web" }
    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    // Estado para el conteo de cochinillas
    var conteoLeve by remember { mutableStateOf(0) }
    var conteoModerado by remember { mutableStateOf(0) }
    var conteoSevero by remember { mutableStateOf(0) }
    var conteoLeveCochinilla by remember { mutableStateOf(0) }
    var conteoModeradoCochinilla by remember { mutableStateOf(0) }
    var conteoSeveroCochinilla by remember { mutableStateOf(0) }

    var conteoLeveGusano by remember { mutableStateOf(0) }
    var conteoModeradoGusano by remember { mutableStateOf(0) }
    var conteoSeveroGusano by remember { mutableStateOf(0) }
    var conteoLeveGomosis by remember { mutableStateOf(0) }
    var conteoModeradoGomosis by remember { mutableStateOf(0) }
    var conteoSeveroGomosis by remember { mutableStateOf(0) }
    var conteoLeveTecla by remember { mutableStateOf(0) }
    var conteoModeradoTecla by remember { mutableStateOf(0) }
    var conteoSeveroTecla by remember { mutableStateOf(0) }
    var conteoLeveCaracol by remember { mutableStateOf(0) }
    var conteoModeradoCaracol by remember { mutableStateOf(0) }
    var conteoSeveroCaracol by remember { mutableStateOf(0) }
    var conteoLeveBabosas by remember { mutableStateOf(0) }
    var conteoModeradoBabosas by remember { mutableStateOf(0) }
    var conteoSeveroBabosas by remember { mutableStateOf(0) }
    var conteoHallazgoErwinia by remember { mutableStateOf(0) }
    var conteoHallazgoFusarium by remember { mutableStateOf(0) }
    var conteoHallazgoMortalidad by remember { mutableStateOf(0) }
    var conteoHallazgoPhytophtora by remember { mutableStateOf(0) }
    var conteoHallazgoThielaviosi by remember { mutableStateOf(0) }
    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Registrar Hallazgos",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Grupo Forza : $grupo",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fecha Siembra/Poda : $poda",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Área Neta : ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Muestra Actual : $muestreoActual",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        var mostrarErrorTerraza by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Terraza : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (mostrarErrorTerraza) {
            Text(
                text = "Debe registrar una terraza",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Sección de "Babosas"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Babosas
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Babosas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val babosasOptions = listOf("Leve", "Moderada", "Severa")

                babosasOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveBabosas > 0) conteoLeveBabosas--
                                    1 -> if (conteoModeradoBabosas > 0) conteoModeradoBabosas--
                                    2 -> if (conteoSeveroBabosas > 0) conteoSeveroBabosas--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val babosasCount = when (index) {
                            0 -> conteoLeveBabosas
                            1 -> conteoModeradoBabosas
                            2 -> conteoSeveroBabosas
                            else -> 0
                        }

                        TextField(
                            value = babosasCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveBabosas = newValue
                                    1 -> conteoModeradoBabosas = newValue
                                    2 -> conteoSeveroBabosas = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveBabosas++
                                    1 -> conteoModeradoBabosas++
                                    2 -> conteoSeveroBabosas++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Caracol
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Caracol:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val caracolOptions = listOf("Leve", "Moderada", "Severa")

                caracolOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveCaracol > 0) conteoLeveCaracol--
                                    1 -> if (conteoModeradoCaracol > 0) conteoModeradoCaracol--
                                    2 -> if (conteoSeveroCaracol > 0) conteoSeveroCaracol--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val caracolCount = when (index) {
                            0 -> conteoLeveCaracol
                            1 -> conteoModeradoCaracol
                            2 -> conteoSeveroCaracol
                            else -> 0
                        }

                        TextField(
                            value = caracolCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveCaracol = newValue
                                    1 -> conteoModeradoCaracol = newValue
                                    2 -> conteoSeveroCaracol = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveCaracol++
                                    1 -> conteoModeradoCaracol++
                                    2 -> conteoSeveroCaracol++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Cochinilla
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Cochinilla:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val cochinillaOptions = listOf("Leve", "Moderada", "Severa")

                cochinillaOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveCochinilla > 0) conteoLeveCochinilla--
                                    1 -> if (conteoModeradoCochinilla > 0) conteoModeradoCochinilla--
                                    2 -> if (conteoSeveroCochinilla > 0) conteoSeveroCochinilla--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val cochinillaCount = when (index) {
                            0 -> conteoLeveCochinilla
                            1 -> conteoModeradoCochinilla
                            2 -> conteoSeveroCochinilla
                            else -> 0
                        }

                        TextField(
                            value = cochinillaCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveCochinilla = newValue
                                    1 -> conteoModeradoCochinilla = newValue
                                    2 -> conteoSeveroCochinilla = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveCochinilla++
                                    1 -> conteoModeradoCochinilla++
                                    2 -> conteoSeveroCochinilla++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Erwinia
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Erwinia:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoErwinia > 0) {
                                conteoHallazgoErwinia--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoErwinia.toString(),
                        onValueChange = { newValue ->
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoHallazgoErwinia = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoErwinia++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Fusarium
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Fusarium:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoFusarium> 0) {
                                conteoHallazgoFusarium--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoFusarium.toString(),
                        onValueChange = { newValue ->
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoHallazgoFusarium = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoFusarium++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Gomosis
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Gomosis:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val gomosisOptions = listOf("Leve", "Moderada", "Severa")

                gomosisOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveGomosis > 0) conteoLeveGomosis--
                                    1 -> if (conteoModeradoGomosis > 0) conteoModeradoGomosis--
                                    2 -> if (conteoSeveroGomosis > 0) conteoSeveroGomosis--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable con estilo consistente
                        val gomosisCount = when (index) {
                            0 -> conteoLeveGomosis
                            1 -> conteoModeradoGomosis
                            2 -> conteoSeveroGomosis
                            else -> 0
                        }

                        TextField(
                            value = gomosisCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveGomosis = newValue
                                    1 -> conteoModeradoGomosis = newValue
                                    2 -> conteoSeveroGomosis = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto ajustado
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveGomosis++
                                    1 -> conteoModeradoGomosis++
                                    2 -> conteoSeveroGomosis++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Gusano Soldado
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Gusano Soldado:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val gusanoOptions = listOf("Leve", "Moderada", "Severa")

                gusanoOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveGusano > 0) conteoLeveGusano--
                                    1 -> if (conteoModeradoGusano > 0) conteoModeradoGusano--
                                    2 -> if (conteoSeveroGusano > 0) conteoSeveroGusano--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val gusanoCount = when (index) {
                            0 -> conteoLeveGusano
                            1 -> conteoModeradoGusano
                            2 -> conteoSeveroGusano
                            else -> 0
                        }

                        TextField(
                            value = gusanoCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveGusano = newValue
                                    1 -> conteoModeradoGusano = newValue
                                    2 -> conteoSeveroGusano = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveGusano++
                                    1 -> conteoModeradoGusano++
                                    2 -> conteoSeveroGusano++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Mortalidad
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mortalidad:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoMortalidad > 0) {
                                conteoHallazgoMortalidad--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoMortalidad.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoMortalidad = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoMortalidad++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Phytophtora
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Phytophtora:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoPhytophtora > 0) {
                                conteoHallazgoPhytophtora--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoPhytophtora.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoPhytophtora = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto más grande
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoPhytophtora++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Tecla
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Tecla:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val teclaOptions = listOf("Leve", "Moderada", "Severa")

                teclaOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveTecla > 0) conteoLeveTecla--
                                    1 -> if (conteoModeradoTecla > 0) conteoModeradoTecla--
                                    2 -> if (conteoSeveroTecla > 0) conteoSeveroTecla--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val teclaCount = when (index) {
                            0 -> conteoLeveTecla
                            1 -> conteoModeradoTecla
                            2 -> conteoSeveroTecla
                            else -> 0
                        }

                        TextField(
                            value = teclaCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveTecla = newValue
                                    1 -> conteoModeradoTecla = newValue
                                    2 -> conteoSeveroTecla = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveTecla++
                                    1 -> conteoModeradoTecla++
                                    2 -> conteoSeveroTecla++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Thielaviosi
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Thielaviosi:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoThielaviosi > 0) conteoHallazgoThielaviosi--
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable
                    TextField(
                        value = conteoHallazgoThielaviosi.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoThielaviosi = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto más grande
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoThielaviosi++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha Muestra Actual:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected,
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fecha Sistema",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_ahora()}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if (peso == "") {
                        mostrarErrorTerraza = true
                    } else {
                        println("Esto se esta guardadno"+conteoLeveCochinilla)
                        mostrarErrorTerraza = false
                        guardadoExitoso = guardar(
                            bloque = bloque,
                            terraza = peso,
                            cochinilla_leve = conteoLeveCochinilla,
                            cochinilla_moderada = conteoModeradoCochinilla,
                            cochinilla_severa = conteoSeveroCochinilla,
                            gusano_leve = conteoLeveGusano,
                            gusano_moderada = conteoModeradoGusano,
                            gusano_severa = conteoSeveroGusano,
                            gomosis_leve = conteoLeveGomosis,
                            gomosis_moderada = conteoModeradoGomosis,
                            gomosis_severa = conteoSeveroGomosis,
                            tecla_leve = conteoLeveTecla,
                            tecla_moderada = conteoModeradoTecla,
                            tecla_severa= conteoSeveroTecla,
                            caracol_leve = conteoLeveCaracol,
                            caracol_moderada = conteoModeradoCaracol,
                            caracol_severa = conteoSeveroCaracol,
                            babosas_leve = conteoLeveBabosas,
                            babosas_moderada = conteoModeradoBabosas,
                            babosas_severa = conteoSeveroBabosas,
                            Erwinia = conteoHallazgoErwinia,
                            Fusarium= conteoHallazgoFusarium,
                            Mortalidad= conteoHallazgoMortalidad,
                            Phytophtora= conteoHallazgoPhytophtora,
                            Thielaviosi= conteoHallazgoThielaviosi,
                            nuevaenfermedad = nuevaenfermedadSelected,
                            fecha = fecha_ahora(),
                            fecha_muestreo = nuevaFecha,
                            viewModel = viewModel,
                            usuario = username,
                            origen = "enfermedades",
                        )
                        if (guardadoExitoso) {
                            peso = ""
                            conteoLeveCochinilla = 0
                            conteoModeradoCochinilla = 0
                            conteoSeveroCochinilla = 0
                            conteoLeveGusano = 0
                            conteoModeradoGusano = 0
                            conteoSeveroGusano = 0
                            conteoLeveGomosis = 0
                            conteoModeradoGomosis = 0
                            conteoSeveroGomosis = 0
                            conteoLeveTecla = 0
                            conteoModeradoTecla = 0
                            conteoSeveroTecla = 0
                            conteoLeveCaracol = 0
                            conteoModeradoCaracol = 0
                            conteoSeveroCaracol = 0
                            conteoLeveBabosas = 0
                            conteoModeradoBabosas = 0
                            conteoSeveroBabosas = 0
                            conteoHallazgoErwinia = 0
                            conteoHallazgoFusarium = 0
                            conteoHallazgoMortalidad = 0
                            conteoHallazgoPhytophtora = 0
                            conteoHallazgoThielaviosi = 0
                            nuevaenfermedadSelected = ""
                            nuevaFecha = ""
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startforms/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formularioCosecha(
    bloque: String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }
    var nuevaenfermedadSelected by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    val muestreosActuales = viewModel.obtenerDatosGuardados().filter { it["Origen"] != "Web" }
    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    // Estado para el conteo de cochinillas
    var conteoFrutaDejada by remember { mutableStateOf(0) }
    var conteoPlantaSinParir by remember { mutableStateOf(0) }
    var conteoFrutaJoven by remember { mutableStateOf(0) }
    var conteoFrutaNoAprovechable by remember { mutableStateOf(0) }
    var conteoFrutaAdelantada by remember { mutableStateOf(0) }
    var conteoCoronas by remember { mutableStateOf(0) }
    var conteoFrutaEnferma by remember { mutableStateOf(0) }
    var conteoDañoMecanico by remember { mutableStateOf(0) }
    var conteoQuemaSol by remember { mutableStateOf(0) }
    var conteoMortalidad by remember { mutableStateOf(0) }
    var conteoDescarteEntreCamas by remember { mutableStateOf(0) }
    var conteoGolpeAgua by remember { mutableStateOf(0) }
    var conteoBajoPeso by remember { mutableStateOf(0) }
    var conteoAusente by remember { mutableStateOf(0) }


    var conteoLeveCochinilla by remember { mutableStateOf(0) }
    var conteoModeradoCochinilla by remember { mutableStateOf(0) }
    var conteoSeveroCochinilla by remember { mutableStateOf(0) }

    var conteoLeveGusano by remember { mutableStateOf(0) }
    var conteoModeradoGusano by remember { mutableStateOf(0) }
    var conteoSeveroGusano by remember { mutableStateOf(0) }
    var conteoLeveGomosis by remember { mutableStateOf(0) }
    var conteoModeradoGomosis by remember { mutableStateOf(0) }
    var conteoSeveroGomosis by remember { mutableStateOf(0) }
    var conteoLeveTecla by remember { mutableStateOf(0) }
    var conteoModeradoTecla by remember { mutableStateOf(0) }
    var conteoSeveroTecla by remember { mutableStateOf(0) }
    var conteoLeveCaracol by remember { mutableStateOf(0) }
    var conteoModeradoCaracol by remember { mutableStateOf(0) }
    var conteoSeveroCaracol by remember { mutableStateOf(0) }
    var conteoLeveBabosas by remember { mutableStateOf(0) }
    var conteoModeradoBabosas by remember { mutableStateOf(0) }
    var conteoSeveroBabosas by remember { mutableStateOf(0) }
    var conteoHallazgoErwinia by remember { mutableStateOf(0) }
    var conteoHallazgoFusarium by remember { mutableStateOf(0) }
    var conteoHallazgoMortalidad by remember { mutableStateOf(0) }
    var conteoHallazgoPhytophtora by remember { mutableStateOf(0) }
    var conteoHallazgoThielaviosi by remember { mutableStateOf(0) }
    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val poblacionString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("poblacion") as? String ?: "0.0"
    val poblacion = try {
        poblacionString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val total_frutasString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("total_frutas") as? String ?: "0.0"
    val total_frutas = try {
        total_frutasString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Verificación Cosecha",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Población : ${formatNumberWithCustomSeparators2(poblacion)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Fruta cosechada : ${formatNumberWithCustomSeparators2(total_frutas)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )


        Text(
            text = "Área Neta : ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp) // Reduzco el padding vertical
        ) {
            // Columna para "Ausente:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(0.dp), // Eliminar padding extra
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Ausente:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoAusente.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoAusente = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp) // Reduzco el padding vertical
        ) {
            // Columna para "Bajo peso:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Bajo peso:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoBajoPeso.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoBajoPeso = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Coronas:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Coronas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoCoronas.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoCoronas = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Daño mecánico:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Daño mecánico:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoDañoMecanico.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoDañoMecanico = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Descarte entre camas:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Descarte entre camas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoDescarteEntreCamas.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoDescarteEntreCamas = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta dejada por cosecha:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta dejada por cosecha:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaDejada.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaDejada = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta no aprovechable:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta no aprovechable:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaNoAprovechable.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaNoAprovechable = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta adelantada:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta adelantada:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaAdelantada.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaAdelantada = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta enferma:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta enferma:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaEnferma.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaEnferma = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Golpe de agua:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Golpe de agua:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoGolpeAgua.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoGolpeAgua = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Mortalidad:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Mortalidad:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoMortalidad.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoMortalidad = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Plantas sin parir:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Plantas sin parir:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Ajusta el tamaño del campo de texto
                ) {
                    TextField(
                        value = conteoPlantaSinParir.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoPlantaSinParir = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent // Sin línea de indicador al no estar enfocado
                        )
                    )
                }
            }
        }

        // Contador de Quema de sol
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Quema de sol:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Quema de sol:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Ajusta el tamaño del campo de texto
                ) {
                    TextField(
                        value = conteoQuemaSol.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoQuemaSol = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent // Sin línea de indicador al no estar enfocado
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha factores:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected,
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    guardadoExitoso = guardarCosecha(
                        bloque = bloque,
                        frutaDejada = conteoFrutaDejada,
                        plantaSinParir = conteoPlantaSinParir,
                        frutaJoven = conteoFrutaJoven,
                        frutaNoAprovechable = conteoFrutaNoAprovechable,
                        frutaAdelantada = conteoFrutaAdelantada,
                        coronas = conteoCoronas,
                        frutaEnferma = conteoFrutaEnferma,
                        dañoMecanico = conteoDañoMecanico,
                        quemaSol = conteoQuemaSol,
                        mortalidad = conteoMortalidad,
                        descarteCamas = conteoDescarteEntreCamas,
                        goleAgua = conteoGolpeAgua,
                        bajoPeso= conteoBajoPeso,
                        ausente = conteoAusente,
                        nuevaenfermedad = nuevaenfermedadSelected,
                        fecha = fecha_ahora(),
                        fecha_muestreo = nuevaFecha,
                        viewModel = viewModel,
                        usuario = username,
                        origen = "cosecha",
                    )
                    if (guardadoExitoso) {
                        conteoFrutaDejada = 0
                        conteoPlantaSinParir = 0
                        conteoFrutaJoven = 0
                        conteoFrutaNoAprovechable = 0
                        conteoFrutaAdelantada = 0
                        conteoCoronas = 0
                        conteoFrutaEnferma = 0
                        conteoDañoMecanico = 0
                        conteoQuemaSol = 0
                        conteoMortalidad = 0
                        conteoDescarteEntreCamas = 0
                        conteoGolpeAgua = 0
                        conteoBajoPeso = 0
                        conteoAusente = 0
                        nuevaenfermedadSelected = ""
                        nuevaFecha = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startformsVerificacion/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formularioCosechaEditar(
    bloque: String,
    fecha:String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }
    var peso by remember { mutableStateOf("") }
    val muestreosActuales = viewModel.obtenerDatosGuardados().filter { it["Origen"] != "Web" }
    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    val datoEditar =  viewModel.datosGuardados.find { it["fecha"] == fecha && it["bloque"] == bloque && it["Origen"] != "Web" }
    var nuevaenfermedadSelected by remember { mutableStateOf(datoEditar?.get("observaciones") as? String ?: "") }
    var conteoFrutaDejada by remember {mutableStateOf(datoEditar?.get("Fruta_dejada_por_cosecha") as? Int ?: 0) }
    var conteoPlantaSinParir by remember { mutableStateOf(datoEditar?.get("Plantas_sin_parir") as? Int ?: 0)}
    var conteoFrutaJoven by remember { mutableStateOf(datoEditar?.get("Fruta_joven") as? Int ?: 0) }
    var conteoFrutaNoAprovechable by remember {  mutableStateOf(datoEditar?.get("Fruta_no_aprovechable") as? Int ?: 0)  }
    var conteoFrutaAdelantada by remember {   mutableStateOf(datoEditar?.get("Fruta_adelantada") as? Int ?: 0)  }
    var conteoCoronas by remember { mutableStateOf(datoEditar?.get("Coronas") as? Int ?: 0)   }
    var conteoFrutaEnferma by remember { mutableStateOf(datoEditar?.get("Fruta_enferma") as? Int ?: 0)   }
    var conteoDañoMecanico by remember { mutableStateOf(datoEditar?.get("Daño_mecanico") as? Int ?: 0)   }
    var conteoQuemaSol by remember { mutableStateOf(datoEditar?.get("Quema_de_Sol") as? Int ?: 0) }
    var conteoMortalidad by remember { mutableStateOf(datoEditar?.get("Mortalidad") as? Int ?: 0)}
    var conteoDescarteEntreCamas by remember { mutableStateOf(datoEditar?.get("Descarte_entre_camas") as? Int ?: 0) }
    var conteoGolpeAgua by remember { mutableStateOf(datoEditar?.get("Golpe_de_agua") as? Int ?: 0)}
    var conteoBajoPeso by remember { mutableStateOf(datoEditar?.get("Bajo_peso") as? Int ?: 0)}
    var conteoAusente by remember { mutableStateOf(datoEditar?.get("Ausente") as? Int ?: 0)}


    var conteoLeveCochinilla by remember { mutableStateOf(0) }
    var conteoModeradoCochinilla by remember { mutableStateOf(0) }
    var conteoSeveroCochinilla by remember { mutableStateOf(0) }

    var conteoLeveGusano by remember { mutableStateOf(0) }
    var conteoModeradoGusano by remember { mutableStateOf(0) }
    var conteoSeveroGusano by remember { mutableStateOf(0) }
    var conteoLeveGomosis by remember { mutableStateOf(0) }
    var conteoModeradoGomosis by remember { mutableStateOf(0) }
    var conteoSeveroGomosis by remember { mutableStateOf(0) }
    var conteoLeveTecla by remember { mutableStateOf(0) }
    var conteoModeradoTecla by remember { mutableStateOf(0) }
    var conteoSeveroTecla by remember { mutableStateOf(0) }
    var conteoLeveCaracol by remember { mutableStateOf(0) }
    var conteoModeradoCaracol by remember { mutableStateOf(0) }
    var conteoSeveroCaracol by remember { mutableStateOf(0) }
    var conteoLeveBabosas by remember { mutableStateOf(0) }
    var conteoModeradoBabosas by remember { mutableStateOf(0) }
    var conteoSeveroBabosas by remember { mutableStateOf(0) }
    var conteoHallazgoErwinia by remember { mutableStateOf(0) }
    var conteoHallazgoFusarium by remember { mutableStateOf(0) }
    var conteoHallazgoMortalidad by remember { mutableStateOf(0) }
    var conteoHallazgoPhytophtora by remember { mutableStateOf(0) }
    var conteoHallazgoThielaviosi by remember { mutableStateOf(0) }
    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val poblacionString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("poblacion") as? String ?: "0.0"
    val poblacion = try {
        poblacionString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val total_frutasString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("total_frutas") as? String ?: "0.0"
    val total_frutas = try {
        total_frutasString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Verificación Cosecha",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Población : ${formatNumberWithCustomSeparators2(poblacion)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Fruta cosechada : ${formatNumberWithCustomSeparators2(total_frutas)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )


        Text(
            text = "Área Neta : ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp) // Reduzco el padding vertical
        ) {
            // Columna para "Ausente:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(0.dp), // Eliminar padding extra
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Ausente:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoAusente.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoAusente = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp) // Reduzco el padding vertical
        ) {
            // Columna para "Bajo peso:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Bajo peso:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoBajoPeso.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoBajoPeso = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Coronas:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Coronas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoCoronas.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoCoronas = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Daño mecánico:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Daño mecánico:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoDañoMecanico.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoDañoMecanico = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Descarte entre camas:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Descarte entre camas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoDescarteEntreCamas.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoDescarteEntreCamas = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta dejada por cosecha:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta dejada por cosecha:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaDejada.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaDejada = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta no aprovechable:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta no aprovechable:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaNoAprovechable.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaNoAprovechable = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta adelantada:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta adelantada:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaAdelantada.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaAdelantada = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Fruta enferma:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Fruta enferma:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoFrutaEnferma.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoFrutaEnferma = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Golpe de agua:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Golpe de agua:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoGolpeAgua.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoGolpeAgua = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Mortalidad:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Mortalidad:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Mantener el tamaño del campo de entrada
                ) {
                    TextField(
                        value = conteoMortalidad.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoMortalidad = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Plantas sin parir:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Plantas sin parir:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Ajusta el tamaño del campo de texto
                ) {
                    TextField(
                        value = conteoPlantaSinParir.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoPlantaSinParir = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent // Sin línea de indicador al no estar enfocado
                        )
                    )
                }
            }
        }

        // Contador de Quema de sol
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp)
        ) {
            // Columna para "Quema de sol:"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Text(
                    text = "Quema de sol:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center // Centrar el texto dentro de la columna
                )
            }

            // Columna para el campo de entrada numérica
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                verticalArrangement = Arrangement.Center // Centrar verticalmente
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp) // Ajusta el tamaño del campo de texto
                ) {
                    TextField(
                        value = conteoQuemaSol.toString(),
                        onValueChange = { newValue ->
                            // Validar que la entrada es un número positivo
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoQuemaSol = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Sin línea de indicador al estar enfocado
                            unfocusedIndicatorColor = Color.Transparent // Sin línea de indicador al no estar enfocado
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha factores:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected,
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    viewModel.borrarDatoPorFechaOrigenYBloque(fecha = fecha, bloque = bloque)
                    guardadoExitoso = guardarCosecha(
                        bloque = bloque,
                        frutaDejada = conteoFrutaDejada,
                        plantaSinParir = conteoPlantaSinParir,
                        frutaJoven = conteoFrutaJoven,
                        frutaNoAprovechable = conteoFrutaNoAprovechable,
                        frutaAdelantada = conteoFrutaAdelantada,
                        coronas = conteoCoronas,
                        frutaEnferma = conteoFrutaEnferma,
                        dañoMecanico = conteoDañoMecanico,
                        quemaSol = conteoQuemaSol,
                        mortalidad = conteoMortalidad,
                        descarteCamas = conteoDescarteEntreCamas,
                        goleAgua = conteoGolpeAgua,
                        bajoPeso= conteoBajoPeso,
                        ausente = conteoAusente,
                        nuevaenfermedad = nuevaenfermedadSelected,
                        fecha = fecha_ahora(),
                        fecha_muestreo = nuevaFecha,
                        viewModel = viewModel,
                        usuario = username,
                        origen = "cosecha",
                    )
                    if (guardadoExitoso) {
                        navController.navigate("searchBlockCosecha/$username")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startformsVerificacion/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun formularioVerificacion(
    bloque: String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }
    var nuevaenfermedadSelected by remember { mutableStateOf("") }
    var porcentajeMalezaControlada by remember { mutableStateOf("0") }
    var porcentajeMalezaLeve by remember { mutableStateOf("0") }
    var porcentajeMalezaModerada by remember { mutableStateOf("0") }
    var porcentajeMalezaSevera by remember { mutableStateOf("0") }
    val muestreosActuales = viewModel.obtenerDatosGuardados().filter { it["Origen"] != "Web" }
    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    var estadoPuenteSelected by remember { mutableStateOf("") }
    var dropdownEstadoPuenteExpanded = remember { mutableStateOf(false) }
    val estadoPuenteOptions = listOf("Sin puente","En mal estado","Cumple","")
    var estadoTrinchoSelected by remember { mutableStateOf("") }
    var dropdownEstadoTrinchoExpanded = remember { mutableStateOf(false) }
    val estadoTrinchoOptions = listOf("Sin trincho","En mal estado","Cumple","")
    var observacionSelected by remember { mutableStateOf("") }
    var dropdownObservacionExpanded = remember { mutableStateOf(false) }
    val observacionOptions = listOf("Espinas con encharcamiento","Espinas sedimentadas","Recolectores con encharcamiento","Ninguno","")
    var estadoViasSelected by remember { mutableStateOf("") }
    var dropdownEstadoViasExpanded = remember { mutableStateOf(false) }
    val estadoViasOptions = listOf("Encharcamiento","Malezas","Ninguno","")
    var variablesSelected by remember { mutableStateOf("") }
    var dropdownVariablesExpanded = remember { mutableStateOf(false) }
    val variablesOptions = listOf("Placas","Daño maquinaria","Estado recolector","Floración","Enfermas","Ninguno","")
    var mostrarErrorPorcentajeMaleza by remember { mutableStateOf(false) }
    var mostrarErrorEstadoPuentes by remember { mutableStateOf(false) }
    var mostrarErrorEstadoTrincho by remember { mutableStateOf(false) }
    var mostrarErrorEstadoObservaciones by remember { mutableStateOf(false) }
    var mostrarErrorEstadoVias by remember { mutableStateOf(false) }
    var mostrarErrorEstadoVariable by remember { mutableStateOf(false) }

    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }

    val poblacionString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("poblacion") as? String ?: "0.0"
    val poblacion = try {
        poblacionString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Registrar Hallazgos",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Población : ${formatNumberWithCustomSeparators2(poblacion)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fecha Siembra Último Reporte: $poda",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Área Neta: ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Muestra Actual : $muestreoActual",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        fun isValidPercentage(value: String): Boolean {
            val percentage = value.toIntOrNull()
            return percentage != null && percentage in 0..100
        }

        fun isTotalPercentageValid(): Boolean {
            val controlada = porcentajeMalezaControlada.toIntOrNull() ?: 0
            val leve = porcentajeMalezaLeve.toIntOrNull() ?: 0
            val moderada = porcentajeMalezaModerada.toIntOrNull() ?: 0
            val severa = porcentajeMalezaSevera.toIntOrNull() ?: 0
            return controlada + leve + moderada + severa <= 100
        }

        OutlinedTextField(
            value = porcentajeMalezaControlada,
            onValueChange = { newValue ->
                // Validate the percentage value is a number between 0 and 100
                val percentage = newValue.toFloatOrNull()
                if (percentage != null && percentage in 0f..100f) {
                    porcentajeMalezaControlada = newValue
                } else {
                    // Optionally reset the value or provide feedback
                    porcentajeMalezaControlada = "0"
                }
            },
            label = { Text("Porcentaje de maleza controlada : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            ),
            singleLine = true // Ensure it's a single-line input
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaLeve,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaLeve = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaLeve = "0"
                    }
                }
            },
            label = { Text("Porcentaje de maleza leve : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaModerada,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaModerada = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaModerada = "0"
                    }
                }
            },
            label = { Text("Porcentaje de maleza moderada : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
            ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaSevera,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaSevera = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaSevera = "0"
                    }
                }
            },
            label = { Text("Porcentaje de maleza severa : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
            ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            )
        )
        if (mostrarErrorPorcentajeMaleza) {
            Text(
                text = "La suma de porcentajes de Maleza debe ser 100.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoPuenteExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado puente: $estadoPuenteSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoPuenteExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoPuenteExpanded.value,
                onDismissRequest = { dropdownEstadoPuenteExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoPuenteOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoPuenteSelected = option
                            dropdownEstadoPuenteExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoPuenteSelected = option
                            dropdownEstadoPuenteExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoPuentes) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoTrinchoExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado trincho: $estadoTrinchoSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoTrinchoExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoTrinchoExpanded.value,
                onDismissRequest = { dropdownEstadoTrinchoExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoTrinchoOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoTrinchoSelected = option
                            dropdownEstadoTrinchoExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoTrinchoSelected = option
                            dropdownEstadoTrinchoExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoTrincho) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownObservacionExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Observaciones Drenajes: $observacionSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownObservacionExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownObservacionExpanded.value,
                onDismissRequest = { dropdownObservacionExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                observacionOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            observacionSelected = option
                            dropdownObservacionExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            observacionSelected = option
                            dropdownObservacionExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoObservaciones) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoViasExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado Vias: $estadoViasSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoViasExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoViasExpanded.value,
                onDismissRequest = { dropdownEstadoViasExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoViasOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoViasSelected = option
                            dropdownEstadoViasExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoViasSelected = option
                            dropdownEstadoViasExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoVias) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownVariablesExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Variables observadas: $variablesSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownVariablesExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownVariablesExpanded.value,
                onDismissRequest = { dropdownVariablesExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                variablesOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            variablesSelected = option
                            dropdownVariablesExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            variablesSelected = option
                            dropdownVariablesExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoVariable) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha Hallazgo:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected,
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fecha Sistema",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_ahora()}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if ((porcentajeMalezaControlada.toIntOrNull()!!+ porcentajeMalezaLeve.toIntOrNull()!! +porcentajeMalezaModerada.toIntOrNull()!!+porcentajeMalezaSevera.toIntOrNull()!!)!=100 || estadoPuenteSelected == "" || estadoTrinchoSelected == "" || observacionSelected == "" || estadoViasSelected == "" || variablesSelected == "") {
                        if ((porcentajeMalezaControlada.toIntOrNull()!!+ porcentajeMalezaLeve.toIntOrNull()!! +porcentajeMalezaModerada.toIntOrNull()!!+porcentajeMalezaSevera.toIntOrNull()!!)!=100) {
                            mostrarErrorPorcentajeMaleza = true
                        } else {
                            mostrarErrorPorcentajeMaleza = false
                        }
                        if (estadoPuenteSelected == "") {
                            mostrarErrorEstadoPuentes = true
                        } else {
                            mostrarErrorEstadoPuentes = false
                        }
                        if (estadoTrinchoSelected == "") {
                            mostrarErrorEstadoTrincho = true
                        } else {
                            mostrarErrorEstadoTrincho = false
                        }
                        if (observacionSelected == "" ) {
                            mostrarErrorEstadoObservaciones = true
                        } else {
                            mostrarErrorEstadoObservaciones = false
                        }
                        if (estadoViasSelected == "" ) {
                            mostrarErrorEstadoVias = true
                        } else {
                            mostrarErrorEstadoVias = false
                        }
                        if (variablesSelected == "") {
                            mostrarErrorEstadoVariable = true
                        } else {
                            mostrarErrorEstadoVariable = false
                        }
                    } else {
                        guardadoExitoso = guardarVerificacion(
                            bloque = bloque,
                            porcentajeMalezaControlada = porcentajeMalezaControlada ?: "0",
                            porcentajeMalezaLeve = porcentajeMalezaLeve ?: "0",
                            porcentajeMalezaModerada = porcentajeMalezaModerada ?: "0",
                            porcentajeMalezaSevera = porcentajeMalezaSevera ?: "0",
                            estadoViasSelected = estadoViasSelected,
                            estadoPuenteSelected = estadoPuenteSelected,
                            estadoTrinchoSelected = estadoTrinchoSelected,
                            observacionSelected = observacionSelected,
                            variablesSelected = variablesSelected,
                            nuevaenfermedad = nuevaenfermedadSelected,
                            fecha = fecha_ahora(),
                            fecha_muestreo = nuevaFecha,
                            viewModel = viewModel,
                            usuario = username,
                            origen = "verificacion",
                        )
                        if (guardadoExitoso) {
                            porcentajeMalezaControlada = ""
                            porcentajeMalezaLeve = ""
                            porcentajeMalezaModerada = ""
                            porcentajeMalezaSevera = ""
                            estadoPuenteSelected = ""
                            estadoViasSelected = ""
                            estadoTrinchoSelected = ""
                            observacionSelected = ""
                            variablesSelected = ""
                            nuevaenfermedadSelected = ""
                            nuevaFecha = ""
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startFormsVerificacionCosecha/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun formularioVerificacionEditar(
    bloque: String,
    fecha: String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val datoEditar =  viewModel.datosGuardados.find { it["fecha"] == fecha && it["bloque"] == bloque && it["Origen"] != "Web" }
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }
    var nuevaenfermedadSelected by remember { mutableStateOf(datoEditar?.get("observaciones") as? String ?: "") }
    var porcentajeMalezaControlada by remember {mutableStateOf(datoEditar?.get("porcentaje_maleza_controlada") as? String ?: "") }
    var porcentajeMalezaLeve by remember { mutableStateOf(datoEditar?.get("porcentaje_maleza_leve") as? String ?: "")  }
    var porcentajeMalezaModerada by remember { mutableStateOf(datoEditar?.get("porcentaje_maleza_moderada") as? String ?: "") }
    var porcentajeMalezaSevera by remember { mutableStateOf(datoEditar?.get("porcentaje_maleza_severa") as? String ?: "") }
    val muestreosActuales = viewModel.obtenerDatosGuardados().filter { it["Origen"] != "Web" }
    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    var estadoPuenteSelected by remember { mutableStateOf(datoEditar?.get("estado_puente") as? String ?: "")  }
    var dropdownEstadoPuenteExpanded = remember { mutableStateOf(false) }
    val estadoPuenteOptions = listOf("Sin puente","En mal estado","Cumple","")
    var estadoTrinchoSelected by remember {  mutableStateOf(datoEditar?.get("estado_trincho") as? String ?: "") }
    var dropdownEstadoTrinchoExpanded = remember { mutableStateOf(false) }
    val estadoTrinchoOptions = listOf("Sin trincho","En mal estado","Cumple","")
    var observacionSelected by remember {  mutableStateOf(datoEditar?.get("observacion") as? String ?: "") }
    var dropdownObservacionExpanded = remember { mutableStateOf(false) }
    val observacionOptions = listOf("Espinas con encharcamiento","Espinas sedimentadas","Recolectores con encharcamiento","Ninguno","")
    var estadoViasSelected by remember {  mutableStateOf(datoEditar?.get("estado_vias") as? String ?: "") }
    var dropdownEstadoViasExpanded = remember { mutableStateOf(false) }
    val estadoViasOptions = listOf("Encharcamiento","Malezas","Ninguno","")
    var variablesSelected by remember { mutableStateOf(datoEditar?.get("variable") as? String ?: "") }
    var dropdownVariablesExpanded = remember { mutableStateOf(false) }
    val variablesOptions = listOf("Placas","Daño maquinaria","Estado recolector","Floración","Enfermas","Ninguno","")
    var mostrarErrorPorcentajeMaleza by remember { mutableStateOf(false) }
    var mostrarErrorEstadoPuentes by remember { mutableStateOf(false) }
    var mostrarErrorEstadoTrincho by remember { mutableStateOf(false) }
    var mostrarErrorEstadoObservaciones by remember { mutableStateOf(false) }
    var mostrarErrorEstadoVias by remember { mutableStateOf(false) }
    var mostrarErrorEstadoVariable by remember { mutableStateOf(false) }

    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }

    val poblacionString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("poblacion") as? String ?: "0.0"
    val poblacion = try {
        poblacionString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Registrar Hallazgos",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Población : ${formatNumberWithCustomSeparators2(poblacion)}",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fecha Siembra Último Reporte: $poda",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Área Neta: ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Muestra Actual : $muestreoActual",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        fun isValidPercentage(value: String): Boolean {
            val percentage = value.toIntOrNull()
            return percentage != null && percentage in 0..100
        }

        fun isTotalPercentageValid(): Boolean {
            val controlada = porcentajeMalezaControlada.toIntOrNull() ?: 0
            val leve = porcentajeMalezaLeve.toIntOrNull() ?: 0
            val moderada = porcentajeMalezaModerada.toIntOrNull() ?: 0
            val severa = porcentajeMalezaSevera.toIntOrNull() ?: 0
            return controlada + leve + moderada + severa <= 100
        }

        OutlinedTextField(
            value = porcentajeMalezaControlada,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaControlada = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaControlada = ""
                    }
                }
            },
            label = { Text("Porcentaje de maleza controlada : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
            ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaLeve,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaLeve = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaLeve = ""
                    }
                }
            },
            label = { Text("Porcentaje de maleza leve : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaModerada,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaModerada = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaModerada = ""
                    }
                }
            },
            label = { Text("Porcentaje de maleza moderada : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
            ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = porcentajeMalezaSevera,
            onValueChange = {
                if (isValidPercentage(it)) {
                    porcentajeMalezaSevera = it
                    if (!isTotalPercentageValid()) {
                        porcentajeMalezaSevera = ""
                    }
                }
            },
            label = { Text("Porcentaje de maleza severa : ") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester)
            ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Clear focus to hide the keyboard
                }
            )
        )
        if (mostrarErrorPorcentajeMaleza) {
            Text(
                text = "La suma de porcentajes de Maleza debe ser 100.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoPuenteExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado puente: $estadoPuenteSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoPuenteExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoPuenteExpanded.value,
                onDismissRequest = { dropdownEstadoPuenteExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoPuenteOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoPuenteSelected = option
                            dropdownEstadoPuenteExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoPuenteSelected = option
                            dropdownEstadoPuenteExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoPuentes) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoTrinchoExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado trincho: $estadoTrinchoSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoTrinchoExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoTrinchoExpanded.value,
                onDismissRequest = { dropdownEstadoTrinchoExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoTrinchoOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoTrinchoSelected = option
                            dropdownEstadoTrinchoExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoTrinchoSelected = option
                            dropdownEstadoTrinchoExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoTrincho) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownObservacionExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Observaciones Drenajes: $observacionSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownObservacionExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownObservacionExpanded.value,
                onDismissRequest = { dropdownObservacionExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                observacionOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            observacionSelected = option
                            dropdownObservacionExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            observacionSelected = option
                            dropdownObservacionExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoObservaciones) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownEstadoViasExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Estado Vias: $estadoViasSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownEstadoViasExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownEstadoViasExpanded.value,
                onDismissRequest = { dropdownEstadoViasExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estadoViasOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            estadoViasSelected = option
                            dropdownEstadoViasExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            estadoViasSelected = option
                            dropdownEstadoViasExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoVias) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .height(52.dp)
                .clickable {
                    dropdownVariablesExpanded.value = true
                    focusManager.clearFocus()
                }
        ) {
            Text(
                text = "Variables observadas: $variablesSelected",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth().clickable(onClick = {
                        dropdownVariablesExpanded.value = true
                    })
            )
            DropdownMenu(
                expanded = dropdownVariablesExpanded.value,
                onDismissRequest = { dropdownVariablesExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                variablesOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            variablesSelected = option
                            dropdownVariablesExpanded.value = false
                        },
                        modifier = Modifier.fillMaxWidth().clickable {
                            variablesSelected = option
                            dropdownVariablesExpanded.value = false
                        }
                    ) {
                        Text(
                            text = option,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
        if (mostrarErrorEstadoVariable) {
            Text(
                text = "Por favor, seleccione una opción.",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha Hallazgo:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected,
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fecha Sistema",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_ahora()}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if ((porcentajeMalezaControlada.toIntOrNull()!!+ porcentajeMalezaLeve.toIntOrNull()!! +porcentajeMalezaModerada.toIntOrNull()!!+porcentajeMalezaSevera.toIntOrNull()!!)!=100 || estadoPuenteSelected == "" || estadoTrinchoSelected == "" || observacionSelected == "" || estadoViasSelected == "" || variablesSelected == "") {
                        if ((porcentajeMalezaControlada.toIntOrNull()!!+ porcentajeMalezaLeve.toIntOrNull()!! +porcentajeMalezaModerada.toIntOrNull()!!+porcentajeMalezaSevera.toIntOrNull()!!)!=100) {
                            mostrarErrorPorcentajeMaleza = true
                        } else {
                            mostrarErrorPorcentajeMaleza = false
                        }
                        if (estadoPuenteSelected == "") {
                            mostrarErrorEstadoPuentes = true
                        } else {
                            mostrarErrorEstadoPuentes = false
                        }
                        if (estadoTrinchoSelected == "") {
                            mostrarErrorEstadoTrincho = true
                        } else {
                            mostrarErrorEstadoTrincho = false
                        }
                        if (observacionSelected == "" ) {
                            mostrarErrorEstadoObservaciones = true
                        } else {
                            mostrarErrorEstadoObservaciones = false
                        }
                        if (estadoViasSelected == "" ) {
                            mostrarErrorEstadoVias = true
                        } else {
                            mostrarErrorEstadoVias = false
                        }
                        if (variablesSelected == "") {
                            mostrarErrorEstadoVariable = true
                        } else {
                            mostrarErrorEstadoVariable = false
                        }
                    } else {
                        viewModel.borrarDatoPorFechaOrigenYBloque(fecha = fecha, bloque = bloque)
                        guardadoExitoso = guardarVerificacion(
                            bloque = bloque,
                            porcentajeMalezaControlada = porcentajeMalezaControlada,
                            porcentajeMalezaLeve = porcentajeMalezaLeve,
                            porcentajeMalezaModerada = porcentajeMalezaModerada,
                            porcentajeMalezaSevera = porcentajeMalezaSevera,
                            estadoViasSelected = estadoViasSelected,
                            estadoPuenteSelected = estadoPuenteSelected,
                            estadoTrinchoSelected = estadoTrinchoSelected,
                            observacionSelected = observacionSelected,
                            variablesSelected = variablesSelected,
                            nuevaenfermedad = nuevaenfermedadSelected,
                            fecha = fecha,
                            fecha_muestreo = nuevaFecha,
                            viewModel = viewModel,
                            usuario = username,
                            origen = "verificacion",
                        )
                        if (guardadoExitoso) {
                            navController.navigate("searchBlockVerificacion/$username")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startFormsVerificacionCosecha/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}
private fun fecha_ahora(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return currentDateTime.format(formatter)
}

@Composable
fun panelControl(viewModel: DatosGuardadosViewModel, navController: NavController, context: Context,username: String) {
    val datosGuardados = viewModel.datosGuardados.filter { it["Origen"] != "Web" && it["aplicacion"] == "enfermedades"}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Hallazgos Individuales",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))

        val filters = remember { mutableStateOf("") }
        val headers = arrayOf("Fecha Muestra Actual","Bloque", "Terraza", "Conteo Cochinilla","Conteo Babosa","Conteo Gusano Soldado","Conteo Gomosis","Conteo Tecla","Conteo Caracol","Conteo Erwinia","Conteo Fusarium","Conteo Mortalidad","Conteo Phytophtora","Conteo Thielaviopsi","Eliminar","Editar")
        val data = datosGuardados.map { rowData ->
            val bloque = rowData["bloque"] ?: ""
            val terraza= rowData["terraza"] ?: ""
            val conteoCochinilla = (rowData["cochinilla_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["cochinilla_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["cochinilla_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoBabosa = (rowData["babosas_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["babosas_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["babosas_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoGusano = (rowData["gusano_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["gusano_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["gusano_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoGomosis = (rowData["gomosis_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["gomosis_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["gomosis_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoTecla = (rowData["tecla_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["tecla_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["tecla_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoCaracol = (rowData["caracol_leve"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["caracol_moderada"]?.toString()?.toIntOrNull() ?: 0) +
                    (rowData["caracol_severa"]?.toString()?.toIntOrNull() ?: 0)
            val conteoErwinia = rowData["Erwinia"]?.toString()?.toIntOrNull() ?: 0
            val conteoFusarium = rowData["Fusarium"]?.toString()?.toIntOrNull() ?: 0
            val conteoMortalidad = rowData["Mortalidad"]?.toString()?.toIntOrNull() ?: 0
            val conteoPhytophtora = rowData["Phytophtora"]?.toString()?.toIntOrNull() ?: 0
            val conteoThielaviosi = rowData["Thielaviosi"]?.toString()?.toIntOrNull() ?: 0
            val fecha = rowData["fecha"]
            val acciones = "Eliminar Muestra"
            val acciones1 = "Editar"
            arrayOf(fecha,bloque, terraza,conteoCochinilla,conteoBabosa,conteoGusano,conteoGomosis,conteoTecla,conteoCaracol, conteoErwinia,conteoFusarium,conteoMortalidad,conteoPhytophtora,conteoThielaviosi,acciones,acciones1)
        }.toTypedArray()

        val datos: Array<Array<String>> = data.map { it.map { it.toString() }.toTypedArray() }.toTypedArray()
        Button(
            onClick = {
                navController.navigate("startForms/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver a la pantalla principal")
        }

        Table(
            filter = filters,
            headers = headers,
            data = datos,
            viewModel = viewModel,
            numBottoms = 2,
            navController = navController,
            username = username// Alinear celdas a la izquierda
        )

    }
}




@Composable
fun Table(filter: MutableState<String>, headers: Array<String>, data: Array<Array<String>>, viewModel: DatosGuardadosViewModel, numBottoms: Int, navController: NavController,username: String) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = filter.value,
            onValueChange = { filter.value = it },
            label = { Text("Filtro por palabra clave") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        headers.forEachIndexed { index, header ->
                            val isButton = false
                            val bloque = if (isButton) "" else "Bloque"
                            TableCell(text = header, isButton = isButton, viewModel = viewModel, bloque = bloque,fecha = "",    navController = navController, username = username)
                        }
                    }
                }
                items(data.filter { it.any { item -> item.contains(filter.value, ignoreCase = true) } }) { rowData ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowData.forEachIndexed { index, cellData ->
                            val isButton = index >= rowData.size - numBottoms
                            val bloque = rowData[1]
                            TableCell(text = cellData, isButton = isButton, viewModel = viewModel, bloque = bloque,fecha = rowData[0], navController = navController,username = username)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TableCell(
    text: String,
    isButton: Boolean = false,
    viewModel: DatosGuardadosViewModel,
    bloque: String,
    fecha: String,
    navController: NavController,
    username: String
) {
    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }
    var eliminandoBloque by remember { mutableStateOf(false) }
    var eliminandoMuestra by remember { mutableStateOf(false) }
    val datoGuardado= viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] != "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque && it["fecha"] == fecha } // Obtener el primer elemento con el bloque deseado
        ?.get("aplicacion") as? String
    print(datoGuardado+"eee")
    val cellModifier = if (isButton) {
        Modifier
            .padding(8.dp)
            .width(150.dp)
    } else {
        Modifier
            .padding(8.dp)
            .width(100.dp)
    }

    Box(
        modifier = cellModifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isButton) {
                when (text) {
                    "Eliminar", "Eliminar Muestra" -> {
                        Box(
                            modifier = Modifier
                                .clickable(onClick = {
                                    showDialog.value = true
                                    if (text == "Eliminar") {
                                        eliminandoBloque = true
                                    } else if (text == "Eliminar Muestra") {
                                        eliminandoMuestra = true
                                    }
                                })
                                .background(Color.Transparent)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.images),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    "Editar" -> {
                        val datoGuardado = viewModel.obtenerDatosGuardados()
                            .filter { it["Origen"] != "Web" }
                            .firstOrNull { it["bloque"] == bloque && it["fecha"] == fecha }
                            ?.get("aplicacion") as? String

                        Box(
                            modifier = Modifier
                                .clickable(onClick = {
                                    when (datoGuardado) {
                                        "enfermedades" -> navController.navigate("formularioEditar/$fecha/$bloque/$username")
                                        "verificacion" -> navController.navigate("formularioVerificacionEditar/$fecha/$bloque/$username")
                                        "cosecha" -> navController.navigate("formularioCosechaEditar/$fecha/$bloque/$username")
                                    }
                                })
                                .background(Color.Transparent)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.images1),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = text,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                // Título dependiendo del contexto
                if (eliminandoBloque) {
                    Text("¿Desea eliminar todas las muestras del bloque?")
                } else {
                    Text("¿Desea eliminar la muestra del bloque $bloque del $fecha?")
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Lógica de eliminación dependiendo del contexto
                    if (eliminandoBloque) {
                        viewModel.borrarDatoPorBloque(bloque = bloque)
                    } else {
                        viewModel.borrarDatoPorFechaOrigenYBloque(fecha = fecha, bloque = bloque)
                    }
                    showDialog.value = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

}
@Composable
private fun searchBlock(
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    context: Context,
    username: String
) {
    var showDialogNew by remember { mutableStateOf(false) }
    val datosGuardados = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
    var filter by remember { mutableStateOf("") }

    // Obtener una lista de bloques únicos
    val bloques = datosGuardados.map { it["bloque"].toString() }.distinct()

    // Filtrar datos basados en el bloque seleccionado
    val filteredList = remember { mutableStateListOf<Map<String, Any>>() }
    filteredList.clear()
    if (filter.isNotEmpty()) {
        datosGuardados.forEach { dato ->
            if (dato["bloque"].toString() == filter) {
                filteredList.add(dato)
            }
        }
    } else {
        filteredList.addAll(datosGuardados)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Hallazgos  por Bloque",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filters = remember { mutableStateOf("") }

        val headers1 = arrayOf("Fecha Muestra Actual","Bloque", "Conteo Cochinilla","Conteo Babosa","Conteo Gusano Soldado","Conteo Gomosis","Conteo Tecla","Conteo Caracol","Conteo Erwinia","Conteo Fusarium","Conteo Mortalidad","Conteo Phytophtora","Conteo Thielaviopsi","Acciones")

        val datas = datosGuardados
            .groupBy { it["bloque"] } // Agrupar por bloque
            .map { (_, groupData) ->
                val bloque = groupData.firstOrNull()?.get("bloque") ?: ""
                val conteoCochinilla =  groupData.mapNotNull { it["cochinilla_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["cochinilla_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["cochinilla_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoBabosa = groupData.mapNotNull { it["babosas_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["babosas_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["babosas_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoGusano = groupData.mapNotNull { it["gusano_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["gusano_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["gusano_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoGomosis = groupData.mapNotNull { it["gomosis_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["gomosis_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["gomosis_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoTecla = groupData.mapNotNull { it["tecla_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["tecla_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["tecla_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoCaracol = groupData.mapNotNull { it["caracol_leve"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["caracol_moderada"]?.toString()?.toIntOrNull() ?: 0}.sum()+
                        groupData.mapNotNull { it["caracol_severa"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoErwinia = groupData.mapNotNull { it["Erwinia"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoFusarium = groupData.mapNotNull { it["Fusarium"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoMortalidad = groupData.mapNotNull { it["Mortalidad"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoPhytophtora = groupData.mapNotNull { it["Phytophtora"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val conteoThielaviosi = groupData.mapNotNull { it["Thielaviosi"]?.toString()?.toIntOrNull() ?: 0}.sum()
                val fecha = groupData.firstOrNull()?.get("fecha")
                val acciones = "Eliminar"
                arrayOf(fecha,bloque,conteoCochinilla,conteoBabosa,conteoGusano,conteoGomosis,conteoTecla,conteoCaracol, conteoErwinia,conteoFusarium,conteoMortalidad,conteoPhytophtora,conteoThielaviosi, acciones)
            }.toTypedArray()

        if (showDialogNew) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "Se están enviando los datos. Por favor, espere...")
                },
                confirmButton = { /* No se agrega ningún botón */ }
            )
        }

        val datos1: Array<Array<String>> = datas.map { it.map { it.toString() }.toTypedArray() }.toTypedArray()
        val coroutineScope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var showDialogMuestras by remember { mutableStateOf(false) }
        val datos = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialog = false
                },
                title = {
                    Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            showDialogNew = true
                            coroutineScope.launch {
                                sendData(viewModel = viewModel, context = context){ success ->
                                    showDialogNew = !success
                                    println(success)
                                    if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                                }
                            }
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
        if (showDialogMuestras) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialogMuestras = false
                },
                title = {
                    Text(text = "¿Está seguro de eliminar todas las muestras?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                            viewModel.borrarTodosLosDatosGuardadosNoWeb()
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }

        Button(
            onClick = {
                navController.navigate("startForms/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver a la pantalla principal")
        }

        Table(
            filter = filters,
            headers = headers1,
            data = datos1,
            viewModel = viewModel,// Alinear celdas a la izquierda,
            numBottoms = 1,
            navController = navController,
            username = username

        )

    }
}

@Composable
private fun searchBlockVerificacion(
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    context: Context,
    username: String
) {
    var showDialogNew by remember { mutableStateOf(false) }

    val datosGuardados = viewModel.datosGuardados.filter { it["aplicacion"] == "verificacion"}
    var filter by remember { mutableStateOf("") }

    // Obtener una lista de bloques únicos
    val bloques = datosGuardados.map { it["bloque"].toString() }.distinct()

    // Filtrar datos basados en el bloque seleccionado
    val filteredList = remember { mutableStateListOf<Map<String, Any>>() }
    filteredList.clear()
    if (filter.isNotEmpty()) {
        datosGuardados.forEach { dato ->
            if (dato["bloque"].toString() == filter) {
                filteredList.add(dato)
            }
        }
    } else {
        filteredList.addAll(datosGuardados)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Observaciones  por Bloque",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filters = remember { mutableStateOf("") }

        val headers1 = arrayOf("Fecha Muestra Actual","Bloque", "Porcentaje Maleza Controlada","Porcentaje Maleza Leve","Porcentaje Maleza Moderada","Porcentaje Maleza Severa","Estado Puente","Estado Trincho","Observaciones Drenajes","Estado Vías","Variables Observadas","Acciones")
        val datas = datosGuardados
            .groupBy { it["bloque"] } // Agrupar por bloque
            .map { (_, groupData) ->
                val bloque = groupData.firstOrNull()?.get("bloque") ?: ""
                val porcentaje_maleza_controlada = groupData.firstOrNull()?.get("porcentaje_maleza_controlada") ?: ""
                val porcentaje_maleza_leve = groupData.firstOrNull()?.get("porcentaje_maleza_leve") ?: ""
                val porcentaje_maleza_moderada = groupData.firstOrNull()?.get("porcentaje_maleza_moderada") ?: ""
                val porcentaje_maleza_severa = groupData.firstOrNull()?.get("porcentaje_maleza_severa") ?: ""
                val estado_puente = groupData.firstOrNull()?.get("estado_puente") ?: ""
                val estado_vias = groupData.firstOrNull()?.get("estado_vias") ?: ""
                val estado_trincho = groupData.firstOrNull()?.get("estado_trincho") ?: ""
                val observacion = groupData.firstOrNull()?.get("observacion") ?: ""
                val variable = groupData.firstOrNull()?.get("variable") ?: ""
                val fecha = groupData.firstOrNull()?.get("fecha")
                val acciones = "Eliminar Muestra"
                val acciones1 = "Editar"
                arrayOf(fecha,bloque,porcentaje_maleza_controlada,porcentaje_maleza_leve,porcentaje_maleza_moderada,porcentaje_maleza_severa,estado_puente,estado_vias, estado_trincho,observacion,variable, acciones,acciones1)
            }.toTypedArray()

        if (showDialogNew) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "Se están enviando los datos. Por favor, espere...")
                },
                confirmButton = { /* No se agrega ningún botón */ }
            )
        }

        val datos1: Array<Array<String>> = datas.map { it.map { it.toString() }.toTypedArray() }.toTypedArray()
        val coroutineScope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var showDialogMuestras by remember { mutableStateOf(false) }
        val datos = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialog = false
                },
                title = {
                    Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            showDialogNew = true
                            coroutineScope.launch {
                                sendData(viewModel = viewModel, context = context){ success ->
                                    showDialogNew = !success
                                    println(success)
                                    if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                                }
                            }
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
        if (showDialogMuestras) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialogMuestras = false
                },
                title = {
                    Text(text = "¿Está seguro de eliminar todas las muestras?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                            viewModel.borrarTodosLosDatosGuardadosNoWeb()
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }

        Button(
            onClick = {
                navController.navigate("startFormsVerificacionCosecha/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver a la pantalla principal")
        }

        Table(
            filter = filters,
            headers = headers1,
            data = datos1,
            viewModel = viewModel,// Alinear celdas a la izquierda,
            numBottoms = 2,
            navController = navController,
            username = username

        )

    }
}
@Composable
private fun searchBlockCosecha(
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    context: Context,
    username: String
) {
    var showDialogNew by remember { mutableStateOf(false) }
    val datosGuardados = viewModel.datosGuardados.filter { it["aplicacion"] == "cosecha" }
    var filter by remember { mutableStateOf("") }

    // Obtener una lista de bloques únicos
    val bloques = datosGuardados.map { it["bloque"].toString() }.distinct()

    // Filtrar datos basados en el bloque seleccionado
    val filteredList = remember { mutableStateListOf<Map<String, Any>>() }
    filteredList.clear()
    if (filter.isNotEmpty()) {
        datosGuardados.forEach { dato ->
            if (dato["bloque"].toString() == filter) {
                filteredList.add(dato)
            }
        }
    } else {
        filteredList.addAll(datosGuardados)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Cosechas por Bloque",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filters = remember { mutableStateOf("") }
        val headers1 = arrayOf("Fecha Muestra Actual","Bloque", "Fruta Dejada por Cosecha","Plantas sin Parir","Fruta Joven","Fruta no Aprovechable","Fruta Adelantada","Coronas","Fruta Enferma","Daño Mécanico","Quema de Sol","Mortalidad","Descarte entre Camas","Golpe de Agua","Bajo Peso","Ausente","Acciones")
        val datas = datosGuardados
            .filter { it["aplicacion"] == "cosecha" }
            .groupBy { it["bloque"] }
            .map { (_, groupData) ->
                val firstEntry = groupData.firstOrNull()
                val bloque = firstEntry?.get("Bloque") ?: ""
                val frutaDejadaPorCosecha = firstEntry?.get("Fruta_dejada_por_cosecha") ?: ""
                val plantasSinParir = firstEntry?.get("Plantas_sin_parir") ?: ""
                val frutaJoven = firstEntry?.get("Fruta_joven") ?: ""
                val frutaNoAprovechable = firstEntry?.get("Fruta_no_aprovechable") ?: ""
                val frutaAdelantada = firstEntry?.get("Fruta_adelantada") ?: ""
                val coronas = firstEntry?.get("Coronas") ?: ""
                val frutaEnferma = firstEntry?.get("Fruta_enferma") ?: ""
                val danoMecanico = firstEntry?.get("Daño_mecanico") ?: ""
                val quemaDeSol = firstEntry?.get("Quema_de_Sol") ?: ""
                val mortalidad = firstEntry?.get("Mortalidad") ?: ""
                val descarteEntreCamas = firstEntry?.get("Descarte_entre_camas") ?: ""
                val golpeDeAgua = firstEntry?.get("Golpe_de_agua") ?: ""
                val bajoPeso = firstEntry?.get("Bajo_peso") ?: ""
                val ausente = firstEntry?.get("Ausente") ?: ""
                val fecha = firstEntry?.get("fecha") ?: ""
                val acciones = "Eliminar Muestra"
                val acciones1 = "Editar"
                arrayOf(fecha, bloque, frutaDejadaPorCosecha, plantasSinParir, frutaJoven, frutaNoAprovechable, frutaAdelantada, coronas, frutaEnferma, danoMecanico, quemaDeSol, mortalidad, descarteEntreCamas, golpeDeAgua, bajoPeso, ausente, acciones,acciones1)
            }
            .toTypedArray()


        if (showDialogNew) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "Se están enviando los datos. Por favor, espere...")
                },
                confirmButton = { /* No se agrega ningún botón */ }
            )
        }

        val datos1: Array<Array<String>> = datas.map { it.map { it.toString() }.toTypedArray() }.toTypedArray()
        val coroutineScope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var showDialogMuestras by remember { mutableStateOf(false) }
        val datos = viewModel.datosGuardados
            .filter { it["Origen"] != "Web" }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialog = false
                },
                title = {
                    Text(text = "¿Está seguro de subir ${datos.count()} registros?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            showDialogNew = true
                            coroutineScope.launch {
                                sendData(viewModel = viewModel, context = context){ success ->
                                    showDialogNew = !success
                                    println(success)
                                    if(!showDialogNew){viewModel.borrarTodosLosDatosGuardadosNoWeb()}
                                }
                            }
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
        if (showDialogMuestras) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog if the user clicks outside of it or presses the back button
                    showDialogMuestras = false
                },
                title = {
                    Text(text = "¿Está seguro de eliminar todas las muestras?")
                    //TODO
                    // "¿Está seguro de eliminar el bloque $bloque con fecha muestra $fecha_muestra y $cantidad de registros?"
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                            viewModel.borrarTodosLosDatosGuardadosNoWeb()
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialogMuestras = false
                        }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }


        Button(
            onClick = {
                navController.navigate("startFormsVerificacion/$username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver a la pantalla principal")
        }

        Table(
            filter = filters,
            headers = headers1,
            data = datos1,
            viewModel = viewModel,// Alinear celdas a la izquierda,
            numBottoms = 2,
            navController = navController,
            username = username

        )

    }
}
fun formatearNumero(numero: Float): String {
    val formato = NumberFormat.getInstance(Locale.getDefault())
    formato.maximumFractionDigits = 0
    formato.isGroupingUsed = true
    val numeroFormateado = formato.format(numero)
    return numeroFormateado.replace(',', '.')
}
fun formatearNumero1(pesoBloque: Double?): Float {
    if (pesoBloque != null) {
        return pesoBloque.toFloat()
    } // Convierte el Double a Float
    return TODO("Provide the return value")
}

private fun guardar(
    bloque: String,
    terraza: String,
    cochinilla_leve: Int,
    cochinilla_moderada: Int,
    cochinilla_severa: Int,
    gusano_leve: Int,
    gusano_moderada: Int,
    gusano_severa: Int,
    gomosis_leve: Int,
    gomosis_moderada: Int,
    gomosis_severa: Int,
    tecla_leve: Int,
    tecla_moderada: Int,
    tecla_severa: Int,
    caracol_leve: Int,
    caracol_moderada: Int,
    caracol_severa: Int,
    babosas_leve: Int,
    babosas_moderada: Int,
    babosas_severa: Int,
    Erwinia:Int,
    Fusarium: Int,
    Mortalidad: Int,
    Phytophtora: Int,
    Thielaviosi:Int,
    fecha: String,
    fecha_muestreo: String,
    viewModel: DatosGuardadosViewModel,
    nuevaenfermedad: String,
    usuario : String,
    origen : String,
): Boolean {
    return try {
        val nuevoDato = mapOf(
            "bloque" to bloque,
            "terraza" to terraza,
            "cochinilla_leve" to cochinilla_leve.toString(),
            "cochinilla_moderada" to cochinilla_moderada.toString(),
            "cochinilla_severa" to cochinilla_severa.toString(),
            "gusano_leve" to gusano_leve.toString(),
            "gusano_moderada" to gusano_moderada.toString(),
            "gusano_severa" to gusano_severa.toString(),
            "gomosis_leve" to gomosis_leve.toString(),
            "gomosis_moderada" to gomosis_moderada.toString(),
            "gomosis_severa" to gomosis_severa.toString(),
            "tecla_leve" to tecla_leve.toString(),
            "tecla_moderada" to tecla_moderada.toString(),
            "tecla_severa" to tecla_severa.toString(),
            "caracol_leve" to caracol_leve.toString(),
            "caracol_moderada" to caracol_moderada.toString(),
            "caracol_severa" to caracol_severa.toString(),
            "babosas_leve" to babosas_leve.toString(),
            "babosas_moderada" to babosas_moderada.toString(),
            "babosas_severa" to babosas_severa.toString(),
            "Erwinia" to Erwinia.toString(),
            "Fusarium" to Fusarium.toString(),
            "Mortalidad" to Mortalidad.toString(),
            "Phytophtora" to Phytophtora.toString(),
            "Thielaviosi" to Thielaviosi.toString(),
            "fecha" to fecha,
            "observaciones" to nuevaenfermedad,
            "fecha_muestreo" to fecha_muestreo,
            "usuario" to usuario,
            "aplicacion" to origen
        )
        viewModel.agregarDato(nuevoDato)
        println("Exitoso")
        println("Valores guardados:")
        true // Retorna true si se guardó exitosamente
    } catch (e: Exception) {
        println("Fallo: $e")
        false // Retorna false si ocurrió un fallo al guardar
    }
}
private fun guardarCosecha(
    bloque:String,
    frutaDejada: Int,
    plantaSinParir: Int,
    frutaJoven: Int,
    frutaNoAprovechable: Int,
    frutaAdelantada: Int,
    coronas: Int,
    frutaEnferma: Int,
    dañoMecanico: Int,
    quemaSol: Int,
    mortalidad: Int,
    descarteCamas: Int,
    goleAgua: Int,
    bajoPeso: Int,
    ausente: Int,
    fecha: String,
    fecha_muestreo: String,
    viewModel: DatosGuardadosViewModel,
    nuevaenfermedad: String,
    usuario : String,
    origen : String,
): Boolean {
    return try {
        val nuevoDato = mapOf(
            "Bloque" to bloque,
            "Fruta_dejada_por_cosecha" to frutaDejada.toString(),
            "Plantas_sin_parir" to plantaSinParir.toString(),
            "Fruta_joven" to frutaJoven.toString(),
            "Fruta_no_aprovechable" to frutaNoAprovechable.toString(),
            "Fruta_adelantada" to frutaAdelantada.toString(),
            "Coronas" to coronas.toString(),
            "Fruta_enferma" to frutaEnferma.toString(),
            "Daño_mecanico" to dañoMecanico.toString(),
            "Quema_de_Sol" to quemaSol.toString(),
            "Mortalidad" to mortalidad.toString(),
            "Descarte_entre_camas" to descarteCamas.toString(),
            "Golpe_de_agua" to goleAgua.toString(),
            "Bajo_peso" to bajoPeso.toString(),
            "Ausente" to ausente.toString(),
            "fecha" to fecha,
            "observaciones" to nuevaenfermedad,
            "fecha_muestreo" to fecha_muestreo,
            "usuario" to usuario,
            "aplicacion" to origen
        )
        viewModel.agregarDato(nuevoDato)
        println("Exitoso")
        println("Valores guardados:")
        true // Retorna true si se guardó exitosamente
    } catch (e: Exception) {
        println("Fallo: $e")
        false // Retorna false si ocurrió un fallo al guardar
    }
}

private fun guardarVerificacion(
    bloque: String,
    porcentajeMalezaControlada: Comparable<*>,
    porcentajeMalezaLeve:String,
    porcentajeMalezaModerada:String,
    porcentajeMalezaSevera:String,
    estadoPuenteSelected:String,
    estadoTrinchoSelected: String,
    observacionSelected:String,
    variablesSelected: String,
    estadoViasSelected:String,
    fecha: String,
    fecha_muestreo: String,
    viewModel: DatosGuardadosViewModel,
    nuevaenfermedad: String,
    usuario: String,
    origen: String,
): Boolean {
    return try {
        val nuevoDato = mapOf(
            "bloque" to bloque,
            "porcentaje_maleza_controlada" to porcentajeMalezaControlada,
            "porcentaje_maleza_leve" to porcentajeMalezaLeve,
            "porcentaje_maleza_moderada" to porcentajeMalezaModerada,
            "porcentaje_maleza_severa" to porcentajeMalezaSevera,
            "estado_puente" to estadoPuenteSelected,
            "estado_vias" to estadoViasSelected,
            "estado_trincho" to estadoTrinchoSelected,
            "observacion" to observacionSelected,
            "variable" to variablesSelected,
            "fecha" to fecha,
            "observaciones" to nuevaenfermedad,
            "fecha_muestreo" to fecha_muestreo,
            "usuario" to usuario,
            "aplicacion" to origen
        )
        viewModel.agregarDato(nuevoDato)
        println("Exitoso")
        println("Valores guardados:")
        true // Retorna true si se guardó exitosamente
    } catch (e: Exception) {
        println("Fallo: $e")
        false // Retorna false si ocurrió un fallo al guardar
    }
}
fun updateBlocks(viewModel: DatosGuardadosViewModel, callback: (Boolean) -> Unit) {
    viewModel.borrarTodosLosDatosGuardados()
    val client = OkHttpClient.Builder()
        .callTimeout(200, TimeUnit.SECONDS)
        .build()

    val request = Request.Builder()
        .url("http://controlgestionguapa.ddns.net:8000/consultor/api_get_blocks")
        .build()

    var success = false // Booleano para indicar el éxito de la operación

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()

            // Ocultar la barra de progreso en caso de fallo
            viewModel.setProgressVisible(false)
            callback(false) // Llamamos a la función de devolución de llamada con false
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val responseString = response.body?.string()
                val cleanedResponseString = responseString?.replace("NaN", "\" \"") // Reemplazar NaN por cadena vacía
                val jsonArray = cleanedResponseString?.let { JSONArray(it) } ?: JSONArray()
                var i = 0
                var saveSuccessful = true
                while (i < jsonArray.length() && saveSuccessful) {
                    val elemento = jsonArray.getJSONObject(i)
                    saveSuccessful = saveDataWeb(elemento = elemento, viewModel = viewModel)
                    i++
                    println("Holi $i")
                }
                success = saveSuccessful // Establecer success basado en si todos los datos se guardaron correctamente
                callback(success) // Llamamos a la función de devolución de llamada con el valor final de success
            }
        }
    })
}

fun saveDataWeb(elemento: JSONObject, viewModel: DatosGuardadosViewModel): Boolean {
    return try {
        val nuevoDato = mapOf(
            "Fecha_Cargue" to fecha_ahora(),
            "Origen" to "Web",
            "bloque" to elemento.optString("bloque", ""),
            "area" to elemento.optString("area", ""),
            "fecha_siembra" to elemento.optString("fecha_siembra", ""),
            "grupo_forza" to elemento.optString("grupo_forza", ""),
            "poblacion" to elemento.optString("poblacion", ""),
            "total_frutas" to elemento.optString("total_frutas", "")
        )
        viewModel.agregarDato(nuevoDato)
        true // Retorna true si se guardó exitosamente
    } catch (e: Exception) {
        println("Fallo: $e")
        false // Retorna false si ocurrió un fallo al guardar
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formularioEditar(
    bloque: String,
    fecha: String,
    viewModel: DatosGuardadosViewModel,
    navController: NavController,
    username: String
): Boolean {
    val cal = Calendar.getInstance()
    val añoActual = cal.get(Calendar.YEAR).toString()
    val mesActual = (cal.get(Calendar.MONTH) + 1).toString() // Se suma 1 porque los meses van de 0 a 11
    val diaActual = cal.get(Calendar.DAY_OF_MONTH).toString()
    var nuevoAnio by remember { mutableStateOf(añoActual) }
    var nuevoDia by remember { mutableStateOf(diaActual) }
    var nuevoMes by remember { mutableStateOf(mesActual) }

    val muestreosActuales = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] != "Web"  }

    val muestreoActual = muestreosActuales.count { it["bloque"] == bloque }.toInt() + 1
    var guardadoExitoso = false
    val datoEditar =  viewModel.datosGuardados.find { it["fecha"] == fecha && it["bloque"] == bloque && it["Origen"] != "Web" }
    var peso by remember { mutableStateOf(datoEditar?.get("terraza") as? String) }
    var nuevaenfermedadSelected by remember { mutableStateOf(datoEditar?.get("observaciones") as? String) }
    var conteoLeveCochinilla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("cochinilla_leve") as? String)) }
    var conteoModeradoCochinilla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("cochinilla_moderada") as? String)) }
    var conteoSeveroCochinilla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("cochinilla_severa") as? String)) }
    var conteoLeveGusano by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gusano_leve") as? String)) }
    var conteoModeradoGusano by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gusano_moderada") as? String)) }
    var conteoSeveroGusano by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gusano_severa") as? String)) }
    var conteoLeveGomosis by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gomosis_leve") as? String)) }
    var conteoModeradoGomosis by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gomosis_moderada") as? String)) }
    var conteoSeveroGomosis by remember { mutableStateOf(safeStringToInt(datoEditar?.get("gomosis_severa") as? String)) }
    var conteoLeveTecla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("tecla_leve") as? String)) }
    var conteoModeradoTecla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("tecla_moderada") as? String)) }
    var conteoSeveroTecla by remember { mutableStateOf(safeStringToInt(datoEditar?.get("tecla_severa") as? String)) }
    var conteoLeveCaracol by remember { mutableStateOf(safeStringToInt(datoEditar?.get("caracol_leve") as? String)) }
    var conteoModeradoCaracol by remember { mutableStateOf(safeStringToInt(datoEditar?.get("caracol_moderada") as? String)) }
    var conteoSeveroCaracol by remember { mutableStateOf(safeStringToInt(datoEditar?.get("caracol_severa") as? String)) }
    var conteoLeveBabosas by remember { mutableStateOf(safeStringToInt(datoEditar?.get("babosas_leve") as? String)) }
    var conteoModeradoBabosas by remember { mutableStateOf(safeStringToInt(datoEditar?.get("babosas_moderada") as? String)) }
    var conteoSeveroBabosas by remember { mutableStateOf(safeStringToInt(datoEditar?.get("babosas_severa") as? String)) }
    var conteoHallazgoErwinia by remember { mutableStateOf(safeStringToInt(datoEditar?.get("Erwinia") as? String)) }
    var conteoHallazgoFusarium by remember { mutableStateOf(safeStringToInt(datoEditar?.get("Fusarium") as? String)) }
    var conteoHallazgoMortalidad by remember { mutableStateOf(safeStringToInt(datoEditar?.get("Mortalidad") as? String)) }
    var conteoHallazgoPhytophtora by remember { mutableStateOf(safeStringToInt(datoEditar?.get("Phytophtora") as? String)) }
    var conteoHallazgoThielaviosi by remember { mutableStateOf(safeStringToInt(datoEditar?.get("Thielaviosi") as? String)) }
    val grupo = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("grupo_forza") as? String
    val poda = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("fecha_siembra") as? String
    val areaString = viewModel.obtenerDatosGuardados()
        .filter { it["Origen"] == "Web" } // Filtrar por origen web
        .firstOrNull { it["bloque"] == bloque } // Obtener el primer elemento con el bloque deseado
        ?.get("area") as? String ?: "0.0" // Usa "0.0" como valor por defecto

// Convierte el valor del área a Double, manejando posibles errores
    val area = try {
        areaString.toDouble() // Convierte el String a Double
    } catch (e: NumberFormatException) {
        0.0 // Si ocurre un error de formato, usa 0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Sección de "Registrar Hallazgos"
        Text(
            text = "Registrar Hallazgos",
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bloque : $bloque",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Grupo Forza : $grupo",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fecha Siembra/Poda : $poda",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Área Neta : ${formatNumberWithCustomSeparators(area)} [Ha]",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Muestra Actual : $muestreoActual",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        var mostrarErrorTerraza by remember { mutableStateOf(false) }
        peso?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { peso = it },
                label = { Text("Terraza : ") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        if (mostrarErrorTerraza) {
            Text(
                text = "Debe registrar una terraza",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Sección de "Babosas"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Babosas
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Babosas:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val babosasOptions = listOf("Leve", "Moderada", "Severa")

                babosasOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveBabosas > 0) conteoLeveBabosas--
                                    1 -> if (conteoModeradoBabosas > 0) conteoModeradoBabosas--
                                    2 -> if (conteoSeveroBabosas > 0) conteoSeveroBabosas--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val babosasCount = when (index) {
                            0 -> conteoLeveBabosas
                            1 -> conteoModeradoBabosas
                            2 -> conteoSeveroBabosas
                            else -> 0
                        }

                        TextField(
                            value = babosasCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveBabosas = newValue
                                    1 -> conteoModeradoBabosas = newValue
                                    2 -> conteoSeveroBabosas = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveBabosas++
                                    1 -> conteoModeradoBabosas++
                                    2 -> conteoSeveroBabosas++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Caracol
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Caracol:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val caracolOptions = listOf("Leve", "Moderada", "Severa")

                caracolOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveCaracol > 0) conteoLeveCaracol--
                                    1 -> if (conteoModeradoCaracol > 0) conteoModeradoCaracol--
                                    2 -> if (conteoSeveroCaracol > 0) conteoSeveroCaracol--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val caracolCount = when (index) {
                            0 -> conteoLeveCaracol
                            1 -> conteoModeradoCaracol
                            2 -> conteoSeveroCaracol
                            else -> 0
                        }

                        TextField(
                            value = caracolCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveCaracol = newValue
                                    1 -> conteoModeradoCaracol = newValue
                                    2 -> conteoSeveroCaracol = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveCaracol++
                                    1 -> conteoModeradoCaracol++
                                    2 -> conteoSeveroCaracol++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Cochinilla
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Cochinilla:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val cochinillaOptions = listOf("Leve", "Moderada", "Severa")

                cochinillaOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveCochinilla > 0) conteoLeveCochinilla--
                                    1 -> if (conteoModeradoCochinilla > 0) conteoModeradoCochinilla--
                                    2 -> if (conteoSeveroCochinilla > 0) conteoSeveroCochinilla--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val cochinillaCount = when (index) {
                            0 -> conteoLeveCochinilla
                            1 -> conteoModeradoCochinilla
                            2 -> conteoSeveroCochinilla
                            else -> 0
                        }

                        TextField(
                            value = cochinillaCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveCochinilla = newValue
                                    1 -> conteoModeradoCochinilla = newValue
                                    2 -> conteoSeveroCochinilla = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveCochinilla++
                                    1 -> conteoModeradoCochinilla++
                                    2 -> conteoSeveroCochinilla++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Erwinia
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Erwinia:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoErwinia > 0) {
                                conteoHallazgoErwinia--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoErwinia.toString(),
                        onValueChange = { newValue ->
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoHallazgoErwinia = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoErwinia++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Fusarium
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Fusarium:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoFusarium> 0) {
                                conteoHallazgoFusarium--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoFusarium.toString(),
                        onValueChange = { newValue ->
                            val newIntValue = newValue.toIntOrNull() ?: 0
                            if (newIntValue >= 0) {
                                conteoHallazgoFusarium = newIntValue
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoFusarium++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Gomosis
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Gomosis:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val gomosisOptions = listOf("Leve", "Moderada", "Severa")

                gomosisOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveGomosis > 0) conteoLeveGomosis--
                                    1 -> if (conteoModeradoGomosis > 0) conteoModeradoGomosis--
                                    2 -> if (conteoSeveroGomosis > 0) conteoSeveroGomosis--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable con estilo consistente
                        val gomosisCount = when (index) {
                            0 -> conteoLeveGomosis
                            1 -> conteoModeradoGomosis
                            2 -> conteoSeveroGomosis
                            else -> 0
                        }

                        TextField(
                            value = gomosisCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveGomosis = newValue
                                    1 -> conteoModeradoGomosis = newValue
                                    2 -> conteoSeveroGomosis = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto ajustado
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveGomosis++
                                    1 -> conteoModeradoGomosis++
                                    2 -> conteoSeveroGomosis++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Gusano Soldado
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Gusano Soldado:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val gusanoOptions = listOf("Leve", "Moderada", "Severa")

                gusanoOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveGusano > 0) conteoLeveGusano--
                                    1 -> if (conteoModeradoGusano > 0) conteoModeradoGusano--
                                    2 -> if (conteoSeveroGusano > 0) conteoSeveroGusano--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val gusanoCount = when (index) {
                            0 -> conteoLeveGusano
                            1 -> conteoModeradoGusano
                            2 -> conteoSeveroGusano
                            else -> 0
                        }

                        TextField(
                            value = gusanoCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveGusano = newValue
                                    1 -> conteoModeradoGusano = newValue
                                    2 -> conteoSeveroGusano = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveGusano++
                                    1 -> conteoModeradoGusano++
                                    2 -> conteoSeveroGusano++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Mortalidad
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mortalidad:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoMortalidad> 0) {
                                conteoHallazgoMortalidad--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoMortalidad.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoMortalidad = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto ajustado
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoMortalidad++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Phytophtora
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Phytophtora:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoPhytophtora > 0) {
                                conteoHallazgoPhytophtora--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable con estilo consistente
                    TextField(
                        value = conteoHallazgoPhytophtora.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoPhytophtora = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto más grande
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoPhytophtora++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contadores de Tecla
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Tecla:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                val teclaOptions = listOf("Leve", "Moderada", "Severa")

                teclaOptions.forEachIndexed { index, label ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón de "-" para decrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> if (conteoLeveTecla > 0) conteoLeveTecla--
                                    1 -> if (conteoModeradoTecla > 0) conteoModeradoTecla--
                                    2 -> if (conteoSeveroTecla > 0) conteoSeveroTecla--
                                }
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                        }

                        // Campo de entrada numérica editable
                        val teclaCount = when (index) {
                            0 -> conteoLeveTecla
                            1 -> conteoModeradoTecla
                            2 -> conteoSeveroTecla
                            else -> 0
                        }

                        TextField(
                            value = teclaCount.toString(),
                            onValueChange = { value ->
                                val newValue = value.toIntOrNull() ?: 0
                                when (index) {
                                    0 -> conteoLeveTecla = newValue
                                    1 -> conteoModeradoTecla = newValue
                                    2 -> conteoSeveroTecla = newValue
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(100.dp), // Tamaño del campo de texto más grande
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            ),
                            singleLine = true
                        )

                        // Botón de "+" para incrementar el valor
                        IconButton(
                            onClick = {
                                when (index) {
                                    0 -> conteoLeveTecla++
                                    1 -> conteoModeradoTecla++
                                    2 -> conteoSeveroTecla++
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Incrementar")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Contador de Thielaviosi
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Thielaviosi:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hallazgo",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón de "-" para decrementar el valor
                    IconButton(
                        onClick = {
                            if (conteoHallazgoThielaviosi > 0) conteoHallazgoThielaviosi--
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Decrementar")
                    }

                    // Campo de entrada numérica editable
                    TextField(
                        value = conteoHallazgoThielaviosi.toString(),
                        onValueChange = { value ->
                            val newValue = value.toIntOrNull() ?: 0
                            conteoHallazgoThielaviosi = newValue
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(100.dp), // Tamaño del campo de texto más grande
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        singleLine = true
                    )

                    // Botón de "+" para incrementar el valor
                    IconButton(
                        onClick = {
                            conteoHallazgoThielaviosi++
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Incrementar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Fecha Muestra Actual:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Campo para el año (4 dígitos)
            OutlinedTextField(
                value = nuevoAnio,
                onValueChange = { nuevoAnio = it.take(4) }, // Limitar a 4 dígitos
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Campo para el mes (2 dígitos)
            OutlinedTextField(
                value = nuevoMes,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoMes = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 12 -> "12" // Si es mayor que 12, establecer como "12"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )


            // Campo para el día (2 dígitos)
            OutlinedTextField(
                value = nuevoDia,
                onValueChange = {
                    val nuevoValor = it.take(2).toIntOrNull() // Convertir a Int
                    nuevoDia = when {
                        nuevoValor == null -> "" // Si la conversión falla, establecer el valor como vacío
                        nuevoValor < 1 -> "01" // Si es menor que 1, establecer como "01"
                        nuevoValor > 31 -> "31" // Si es mayor que 31, establecer como "31"
                        else -> "%02d".format(nuevoValor) // Formatear como dos dígitos con ceros a la izquierda
                    }
                },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }


        var nuevaFecha = "$nuevoAnio-$nuevoMes-$nuevoDia"

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nuevaenfermedadSelected.toString(),
            onValueChange = { nuevaenfermedadSelected = it }, // Actualización de la variable
            label = { Text("Otras novedades y observaciones adicionales") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        var isEditBlockChecked by remember { mutableStateOf(false) }
        var showDialogBloque by remember { mutableStateOf(false) }
        var newBloque by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isEditBlockChecked,
                    onCheckedChange = { isChecked ->
                        isEditBlockChecked = isChecked
                        if (isChecked) {
                            showDialogBloque = true
                        }
                    }
                )
                Text(text = "¿Editar Bloque?", modifier = Modifier.padding(start = 8.dp))
            }
            if (showDialogBloque) {
                AlertDialog(
                    onDismissRequest = {
                        showDialogBloque = false
                    },
                    title = {
                        Text(text = "Editar Bloque")
                    },
                    text = {
                        OutlinedTextField(
                            value = newBloque,
                            onValueChange = { newBloque = it },
                            label = { Text("Nuevo Bloque") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialogBloque = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showDialogBloque = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
        Text(
            text = "Fecha Sistema",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${fecha_ahora()}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botones de guardar y volver
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if(!isEditBlockChecked){
                    viewModel.borrarDatoPorFechaOrigenYBloque(fecha = fecha, bloque = bloque)
                    guardadoExitoso = guardar(
                        bloque = bloque,
                        terraza = peso.toString(),
                        cochinilla_leve = conteoLeveCochinilla,
                        cochinilla_moderada = conteoModeradoCochinilla,
                        cochinilla_severa = conteoSeveroCochinilla,
                        gusano_leve = conteoLeveGusano,
                        gusano_moderada = conteoModeradoGusano,
                        gusano_severa = conteoSeveroGusano,
                        gomosis_leve = conteoLeveGomosis,
                        gomosis_moderada = conteoModeradoGomosis,
                        gomosis_severa = conteoSeveroGomosis,
                        tecla_leve = conteoLeveTecla,
                        tecla_moderada = conteoModeradoTecla,
                        tecla_severa= conteoSeveroTecla,
                        caracol_leve = conteoLeveCaracol,
                        caracol_moderada = conteoModeradoCaracol,
                        caracol_severa = conteoSeveroCaracol,
                        babosas_leve = conteoLeveBabosas,
                        babosas_moderada = conteoModeradoBabosas,
                        babosas_severa = conteoSeveroBabosas,
                        Erwinia = conteoHallazgoErwinia,
                        Fusarium= conteoHallazgoFusarium,
                        Mortalidad= conteoHallazgoMortalidad,
                        Phytophtora= conteoHallazgoPhytophtora,
                        Thielaviosi= conteoHallazgoThielaviosi,
                        nuevaenfermedad = nuevaenfermedadSelected.toString(),
                        fecha = fecha_ahora(),
                        fecha_muestreo = nuevaFecha,
                        viewModel = viewModel,
                        usuario = username,
                        origen = "enfermedades",
                    )}
                    else{
                        viewModel.borrarDatoPorFechaOrigenYBloque(fecha = fecha, bloque = bloque)
                        guardadoExitoso = guardar(
                            bloque = newBloque,
                            terraza = peso.toString(),
                            cochinilla_leve = conteoLeveCochinilla,
                            cochinilla_moderada = conteoModeradoCochinilla,
                            cochinilla_severa = conteoSeveroCochinilla,
                            gusano_leve = conteoLeveGusano,
                            gusano_moderada = conteoModeradoGusano,
                            gusano_severa = conteoSeveroGusano,
                            gomosis_leve = conteoLeveGomosis,
                            gomosis_moderada = conteoModeradoGomosis,
                            gomosis_severa = conteoSeveroGomosis,
                            tecla_leve = conteoLeveTecla,
                            tecla_moderada = conteoModeradoTecla,
                            tecla_severa= conteoSeveroTecla,
                            caracol_leve = conteoLeveCaracol,
                            caracol_moderada = conteoModeradoCaracol,
                            caracol_severa = conteoSeveroCaracol,
                            babosas_leve = conteoLeveBabosas,
                            babosas_moderada = conteoModeradoBabosas,
                            babosas_severa = conteoSeveroBabosas,
                            Erwinia = conteoHallazgoErwinia,
                            Fusarium= conteoHallazgoFusarium,
                            Mortalidad= conteoHallazgoMortalidad,
                            Phytophtora= conteoHallazgoPhytophtora,
                            Thielaviosi= conteoHallazgoThielaviosi,
                            nuevaenfermedad = nuevaenfermedadSelected.toString(),
                            fecha = fecha_ahora(),
                            fecha_muestreo = nuevaFecha,
                            viewModel = viewModel,
                            usuario = username,
                            origen = "enfermedads",
                        )
                    }
                    if (guardadoExitoso) {
                        navController.navigate("panelControl/$username")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar")
            }
            Button(
                onClick = {
                    navController.navigate("startForms/$username")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Volver a la pantalla principal")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logi),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Powered by Guapa \n Versión 1.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
    return true
}
fun safeStringToInt(value: String?): Int {
    return try {
        value?.toDouble()?.toInt() ?: 0
    } catch (e: NumberFormatException) {
        0
    }
}
@Composable
fun rememberExposureNeg_1(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "exposure_neg_1",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6.5f, 23f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.958f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                reflectiveQuadToRelative(0.917f, -0.375f)
                horizontalLineToRelative(8.833f)
                quadToRelative(0.542f, 0f, 0.917f, 0.375f)
                reflectiveQuadToRelative(0.375f, 0.917f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.917f, 0.375f)
                close()
                moveToRelative(21.792f, 8.458f)
                quadToRelative(-0.625f, 0f, -1.063f, -0.458f)
                quadToRelative(-0.437f, -0.458f, -0.437f, -1.083f)
                verticalLineTo(12.625f)
                lineToRelative(-2.875f, 2.042f)
                quadToRelative(-0.459f, 0.333f, -1.021f, 0.229f)
                quadToRelative(-0.563f, -0.104f, -0.896f, -0.604f)
                quadToRelative(-0.333f, -0.459f, -0.229f, -1.021f)
                quadToRelative(0.104f, -0.563f, 0.604f, -0.896f)
                lineToRelative(4.208f, -3.083f)
                quadToRelative(0.417f, -0.334f, 1.063f, -0.375f)
                quadToRelative(0.646f, -0.042f, 1.104f, 0.208f)
                quadToRelative(0.417f, 0.25f, 0.729f, 0.771f)
                quadToRelative(0.313f, 0.521f, 0.313f, 1.021f)
                verticalLineToRelative(19f)
                quadToRelative(0f, 0.625f, -0.438f, 1.083f)
                quadToRelative(-0.437f, 0.458f, -1.062f, 0.458f)
                close()
            }
        }.build()
    }
}
@Composable
fun rememberCheckIndeterminateSmall(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "check_indeterminate_small",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.042f, 21.292f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadTo(11.75f, 20f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.395f, 0.917f, -0.395f)
                horizontalLineToRelative(13.916f)
                quadToRelative(0.542f, 0f, 0.938f, 0.395f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                quadToRelative(0f, 0.542f, -0.396f, 0.917f)
                reflectiveQuadToRelative(-0.938f, 0.375f)
                close()
            }
        }.build()
    }
}

private suspend fun sendData(viewModel: DatosGuardadosViewModel, context: Context, callback: (Boolean) -> Unit) {
    withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val datosGuardados = viewModel.datosGuardados.filter { it["Origen"] != "Web" }

        var datoEnviado = false // Inicialmente no se ha enviado ningún dato
        generateJsonFile(viewModel, context, "cosecha")
        generateJsonFile(viewModel, context, "verificacion")
        generateJsonFile(viewModel, context, "enfermedades")
        var index = 0
        while (index < datosGuardados.size) {
            val dato = datosGuardados[index]
            val fechaCompleta = dato["fecha"] as? String ?: ""
            fecha_ahora()
            val (fechaCargue, horaCargue) = if (fecha_ahora().isNotEmpty()) {
                val partes = fecha_ahora().split(" ")
                if (partes.size == 2) {
                    partes[0] to partes[1]
                } else {
                    "" to ""
                }
            } else {
                "" to ""
            }
            val (fechaSistema, horaSistema) = if (fechaCompleta.isNotEmpty()) {
                val partes = fechaCompleta.split(" ")
                if (partes.size == 2) {
                    partes[0] to partes[1]
                } else {
                    "" to ""
                }
            } else {
                "" to ""
            }

            try {
                val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val requestBodys = when (dato["aplicacion"]) {
                    "cosecha" -> {
                        JsonObject().apply  {
                                addProperty("Bloque", dato["Bloque"] as? String ?: "")
                                addProperty("Fruta_dejada_por_cosecha", dato["Fruta_dejada_por_cosecha"] as? String ?: "")
                                addProperty("Plantas_sin_parir", dato["Plantas_sin_parir"] as? String ?: "")
                                addProperty("Fruta_joven", dato["Fruta_joven"] as? String ?: "")
                                addProperty("Fruta_no_aprovechable", dato["Fruta_no_aprovechable"] as? String ?: "")
                                addProperty("Fruta_adelantada", dato["Fruta_adelantada"] as? String ?: "")
                                addProperty("Coronas", dato["Coronas"] as? String ?: "")
                                addProperty("Fruta_enferma", dato["Fruta_enferma"] as? String ?: "")
                                addProperty("Daño_mecanico", dato["Daño_mecanico"] as? String ?: "")
                                addProperty("Quema_de_Sol", dato["Quema_de_Sol"] as? String ?: "")
                                addProperty("Mortalidad", dato["Mortalidad"] as? String ?: "")
                                addProperty("Descarte_entre_camas", dato["Descarte_entre_camas"] as? String ?: "")
                                addProperty("Bajo_peso", dato["Bajo_peso"] as? String ?: "")
                                addProperty("Golpe_de_agua", dato["Golpe_de_agua"] as? String ?: "")
                                addProperty("Ausente", dato["Ausente"] as? String ?: "")
                                addProperty("observaciones", dato["observaciones"] as? String ?: "")
                                addProperty("usuario", dato["usuario"] as? String ?: "")
                                addProperty("fecha_sistema", fechaSistema)
                                addProperty("hora_sistema", horaSistema)
                                addProperty("fecha_cargue", fechaCargue)
                                addProperty("hora_cargue", horaCargue)
                                addProperty("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                                addProperty("aplicacion", "cosecha")
                            }
                    }
                    "verificacion" -> {
                        JsonObject().apply {
                            addProperty("bloque", dato["bloque"] as? String ?: "")
                            addProperty("porcentaje_maleza_controlada", dato["porcentaje_maleza_controlada"] as? String ?: "")
                            addProperty("porcentaje_maleza_leve", dato["porcentaje_maleza_leve"] as? String ?: "")
                            addProperty("porcentaje_maleza_moderada", dato["porcentaje_maleza_moderada"] as? String ?: "")
                            addProperty("porcentaje_maleza_severa", dato["porcentaje_maleza_severa"] as? String ?: "")
                            addProperty("estado_puente", dato["estado_puente"] as? String ?: "")
                            addProperty("estado_vias", dato["estado_vias"] as? String ?: "")
                            addProperty("estado_trincho", dato["estado_trincho"] as? String ?: "")
                            addProperty("observacion", dato["observacion"] as? String ?: "")
                            addProperty("variables", dato["variable"] as? String ?: "")
                            addProperty("observaciones", dato["nuevaenfermedad"] as? String ?: "")
                            addProperty("fecha_sistema", fechaSistema)
                            addProperty("hora_sistema", horaSistema)
                            addProperty("fecha_cargue", fechaCargue)
                            addProperty("hora_cargue", horaCargue)
                            addProperty("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                            addProperty("usuario", dato["usuario"] as? String ?: "")
                            addProperty("aplicacion", "verificacion")
                        }
                    }
                    "enfermedades" -> {
                        JsonObject().apply {
                            addProperty("bloque", dato["bloque"] as? String ?: "")
                            addProperty("terraza", dato["terraza"] as? String ?: "")
                            addProperty("cochinilla_leve", dato["cochinilla_leve"] as? String ?: "")
                            addProperty("cochinilla_moderada", dato["cochinilla_moderada"] as? String ?: "")
                            addProperty("cochinilla_severa", dato["cochinilla_severa"] as? String ?: "")
                            addProperty("g_soldado_leve", dato["gusano_leve"] as? String ?: "")
                            addProperty("g_soldado_moderado", dato["gusano_moderada"] as? String ?: "")
                            addProperty("g_soldado_severo", dato["gusano_severa"] as? String ?: "")
                            addProperty("gomosis_leve", dato["gomosis_leve"] as? String ?: "")
                            addProperty("gomosis_moderada", dato["gomosis_moderada"] as? String ?: "")
                            addProperty("gomosis_severa", dato["gomosis_severa"] as? String ?: "")
                            addProperty("tecla_leve", dato["tecla_leve"] as? String ?: "")
                            addProperty("tecla_moderada", dato["tecla_moderada"] as? String ?: "")
                            addProperty("tecla_severa", dato["tecla_severa"] as? String ?: "")
                            addProperty("caracol_leve", dato["caracol_leve"] as? String ?: "")
                            addProperty("caracol_moderada", dato["caracol_moderada"] as? String ?: "")
                            addProperty("caracol_severa", dato["caracol_severa"] as? String ?: "")
                            addProperty("babosas_leve", dato["babosas_leve"] as? String ?: "")
                            addProperty("babosas_moderada", dato["babosas_moderada"] as? String ?: "")
                            addProperty("babosas_severa", dato["babosas_severa"] as? String ?: "")
                            addProperty("Erwinia", dato["Erwinia"] as? String ?: "")
                            addProperty("Fusarium", dato["Fusarium"] as? String ?: "")
                            addProperty("Mortalidad", dato["Mortalidad"] as? String ?: "")
                            addProperty("Phytophtora", dato["Phytophtora"] as? String ?: "")
                            addProperty("Thielaviosi", dato["Thielaviosi"] as? String ?: "")
                            addProperty("observaciones", dato["nuevaenfermedad"] as? String ?: "")
                            addProperty("fecha_sistema", fechaSistema)
                            addProperty("hora_sistema", horaSistema)
                            addProperty("fecha_cargue", fechaCargue)
                            addProperty("hora_cargue", horaCargue)
                            addProperty("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                            addProperty("usuario", dato["usuario"] as? String ?: "")
                            addProperty("aplicacion", "enfermedades")
                        }
                    }
                    else -> null // Manejar otros tipos de datos si es necesario
                }
                var yuyu = ""
                when (dato["aplicacion"]) {
                    "cosecha" -> {
                       yuyu = "cosecha"
                    }
                    "verificacion" -> {
                       yuyu =  "verificacion"
                    }
                    "enfermedades" -> {
                        yuyu = "enfermedades"
                    }
                    else -> null // Manejar otros tipos de datos si es necesario
                }
                val requestBody = requestBodys.toString().toRequestBody(jsonMediaType)
                val request = Request.Builder()
                    .url("http://controlgestionguapa.ddns.net:8000/consultor/api_prueba")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val responseString = responseBody?.takeIf { it.isNotEmpty() }

                if (!responseString.isNullOrEmpty()) {
                    datoEnviado = true
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Datos enviados correctamente", Toast.LENGTH_LONG).show()
                    }

                    val nuevoDato = mapOf(
                        "aplicacion" to yuyu,
                        "estatus" to "enviado",
                        "fecha" to fecha_ahora() // Asegúrate de tener la función fecha_ahora implementada correctamente
                    )
                    viewModel.agregarDato(nuevoDato)

                    index++
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al enviar datos. Contactar con Estrategia de datos", Toast.LENGTH_LONG).show()
                    }
                    break // Salir del bucle si hay un error en el envío
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al enviar datos. Contactar con Estrategia de datos", Toast.LENGTH_LONG).show()
                }
                break // Salir del bucle si hay una excepción
            }
        }

        // Llamar al callback para indicar si todos los datos se enviaron correctamente
        callback(datoEnviado)
    }
}

fun Iterable<Double>.averageOrNull(): Double? {
    if (this.none()) return null
    return this.average()
}

private suspend fun loginUser(context: Context, username: String, password: String, callback: (Boolean) -> Unit) {
    withContext(Dispatchers.IO) {
        if (isNetworkAvailable(context)) {
            val client = OkHttpClient()
            val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

            val json = JSONObject().apply {
                put("nombre", username)
                put("contraseña", password)
            }

            val requestBody = json.toString().toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url("http://controlgestionguapa.ddns.net:8000/consultor/api_get_users")
                .post(requestBody)
                .build()

            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }
                val responseBody = response.body?.string()
                val jsonResponse = responseBody?.let { JSONObject(it) }
                val message = jsonResponse?.getString("message")
                val success = message == "Aceptado"

                withContext(Dispatchers.Main) {
                    if (success) {
                        // Guardar credenciales en LocalUserStore
                        val localUserStore = LocalUserStore(context)
                        val user = LocalUserStore.User(username, password)
                        localUserStore.saveUser(user)

                        // Print statements for debugging
                        println("Saving user: $username with password: $password")
                        println("Stored users: ${localUserStore.getUsers()}")

                        Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, message ?: "Unknown error occurred", Toast.LENGTH_LONG).show()
                    }
                    callback(success)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error connecting to server", Toast.LENGTH_LONG).show()
                    callback(false)
                }
            }
        } else {
            // Manejar autenticación offline
            val localUserStore = LocalUserStore(context)
            val user = localUserStore.getUser(username)
            val success = user?.password == password

            withContext(Dispatchers.Main) {
                if (success) {
                    println("Authenticated offline user: $username")
                    Toast.makeText(context, "Offline login successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                }
                callback(success)
            }
        }
    }
}

// Función para actualizar la lista local cuando hay conexión a Internet
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
suspend fun generateExcelFile(
    viewModel: DatosGuardadosViewModel,
    context: Context,
    aplicacion: String
): File? {
    return withContext(Dispatchers.IO) {
        try {
            generateJsonFile(viewModel, context, aplicacion)
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Datos Guardados")

            val headers = when (aplicacion) {
                "cosecha" -> arrayOf(
                    "Bloque", "Fruta_dejada_por_cosecha", "Plantas_sin_parir", "Fruta_joven",
                    "Fruta_no_aprovechable", "Fruta_adelantada", "Coronas", "Fruta_enferma",
                    "Daño_mecanico", "Quema_de_Sol", "Mortalidad", "Descarte_entre_camas",
                    "Golpe_de_agua", "Bajo_peso", "Ausente", "fecha", "observaciones",
                    "fecha_muestreo", "usuario"
                )
                "verificacion" -> arrayOf(
                    "bloque", "porcentaje_maleza_controlada", "porcentaje_maleza_leve",
                    "porcentaje_maleza_moderada", "porcentaje_maleza_severa", "estado_puente",
                    "estado_trincho", "observacion", "variables", "estado_vias", "fecha",
                    "observaciones", "fecha_muestreo", "usuario"
                )
                "enfermedades" -> arrayOf(
                    "bloque", "terraza", "cochinilla_leve", "cochinilla_moderada",
                    "cochinilla_severa", "gusano_leve", "gusano_moderada", "gusano_severa",
                    "gomosis_leve", "gomosis_moderada", "gomosis_severa", "tecla_leve",
                    "tecla_moderada", "tecla_severa", "caracol_leve", "caracol_moderada",
                    "caracol_severa", "babosas_leve", "babosas_moderada", "babosas_severa",
                    "Erwinia", "Fusarium", "Mortalidad", "Phytophtora", "Thielaviosi",
                    "fecha", "observaciones", "fecha_muestreo", "usuario"
                )
                else -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Tipo de aplicación desconocido", Toast.LENGTH_LONG).show()
                    }
                    return@withContext null
                }
            }

            // Crear fila de encabezado
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { index, header ->
                headerRow.createCell(index).setCellValue(header)
            }

            // Filtrar datos guardados
            val datosGuardados = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
            val filteredDatosGuardados = datosGuardados.filter { it["aplicacion"] == aplicacion }

            // Llenar el Excel con datos
            filteredDatosGuardados.forEachIndexed { rowIndex, dato ->
                val row = sheet.createRow(rowIndex + 1)
                headers.forEachIndexed { index, header ->
                    row.createCell(index).setCellValue(dato[header] as? String ?: "")
                }
            }

            // Guardar el archivo en la carpeta "Descargas"
            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDirectory, "DatosGuardados.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            workbook.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al generar el archivo Excel", Toast.LENGTH_LONG).show()
            }
            null
        }
    }
}
suspend fun generateJsonFile(
    viewModel: DatosGuardadosViewModel,
    context: Context,
    aplicacion: String
): File? {
    return withContext(Dispatchers.IO) {
        try {
            // Filtrar datos guardados
            val datosGuardados = viewModel.datosGuardados.filter { it["Origen"] != "Web" }
            val filteredDatosGuardados = datosGuardados.filter { it["aplicacion"] == aplicacion }

            // Crear un JSONArray con los datos filtrados
            val jsonArray = JSONArray()

            filteredDatosGuardados.forEach { dato ->
                val jsonObject = JSONObject()
                when (aplicacion) {
                    "cosecha" -> {
                        jsonObject.put("Bloque", dato["Bloque"] as? String ?: "")
                        jsonObject.put("Fruta_dejada_por_cosecha", dato["Fruta_dejada_por_cosecha"] as? String ?: "")
                        jsonObject.put("Plantas_sin_parir", dato["Plantas_sin_parir"] as? String ?: "")
                        jsonObject.put("Fruta_joven", dato["Fruta_joven"] as? String ?: "")
                        jsonObject.put("Fruta_no_aprovechable", dato["Fruta_no_aprovechable"] as? String ?: "")
                        jsonObject.put("Fruta_adelantada", dato["Fruta_adelantada"] as? String ?: "")
                        jsonObject.put("Coronas", dato["Coronas"] as? String ?: "")
                        jsonObject.put("Fruta_enferma", dato["Fruta_enferma"] as? String ?: "")
                        jsonObject.put("Daño_mecanico", dato["Daño_mecanico"] as? String ?: "")
                        jsonObject.put("Quema_de_Sol", dato["Quema_de_Sol"] as? String ?: "")
                        jsonObject.put("Mortalidad", dato["Mortalidad"] as? String ?: "")
                        jsonObject.put("Descarte_entre_camas", dato["Descarte_entre_camas"] as? String ?: "")
                        jsonObject.put("Golpe_de_agua", dato["Golpe_de_agua"] as? String ?: "")
                        jsonObject.put("Bajo_peso", dato["Bajo_peso"] as? String ?: "")
                        jsonObject.put("Ausente", dato["Ausente"] as? String ?: "")
                        jsonObject.put("fecha", dato["fecha"] as? String ?: "")
                        jsonObject.put("observaciones", dato["observaciones"] as? String ?: "")
                        jsonObject.put("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                        jsonObject.put("usuario", dato["usuario"] as? String ?: "")
                    }
                    "verificacion" -> {
                        jsonObject.put("bloque", dato["bloque"] as? String ?: "")
                        jsonObject.put("porcentaje_maleza_controlada", dato["porcentaje_maleza_controlada"] as? String ?: "")
                        jsonObject.put("porcentaje_maleza_leve", dato["porcentaje_maleza_leve"] as? String ?: "")
                        jsonObject.put("porcentaje_maleza_moderada", dato["porcentaje_maleza_moderada"] as? String ?: "")
                        jsonObject.put("porcentaje_maleza_severa", dato["porcentaje_maleza_severa"] as? String ?: "")
                        jsonObject.put("estado_puente", dato["estado_puente"] as? String ?: "")
                        jsonObject.put("estado_trincho", dato["estado_trincho"] as? String ?: "")
                        jsonObject.put("observacion", dato["observacion"] as? String ?: "")
                        jsonObject.put("variables", dato["variables"] as? String ?: "")
                        jsonObject.put("estado_vias", dato["estado_vias"] as? String ?: "")
                        jsonObject.put("fecha", dato["fecha"] as? String ?: "")
                        jsonObject.put("observaciones", dato["observaciones"] as? String ?: "")
                        jsonObject.put("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                        jsonObject.put("usuario", dato["usuario"] as? String ?: "")
                    }
                    "enfermedades" -> {
                        jsonObject.put("bloque", dato["bloque"] as? String ?: "")
                        jsonObject.put("terraza", dato["terraza"] as? String ?: "")
                        jsonObject.put("cochinilla_leve", dato["cochinilla_leve"] as? String ?: "")
                        jsonObject.put("cochinilla_moderada", dato["cochinilla_moderada"] as? String ?: "")
                        jsonObject.put("cochinilla_severa", dato["cochinilla_severa"] as? String ?: "")
                        jsonObject.put("gusano_leve", dato["gusano_leve"] as? String ?: "")
                        jsonObject.put("gusano_moderada", dato["gusano_moderada"] as? String ?: "")
                        jsonObject.put("gusano_severa", dato["gusano_severa"] as? String ?: "")
                        jsonObject.put("gomosis_leve", dato["gomosis_leve"] as? String ?: "")
                        jsonObject.put("gomosis_moderada", dato["gomosis_moderada"] as? String ?: "")
                        jsonObject.put("gomosis_severa", dato["gomosis_severa"] as? String ?: "")
                        jsonObject.put("tecla_leve", dato["tecla_leve"] as? String ?: "")
                        jsonObject.put("tecla_moderada", dato["tecla_moderada"] as? String ?: "")
                        jsonObject.put("tecla_severa", dato["tecla_severa"] as? String ?: "")
                        jsonObject.put("caracol_leve", dato["caracol_leve"] as? String ?: "")
                        jsonObject.put("caracol_moderada", dato["caracol_moderada"] as? String ?: "")
                        jsonObject.put("caracol_severa", dato["caracol_severa"] as? String ?: "")
                        jsonObject.put("babosas_leve", dato["babosas_leve"] as? String ?: "")
                        jsonObject.put("babosas_moderada", dato["babosas_moderada"] as? String ?: "")
                        jsonObject.put("babosas_severa", dato["babosas_severa"] as? String ?: "")
                        jsonObject.put("Erwinia", dato["Erwinia"] as? String ?: "")
                        jsonObject.put("Fusarium", dato["Fusarium"] as? String ?: "")
                        jsonObject.put("Mortalidad", dato["Mortalidad"] as? String ?: "")
                        jsonObject.put("Phytophtora", dato["Phytophtora"] as? String ?: "")
                        jsonObject.put("Thielaviosi", dato["Thielaviosi"] as? String ?: "")
                        jsonObject.put("fecha", dato["fecha"] as? String ?: "")
                        jsonObject.put("observaciones", dato["observaciones"] as? String ?: "")
                        jsonObject.put("fecha_muestreo", dato["fecha_muestreo"] as? String ?: "")
                        jsonObject.put("usuario", dato["usuario"] as? String ?: "")
                    }
                    else -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Tipo de aplicación desconocido", Toast.LENGTH_LONG).show()
                        }
                        return@withContext null
                    }
                }
                jsonArray.put(jsonObject)
            }

            // Convertir el JSONArray a String
            val jsonString = jsonArray.toString(4) // Indentación para mayor legibilidad

            // Guardar el archivo JSON en la carpeta "Descargas"
            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDirectory, "DatosGuardados${aplicacion}.json")
            file.writeText(jsonString)

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Archivo JSON generado y guardado en Descargas", Toast.LENGTH_LONG).show()
            }

            file
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al generar el archivo JSON", Toast.LENGTH_LONG).show()
            }
            null
        }
    }
}


fun formatNumberWithCustomSeparators(number: Double): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val decimalFormat = DecimalFormat("#,##0.000", symbols) // Modificado para no siempre mostrar 3 decimales
    return decimalFormat.format(number)
}

fun formatNumberWithCustomSeparators2(number: Double): String {
    // Configurar los símbolos para usar punto como separador de miles y coma como separador de decimales
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }

    // Crear un formato con dos decimales
    val decimalFormat = DecimalFormat("#,##0", symbols)

    // Formatear el número usando el formato configurado
    return decimalFormat.format(number)
}