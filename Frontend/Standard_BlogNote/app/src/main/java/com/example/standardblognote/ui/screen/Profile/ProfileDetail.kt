
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.ProfileDetail
import com.example.standardblognote.ui.Components.Navbar

@Composable
fun ProfileDetail(navController: NavHostController, viewModel: HomeViewModel) {
    val email by viewModel.emailId.observeAsState()
    val profileViewModel: ProfileViewModel = viewModel()

    // Fetch user profile
    profileViewModel.fetchUser("string")

    val userLiveData = profileViewModel.userLiveData

    val firstName = remember { mutableStateOf(userLiveData.value.username ) }
    val emailValue = remember { mutableStateOf(userLiveData.value.email ) }
    val phone = remember { mutableStateOf(userLiveData.value.username ) }
    val address = remember { mutableStateOf(userLiveData.value.username ?: "") }
    Log.d("UserProfile", "UserLiveDatadsadsadsa: ${userLiveData.value}")

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Navbar(email)
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

        ProfileDetailTextFields(userLiveData.value,firstName, emailValue, phone, address,)

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            ProfileButton(onClick = { /*TODO*/ }, text = "Luu lại")
            ProfileButton(onClick = {  navController.popBackStack() }, text = "Quay lại trang chủ")
        }
    }
}

@Composable
fun ProfileDetailTextFields(
    userLiveData: ProfileDetail?,
    firstName: MutableState<String>,
    emailValue: MutableState<String>,
    phone: MutableState<String>,
    address: MutableState<String>
) {
    userLiveData?.let { user ->
        firstName.value = user.username
        emailValue.value = user.email
        phone.value = user.username
        address.value = user.username
    }
    TextField(
        value = firstName.value,
        onValueChange = { newValue -> firstName.value = newValue },
        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "name") },
        trailingIcon = {
            IconButton(onClick = { firstName.value = "" }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    )

    TextField(
        value = emailValue.value,
        onValueChange = { newValue -> emailValue.value = newValue },
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "email") },
        trailingIcon = {
            IconButton(onClick = { emailValue.value = "" }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    )

    TextField(
        value = phone.value,
        onValueChange = { newValue -> phone.value = newValue },
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "phone") },
        trailingIcon = {
            IconButton(onClick = { phone.value = "" }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    )

    TextField(
        value = address.value,
        onValueChange = { newValue -> address.value = newValue },
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "address") },
        trailingIcon = {
            IconButton(onClick = { address.value = "" }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
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
