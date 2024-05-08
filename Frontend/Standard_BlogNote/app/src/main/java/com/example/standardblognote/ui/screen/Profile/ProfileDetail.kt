
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.ProfileDetail

@Composable
fun ProfileDetail(navController: NavHostController, viewModel: HomeViewModel,) {
 //   val email by viewModel.emailId.observeAsState()
    val profileViewModel: ProfileViewModel = viewModel()
            //    lấy use uid
        //    val uid by homeViewModel.uid.observeAsState()
            val emailId by viewModel.emailId.observeAsState()
        //    val NotificationService = NotificationService(context = context)

            val context = LocalContext.current
            val scope = rememberCoroutineScope()


            // Lấy UID từ SharedPreferences
            val uid = viewModel.getUidFromSharedPreferences() ?: ""
            if(uid == "") {
                LaunchedEffect(Unit) {
                    viewModel.fetchUidLogin()
                }
            }

            LaunchedEffect(Unit) {
                viewModel.getUserData()
            }
    Log.i("Get UID sadsadas" , "${uid}")

    // Fetch user profile
    profileViewModel.fetchUser(uid)

    val userLiveData = profileViewModel.userLiveData

    val firstName = remember { mutableStateOf(userLiveData.value.username ) }
    val emailValue = remember { mutableStateOf(userLiveData.value.email ) }
    val password = remember { mutableStateOf(userLiveData.value.password ) }
    val Repassword = remember {  mutableStateOf("") }
    Log.d("UserProfile", "UserLiveDatadsadsadsa: ${userLiveData.value.email}")

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally

    )    {

        ConstraintLayout() {
            val(topImg,profile)=createRefs()
            Image(
                painterResource(id = R.drawable.top_background),null,
                Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            Image(
                painterResource(id = R.drawable.profile),null,
                Modifier
                    .constrainAs(profile){
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }

        ProfileDetailTextFields(userLiveData.value,firstName, emailValue, password, Repassword,)

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            ProfileButton(onClick = {  navController.popBackStack() }, text = "Quay lại trang chủ")
        }
    }
}

@Composable
fun ProfileDetailTextFields(
    userLiveData: ProfileDetail?,
    firstName: MutableState<String>,
    emailValue: MutableState<String>,
    password: MutableState<String>,
    Repassword: MutableState<String>
) {
    userLiveData?.let { user ->
        firstName.value = user.username
        emailValue.value = user.email
        password.value = user.password
        Repassword.value = user.password
    }
    TextField(
        value = firstName.value,
        onValueChange = { newValue -> firstName.value = newValue },
        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "name") },

    )

    TextField(
        value = emailValue.value,
        onValueChange = { newValue -> emailValue.value = newValue },
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "email") },

    )

    TextField(
        value = password.value,
        onValueChange = { newValue -> password.value = newValue },
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Password, contentDescription = "password") },

        visualTransformation = PasswordVisualTransformation(),

        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        label = { Text("Password") }
    )


}

@Composable
fun ProfileButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#3399FF"))),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
